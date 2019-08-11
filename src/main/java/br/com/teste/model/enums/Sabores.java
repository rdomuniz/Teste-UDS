package br.com.teste.model.enums;

import java.math.BigDecimal;

public enum Sabores {
	
	CALABRESA(new Detalhe(new BigDecimal("0"), new Integer(0))),
	MARGUERITA(new Detalhe(new BigDecimal("0"), new Integer(0))),
	PORTUGUESA(new Detalhe(new BigDecimal("0"), new Integer(5)));

	private final Detalhe detalhe;
	
	private Sabores(Detalhe detalhe) {
		this.detalhe = detalhe;
	}

	public Detalhe getDetalhe() {
		return detalhe;
	}
}