package br.com.teste.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
public class RelogioMock implements Relogio {
	
	private Date hoje;
	
	public RelogioMock() {
		reseta();
	}
 	
	public RelogioMock(Date hoje) {
		this.hoje = hoje;
	}

	@Override
	public Date hoje() {
		return hoje;
	}
	
	@Override
	public int horaAtualSemMinutos() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hoje());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	@Override
	public Date hojeSemHora() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(format.format(hoje()));
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public Date ontem() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return format.parse(format.format(DateUtils.addDays(hoje(), -1)));
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}
	
	public void reseta() {
		this.hoje = new Date();
	}

}
