package br.com.teste.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResolvedException extends TesteUDSException {

	private static final long serialVersionUID = -1559309002274274359L;

	public ResolvedException(String erro) {
		super(erro);
	}
	
}
