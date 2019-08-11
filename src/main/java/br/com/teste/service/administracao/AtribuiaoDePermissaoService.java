package br.com.teste.service.administracao;

import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTO;
import br.com.teste.service.GeralService;

public interface AtribuiaoDePermissaoService extends GeralService{

	public AtribuicaoDePermissaoDTO buscar(Long id);
	public AtribuicaoDePermissaoDTO atualizar(AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO);
	
}
