package br.com.teste.model;

import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;

public class PedidoBuilder {

	public static Pedido get1() {
		Pedido pedido  = new Pedido();
		pedido.setId(Long.valueOf(1));
		pedido.setTamanho(Tamanhos.GRANDE);
		pedido.setSabor(Sabores.CALABRESA);
		pedido.getPersonalizacoes().add(Personalizacoes.BORDA_RECHEADA);
		return pedido;
	}

}
