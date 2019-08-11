package br.com.teste.repository.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseSetup;

import br.com.teste.GeralTests;
import br.com.teste.model.GeralBuilder;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.administracao.UsuarioTest;
import br.com.teste.model.enums.ClassificacaoDoUsuario;
import br.com.teste.model.enums.StatusDoRegistro;
import br.com.teste.model.filter.administracao.UsuarioFilter;

@DatabaseSetup("UsuarioRepositoryTest.xml")
public class UsuarioRepositoryTest extends GeralTests {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@After
	public void setDown() {
		usuarioRepository.getSession().getTransaction().rollback();
	}
	
	@Test
	public void deveriaListarSemExcluidos() {
		UsuarioFilter filtro = new UsuarioFilter();
		filtro.setSomenteAtivo(false);
		List<Usuario> resultado = usuarioRepository.findByFilter(filtro);
		assertEquals(2, resultado.size());
		UsuarioTest.verifica(usuarioBase2(), resultado.get(0));
		UsuarioTest.verifica(usuarioBase1(), resultado.get(1));
	}

	@Test
	public void deveriaListarComFiltro() {
		UsuarioFilter filtro = new UsuarioFilter();
		filtro.setId(Long.valueOf(-2));
		filtro.setNome("usuario 2");
		filtro.setLogin("loginTeste2");
		List<Usuario> resultado = usuarioRepository.findByFilter(filtro);
		assertEquals(1, resultado.size());
		UsuarioTest.verifica(usuarioBase1(), resultado.get(0));
	}

	@Test
	public void deveriaBuscar() {
		UsuarioTest.verifica(usuarioBase1(), usuarioRepository.findById(Long.valueOf(-2)).get());
	}

	@Test
	public void deveriaBuscarPeloLogin() {
		UsuarioTest.verifica(usuarioBase1(), usuarioRepository.findByLogin("loginTeste2").get());
	}
	
	@Test
	public void deveriaIncluir() {
		Usuario usuarioEsperado = usuarioInclusao();
		usuarioEsperado.setId(usuarioRepository.save(usuarioInclusao()).getId());
		UsuarioTest.verifica(usuarioEsperado, usuarioRepository.findById(usuarioEsperado.getId()).get());
	}

	@Test
	public void deveriaAtualizar() {
		Usuario usuario = usuarioBase1();
		usuario.setNome("teste atualização");
		usuarioRepository.save(usuario);
		Usuario usuarioEsperado = usuarioBase1();
		usuarioEsperado.setNome("teste atualização");
		UsuarioTest.verifica(usuarioEsperado, usuarioRepository.findById(Long.valueOf(-2)).get());
	}
	
	@Test
	public void deveriaExcluir() {
		usuarioRepository.deleteById(Long.valueOf(-2));
		assertFalse(usuarioRepository.findById(Long.valueOf(-2)).isPresent());
	}
	
	private Usuario usuarioBase1() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(-2));
		usuario.setNome("b usuario 2");
		usuario.setLogin("loginTeste2");
		usuario.setSenha("senhaTeste2");
		usuario.setClassificacao(ClassificacaoDoUsuario.GERENTE);
		usuario.setAdministrador(true);
		usuario.setDataDeAutenticacao(GeralBuilder.getData("02/01/2019"));
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		usuario.getGrupos().add(grupoDePermissao);
		usuario.setStatusDoRegistro(StatusDoRegistro.ATIVO);
		return usuario;
	}
	
	private Usuario usuarioBase2() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(-3));
		usuario.setNome("a usuario 3");
		usuario.setLogin("loginTeste3");
		usuario.setSenha("senhaTeste3");
		usuario.setClassificacao(ClassificacaoDoUsuario.OPERADOR);
		usuario.setAdministrador(false);
		usuario.setDataDeAutenticacao(GeralBuilder.getData("03/01/2019"));
		usuario.setStatusDoRegistro(StatusDoRegistro.INATIVO);
		return usuario;
	}
	
	private Usuario usuarioInclusao() {
		Usuario usuario = new Usuario();
		usuario.setNome("teste inclusão");
		usuario.setLogin("loginInclusao");
		usuario.setSenha("senhaInclusao");
		usuario.setClassificacao(ClassificacaoDoUsuario.GERENTE);
		usuario.setAdministrador(true);
		usuario.setDataDeAutenticacao(GeralBuilder.getData("01/01/2019"));
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		usuario.getGrupos().add(grupoDePermissao);
		usuario.setStatusDoRegistro(StatusDoRegistro.ATIVO);
		return usuario;
	}
	
}