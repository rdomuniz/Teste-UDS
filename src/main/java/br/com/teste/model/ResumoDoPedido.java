package br.com.teste.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import br.com.teste.exception.ResolvedException;
import br.com.teste.model.enums.Detalhe;
import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;
import lombok.Data;

@Data
public class ResumoDoPedido {

	private Long id;
	private Tamanhos tamanho;
	private Sabores sabor;
	private List<ResumoDaPersonalizacaoDoPedido> personalizacoes = new ArrayList<>();
	private BigDecimal valorTotal = BigDecimal.ZERO;
	private Integer tempoDePreparo = Integer.valueOf(0);

	public ResumoDoPedido() {}
	public ResumoDoPedido(Pedido pedido) {
		this.id = pedido.getId();
		this.tamanho = pedido.getTamanho();
		this.sabor = pedido.getSabor();
		adciona(tamanho.getDetalhe());
		adciona(sabor.getDetalhe());
		for (Personalizacoes personalizacao : pedido.getPersonalizacoes()) {
			if(personalizacoes.stream().anyMatch(p -> p.getPersonalizacao() == personalizacao))
				throw new ResolvedException("Personalização '" + personalizacao + "' repetida!");
			personalizacoes.add(new ResumoDaPersonalizacaoDoPedido(personalizacao));
			adciona(personalizacao.getDetalhe());
		}
		this.valorTotal.setScale(2, RoundingMode.HALF_DOWN);
	}

	private void adciona(Detalhe detalhe) {
		this.valorTotal = this.valorTotal.add(detalhe.getAdcionalDeValor());
		this.tempoDePreparo = this.tempoDePreparo + detalhe.getAdcionalDeTempo();
	}
	
}
