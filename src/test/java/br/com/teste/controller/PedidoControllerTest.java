package br.com.teste.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import br.com.teste.ControllerTests;
import br.com.teste.model.Pedido;
import br.com.teste.model.PedidoBuilder;
import br.com.teste.model.PedidoTest;
import br.com.teste.model.ResumoDoPedidoBuilder;
import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;

public class PedidoControllerTest extends ControllerTests {

	@Test
	@WithMockUser(roles = "PEDIDO")
	public void deveriaBuscar() throws Exception {
		when(pedidoService.buscar(anyLong())).thenReturn(ResumoDoPedidoBuilder.get1());
		mvc.perform(get("/pedidos/1")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"tamanho\":\"GRANDE\",\"sabor\":\"CALABRESA\",\"personalizacoes\":[{\"personalizacao\":\"BORDA_RECHEADA\",\"valor\":5.00}],\"valorTotal\":5.00,\"tempoDePreparo\":1}"));
		ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
		verify(pedidoService, times(1)).buscar(argument.capture());
		assertEquals(Long.valueOf(1), argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "PEDIDO_INCLUIR")
	public void deveriaCriar() throws Exception {
		when(pedidoService.criar(any(Pedido.class))).thenReturn(ResumoDoPedidoBuilder.get1());
		mvc.perform(post("/pedidos")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"tamanho\":\"GRANDE\",\"sabor\":\"CALABRESA\",\"personalizacoes\":[\"BORDA_RECHEADA\"]}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"tamanho\":\"GRANDE\",\"sabor\":\"CALABRESA\",\"personalizacoes\":[{\"personalizacao\":\"BORDA_RECHEADA\",\"valor\":5.00}],\"valorTotal\":5.00,\"tempoDePreparo\":1}"));
		ArgumentCaptor<Pedido> argument = ArgumentCaptor.forClass(Pedido.class);
		verify(pedidoService, times(1)).criar(argument.capture());
		PedidoTest.verifica(pedido(), argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "PEDIDO")
	public void deveriaMontarResumo() throws Exception {
		when(pedidoService.montaResumo(any(Pedido.class))).thenReturn(ResumoDoPedidoBuilder.get1());
		mvc.perform(put("/pedidos/montarResumo")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"id\":1,\"tamanho\":\"GRANDE\",\"sabor\":\"CALABRESA\",\"personalizacoes\":[\"BORDA_RECHEADA\"]}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"tamanho\":\"GRANDE\",\"sabor\":\"CALABRESA\",\"personalizacoes\":[{\"personalizacao\":\"BORDA_RECHEADA\",\"valor\":5.00}],\"valorTotal\":5.00,\"tempoDePreparo\":1}"));
		ArgumentCaptor<Pedido> argument = ArgumentCaptor.forClass(Pedido.class);
		verify(pedidoService, times(1)).montaResumo(argument.capture());
		PedidoTest.verifica(PedidoBuilder.get1(), argument.getValue());
	}
	
	public static Pedido pedido() {
		Pedido pedido  = new Pedido();
		pedido.setTamanho(Tamanhos.GRANDE);
		pedido.setSabor(Sabores.CALABRESA);
		pedido.getPersonalizacoes().add(Personalizacoes.BORDA_RECHEADA);
		return pedido;
	}

}
