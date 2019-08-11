package br.com.teste.service.administracao;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.exception.ResolvedException;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTO;
import br.com.teste.repository.administracao.UsuarioRepository;
import br.com.teste.service.GeralServiceImpl;

@Service
@Transactional
public class AtribuiaoDePermissaoServiceImpl extends GeralServiceImpl implements AtribuiaoDePermissaoService {

	private UsuarioRepository usuarioRepository;
	@Autowired
	public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public AtribuicaoDePermissaoDTO buscar(Long id) {
		Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new ResolvedException("Usuário não existe!"));
		AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO = new AtribuicaoDePermissaoDTO();
		atribuicaoDePermissaoDTO.setId(id);
		atribuicaoDePermissaoDTO.setIdsDosGrupos(usuario.getGrupos().stream().map(grupo -> grupo.getId()).collect(Collectors.toList()));
		return atribuicaoDePermissaoDTO;
	}

	@Override
	public AtribuicaoDePermissaoDTO atualizar(AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO) {
		Usuario usuario = usuarioRepository.findById(atribuicaoDePermissaoDTO.getId()).orElseThrow(() -> new ResolvedException("Usuário não existe!"));
		usuario.setGrupos(atribuicaoDePermissaoDTO.getIdsDosGrupos().stream().map(id -> new GrupoDePermissao(id)).collect(Collectors.toList()));
		usuarioRepository.save(usuario);
		return atribuicaoDePermissaoDTO;
	}

}
