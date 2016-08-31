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
public class HelpDatesConvertService {

	public String fechaCompleta(Date date, String starting) {
		String result;
		DateFormat format;

		format = new SimpleDateFormat("yyyy/MM/dd");
		result = format.format(date);
		result += " " + starting;

		return result;
	}

	public Date formatFromStringToDate(String date) {
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

	public Integer hoursToMinutes(Double hours) {
		Integer result;

		result = (int) Math.round(hours * 60);

		return result;
	}
}
