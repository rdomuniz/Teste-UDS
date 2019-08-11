package br.com.teste.model.dto.administracao;

public class AtribuicaoDePermissaoDTOBuilder {

	public static AtribuicaoDePermissaoDTO getAtribuicaoDePermissaoDTO1() {
		AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO = new AtribuicaoDePermissaoDTO();
		atribuicaoDePermissaoDTO.setId(Long.valueOf(1));
		atribuicaoDePermissaoDTO.getIdsDosGrupos().add(Long.valueOf(1));
		atribuicaoDePermissaoDTO.getIdsDosGrupos().add(Long.valueOf(2));
		atribuicaoDePermissaoDTO.getIdsDosGrupos().add(Long.valueOf(3));
		return atribuicaoDePermissaoDTO;
	}
	
	
}
