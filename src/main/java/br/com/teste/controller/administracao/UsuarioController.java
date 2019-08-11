package br.com.teste.controller.administracao;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import br.com.teste.model.UserAccount;
import br.com.teste.model.administracao.Usuario;
import br.com.teste.model.filter.administracao.UsuarioFilter;
import br.com.teste.model.view.listagem.administracao.UsuarioView;
import br.com.teste.service.administracao.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController extends GeralController {

	@Autowired
	private UsuarioService usuarioService; 
	
	@GetMapping
	@JsonView(UsuarioView.class)
	public List<Usuario> listar(UsuarioFilter filtro) {
		return usuarioService.listar(filtro);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USUARIO') OR hasRole('USUARIO_EDITAR')")
	public Usuario buscar(@PathVariable(value = "id") Long id) {
		Usuario usuario = usuarioService.buscar(id);
		preparaParaTela(usuario);
		return usuario;
	}
	
	@PostMapping
	@Validated(ONCreate.class)
	@PreAuthorize("hasRole('USUARIO_INCLUIR')")
	public Usuario criar(@Valid @RequestBody Usuario usuario) {
		usuario = usuarioService.criar(usuario);
		preparaParaTela(usuario);
		return usuario;
	}
	
	@PutMapping
	@Validated(ONUpdate.class)
	@PreAuthorize("hasRole('USUARIO_EDITAR')")
	public Usuario atualizar(@Valid @RequestBody Usuario usuario) {
		usuario = usuarioService.atualizar(usuario);
		preparaParaTela(usuario);
		return usuario;
	}
	
	@PutMapping("/senha/resetar")
	@PreAuthorize("hasRole('USUARIO_EDITAR')")
	public Usuario resetarSenha(@RequestBody Usuario usuario) {
		usuario = usuarioService.atualizarSenha(usuario);
		preparaParaTela(usuario);
		return usuario;
	}

	@PutMapping("/senha/atualizar")
	public Usuario atualizarSenha(@RequestBody Usuario usuario) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		usuario.setId(((UserAccount)authentication.getPrincipal()).getId());
		usuario = usuarioService.atualizarSenha(usuario);
		preparaParaTela(usuario);
		return usuario;
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USUARIO_EXCLUIR')")
	public ResponseEntity<?> excluir(@PathVariable(value = "id") Long id) {
		usuarioService.excluir(id);
		return ResponseEntity.ok().build();
	}
	
	private void preparaParaTela(Usuario usuario) {
		usuario.setGrupos(null);
	}
	
}
