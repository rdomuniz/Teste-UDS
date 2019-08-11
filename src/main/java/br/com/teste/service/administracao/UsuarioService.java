package br.com.teste.service.administracao;

import java.util.List;

import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.filter.administracao.UsuarioFilter;
import br.com.teste.service.GeralService;

public interface UsuarioService extends GeralService {

	public List<Usuario> listar(UsuarioFilter filtro);
	public Usuario buscar(Long id);
	public Usuario buscarPorLogin(String login);
	public Usuario criar(Usuario usuario);
	public Usuario atualizar(Usuario usuario);
	public Usuario atualizarSenha(Usuario usuario);
	public void atualizarDataDeAutenticacao(Long id);
	public void excluir(Long id);
	
}
