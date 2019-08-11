package br.com.teste.exception;

public class TesteException extends RuntimeException {

	private static final long serialVersionUID = -1559309002274274359L;

	public TesteException(String erro) {
		super(erro);
	}

	public TesteException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
