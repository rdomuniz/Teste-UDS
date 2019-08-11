package br.com.teste;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import br.com.teste.service.administracao.AtribuiaoDePermissaoService;
import br.com.teste.service.administracao.GrupoDePermissaoService;
import br.com.teste.service.administracao.UsuarioService;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public abstract class ControllerTests {

	@Autowired 
	protected MockMvc mvc;
	
	@MockBean protected AtribuiaoDePermissaoService atribuiaoDePermissaoService;
	@MockBean protected GrupoDePermissaoService grupoDePermissaoService;
	@MockBean protected UsuarioService usuarioService;
	
}
