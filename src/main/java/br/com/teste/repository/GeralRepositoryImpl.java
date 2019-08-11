package br.com.teste.repository;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import br.com.teste.exception.TesteException;
import br.com.teste.model.CacheDeReservaDeCodigo;

public class GeralRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements GeralRepository<T, ID> {

	private EntityManager entityManager;
	private JpaEntityInformation<T, ?> entityInformation;

	public GeralRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager = entityManager;
	}
	
	public GeralRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityInformation = entityInformation;
		this.entityManager = entityManager;
	}
	
	@Override
	public Long carregaPrimeiroCodigoDisponivel() {
		if("java.lang.Long".compareTo(entityInformation.getIdType().getName()) != 0)
			throw new TesteException("Metodo usado só para entidades com id em long!");
		CacheDeReservaDeCodigo cache = CacheDeReservaDeCodigo.getInstance();
		String table = entityInformation.getJavaType().getAnnotation(Entity.class).name();
		String coluna = "";
		try {coluna = entityInformation.getJavaType().getDeclaredField(entityInformation.getIdAttribute().getName()).getAnnotation(Column.class).name();} catch (NoSuchFieldException | SecurityException e) {}
		String selectPrimario = " SELECT -C- AS CODIGO FROM -T- ";
		String selectSecuntario = " SELECT -C- - 1 AS CODIGO FROM -T- ";
		List<Object> codigos = null;
		if(cache != null)
			codigos = cache.listar(entityInformation.getJavaType());
		StringBuilder builder = new StringBuilder();
		if(codigos.isEmpty()) {
			builder.append(" SELECT CASE ");
			builder.append("   WHEN (SELECT MIN(P1.-C-) FROM -T- P1) > 1 THEN 1 ");
			builder.append("   WHEN (SELECT MIN(P1.-C-) FROM -T- P1) IS NULL THEN 1 ");
			builder.append("   ELSE ( ");
			builder.append("        SELECT MIN(P1.CODIGO + 1) ");
			builder.append("        FROM (-SELECT_PRIMARIO-) P1 ");
			builder.append("        LEFT OUTER JOIN (-SELECT_SECUNDARIO-) P2 on P1.CODIGO = P2.CODIGO ");
			builder.append("        WHERE P2.CODIGO IS NULL ");
			builder.append("   ) ");
			builder.append(" END AS CODIGO ");
			builder.append(" FROM PROPRIO ");
		} else {
			builder.append(" SELECT MIN(P1.CODIGO + 1) ");
			builder.append(" FROM (-SELECT_PRIMARIO-) P1 ");
			builder.append(" LEFT OUTER JOIN (-SELECT_SECUNDARIO-) P2 on P1.CODIGO = P2.CODIGO ");
			builder.append(" WHERE P2.CODIGO IS NULL ");
		}
		String sql = builder.toString();
		if(cache != null && !codigos.isEmpty()) {
			for (Object codigo : codigos) {
				selectPrimario = selectPrimario + " UNION SELECT " + codigo + " AS CODIGO FROM PROPRIO ";
				selectSecuntario = selectSecuntario + " UNION SELECT " + codigo + " - 1 AS CODIGO FROM PROPRIO ";
			}
		}
		sql = sql.replace("-SELECT_PRIMARIO-", selectPrimario);
		sql = sql.replace("-SELECT_SECUNDARIO-", selectSecuntario);
		sql = sql.replace("-C-", coluna).replace("-T-", table);
		Query query = entityManager.createNativeQuery(sql);
		Long retorno = ((Number) query.getSingleResult()).longValue();
		cache.reserva(entityInformation.getJavaType(), retorno);
		return retorno;
	}

	@Override
	public Session getSession() {
		return (Session) entityManager.getDelegate();
	}
	
	
	
}
