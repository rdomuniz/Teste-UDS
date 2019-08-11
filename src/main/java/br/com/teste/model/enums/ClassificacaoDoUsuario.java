package br.com.teste.model.enums;

public enum ClassificacaoDoUsuario {
	
	SUPERVISOR("Supervisor"),
	GERENTE("Gerente"),
	OPERADOR("Operador");

	private final String descricao;

	private ClassificacaoDoUsuario(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}