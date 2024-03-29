package br.com.teste.model.administracao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.GeralTests;
import br.com.teste.model.ONCreate;
import br.com.teste.model.ONUpdate;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;

public class GrupoDePermissaoTest extends GeralTests {

	@Autowired
	private Validator validator;

	@Test
	public void deveriaValidarCamposQueNaoDeveSerPreenchido() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		grupoDePermissao.setDescricao("teste");
		grupoDePermissao.setAtivo(true);
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONCreate.class);
		assertEquals(1, validate.size());
		assertEquals("ID não deve ser informado!", getMessageValidation(validate, "id"));
	}

	@Test
	public void deveriaValidarCamposObrigatoriosNaInclusao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONCreate.class);
		assertEquals(2, validate.size());
		assertEquals("Descrição deve ser informado!", getMessageValidation(validate, "descricao"));
		assertEquals("Deve ser informado se é ativo!", getMessageValidation(validate, "ativo"));
	}

	@Test
	public void deveriaValidarCamposObrigatoriosNaAlteracao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONUpdate.class);
		assertEquals(3, validate.size());
		assertEquals("ID deve ser informado!", getMessageValidation(validate, "id"));
		assertEquals("Descrição deve ser informado!", getMessageValidation(validate, "descricao"));
		assertEquals("Deve ser informado se é ativo!", getMessageValidation(validate, "ativo"));
	}

	@Test
	public void deveriaValidarCamposExessoDeTamanhoNaInclusao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setDescricao(
				"01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		grupoDePermissao.setAtivo(true);
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONCreate.class);
		assertEquals(1, validate.size());
		assertEquals("Descrição ultrapassou 100 caracteres!", getMessageValidation(validate, "descricao"));
	}

	@Test
	public void deveriaValidarCamposExessoDeTamanhoNaAlteracao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		grupoDePermissao.setDescricao(
				"01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
		grupoDePermissao.setAtivo(true);
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONUpdate.class);
		assertEquals(1, validate.size());
		assertEquals("Descrição ultrapassou 100 caracteres!", getMessageValidation(validate, "descricao"));
	}

	@Test
	public void deveriaValidarCamposComSucessoNaInclusao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setDescricao("teste");
		grupoDePermissao.setAtivo(true);
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONCreate.class);
		assertEquals(0, validate.size());
	}

	@Test
	public void deveriaValidarCamposComSucessoNaAlteracao() {
		GrupoDePermissao grupoDePermissao = new GrupoDePermissao();
		grupoDePermissao.setId(Long.valueOf(1));
		grupoDePermissao.setDescricao("teste");
		grupoDePermissao.setAtivo(true);
		Set<ConstraintViolation<GrupoDePermissao>> validate = validator.validate(grupoDePermissao, ONUpdate.class);
		assertEquals(0, validate.size());
	}
	
	public static void verifica(GrupoDePermissao grupoDePermissao, GrupoDePermissao grupoDePermissaoSalvo) {
		assertNotNull(grupoDePermissaoSalvo);
		assertEquals(grupoDePermissao.getId(), grupoDePermissaoSalvo.getId());
		assertEquals(grupoDePermissao.getDescricao(), grupoDePermissaoSalvo.getDescricao());
		assertEquals(grupoDePermissao.getAtivo(), grupoDePermissaoSalvo.getAtivo());
		assertEquals(grupoDePermissao.getAcessos().size(), grupoDePermissaoSalvo.getAcessos().size());
		for(AcessoDoSistema acessoDoSistema : grupoDePermissao.getAcessos())
			assertTrue(verificaAcesso(grupoDePermissaoSalvo, acessoDoSistema));
		assertEquals(grupoDePermissao.getPermissoes().size(), grupoDePermissaoSalvo.getPermissoes().size());
		for(PermissaoDoSistema permissaoDoSistema : grupoDePermissao.getPermissoes())
			assertTrue(verificaPermissao(grupoDePermissaoSalvo, permissaoDoSistema));
	}

	private String getMessageValidation(Set<ConstraintViolation<GrupoDePermissao>> validate, String path) {
		Optional<ConstraintViolation<GrupoDePermissao>> find = validate.stream()
				.filter(v -> v.getPropertyPath().toString().equals(path)).findAny();
		if (!find.isPresent())
			return null;
		return find.get().getMessage();
	}
	
	private static boolean verificaAcesso(GrupoDePermissao grupoDePermissao, AcessoDoSistema acessoDoSistema) {
		for(AcessoDoSistema acessoDoSistemaCorrente : grupoDePermissao.getAcessos()) {
			if(acessoDoSistemaCorrente == acessoDoSistema)
				return true;
		}
		return false;
	}

	private static boolean verificaPermissao(GrupoDePermissao grupoDePermissao, PermissaoDoSistema permissaoDoSistema) {
		for(PermissaoDoSistema permissaoDoSistemaCorrente : grupoDePermissao.getPermissoes()) {
			if(permissaoDoSistemaCorrente == permissaoDoSistema)
				return true;
		}
		return false;
	}

}
