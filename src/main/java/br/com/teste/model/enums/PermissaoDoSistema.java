package br.com.teste.model.enums;

public enum PermissaoDoSistema {

	DEFAULT (AcessoDoSistema.DEFAULT, "default", true);
	
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