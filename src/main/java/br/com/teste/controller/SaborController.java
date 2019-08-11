package br.com.teste.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.model.enums.Sabores;

@RestController
@RequestMapping("/sabores")
public class SaborController extends GeralController {

	@GetMapping
	public Sabores[] listar() {
		return Sabores.values();
	}
	
}
