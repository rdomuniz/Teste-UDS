package br.com.teste.model.enums;

import java.math.BigDecimal;

public enum Personalizacoes {
	
	EXTRA_BACON(new Detalhe(new BigDecimal("3.00"), new Integer(0))),
	SEM_CEBOLA(new Detalhe(new BigDecimal("0.00"), new Integer(0))),
	BORDA_RECHEADA(new Detalhe(new BigDecimal("5.00"), new Integer(5)));

	private final Detalhe detalhe;
	
	private Personalizacoes(Detalhe detalhe) {
		this.detalhe = detalhe;
	}

	public Detalhe getDetalhe() {
		return detalhe;
	}

}