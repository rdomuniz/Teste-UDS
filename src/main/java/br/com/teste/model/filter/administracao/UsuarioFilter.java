package br.com.teste.model.filter.administracao;

import lombok.Data;

@Data
public class UsuarioFilter {

	private Long id;
	private String nome;
	private String login;
	private Boolean somenteAtivo = true;
	
}
