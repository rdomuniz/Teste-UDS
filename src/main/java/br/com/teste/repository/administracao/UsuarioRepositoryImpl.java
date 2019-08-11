package br.com.teste.repository.administracao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.enums.StatusDoRegistro;
import br.com.teste.model.filter.administracao.UsuarioFilter;
import br.com.teste.repository.GeralRepositoryCustom;

@Repository
public class UsuarioRepositoryImpl extends GeralRepositoryCustom implements UsuarioRepositoryCustom {

	@Override
	public Optional<Usuario> findByLogin(String login) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> root = criteria.from(Usuario.class);
		criteria.select(root);
		criteria.where(
			builder.and(
				builder.notEqual(root.get("statusDoRegistro"), StatusDoRegistro.EXCLUIDO),
				builder.equal(root.get("login"), login)
			)
		);
		
		return Optional.ofNullable(getSession().createQuery(criteria).uniqueResult());
	}

	@Override
	public List<Usuario> findByFilter(UsuarioFilter filtro) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<Usuario> criteria = builder.createQuery(Usuario.class);
		Root<Usuario> from = criteria.from(Usuario.class);
		List<Predicate> predicates = new ArrayList<Predicate>();
		if(filtro.getSomenteAtivo())
			predicates.add(builder.equal(from.get("statusDoRegistro"), StatusDoRegistro.ATIVO));
		else
			predicates.add(builder.notEqual(from.get("statusDoRegistro"), StatusDoRegistro.EXCLUIDO));
		if(filtro.getId() != null)
			predicates.add(builder.equal(from.get("id"), filtro.getId()));
		if(StringUtils.isNotBlank(filtro.getNome()))
			predicates.add(builder.like(builder.lower(from.get("nome")), "%" + filtro.getNome().toLowerCase() + "%"));
		if(StringUtils.isNotBlank(filtro.getLogin()))
			predicates.add(builder.like(builder.lower(from.get("login")), "%" + filtro.getLogin().toLowerCase() + "%"));
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		criteria.orderBy(builder.asc(from.get("nome")));
		return getSession().createQuery(criteria).list();
	}
	
}