package br.com.teste.controller.administracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.controller.GeralController;
import br.com.teste.model.dto.administracao.AtribuicaoDePermissaoDTO;
import br.com.teste.service.administracao.AtribuiaoDePermissaoService;

@RestController
@RequestMapping("/atribuicaoDePermissoes")
public class AtribuicaoDePermicaoController extends GeralController {

	@Autowired
	private AtribuiaoDePermissaoService atribuiaoDePermissaoService; 
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ATRIBUICAO_PERMISSAO_CONFIGURAR')")
	public AtribuicaoDePermissaoDTO burcar(@PathVariable(value="id") Long id) {
		return atribuiaoDePermissaoService.buscar(id);
	}
	
	@PutMapping
	@PreAuthorize("hasRole('ATRIBUICAO_PERMISSAO_CONFIGURAR')")
	public AtribuicaoDePermissaoDTO atualizar(@RequestBody AtribuicaoDePermissaoDTO atribuicaoDeAcesso) {
		return atribuiaoDePermissaoService.atualizar(atribuicaoDeAcesso);
	}
	
}