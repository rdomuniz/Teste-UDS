package br.com.teste.model.administracao;

import br.com.teste.model.GeralBuilder;
import br.com.teste.model.enums.ClassificacaoDoUsuario;
import br.com.teste.model.enums.StatusDoRegistro;

public class UsuarioBuilder {

	public static Usuario get1() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(1));
		usuario.setNome("usuário 1");
		usuario.setSenha("senha1");
		usuario.setConfirmacaoDeSenha("senha1");
		usuario.setClassificacao(ClassificacaoDoUsuario.GERENTE);
		usuario.setAdministrador(true);
		usuario.setStatusDoRegistro(StatusDoRegistro.EXCLUIDO);
		usuario.setDataDeAutenticacao(GeralBuilder.getData("01/01/2019"));
		usuario.setDataDeInclusao(GeralBuilder.getData("01/01/2019"));
		usuario.setDataDeAlteracao(GeralBuilder.getData("01/01/2019"));
		usuario.setDataDeExclusao(GeralBuilder.getData("01/01/2019"));
		return usuario;
	}
	
	public static Usuario get2() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(2));
		usuario.setNome("usuário 2");
		usuario.setLogin("login2");
		usuario.setSenha("senha2");
		usuario.setConfirmacaoDeSenha("senha2");
		usuario.setClassificacao(ClassificacaoDoUsuario.GERENTE);
		usuario.setAdministrador(true);
		usuario.setStatusDoRegistro(StatusDoRegistro.INATIVO);
		usuario.setDataDeAutenticacao(GeralBuilder.getData("02/01/2019"));
		usuario.setDataDeInclusao(GeralBuilder.getData("02/01/2019"));
		usuario.setDataDeAlteracao(GeralBuilder.getData("02/01/2019"));
		return usuario;
	}
	
	public static Usuario get3() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(3));
		usuario.setNome("usuário 3");
		usuario.setLogin("login3");
		usuario.setSenha("senha3");
		usuario.setConfirmacaoDeSenha("senha3");
		usuario.setClassificacao(ClassificacaoDoUsuario.GERENTE);
		usuario.setAdministrador(false);
		usuario.setDataDeAutenticacao(GeralBuilder.getData("03/01/2019"));
		usuario.setStatusDoRegistro(StatusDoRegistro.ATIVO);
		usuario.setDataDeInclusao(GeralBuilder.getData("03/01/2019"));
		usuario.setDataDeAlteracao(GeralBuilder.getData("03/01/2019"));
		return usuario;
	}
	
}
