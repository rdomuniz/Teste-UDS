package br.com.teste.exception;

public class TesteUDSException extends RuntimeException {

	private static final long serialVersionUID = -1559309002274274359L;

	public TesteUDSException(String erro) {
		super(erro);
	}

	public TesteUDSException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
