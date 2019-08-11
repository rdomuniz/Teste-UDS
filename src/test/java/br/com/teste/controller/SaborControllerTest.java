package br.com.teste.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.http.MediaType;

import br.com.teste.ControllerTests;

public class SaborControllerTest extends ControllerTests {

	@Test
	public void deveriaListar() throws Exception {
		mvc.perform(get("/sabores")
			.contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(content().string("[\"CALABRESA\",\"MARGUERITA\",\"PORTUGUESA\"]"));
	}

}
