package br.com.teste.repository.administracao;

import java.util.List;
import java.util.Optional;

import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.filter.administracao.UsuarioFilter;

public interface UsuarioRepositoryCustom {

	public Optional<Usuario> findByLogin(String login);
	public List<Usuario> findByFilter(UsuarioFilter filtro);
	
}
