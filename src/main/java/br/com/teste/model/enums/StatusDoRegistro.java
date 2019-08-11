package br.com.teste.model.enums;

public enum StatusDoRegistro {
	
	ATIVO("Ativo"),
	INATIVO("Inativo"),
	EXCLUIDO("Excluído");

	private final String descricao;

	private StatusDoRegistro(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}