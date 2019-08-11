package br.com.teste.repository;

import static org.junit.Assert.assertFalse;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.teste.GeralTests;
import br.com.teste.model.Pedido;
import br.com.teste.model.PedidoTest;
import br.com.teste.model.enums.Personalizacoes;
import br.com.teste.model.enums.Sabores;
import br.com.teste.model.enums.Tamanhos;

@DatabaseSetup("PedidoRepositoryTest.xml")
public class PedidoRepositoryTest extends GeralTests {

	@Autowired
	private PedidoRepository pedidoRepository;

	@After
	public void setDown() {
		pedidoRepository.getSession().getTransaction().rollback();
	}
	
	@Test
	public void deveriaBuscar() {
		PedidoTest.verifica(pedidoBase1(), pedidoRepository.findById(Long.valueOf(-1)).get());
	}
	
	@Test
	public void deveriaIncluir() {
		Pedido pedidoEsperado = pedidoInclusao();
		pedidoEsperado.setId(pedidoRepository.save(pedidoEsperado).getId());
		PedidoTest.verifica(pedidoEsperado,pedidoRepository.findById(pedidoEsperado.getId()).get());
	}
	
	@Test
	public void deveriaAtualizar() {
		Pedido pedido = pedidoBase1();
		pedido.setTamanho(Tamanhos.MEDIA);
		pedido.getPersonalizacoes().add(Personalizacoes.EXTRA_BACON);
		pedidoRepository.save(pedido);
		Pedido pedidoEsperado = pedidoBase1();
		pedidoEsperado.setTamanho(Tamanhos.MEDIA);
		pedidoEsperado.getPersonalizacoes().add(Personalizacoes.EXTRA_BACON);
		PedidoTest.verifica(pedidoEsperado,pedidoRepository.findById(Long.valueOf(-1)).get());
	}
	
	@Test
	public void deveriaExcluir() {
		pedidoRepository.deleteById(Long.valueOf(-1));
		assertFalse(pedidoRepository.findById(Long.valueOf(-1)).isPresent());
	}

	private Pedido pedidoBase1() {
		Pedido pedido  = new Pedido();
		pedido.setId(Long.valueOf(-1));
		pedido.setTamanho(Tamanhos.GRANDE);
		pedido.setSabor(Sabores.CALABRESA);
		pedido.getPersonalizacoes().add(Personalizacoes.BORDA_RECHEADA);
		return pedido;
	}
	
	private br.com.teste.model.Pedido pedidoInclusao() {
		Pedido pedido  = new Pedido();
		pedido.setTamanho(Tamanhos.MEDIA);
		pedido.setSabor(Sabores.MARGUERITA);
		pedido.getPersonalizacoes().add(Personalizacoes.SEM_CEBOLA);
		pedido.getPersonalizacoes().add(Personalizacoes.BORDA_RECHEADA);
		return pedido;
	}
	
}
