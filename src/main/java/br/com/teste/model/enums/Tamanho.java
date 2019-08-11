package br.com.teste.model.enums;

public enum Tamanho {
	
	PEQUENA("Pequena"),
	MEDIA("Média"),
	GRANDE("Grande");

	private final String descricao;

	private Tamanho(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}