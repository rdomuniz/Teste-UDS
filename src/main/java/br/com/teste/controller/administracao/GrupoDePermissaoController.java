package br.com.teste.controller.administracao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.teste.controller.GeralController;
import br.com.teste.model.ONCreate;
import br.com.teste.model.ONUpdate;
import br.com.teste.model.administracao.GrupoDePermissao;
import br.com.teste.model.dto.administracao.AcessoDTO;
import br.com.teste.model.enums.AcessoDoSistema;
import br.com.teste.model.enums.PermissaoDoSistema;
import br.com.teste.model.filter.administracao.GrupoDePermissaoFilter;
import br.com.teste.model.view.listagem.administracao.GrupoDePermissaoView;
import br.com.teste.service.administracao.GrupoDePermissaoService;

@RestController
@RequestMapping("/gruposDePermissao")
public class GrupoDePermissaoController extends GeralController {

	@Autowired
	private GrupoDePermissaoService grupoDePermissaoService; 
	
	@GetMapping
	@JsonView(GrupoDePermissaoView.class)
	public List<GrupoDePermissao> listar(GrupoDePermissaoFilter filtro) {
		return grupoDePermissaoService.listar(filtro);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('GRUPO_PERMISSAO') OR hasRole('GRUPO_PERMISSAO_INCLUIR') OR hasRole('GRUPO_PERMISSAO_EDITAR')")
	public GrupoDePermissao burcar(@PathVariable(value="id") Long id) {
		return grupoDePermissaoService.buscar(id);
	}
	
	@GetMapping("/permissaos")
	@PreAuthorize("hasRole('GRUPO_PERMISSAO_INCLUIR') OR hasRole('GRUPO_PERMISSAO_EDITAR')")
	public Map<AcessoDoSistema, AcessoDTO> listarPermissaos() {
		Map<AcessoDoSistema, AcessoDTO> permissaos = new LinkedHashMap<>();
		for (PermissaoDoSistema permissao : PermissaoDoSistema.values()) {
			AcessoDTO dto = permissaos.get(permissao.getAcessoDoSistema());
			if(dto == null) {
				dto = new AcessoDTO();
				dto.setDescricao(permissao.getAcessoDoSistema().getDescricao());
				permissaos.put(permissao.getAcessoDoSistema(), dto);
			}
			dto.getPermisoes().put(permissao, permissao.getDescricao());
		}
		return permissaos;
	}
	
	@PostMapping
	@Validated(ONCreate.class)
	@PreAuthorize("hasRole('GRUPO_PERMISSAO_INCLUIR')")
	public GrupoDePermissao criar(@Valid @RequestBody GrupoDePermissao grupoDePermissao) {
		return grupoDePermissaoService.criar(grupoDePermissao);
	}
	
	@PutMapping
	@Validated(ONUpdate.class)
	@PreAuthorize("hasRole('GRUPO_PERMISSAO_EDITAR')")
	public GrupoDePermissao atualizar(@Valid @RequestBody GrupoDePermissao grupoDePermissao) {
		return grupoDePermissaoService.atualizar(grupoDePermissao);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('GRUPO_PERMISSAO_EXCLUIR')")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		grupoDePermissaoService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
}
