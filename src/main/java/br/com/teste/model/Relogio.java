package br.com.teste.model;

import java.util.Date;

public interface Relogio {

	public Date hoje();
	public Date hojeSemHora();
	public Date ontem();
	public int horaAtualSemMinutos();

}
