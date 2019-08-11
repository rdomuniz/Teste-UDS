package br.com.teste.model.enums;

import java.math.BigDecimal;

public class Detalhe {

	private BigDecimal adcionalDeValor;
	private Integer adcionalDeTempo;
	
	public Detalhe(BigDecimal adcionalDeValor, Integer adcionalDeTempo) {
		this.adcionalDeValor = adcionalDeValor;
		this.adcionalDeTempo = adcionalDeTempo;
	}
	
	public BigDecimal getAdcionalDeValor() {
		return adcionalDeValor;
	}
	public Integer getAdcionalDeTempo() {
		return adcionalDeTempo;
	}
	
}
