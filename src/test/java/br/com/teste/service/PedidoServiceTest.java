package br.com.teste.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.GeralTests;
import br.com.teste.exception.ResolvedException;
import br.com.teste.model.Pedido;
import br.com.teste.model.PedidoBuilder;
import br.com.teste.model.PedidoTest;
import br.com.teste.model.ResumoDoPedido;
import br.com.teste.model.ResumoDoPedidoBuilder;
import br.com.teste.model.ResumoDoPedidoTest;
import br.com.teste.repository.PedidoRepository;

public class PedidoServiceTest extends GeralTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private PedidoService pedidoService;
	@Mock
	private PedidoRepository pedidoRepository;
	
	@Before
	public void setUp() {
		((PedidoServiceImpl)pedidoService).setPedidoRepository(pedidoRepository);
	}
	
	@Test
	public void deveriaBuscar() {
		Long id = Long.valueOf(1);
		Pedido pedido = PedidoBuilder.get1();
		when(pedidoRepository.findById(id)).thenReturn(Optional.of(pedido));
		ResumoDoPedido retorno = pedidoService.buscar(id);
		verify(pedidoRepository, times(1)).findById(id);
		ResumoDoPedidoTest.verifica(ResumoDoPedidoBuilder.get1(), retorno);
	}
	
	@Test
	public void deveriaDarErroAoBuscarENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Pedido não existe!");
		pedidoService.buscar(Long.valueOf(1));
	}
	
	@Test
	public void deveriaCriar() {
		Pedido pedido = PedidoBuilder.get1();
		pedido.setId(null);
		when(pedidoRepository.save(pedido)).thenReturn(PedidoBuilder.get1());
		ResumoDoPedido retorno = pedidoService.criar(pedido);
		ArgumentCaptor<Pedido> argument = ArgumentCaptor.forClass(Pedido.class);
		verify(pedidoRepository, times(1)).save(argument.capture());
		Pedido pedidoEsperado = PedidoBuilder.get1();
		pedidoEsperado.setId(null);
		PedidoTest.verifica(pedidoEsperado, argument.getValue());
		ResumoDoPedidoTest.verifica(ResumoDoPedidoBuilder.get1(), retorno);
	}
	
	@Test
	public void deveriaMontarResumoPorUmPedido() {
		ResumoDoPedidoTest.verifica(ResumoDoPedidoBuilder.get1(), pedidoService.montaResumo(PedidoBuilder.get1()));
	}

}
