package br.com.teste.service.administracao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.teste.exception.ResolvedException;
import br.com.teste.model.Relogio;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.enums.StatusDoRegistro;
import br.com.teste.model.filter.administracao.UsuarioFilter;
import br.com.teste.repository.administracao.UsuarioRepository;
import br.com.teste.service.GeralServiceImpl;

@Service
@Transactional
public class UsuarioServiceImpl extends GeralServiceImpl implements UsuarioService {

	private static final String MSG_NAO_EXISTE = "Usuário não existe!";
	
	private Relogio relogio;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UsuarioRepository usuarioRepository;
	@Autowired
	public void setRelogio(Relogio relogio) {
		this.relogio = relogio;
	}
	@Autowired
	public void setBCryptPasswordEncoder(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	@Autowired
	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public List<Usuario> listar(UsuarioFilter filtro) {
		return usuarioRepository.findByFilter(filtro);
	}

	@Override
	public Usuario buscar(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
	}

	@Override
	public Usuario buscarPorLogin(String login) {
		Usuario usuario = usuarioRepository.findByLogin(login).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
		Hibernate.initialize(usuario.getGrupos());
		usuario.getGrupos().forEach(grupo -> {
			Hibernate.initialize(grupo.getAcessos());
			Hibernate.initialize(grupo.getPermissoes());
		});
		return usuario;
	}

	@Override
	public Usuario criar(Usuario usuario) {
		if(usuarioRepository.findByLogin(usuario.getLogin()).isPresent())
			throw new ResolvedException("Já existe um usuário com este login");
		if(StatusDoRegistro.EXCLUIDO.compareTo(usuario.getStatusDoRegistro()) == 0)
			throw new ResolvedException("Não pode marcar como excluido na inclusão!");
		verificaConfirmacaoDeSenha(usuario);
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuario);
	}

	@Override
	public Usuario atualizar(Usuario usuario) {
		Usuario usuarioBase = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
		if(StatusDoRegistro.EXCLUIDO.compareTo(usuario.getStatusDoRegistro()) == 0)
			throw new ResolvedException("Não pode marcar como excluido na edição!");
		usuarioBase.apreencheAtualizacao(usuario);
		return usuarioRepository.save(usuarioBase);
	}
	
	@Override
	public Usuario atualizarSenha(Usuario usuario) {
		Usuario usuarioBase = usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
		verificaConfirmacaoDeSenha(usuario);
		usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
		return usuarioRepository.save(usuarioBase);
	}
	
	@Override
	public void atualizarDataDeAutenticacao(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
		usuario.setDataDeAutenticacao(relogio.hoje());
		usuarioRepository.save(usuario);
	}

	@Override
	public void excluir(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
		usuario.setStatusDoRegistro(StatusDoRegistro.EXCLUIDO);
		usuarioRepository.save(usuario);
	}
	
	private void verificaConfirmacaoDeSenha(Usuario usuario) {
		if(usuario.getSenha().compareTo(usuario.getConfirmacaoDeSenha()) != 0)
			throw new ResolvedException("Confimação de senha não confere!");
	}
	
}