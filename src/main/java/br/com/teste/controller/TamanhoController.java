package br.com.teste.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.model.enums.Tamanho;

@RestController
@RequestMapping("/tamanhos")
public class TamanhoController extends GeralController {

	@GetMapping
	public Tamanho[] listar() {
		return Tamanho.values();
	}
	
}
