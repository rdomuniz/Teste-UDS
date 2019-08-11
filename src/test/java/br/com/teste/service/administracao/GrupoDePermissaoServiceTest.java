package br.com.teste.service.administracao;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
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
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.administracao.GrupoDePermissaoBuilder;
import br.com.teste.model.administracao.GrupoDePermissaoTest;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;
import br.com.teste.repository.administracao.GrupoDePermissaoRepository;

public class GrupoDePermissaoServiceTest extends GeralTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private GrupoDePermissaoService grupoDePermissaoService;
	@Mock
	private GrupoDePermissaoRepository grupoDePermissaoRepository;
	
	@Before
	public void setUp() {
		((GrupoDePermissaoServiceImpl)grupoDePermissaoService).setGrupoDePermissaoRepository(grupoDePermissaoRepository);
	}
	
	@Test
	public void deveriaListar() {
		GrupoDePermissaoFilter filtro = new GrupoDePermissaoFilter();
		List<GrupoDePermissao> retorno = new ArrayList<>();
		retorno.add(GrupoDePermissaoBuilder.get1());
		retorno.add(GrupoDePermissaoBuilder.get2());
		when(grupoDePermissaoRepository.findByFilter(filtro)).thenReturn(retorno);
		retorno = grupoDePermissaoService.listar(filtro);
		verify(grupoDePermissaoRepository, times(1)).findByFilter(filtro);
		assertEquals(2, retorno.size());
		GrupoDePermissaoTest.verifica(GrupoDePermissaoBuilder.get1(), retorno.get(0));
		GrupoDePermissaoTest.verifica(GrupoDePermissaoBuilder.get2(), retorno.get(1));
	}
	
	@Test
	public void deveriaBuscar() {
		Long id = Long.valueOf(1);
		GrupoDePermissao grupoDePermissao = GrupoDePermissaoBuilder.get1();
		when(grupoDePermissaoRepository.findById(id)).thenReturn(Optional.of(grupoDePermissao));
		GrupoDePermissao retorno = grupoDePermissaoService.buscar(id);
		verify(grupoDePermissaoRepository, times(1)).findById(id);
		GrupoDePermissaoTest.verifica(GrupoDePermissaoBuilder.get1(), retorno);
	}
	
	@Test
	public void deveriaDarErroAoBuscarENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Grupo de permissão não existe!");
		grupoDePermissaoService.buscar(Long.valueOf(1));
	}
	
	@Test
	public void deveriaCriar() {
		GrupoDePermissao retorno;
		GrupoDePermissao grupoDePermissao = GrupoDePermissaoBuilder.get1();
		grupoDePermissao.setId(null);
		retorno = GrupoDePermissaoBuilder.get1();
		when(grupoDePermissaoRepository.save(grupoDePermissao)).thenReturn(GrupoDePermissaoBuilder.get1());
		retorno = grupoDePermissaoService.criar(grupoDePermissao);
		ArgumentCaptor<GrupoDePermissao> argument = ArgumentCaptor.forClass(GrupoDePermissao.class);
		verify(grupoDePermissaoRepository, times(1)).save(argument.capture());
		GrupoDePermissao grupoDePermissaoEsperado = GrupoDePermissaoBuilder.get1();
		grupoDePermissaoEsperado.setId(null);
		GrupoDePermissaoTest.verifica(grupoDePermissaoEsperado, argument.getValue());
		GrupoDePermissaoTest.verifica(GrupoDePermissaoBuilder.get1(), retorno);
	}
	
	@Test
	public void deveriaAtualizar() {
		GrupoDePermissao retorno;
		GrupoDePermissao grupoDePermissaoEsperado;
		GrupoDePermissao grupoDePermissao = GrupoDePermissaoBuilder.get1();
		grupoDePermissao.setDescricao("teste atualização");
		when(grupoDePermissaoRepository.existsById(grupoDePermissao.getId())).thenReturn(true);
		retorno = GrupoDePermissaoBuilder.get1();
		retorno.setDescricao("teste atualização");
		when(grupoDePermissaoRepository.save(any(GrupoDePermissao.class))).thenReturn(retorno);
		retorno = grupoDePermissaoService.atualizar(grupoDePermissao);
		verify(grupoDePermissaoRepository, times(1)).existsById(grupoDePermissao.getId());
		ArgumentCaptor<GrupoDePermissao> argument = ArgumentCaptor.forClass(GrupoDePermissao.class);
		verify(grupoDePermissaoRepository, times(1)).save(argument.capture());
		grupoDePermissaoEsperado = GrupoDePermissaoBuilder.get1();
		grupoDePermissaoEsperado.setDescricao("teste atualização");
		GrupoDePermissaoTest.verifica(grupoDePermissaoEsperado, argument.getValue());
		grupoDePermissaoEsperado = GrupoDePermissaoBuilder.get1();
		grupoDePermissaoEsperado.setDescricao("teste atualização");
		GrupoDePermissaoTest.verifica(grupoDePermissaoEsperado, retorno);
	}
	
	@Test
	public void deveriaDarErroAoAtualizarENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Grupo de permissão não existe!");
		grupoDePermissaoService.atualizar(GrupoDePermissaoBuilder.get1());
	}
	
	@Test
	public void deveriaExcluir() {
		Long id = Long.valueOf(1);
		when(grupoDePermissaoRepository.existsById(id)).thenReturn(true);
		grupoDePermissaoService.excluir(id);
		verify(grupoDePermissaoRepository, times(1)).deleteById(id);
	}
	
	@Test
	public void deveriaDarErroAoExcluirENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Grupo de permissão não existe!");
		Long id = Long.valueOf(1);
		when(grupoDePermissaoRepository.existsById(id)).thenReturn(false);
		grupoDePermissaoService.excluir(id);
	}

}
