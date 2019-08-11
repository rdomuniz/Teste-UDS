package br.com.teste.model.enums;

import java.math.BigDecimal;

public enum Tamanhos {
	
	PEQUENA(new Detalhe(new BigDecimal("20.00"), new Integer(15))),
	MEDIA(new Detalhe(new BigDecimal("30.00"), new Integer(20))),
	GRANDE(new Detalhe(new BigDecimal("40.00"), new Integer(25)));

	private final Detalhe detalhe;
	
	private Tamanhos(Detalhe detalhe) {
		this.detalhe = detalhe;
	}

	public Detalhe getDetalhe() {
		return detalhe;
	}

}