package br.com.teste.model.enums;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum AcessoDoSistema {

	ATRIBUICAO_PERMISSAO("Administração / Atribuição de Permissão", true),
	GRUPO_PERMISSAO("Administração / Grupo de Permissão", true),
	PEDIDO("Pedido", true),
	USUARIO("Administração / Usuário", true);
	
	private String descricao;
	private Boolean ativo;
	
	private AcessoDoSistema(String descricao, Boolean ativo) {
		this.descricao = descricao;
		this.ativo = ativo;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public Boolean isAtivo() {
		return ativo;
	}
	
	public static List<AcessoDoSistema> getAdministracao() {
		return newArrayList(USUARIO, GRUPO_PERMISSAO, ATRIBUICAO_PERMISSAO);
	}
	
	public static List<AcessoDoSistema> getSemAdministracao() {
		List<AcessoDoSistema> administrador = getAdministracao();
		return Arrays.stream(values()).filter(a -> !administrador.contains(a)).collect(Collectors.toList());
	}

}
