package br.com.teste.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.model.enums.Personalizacoes;

@RestController
@RequestMapping("/personalizacoes")
public class PersonalizacaoController extends GeralController {

	@GetMapping
	public Personalizacoes[] listar() {
		return Personalizacoes.values();
	}
	
}
