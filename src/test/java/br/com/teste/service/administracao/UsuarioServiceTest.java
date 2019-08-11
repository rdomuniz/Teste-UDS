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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.teste.GeralTests;
import br.com.teste.exception.ResolvedException;
import br.com.teste.model.RelogioMock;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.administracao.UsuarioBuilder;
import br.com.teste.model.administracao.UsuarioTest;
import br.com.teste.model.enums.StatusDoRegistro;
import br.com.teste.model.filter.administracao.UsuarioFilter;
import br.com.teste.repository.administracao.UsuarioRepository;

public class UsuarioServiceTest extends GeralTests {

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Autowired 
	private RelogioMock relogio;
	@Autowired
	private UsuarioService usuarioService;
	@Mock
	private UsuarioRepository usuarioRepository;
	@Mock
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Before
	public void setUp() {
		((UsuarioServiceImpl)usuarioService).setBCryptPasswordEncoder(bCryptPasswordEncoder);
		((UsuarioServiceImpl)usuarioService).setUsuarioRepository(usuarioRepository);
	}
	
	@Test
	public void deveriaListar() {
		UsuarioFilter filtro = new UsuarioFilter();
		List<Usuario> retorno = new ArrayList<>();
		retorno.add(UsuarioBuilder.get2());
		retorno.add(UsuarioBuilder.get3());
		when(usuarioRepository.findByFilter(filtro)).thenReturn(retorno);
		retorno = usuarioService.listar(filtro);
		verify(usuarioRepository, times(1)).findByFilter(filtro);
		assertEquals(2, retorno.size());
		UsuarioTest.verifica(UsuarioBuilder.get2(), retorno.get(0));
		UsuarioTest.verifica(UsuarioBuilder.get3(), retorno.get(1));
	}
	
	@Test
	public void deveriaBuscar() {
		Usuario usuario = UsuarioBuilder.get3();
		when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
		Usuario retorno = usuarioService.buscar(usuario.getId());
		verify(usuarioRepository, times(1)).findById(usuario.getId());
		UsuarioTest.verifica(UsuarioBuilder.get3(), retorno);
	}
	
	@Test
	public void deveriaDarErroAoBuscarENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		usuarioService.buscar(Long.valueOf(4));
	}
	
	@Test
	public void deveriaBuscarPorLogin() {
		Usuario usuario = UsuarioBuilder.get3();
		when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuario));
		Usuario retorno = usuarioService.buscarPorLogin(usuario.getLogin());
		verify(usuarioRepository, times(1)).findByLogin(usuario.getLogin());
		UsuarioTest.verifica(UsuarioBuilder.get3(), retorno);
	}
	
	@Test
	public void deveriaDarErroAoBuscarPorLoginENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		usuarioService.buscarPorLogin("login4");
	}
	
	@Test
	public void deveriaCriar() {
		Usuario retorno;
		Usuario usuarioEsperado;
		String senha = "senhaCriptografada";
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setId(null);
		retorno = UsuarioBuilder.get3();
		retorno.setSenha(senha);
		when(bCryptPasswordEncoder.encode(usuario.getSenha())).thenReturn(senha);
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(retorno);
		retorno = usuarioService.criar(usuario);
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioRepository, times(1)).findByLogin(usuario.getLogin());
		verify(usuarioRepository, times(1)).save(argument.capture());
		usuarioEsperado = UsuarioBuilder.get3();
		usuarioEsperado.setId(null);
		usuarioEsperado.setSenha(senha);
		UsuarioTest.verifica(usuarioEsperado, argument.getValue());
		usuarioEsperado = UsuarioBuilder.get3();
		usuarioEsperado.setSenha(senha);
		UsuarioTest.verifica(usuarioEsperado, retorno);
	}
	
	@Test
	public void deveriaDarErroAoCriarETerLoginJaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Já existe um usuário com este login");
		Usuario usuario = UsuarioBuilder.get2();
		when(usuarioRepository.findByLogin(usuario.getLogin())).thenReturn(Optional.of(usuario));
		usuarioService.criar(usuario);
	}
	
	@Test
	public void deveriaDarErroAoCriarECadastradoJaExisteComoExcluido() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Não pode marcar como excluido na inclusão!");
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setStatusDoRegistro(StatusDoRegistro.EXCLUIDO);
		usuarioService.criar(usuario);
	}
	
	@Test
	public void deveriaDarErroAoCriarEConfirmacaoSenhaNaoConferir() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Confimação de senha não confere!");
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setConfirmacaoDeSenha("senhaNaoConfere");
		usuarioService.criar(usuario);
	}

	@Test
	public void deveriaAtualizar() {
		Usuario retorno;
		Usuario usuarioEsperado;
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setNome("Nome Alterado");
		usuario.setSenha("loginAlterado");
		usuario.setSenha("senhaAlterada");
		when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(UsuarioBuilder.get3()));
		retorno = UsuarioBuilder.get3();
		retorno.setNome("Nome Alterado");
		when(usuarioRepository.save(any(Usuario.class))).thenReturn(retorno);
		retorno = usuarioService.atualizar(usuario);
		verify(usuarioRepository, times(1)).findById(usuario.getId());
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioRepository, times(1)).save(argument.capture());
		usuarioEsperado = UsuarioBuilder.get3();
		usuarioEsperado.setNome("Nome Alterado");
		UsuarioTest.verifica(usuarioEsperado, argument.getValue());
		usuarioEsperado = UsuarioBuilder.get3();
		usuarioEsperado.setNome("Nome Alterado");
		UsuarioTest.verifica(usuarioEsperado, retorno);
	}
	
	@Test
	public void deveriaDarErroAoAtualizarENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		usuarioService.atualizar(UsuarioBuilder.get3());
	}
	
	@Test
	public void deveriaDarErroAoAtualizarEJaMarcadoComoExcluido() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Não pode marcar como excluido na edição!");
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setStatusDoRegistro(StatusDoRegistro.EXCLUIDO);
		when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(UsuarioBuilder.get3()));
		usuarioService.atualizar(usuario);
	}
	
	@Test
	public void deveriaAtualizarSenha() {
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setSenha("senhaAlterada");
		usuario.setConfirmacaoDeSenha("senhaAlterada");
		Usuario usuarioBase = UsuarioBuilder.get3();
		when(usuarioRepository.findById(usuarioBase.getId())).thenReturn(Optional.of(usuarioBase));
		usuarioService.atualizarSenha(usuario);
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioRepository, times(1)).findById(usuario.getId());
		verify(usuarioRepository, times(1)).save(argument.capture());
		assertEquals(usuarioBase.getSenha(), argument.getValue().getSenha());
	}
	
	@Test
	public void deveriaDarErroAoAtualizarSenhaENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		usuarioService.atualizarSenha(UsuarioBuilder.get3());
	}
	
	@Test
	public void deveriaDarErroAoAtualizarSenhaEConfirmacaoSenhaNaoConferir() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Confimação de senha não confere!");
		Usuario usuario = UsuarioBuilder.get3();
		usuario.setConfirmacaoDeSenha("senhaNaoConfere");
		Usuario usuarioBase = UsuarioBuilder.get3();
		when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuarioBase));
		usuarioService.atualizarSenha(usuario);
	}
	
	@Test
	public void deveriaAtualizarDataDeAutenticacao() {
		relogio.setHoje(getData("01/02/2018"));
		Long id = Long.valueOf(1);
		when(usuarioRepository.findById(id)).thenReturn(Optional.of(UsuarioBuilder.get2()));
		usuarioService.atualizarDataDeAutenticacao(id);
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioRepository, times(1)).findById(id);
		verify(usuarioRepository, times(1)).save(argument.capture());
		assertEquals(getData("01/02/2018"), argument.getValue().getDataDeAutenticacao());
	}
	
	@Test
	public void deveriaDarErroAoAtualizarDataDeAutenticacaoENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		usuarioService.atualizarDataDeAutenticacao(Long.valueOf(1));
	}
	
	@Test
	public void deveriaExcluir() {
		Long id = Long.valueOf(1);
		Usuario usuario = UsuarioBuilder.get2();
		when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
		usuarioService.excluir(id);
		verify(usuarioRepository, times(1)).findById(id);
		ArgumentCaptor<Usuario> argument = ArgumentCaptor.forClass(Usuario.class);
		verify(usuarioRepository, times(1)).save(argument.capture());
	}
	
	@Test
	public void deveriaDarErroAoExcluirENaoEstaCadastrado() {
		exception.expect(ResolvedException.class);
		exception.expectMessage("Usuário não existe!");
		usuarioService.excluir(Long.valueOf(1));
	}
	
}
