package br.com.teste.controller.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;

import br.com.teste.ControllerTests;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.administracao.UsuarioBuilder;
import br.com.teste.model.administracao.UsuarioTest;
import br.com.teste.model.enums.ClassificacaoDoUsuario;
import br.com.teste.model.enums.StatusDoRegistro;
import br.com.teste.model.filter.administracao.UsuarioFilter;

public class UsuarioControllerTest extends ControllerTests {

	@Test
	public void deveriaListar() throws Exception {
		ArrayList<Usuario> lista = new ArrayList<>();
		lista.add(UsuarioBuilder.get3());
		lista.add(UsuarioBuilder.get2());
		when(usuarioService.listar(any(UsuarioFilter.class))).thenReturn(lista);
		mvc.perform(get("/usuarios?id=1&nome=nome&login=login&somenteAtivo=true")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("[{\"id\":3,\"nome\":\"usuário 3\",\"login\":\"login3\",\"classificacao\":\"GERENTE\",\"administrador\":false},{\"id\":2,\"nome\":\"usuário 2\",\"login\":\"login2\",\"classificacao\":\"GERENTE\",\"administrador\":true}]"));
		ArgumentCaptor<UsuarioFilter> argument = ArgumentCaptor.forClass(UsuarioFilter.class);
		verify(usuarioService, times(1)).listar(argument.capture());
		assertEquals(Long.valueOf(1), argument.getValue().getId());
		assertEquals("nome", argument.getValue().getNome());
		assertEquals("login", argument.getValue().getLogin());
		assertTrue(argument.getValue().getSomenteAtivo());
	}
	
	@Test
	@WithMockUser(roles = "USUARIO")
	public void deveriaBuscar() throws Exception {
		when(usuarioService.buscar(anyLong())).thenReturn(UsuarioBuilder.get2());
		mvc.perform(get("/usuarios/2")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":2,\"nome\":\"usuário 2\",\"login\":\"login2\",\"classificacao\":\"GERENTE\",\"administrador\":true,\"dataDeAutenticacao\":\"2019-01-02T03:00:00.000+0000\",\"statusDoRegistro\":\"INATIVO\",\"dataDeInclusao\":\"2019-01-02T03:00:00.000+0000\",\"dataDeAlteracao\":\"2019-01-02T03:00:00.000+0000\",\"dataDeExclusao\":null,\"grupos\":null}"));
		ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
		verify(usuarioService, times(1)).buscar(argument.capture());
		assertEquals(Long.valueOf(2), argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "USUARIO_INCLUIR")
	public void deveriaCriar() throws Exception {
		when(usuarioService.criar(any(Usuario.class))).thenReturn(UsuarioBuilder.get3());
		mvc.perform(post("/usuarios")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"nome\":\"usuário 3\",\"login\":\"login3\",\"senha\":\"senha3\",\"confirmacaoDeSenha\":\"senha3\",\"classificacao\":\"GERENTE\",\"administrador\":\"true\",\"statusDoRegistro\":\"ATIVO\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":3,\"nome\":\"usuário 3\",\"login\":\"login3\",\"classificacao\":\"GERENTE\",\"administrador\":false,\"dataDeAutenticacao\":\"2019-01-03T03:00:00.000+0000\",\"statusDoRegistro\":\"ATIVO\",\"dataDeInclusao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeAlteracao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeExclusao\":null,\"grupos\":null}"));
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioService, times(1)).criar(argument.capture());
		Usuario usuario = getUsuario();
		usuario.setId(null);
		UsuarioTest.verifica(usuario, argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "USUARIO_EDITAR")
	public void deveriaAtualizar() throws Exception {
		when(usuarioService.atualizar(any(Usuario.class))).thenReturn(UsuarioBuilder.get3());
		mvc.perform(put("/usuarios")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"id\":3,\"nome\":\"usuário 3\",\"login\":\"login3\",\"classificacao\":\"GERENTE\",\"administrador\":\"true\",\"estabelecimentos\":[{\"codigo\":1}],\"statusDoRegistro\":\"ATIVO\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":3,\"nome\":\"usuário 3\",\"login\":\"login3\",\"classificacao\":\"GERENTE\",\"administrador\":false,\"dataDeAutenticacao\":\"2019-01-03T03:00:00.000+0000\",\"statusDoRegistro\":\"ATIVO\",\"dataDeInclusao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeAlteracao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeExclusao\":null,\"grupos\":null}"));
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioService, times(1)).atualizar(argument.capture());
		Usuario usuario = getUsuario();
		usuario.setSenha(null);
		usuario.setConfirmacaoDeSenha(null);
		UsuarioTest.verifica(usuario, argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "USUARIO_EDITAR")
	public void deveriaResetarSenha() throws Exception {
		when(usuarioService.atualizarSenha(any(Usuario.class))).thenReturn(UsuarioBuilder.get3());
		mvc.perform(put("/usuarios/senha/resetar")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"id\":3,\"senha\":\"teste3\",\"confirmacaoDeSenha\":\"teste3\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":3,\"nome\":\"usuário 3\",\"login\":\"login3\",\"classificacao\":\"GERENTE\",\"administrador\":false,\"dataDeAutenticacao\":\"2019-01-03T03:00:00.000+0000\",\"statusDoRegistro\":\"ATIVO\",\"dataDeInclusao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeAlteracao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeExclusao\":null,\"grupos\":null}"));
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioService, times(1)).atualizarSenha(argument.capture());
		assertEquals(Long.valueOf(3), argument.getValue().getId());
		assertNull(argument.getValue().getNome());
		assertNull(argument.getValue().getLogin());
		assertEquals("teste3", argument.getValue().getSenha());
		assertEquals("teste3", argument.getValue().getConfirmacaoDeSenha());
		assertNull(argument.getValue().getClassificacao());
		assertNull(argument.getValue().getAdministrador());
		assertNull(argument.getValue().getDataDeAutenticacao());
		assertNull(argument.getValue().getStatusDoRegistro());
	}
	
	@Test
	@WithUserDetails("ultramanutencao")
	public void deveriaAtualizarSenha() throws Exception {
		when(usuarioService.atualizarSenha(any(Usuario.class))).thenReturn(UsuarioBuilder.get3());
		mvc.perform(put("/usuarios/senha/atualizar")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"senha\":\"teste3\",\"confirmacaoDeSenha\":\"teste3\"}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":3,\"nome\":\"usuário 3\",\"login\":\"login3\",\"classificacao\":\"GERENTE\",\"administrador\":false,\"dataDeAutenticacao\":\"2019-01-03T03:00:00.000+0000\",\"statusDoRegistro\":\"ATIVO\",\"dataDeInclusao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeAlteracao\":\"2019-01-03T03:00:00.000+0000\",\"dataDeExclusao\":null,\"grupos\":null}"));
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioService, times(1)).atualizarSenha(argument.capture());
		assertEquals(Long.valueOf(0), argument.getValue().getId());
		assertNull(argument.getValue().getNome());
		assertNull(argument.getValue().getLogin());
		assertEquals("teste3", argument.getValue().getSenha());
		assertEquals("teste3", argument.getValue().getConfirmacaoDeSenha());
		assertNull(argument.getValue().getClassificacao());
		assertNull(argument.getValue().getAdministrador());
		assertNull(argument.getValue().getDataDeAutenticacao());
		assertNull(argument.getValue().getStatusDoRegistro());
	}
	
	@Test
	@WithMockUser(roles = "USUARIO_EXCLUIR")
	public void deveriaExcluir() throws Exception {
		mvc.perform(delete("/usuarios/3")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
		verify(usuarioService, times(1)).excluir(Long.valueOf(3));
	}
	
	private Usuario getUsuario() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(3));
		usuario.setNome("usuário 3");
		usuario.setLogin("login3");
		usuario.setSenha("senha3");
		usuario.setConfirmacaoDeSenha("senha3");
		usuario.setClassificacao(ClassificacaoDoUsuario.GERENTE);
		usuario.setAdministrador(true);
		usuario.setStatusDoRegistro(StatusDoRegistro.ATIVO);
		return usuario;
	}
	
}
