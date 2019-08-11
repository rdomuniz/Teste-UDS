package br.com.teste.model.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.GeralTests;
import br.com.teste.model.ONCreate;
import br.com.teste.model.ONUpdate;
import br.com.teste.model.enums.StatusDoRegistro;

public class UsuarioTest extends GeralTests {

	@Autowired
	private Validator validator;
	
	@Test
	public void deveriaValidarCamposQueNaoDeveSerPreenchido() {
		Set<ConstraintViolation<Usuario>> validate = validator.validate(UsuarioBuilder.get2(), ONCreate.class);
		assertEquals(1, validate.size());
		assertEquals("ID não deve ser informado!", getMessageValidation(validate,"id"));
	}
	
	@Test
	public void deveriaValidarCamposObrigatoriosNaInclusao() {
		Usuario usuario = new Usuario();
		Set<ConstraintViolation<Usuario>> validate = validator.validate(usuario, ONCreate.class);
		assertEquals(6, validate.size());
		assertEquals("Nome deve ser informado!", getMessageValidation(validate,"nome"));
		assertEquals("Login deve ser informado!", getMessageValidation(validate,"login"));
		assertEquals("Senha deve ser informado!", getMessageValidation(validate,"senha"));
		assertEquals("Confirmação de senha deve ser informado!", getMessageValidation(validate,"confirmacaoDeSenha"));
		assertEquals("Deve ser informado se é administrador!", getMessageValidation(validate,"administrador"));
		assertEquals("Status deve ser informado!", getMessageValidation(validate,"statusDoRegistro"));
	}
	
	@Test
	public void deveriaValidarCamposObrigatoriosNaAlteracao() {
		Usuario usuario = new Usuario();
		Set<ConstraintViolation<Usuario>> validate = validator.validate(usuario, ONUpdate.class);
		assertEquals(5, validate.size());
		assertEquals("ID deve ser informado!", getMessageValidation(validate,"id"));
		assertEquals("Nome deve ser informado!", getMessageValidation(validate,"nome"));
		assertEquals("Login deve ser informado!", getMessageValidation(validate,"login"));
		assertEquals("Deve ser informado se é administrador!", getMessageValidation(validate,"administrador"));
		assertEquals("Status deve ser informado!", getMessageValidation(validate,"statusDoRegistro"));
	}
	
	@Test
	public void deveriaValidarCamposExessoDeTamanhoNaInclusao() {
		Usuario usuario = new Usuario();
		usuario.setNome("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		usuario.setLogin("012345678901234567890");
		usuario.setSenha("senha");
		usuario.setConfirmacaoDeSenha("senha");
		usuario.setAdministrador(true);
		usuario.setStatusDoRegistro(StatusDoRegistro.ATIVO);
		Set<ConstraintViolation<Usuario>> validate = validator.validate(usuario, ONCreate.class);
		assertEquals(2, validate.size());
		assertEquals("Nome ultrapassou 100 caracteres!", getMessageValidation(validate,"nome"));
		assertEquals("Login ultrapassou 20 caracteres!", getMessageValidation(validate,"login"));
	}
	
	@Test
	public void deveriaValidarCamposExessoDeTamanhoNaAlteracao() {
		Usuario usuario = new Usuario();
		usuario.setId(Long.valueOf(1));
		usuario.setNome("01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		usuario.setLogin("012345678901234567890");
		usuario.setAdministrador(true);
		usuario.setStatusDoRegistro(StatusDoRegistro.ATIVO);
		Set<ConstraintViolation<Usuario>> validate = validator.validate(usuario, ONUpdate.class);
		assertEquals(2, validate.size());
		assertEquals("Nome ultrapassou 100 caracteres!", getMessageValidation(validate,"nome"));
		assertEquals("Login ultrapassou 20 caracteres!", getMessageValidation(validate,"login"));
	}
	
	@Test
	public void deveriaValidarComSucessoCamposNaInclusao() {
		Usuario usuario = UsuarioBuilder.get2();
		usuario.setId(null);
		assertEquals(0, validator.validate(usuario, ONCreate.class).size());
	}
	
	@Test
	public void deveriaValidarComSucessoCamposNaAlteracao() {
		assertEquals(0, validator.validate(UsuarioBuilder.get2(), ONUpdate.class).size());
	}
	
	public static void verifica(Usuario usuarioEsperado, Usuario usuario) {
		assertNotNull(usuario);
		assertEquals(usuarioEsperado.getId(), usuario.getId());
		assertEquals(usuarioEsperado.getNome(), usuario.getNome());
		assertEquals(usuarioEsperado.getLogin(), usuario.getLogin());
		assertEquals(usuarioEsperado.getSenha(), usuario.getSenha());
		assertEquals(usuarioEsperado.getClassificacao(), usuario.getClassificacao());
		assertEquals(usuarioEsperado.getAdministrador(), usuario.getAdministrador());
		assertEquals(usuarioEsperado.getDataDeAutenticacao(), usuario.getDataDeAutenticacao());
		assertEquals(usuarioEsperado.getStatusDoRegistro(), usuario.getStatusDoRegistro());
		assertEquals(usuarioEsperado.getGrupos().size(), usuario.getGrupos().size());
		for(GrupoDePermissao grupoDePermissaoEsperado : usuarioEsperado.getGrupos()) {
			assertNotNull(usuario.getGrupos().stream().filter(
				grupoDePermissaoFiltro -> grupoDePermissaoEsperado.getId() != null && grupoDePermissaoFiltro.getId() != null && grupoDePermissaoEsperado.getId().compareTo(grupoDePermissaoFiltro.getId()) == 0
			).findAny().orElse(null));
		}
	}
	
	private String getMessageValidation(Set<ConstraintViolation<Usuario>> validate, String path) {
		Optional<ConstraintViolation<Usuario>> find = validate.stream().filter(v -> v.getPropertyPath().toString().equals(path)).findAny();
		if(!find.isPresent())
			return null;
		return find.get().getMessage();
	}

}
