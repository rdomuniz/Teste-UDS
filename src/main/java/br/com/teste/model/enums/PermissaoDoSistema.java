package br.com.teste.model.enums;

public enum PermissaoDoSistema {

	ATRIBUICAO_PERMISSAO_CONFIGURAR (AcessoDoSistema.ATRIBUICAO_PERMISSAO, DescricaoDePermicao.CONFIGURAR, true),
	GRUPO_PERMISSAO_INCLUIR (AcessoDoSistema.GRUPO_PERMISSAO, DescricaoDePermicao.INCLUIR, true),
	GRUPO_PERMISSAO_EDITAR (AcessoDoSistema.GRUPO_PERMISSAO, DescricaoDePermicao.EDITAR, true),
	GRUPO_PERMISSAO_EXCLUIR (AcessoDoSistema.GRUPO_PERMISSAO, DescricaoDePermicao.EXCLUIR, true),
	USUARIO_INCLUIR (AcessoDoSistema.USUARIO, DescricaoDePermicao.INCLUIR, true),
	USUARIO_EDITAR (AcessoDoSistema.USUARIO, DescricaoDePermicao.EDITAR, true),
	USUARIO_EXCLUIR (AcessoDoSistema.USUARIO, DescricaoDePermicao.EXCLUIR, true),
	USUARIO_SENHA (AcessoDoSistema.USUARIO, "Resertar Senha", true);
	
	private AcessoDoSistema acessoDoSistema;
	private String descricao;
	private Boolean ativo;
	
	private PermissaoDoSistema(AcessoDoSistema acessoDoSistema, String descricao, Boolean ativo) {
		this.acessoDoSistema = acessoDoSistema;
		this.descricao = descricao;
		this.ativo = ativo;
	}
	
	public Boolean isAtivo() {
		return ativo;
	}
	
	public AcessoDoSistema getAcessoDoSistema() {
		return acessoDoSistema;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}