package br.com.teste.repository.administracao;

import br.com.teste.model.administracao.Usuario;
import br.com.teste.repository.GeralRepository;

public interface UsuarioRepository extends GeralRepository<Usuario, Long>, UsuarioRepositoryCustom {

}
