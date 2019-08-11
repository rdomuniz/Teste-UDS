package br.com.teste.model.dto.administracao;

import java.util.HashMap;
import java.util.Map;

import br.com.teste.model.enums.PermissaoDoSistema;
import lombok.Data;

@Data
public class AcessoDTO {

	private String descricao;
	private Map<PermissaoDoSistema, String> permisoes = new HashMap<>();

}