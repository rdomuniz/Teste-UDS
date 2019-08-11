package br.com.teste.repository.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.List;

import org.hamcrest.core.IsInstanceOf;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.teste.GeralTests;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.administracao.GrupoDePermissaoTest;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;

@DatabaseSetup("GrupoDePermissaoRepositoryTest.xml")
public class GrupoDePermissaoRepositoryTest extends GeralTests {

	@Autowired
	private GrupoDePermissaoRepository grupoDePermissaoRepository;
	
	@After
	public void setDown() {
		grupoDePermissaoRepository.getSession().getTransaction().rollback();
	}
	
	@Test
	public void deveriaCarregaPrimeiroCodigoDisponivel() {
		assertEquals(Long.valueOf(3), grupoDePermissaoRepository.carregaPrimeiroCodigoDisponivel());
	}
	
	@Test
	public void deveriaListar() {
		GrupoDePermissaoFilter filtro = new GrupoDePermissaoFilter();
		List<GrupoDePermissao> resultado = grupoDePermissaoRepository.findByFilter(filtro);
		assertEquals(2, resultado.size());
		GrupoDePermissaoTest.verifica(grupoDePermissaoBase2(), resultado.get(0));
		GrupoDePermissaoTest.verifica(grupoDePermissaoBase1(), resultado.get(1));
	}
	
	@Test
	public void deveriaListarComFiltro() {
		GrupoDePermissaoFilter filtro = new GrupoDePermissaoFilter();
		filtro.setDescricao("teste 1");
		List<GrupoDePermissao> resultado = grupoDePermissaoRepository.findByFilter(filtro);
		assertEquals(1, resultado.size());
		GrupoDePermissaoTest.verifica(grupoDePermissaoBase1(), resultado.get(0));
	}
	
	@Test
	public void deveriaBuscar() {
		GrupoDePermissaoTest.verifica(grupoDePermissaoBase2(), grupoDePermissaoRepository.findById(Long.valueOf(2)).get());
	}

	@Test
	public void deveriaIncluir() {
		GrupoDePermissao grupoDePermissaoEsperado = grupoDePermissaoInclusao();
		grupoDePermissaoEsperado.setId(grupoDePermissaoRepository.save(grupoDePermissaoEsperado).getId());
		GrupoDePermissaoTest.verifica(grupoDePermissaoEsperado,grupoDePermissaoRepository.findById(grupoDePermissaoEsperado.getId()).get());
	}
	
	@Test
	public void deveriaAtualizar() {
		GrupoDePermissao grupoDePermissao = grupoDePermissaoBase2();
		grupoDePermissao.setDescricao("teste atualização");
		grupoDePermissao.getAcessos().add(AcessoDoSistema.GRUPO_PERMISSAO);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.GRUPO_PERMISSAO_INCLUIR);
		grupoDePermissaoRepository.save(grupoDePermissao);
		GrupoDePermissao grupoDePermissaoEsperado = grupoDePermissaoBase2();
		grupoDePermissaoEsperado.setDescricao("teste atualização");
		grupoDePermissaoEsperado.getAcessos().add(AcessoDoSistema.GRUPO_PERMISSAO);
		grupoDePermissaoEsperado.getPermissoes().add(PermissaoDoSistema.GRUPO_PERMISSAO_INCLUIR);
		GrupoDePermissaoTest.verifica(grupoDePermissaoEsperado,grupoDePermissaoRepository.findById(Long.valueOf(2)).get());
	}
	
	@Test
	public void deveriaExcluir() {
		grupoDePermissaoRepository.deleteById(Long.valueOf(1));
		assertFalse(grupoDePermissaoRepository.findById(Long.valueOf(1)).isPresent());
	}
	
	@Test
	public void deveriaDarErroDeConstraintFKGrupoDoUsuarioAoExcluir() {
		try {
			grupoDePermissaoRepository.deleteById(Long.valueOf(2));
			entityManager.flush();
			fail();
		} catch (Exception e) {
			assertThat(e.getCause(),IsInstanceOf.instanceOf(ConstraintViolationException.class));
			assertEquals("grupo_permissao_fk",((ConstraintViolationException)e.getCause()).getConstraintName());
			assertEquals("grupo_usuario",((ConstraintViolationException)e.getCause()).getSQLException().getMessage().split("from")[1].split("\"")[1]);
		}
	}
	
	private GrupoDePermissao grupoDePermissaoBase1() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		grupoDePermissao.setDescricao("b teste 1");
		grupoDePermissao.setAtivo(false);
		return grupoDePermissao;
	}
	
	private GrupoDePermissao grupoDePermissaoBase2() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(2));
		grupoDePermissao.setDescricao("a teste 2");
		grupoDePermissao.setAtivo(true);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.USUARIO);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_INCLUIR);
		return grupoDePermissao;
	}
	
	private GrupoDePermissao grupoDePermissaoInclusao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setDescricao("teste inclusão");
		grupoDePermissao.setAtivo(true);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.USUARIO);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.GRUPO_PERMISSAO);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_INCLUIR);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.GRUPO_PERMISSAO_INCLUIR);
		return grupoDePermissao;
	}

}
