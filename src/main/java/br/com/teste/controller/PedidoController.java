package br.com.teste.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.teste.model.ONCreate;
import br.com.teste.model.Pedido;
import br.com.teste.model.ResumoDoPedido;
import br.com.teste.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('PEDIDO')")
	public ResumoDoPedido burcar(@PathVariable(value="id") Long id) {
		return pedidoService.buscar(id);
	}
	
	@PostMapping
	@Validated(ONCreate.class)
	@PreAuthorize("hasRole('PEDIDO_INCLUIR')")
	public ResumoDoPedido criar(@Valid @RequestBody Pedido pedido) {
		return pedidoService.criar(pedido);
	}
	
	@PutMapping("/montarResumo")
	@PreAuthorize("hasRole('PEDIDO')")
	public ResumoDoPedido montaResumo(@Valid @RequestBody Pedido pedido) {
		return pedidoService.montaResumo(pedido);
	}
	
	
	
}
