package br.com.teste.model;

import java.math.BigDecimal;

import br.com.teste.model.enums.Personalizacoes;
import lombok.Data;

@Data
public class ResumoDaPersonalizacaoDoPedido {

	private Personalizacoes personalizacao;
	private BigDecimal valor;

	public ResumoDaPersonalizacaoDoPedido() {}
	public ResumoDaPersonalizacaoDoPedido(Personalizacoes personalizacao) {
		this.personalizacao = personalizacao;
		this.valor = personalizacao.getDetalhe().getAdcionalDeValor();
	}
	
}
