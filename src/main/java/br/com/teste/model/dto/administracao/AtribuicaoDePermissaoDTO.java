package br.com.teste.model.dto.administracao;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AtribuicaoDePermissaoDTO {

	@NotNull(message="ID do usuário deve ser informado!")
	private Long id;
	@Valid
	private List<Long> idsDosGrupos = new ArrayList<>();
	
}
