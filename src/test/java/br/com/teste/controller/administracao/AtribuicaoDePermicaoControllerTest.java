package br.com.teste.controller.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import br.com.teste.ControllerTests;
import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTO;
import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTOBuilder;

public class AtribuicaoDePermicaoControllerTest extends ControllerTests {

	@Test
	@WithMockUser(roles = "ATRIBUICAO_PERMISSAO_CONFIGURAR")
	public void deveriaBuscar() throws Exception {
		when(atribuiaoDePermissaoService.buscar(anyLong())).thenReturn(AtribuicaoDePermissaoDTOBuilder.getAtribuicaoDePermissaoDTO1());
		mvc.perform(get("/atribuicaoDePermissoes/1")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"idsDosGrupos\":[1,2,3]}"));
		ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
		verify(atribuiaoDePermissaoService, times(1)).buscar(argument.capture());
		assertEquals(Long.valueOf(1), argument.getValue());
	}
	
	@Test
	@WithMockUser(roles = "ATRIBUICAO_PERMISSAO_CONFIGURAR")
	public void deveriaAtualizar() throws Exception {
		when(atribuiaoDePermissaoService.atualizar(any(AtribuicaoDePermissaoDTO.class))).thenReturn(AtribuicaoDePermissaoDTOBuilder.getAtribuicaoDePermissaoDTO1());
		mvc.perform(put("/atribuicaoDePermissoes")
			.contentType(MediaType.APPLICATION_JSON_UTF8)
			.content("{\"id\":1,\"idsDosGrupos\":[1,2,3]}"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("{\"id\":1,\"idsDosGrupos\":[1,2,3]}"));
		ArgumentCaptor<AtribuicaoDePermissaoDTO> argument = ArgumentCaptor.forClass(AtribuicaoDePermissaoDTO.class);
		verify(atribuiaoDePermissaoService, times(1)).atualizar(argument.capture());
		assertEquals(Long.valueOf(1), argument.getValue().getId());
		assertEquals(3, argument.getValue().getIdsDosGrupos().size());
		assertTrue(verificaID(argument.getValue(),Long.valueOf(1)));
		assertTrue(verificaID(argument.getValue(),Long.valueOf(2)));
		assertTrue(verificaID(argument.getValue(),Long.valueOf(3)));
	}
	
	private boolean verificaID(AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO, Long id) {
		for(Long idCorrente : atribuicaoDePermissaoDTO.getIdsDosGrupos()) {
			if(id.compareTo(idCorrente) == 0)
				return true;
		}
		return false;
	}

}
