package br.com.teste.controller.administracao;

import static org.junit.Assert.assertEquals;
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
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import br.com.teste.ControllerTests;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.administracao.GrupoDePermissaoBuilder;
import br.com.teste.model.administracao.GrupoDePermissaoTest;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;

public class GrupoDePermissaoControllerTest extends ControllerTests {

	@Test
	public void deveriaListar() throws Exception {
		List<GrupoDePermissao> lista = new ArrayList<>();
		lista.add(GrupoDePermissaoBuilder.get1());
		lista.add(GrupoDePermissaoBuilder.get2());
		when(grupoDePermissaoService.listar(any(GrupoDePermissaoFilter.class))).thenReturn(lista);
		mvc.perform(get("/gruposDePermissao?descricao=teste")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("[{\"id\":1,\"descricao\":\"grupo de permissão 1\",\"ativo\":true},{\"id\":1,\"descricao\":\"grupo de permissão 2\",\"ativo\":true}]"));
		ArgumentCaptor<GrupoDePermissaoFilter> argument = ArgumentCaptor.forClass(GrupoDePermissaoFilter.class);
		verify(grupoDePermissaoService, times(1)).listar(argument.capture());
		assertEquals("teste", argument.getValue().getDescricao());
	}
	
	@Test
	@WithMockUser(roles = "GRUPO_PERMISSAO")
	public void deveriaBuscar() throws Exception {
		when(grupoDePermissaoService.buscar(anyLong())).thenReturn(GrupoDePermissaoBuilder.get1());
		mvc.perform(get("/gruposDePermissao/1")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"descricao\":\"grupo de permissão 1\",\"ativo\":true,\"acessos\":[\"USUARIO\"],\"permissoes\":[\"USUARIO_INCLUIR\",\"USUARIO_EDITAR\"]}"));
		ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
		verify(grupoDePermissaoService, times(1)).buscar(argument.capture());
		assertEquals(Long.valueOf(1), argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "GRUPO_PERMISSAO_INCLUIR")
	public void deveriaCriar() throws Exception {
		when(grupoDePermissaoService.criar(any(GrupoDePermissao.class))).thenReturn(GrupoDePermissaoBuilder.get1());
		mvc.perform(post("/gruposDePermissao")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"descricao\":\"grupo de permissão 1\",\"ativo\":true,\"acessos\":[\"USUARIO\"],\"permissoes\":[\"USUARIO_INCLUIR\",\"USUARIO_EDITAR\"]}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"descricao\":\"grupo de permissão 1\",\"ativo\":true,\"acessos\":[\"USUARIO\"],\"permissoes\":[\"USUARIO_INCLUIR\",\"USUARIO_EDITAR\"]}"));
		ArgumentCaptor<GrupoDePermissao> argument = ArgumentCaptor.forClass(GrupoDePermissao.class);
		verify(grupoDePermissaoService, times(1)).criar(argument.capture());
		GrupoDePermissaoTest.verifica(grupoDePermissao(), argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "GRUPO_PERMISSAO_EDITAR")
	public void deveriaAtualizar() throws Exception {
		when(grupoDePermissaoService.atualizar(any(GrupoDePermissao.class))).thenReturn(GrupoDePermissaoBuilder.get1());
		mvc.perform(put("/gruposDePermissao")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"id\":1,\"descricao\":\"grupo de permissão 1\",\"ativo\":true,\"acessos\":[\"USUARIO\"],\"permissoes\":[\"USUARIO_INCLUIR\",\"USUARIO_EDITAR\"]}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"descricao\":\"grupo de permissão 1\",\"ativo\":true,\"acessos\":[\"USUARIO\"],\"permissoes\":[\"USUARIO_INCLUIR\",\"USUARIO_EDITAR\"]}"));
		ArgumentCaptor<GrupoDePermissao> argument = ArgumentCaptor.forClass(GrupoDePermissao.class);
		verify(grupoDePermissaoService, times(1)).atualizar(argument.capture());
		GrupoDePermissao grupoDePermissaoEsperado = grupoDePermissao();
		grupoDePermissaoEsperado.setId(Long.valueOf(1));
		GrupoDePermissaoTest.verifica(grupoDePermissaoEsperado, argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "GRUPO_PERMISSAO_EXCLUIR")
	public void deveriaExcluir() throws Exception {
		mvc.perform(delete("/gruposDePermissao/1")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk());
		ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
		verify(grupoDePermissaoService, times(1)).excluir(argument.capture());
		assertEquals(Long.valueOf(1), argument.getValue());
	}
	
	public GrupoDePermissao grupoDePermissao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setDescricao("grupo de permissão 1");
		grupoDePermissao.setAtivo(true);
		grupoDePermissao.getAcessos().add(AcessoDoSistema.USUARIO);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_INCLUIR);
		grupoDePermissao.getPermissoes().add(PermissaoDoSistema.USUARIO_EDITAR);
		return grupoDePermissao;
	}

}
