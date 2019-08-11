package br.com.teste.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class RelogioImpl implements Relogio {

	@Override
	public Date hoje() {
		return new Date();
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
	
	@Override
	public int horaAtualSemMinutos() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(hoje());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
}
