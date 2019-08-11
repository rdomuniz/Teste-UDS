package br.com.teste.model;

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
import br.com.teste.model.enums.Personalizacoes;

public class PedidoTest extends GeralTests {
	
	@Autowired
	private Validator validator;

	@Test
	public void deveriaValidarCamposQueNaoDeveSerPreenchido() {
		Set<ConstraintViolation<Pedido>> validate = validator.validate(PedidoBuilder.get1(), ONCreate.class);
		assertEquals(1, validate.size());
		assertEquals("ID não deve ser informado!", getMessageValidation(validate,"id"));
	}
	
	@Test
	public void deveriaValidarCamposObrigatoriosNaInclusao() {
		Pedido pedido = new Pedido();
		Set<ConstraintViolation<Pedido>> validate = validator.validate(pedido, ONCreate.class);
		assertEquals(2, validate.size());
		assertEquals("Tamanho deve ser informado!", getMessageValidation(validate,"tamanho"));
		assertEquals("Sabor deve ser informado!", getMessageValidation(validate,"sabor"));
	}
	
	@Test
	public void deveriaValidarCamposObrigatoriosNaAlteracao() {
		Pedido pedido = new Pedido();
		Set<ConstraintViolation<Pedido>> validate = validator.validate(pedido, ONUpdate.class);
		assertEquals(3, validate.size());
		assertEquals("ID deve ser informado!", getMessageValidation(validate,"id"));
		assertEquals("Tamanho deve ser informado!", getMessageValidation(validate,"tamanho"));
		assertEquals("Sabor deve ser informado!", getMessageValidation(validate,"sabor"));
	}
	
	private String getMessageValidation(Set<ConstraintViolation<Pedido>> validate, String path) {
		Optional<ConstraintViolation<Pedido>> find = validate.stream().filter(v -> v.getPropertyPath().toString().equals(path)).findAny();
		if(!find.isPresent())
			return null;
		return find.get().getMessage();
	}

	public static void verifica(Pedido pedidoEsperado, Pedido pedido) {
		assertNotNull(pedido);
		assertEquals(pedidoEsperado.getId(), pedido.getId());
		assertEquals(pedidoEsperado.getTamanho(), pedido.getTamanho());
		assertEquals(pedidoEsperado.getSabor(), pedido.getSabor());
		assertEquals(pedidoEsperado.getPersonalizacoes().size(), pedido.getPersonalizacoes().size());
		for(Personalizacoes personalizacao : pedidoEsperado.getPersonalizacoes())
			assertTrue(verificaAcesso(pedido, personalizacao));
	}
	
	private static boolean verificaAcesso(Pedido pedido, Personalizacoes personalizacao) {
		for(Personalizacoes personalizacaoCorrente : pedido.getPersonalizacoes()) {
			if(personalizacaoCorrente == personalizacao)
				return true;
		}
		return false;
	}
	
	

}
