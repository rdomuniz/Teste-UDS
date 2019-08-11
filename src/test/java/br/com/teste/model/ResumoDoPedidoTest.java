package br.com.teste.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;

public class ResumoDoPedidoTest {

	@Test
	public void deveriaGerarResumoPorUmPedido() {
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
		verifica(resumo, new ResumoDoPedido(PedidoBuilder.get1()));
	}
	
	public static void verifica(ResumoDoPedido resumoEsperado, ResumoDoPedido resumo) {
		assertNotNull(resumo);
		assertEquals(resumoEsperado.getId(), resumo.getId());
		assertEquals(resumoEsperado.getTamanho(), resumo.getTamanho());
		assertEquals(resumoEsperado.getSabor(), resumo.getSabor());
		assertEquals(resumoEsperado.getPersonalizacoes().size(), resumo.getPersonalizacoes().size());
		for(ResumoDaPersonalizacaoDoPedido resumoDaPersonalizacaoDoPedido : resumoEsperado.getPersonalizacoes())
			assertTrue(verificaAcesso(resumo, resumoDaPersonalizacaoDoPedido));
	}
	
	private static boolean verificaAcesso(ResumoDoPedido resumo, ResumoDaPersonalizacaoDoPedido resumoDaPersonalizacaoDoPedido) {
		for(ResumoDaPersonalizacaoDoPedido resumoDaPersonalizacaoDoPedidoCorrente : resumo.getPersonalizacoes()) {
			if(resumoDaPersonalizacaoDoPedidoCorrente.getPersonalizacao() == resumoDaPersonalizacaoDoPedido.getPersonalizacao()) {
				assertEquals(resumoDaPersonalizacaoDoPedidoCorrente.getValor(), resumoDaPersonalizacaoDoPedido.getValor());
				return true;
			}
		}
		return false;
	}

}
