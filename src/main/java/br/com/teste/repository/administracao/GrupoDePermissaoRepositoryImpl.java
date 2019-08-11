package br.com.teste.repository.administracao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;
import br.com.teste.repository.GeralRepositoryCustom;

@Repository
public class GrupoDePermissaoRepositoryImpl extends GeralRepositoryCustom implements GrupoDePermissaoRepositoryCustom {

	@Override
	public List<GrupoDePermissao> findByFilter(GrupoDePermissaoFilter filtro) {
		CriteriaBuilder builder = getSession().getCriteriaBuilder();
		CriteriaQuery<GrupoDePermissao> criteria = builder.createQuery(GrupoDePermissao.class);
		Root<GrupoDePermissao> from = criteria.from(GrupoDePermissao.class);
		ArrayList<Predicate> predicates = new ArrayList<>();
		if(StringUtils.isNotBlank(filtro.getDescricao()))
			predicates.add(builder.like(builder.lower(from.get("descricao")), "%" + filtro.getDescricao().toLowerCase() + "%"));
		criteria.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
		criteria.orderBy(builder.asc(from.get("descricao")));
		return getSession().createQuery(criteria).list();
	}

}
