package br.com.teste.repository.administracao;

import java.util.List;

import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;

public interface GrupoDePermissaoRepositoryCustom {

	public List<GrupoDePermissao> findByFilter(GrupoDePermissaoFilter filtro);
	
}
