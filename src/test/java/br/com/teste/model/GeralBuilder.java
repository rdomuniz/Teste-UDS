package br.com.teste.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeralBuilder {

	public static Date getData(String value) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy").parse(value);
		} catch (Exception e) {}
		return null;
	}
	
	public static Date getDataComHora(String value) {
		try {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(value);
		} catch (Exception e) {}
		return null;
	}
	
}
