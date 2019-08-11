package br.com.teste.model.dto.administracao;

import static org.junit.Assert.assertEquals;

import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.teste.GeralTests;

public class AtribuicaoDePermissaoDTOTest extends GeralTests {

	@Autowired
	private Validator validator;
	
	@Test
	public void deveriaValidarCamposObrigatoriosNaInclusao() {
		AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO = new AtribuicaoDePermissaoDTO();
		Set<ConstraintViolation<AtribuicaoDePermissaoDTO>> validate = validator.validate(atribuicaoDePermissaoDTO);
		assertEquals(1, validate.size());
		assertEquals("ID do usuário deve ser informado!", getMessageValidation(validate,"id"));
	}
	
	@Test
	public void deveriaValidarComSucessoCampos() {
		AtribuicaoDePermissaoDTO atribuicaoDePermissaoDTO = AtribuicaoDePermissaoDTOBuilder.getAtribuicaoDePermissaoDTO1();
		Set<ConstraintViolation<AtribuicaoDePermissaoDTO>> validate = validator.validate(atribuicaoDePermissaoDTO);
		assertEquals(0, validate.size());
	}
	
	protected String getMessageValidation(Set<ConstraintViolation<AtribuicaoDePermissaoDTO>> validate, String path) {
		Optional<ConstraintViolation<AtribuicaoDePermissaoDTO>> find = validate.stream().filter(v -> v.getPropertyPath().toString().equals(path)).findAny();
		if(!find.isPresent())
			return null;
		return find.get().getMessage();
	}

}
