package br.com.teste.service.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.GeralTests;
import br.com.teste.exception.ResolvedException;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.administracao.UsuarioBuilder;
import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTO;
import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTOBuilder;
import br.com.teste.repository.administracao.UsuarioRepository;

public class AtribuiaoDePermissaoServiceTest extends GeralTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired
	private AtribuiaoDePermissaoService atribuiaoDePermissaoService;
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		((AtribuiaoDePermissaoServiceImpl)atribuiaoDePermissaoService).setUsuarioRepository(usuarioRepository);
	}
	
	@Test
	public void deveriaBuscar() {
		Usuario usuario = UsuarioBuilder.get3();
		when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
		AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO = atribuiaoDePermissaoService.buscar(usuario.getId());
		assertEquals(usuario.getId(), atribuicaoDePermissaoDTO.getId());
		assertEquals(2, atribuicaoDePermissaoDTO.getIdsDosGrupos().size());
		for (GrupoDePermissao grupoDePermissao : usuario.getGrupos())
			assertTrue(verificaGrupo(atribuicaoDePermissaoDTO,grupoDePermissao));
	}
	
	@Test
	public void deveriaDarErroAoBuscarEUsuarioNaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		atribuiaoDePermissaoService.buscar(Long.valueOf(1));
	}
	
	@Test
	public void deveriaAtualizar() {
		Usuario usuario = UsuarioBuilder.get1();
		when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
		AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO = AtribuicaoDePermissaoDTOBuilder.getAtribuicaoDePermissaoDTO1();
		atribuiaoDePermissaoService.atualizar(atribuicaoDePermissaoDTO);
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioRepository, times(1)).save(argument.capture());
		assertEquals(usuario.getId(), atribuicaoDePermissaoDTO.getId());
		assertEquals(3, usuario.getGrupos().size());
		for (Long id : atribuicaoDePermissaoDTO.getIdsDosGrupos())
			assertTrue(verificaGrupo(usuario,id));
	}
	
	private boolean verificaGrupo(AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO, GrupoDePermissao grupoDePermissao) {
		for(Long id : atribuicaoDePermissaoDTO.getIdsDosGrupos()) {
			if(id.compareTo(grupoDePermissao.getId()) == 0)
				return true;
		}
		return false;
	}

	private boolean verificaGrupo(Usuario usuario, Long id) {
		for(GrupoDePermissao grupoDePermissao : usuario.getGrupos()) {
			if(id.compareTo(grupoDePermissao.getId()) == 0)
				return true;
		}
		return false;
	}

}
