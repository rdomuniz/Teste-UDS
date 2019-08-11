package br.com.teste.model;

import java.math.BigDecimal;

import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;

public class ResumoDoPedidoBuilder {

	public static ResumoDoPedido get1() {
		ResumoDoPedido resumo = new ResumoDoPedido();
		resumo.setId(Long.valueOf(1));
		resumo.setTamanho(Tamanhos.GRANDE);
		resumo.setSabor(Sabores.CALABRESA);
		ResumoDaPersonalizacaoDoPedido resumoDaPersonalizacaoDoPedido = new ResumoDaPersonalizacaoDoPedido();
		resumoDaPersonalizacaoDoPedido.setPersonalizacao(Personalizacoes.BORDA_RECHEADA);
		resumoDaPersonalizacaoDoPedido.setValor(new BigDecimal("5.00"));
		resumo.getPersonalizacoes().add(resumoDaPersonalizacaoDoPedido);
		resumo.setValorTotal(new BigDecimal("5.00"));
		resumo.setTempoDePreparo(Integer.valueOf(1));
		return resumo;
	}

}
