package br.com.teste.service;

import br.com.teste.model.Pedido;
import br.com.teste.model.ResumoDoPedido;

public interface PedidoService {

	public ResumoDoPedido buscar(Long id);
	public ResumoDoPedido criar(Pedido pedido);
	public ResumoDoPedido montaResumo(Pedido pedido);
	
}
