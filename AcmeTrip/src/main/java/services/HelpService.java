package services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Transactional
public class HelpService {

	// Método que dado un tipo Date y un string con la hora y minutos, los junta
	// y lo devuelve en el formato yyyy/MM/dd HH:mm
	public String completeDate(Date date, String starting) {
		String result;
		DateFormat format;

		format = new SimpleDateFormat("yyyy/MM/dd");
		result = format.format(date);
		result += " " + starting;

		return result;
	}

	// Dado un String devuelve una fecha formateada
	public Date formatStringToDate(String date) {
		Date result = null;
		DateFormat format;

		format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		try {
			result = format.parse(date);
		} catch (ParseException e) {
			Assert.hasText("No se ha podido formatear la fecha");
		}

		return result;
	}

	// Dado un String devuelve una fecha formateada
	public Date formatStringToDateWithoutHour(String date) {
		Date result = null;
		DateFormat format;

		format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			result = format.parse(date);
		} catch (ParseException e) {
			Assert.hasText("No se ha podido formatear la fecha");
		}

		return result;
	}

	// Dado un Double de horas, devuelve un Integer con los minutos
	// correspondientes
	public Integer hoursToMinutes(Double hours) {
		Integer result;

		result = (int) Math.round(hours * 60);

		return result;
	}
}
