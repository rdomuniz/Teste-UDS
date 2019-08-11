package br.com.teste.service.administracao;

import java.util.List;

import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;
import br.com.teste.service.GeralService;

public interface GrupoDePermissaoService extends GeralService {

	public List<GrupoDePermissao> listar(GrupoDePermissaoFilter filtro);
	public GrupoDePermissao buscar(Long id);
	public GrupoDePermissao criar(GrupoDePermissao grupoDePermissao);
	public GrupoDePermissao atualizar(GrupoDePermissao grupoDePermissao);
	public void excluir(Long id);
	
}
