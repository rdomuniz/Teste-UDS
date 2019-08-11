package br.com.teste.model.administracao;

import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;

public class GrupoDePermissaoBuilder {

	public static GrupoDePermissao get1() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		grupoDePermissao.setDescricao("grupo de permissão 1");
		grupoDePermissao.setAtivo(true);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.USUARIO);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_INCLUIR);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_EDITAR);
		return grupoDePermissao;
	}
	
	public static GrupoDePermissao get2() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(2));
		grupoDePermissao.setDescricao("grupo de permissão 2");
		grupoDePermissao.setAtivo(true);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.USUARIO);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.GRUPO_PERMISSAO);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_INCLUIR);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.GRUPO_PERMISSAO_INCLUIR);
		return grupoDePermissao;
	}
	
}
