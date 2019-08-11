package br.com.teste.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.teste.exception.ResolvedException;
import br.com.teste.model.Pedido;
import br.com.teste.model.ResumoDoPedido;
import br.com.teste.repository.PedidoRepository;

@Service
@Transactional
public class PedidoServiceImpl implements PedidoService {

	private static final String MSG_NAO_EXISTE = "Pedido não existe!";
	
	private PedidoRepository pedidoRepository;
	@Autowired
	public void setPedidoRepository(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}
	
	@Override
	public ResumoDoPedido buscar(Long id) {
		return new ResumoDoPedido(pedidoRepository.findById(id).orElseThrow(() -> new ResolvedException(MSG_NAO_EXISTE)));
	}

	@Override
	public ResumoDoPedido criar(Pedido pedido) {
		return new ResumoDoPedido(pedidoRepository.save(pedido));
	}

	@Override
	public ResumoDoPedido montaResumo(Pedido pedido) {
		return new ResumoDoPedido(pedido);
	}

	
	
}
