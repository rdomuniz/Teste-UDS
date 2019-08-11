package br.com.teste.service.administracao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.exception.ResolvedException;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;
import br.com.teste.repository.administracao.GrupoDePermissaoRepository;
import br.com.teste.service.GeralServiceImpl;

@Service
@Transactional
public class GrupoDePermissaoServiceImpl extends GeralServiceImpl implements GrupoDePermissaoService {

	private static final String MSG_NAO_EXISTE = "Grupo de permissão não existe!";
	
	private GrupoDePermissaoRepository grupoDePermissaoRepository;
	@Autowired
	public void setGrupoDePermissaoRepository(GrupoDePermissaoRepository grupoDePermissaoRepository) {
		this.grupoDePermissaoRepository = grupoDePermissaoRepository;
	}
	
	@Override
	public List<GrupoDePermissao> listar(GrupoDePermissaoFilter filtro) {
		return grupoDePermissaoRepository.findByFilter(filtro);
	}

	@Override
	public GrupoDePermissao buscar(Long id) {
		return grupoDePermissaoRepository.findById(id).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE));
	}

	@Override
	public GrupoDePermissao criar(GrupoDePermissao grupoDePermissao) {
		return grupoDePermissaoRepository.save(grupoDePermissao);
	}

	@Override
	public GrupoDePermissao atualizar(GrupoDePermissao grupoDePermissao) {
		if(!grupoDePermissaoRepository.existsById(grupoDePermissao.getId()))
			throw new ResolvedException(MSG_NAO_EXISTE);
		return grupoDePermissaoRepository.save(grupoDePermissao);
	}
	
	@Override
	public void excluir(Long id) {
		if(!grupoDePermissaoRepository.existsById(id))
			throw new ResolvedException(MSG_NAO_EXISTE);
		grupoDePermissaoRepository.deleteById(id);
	}

}
