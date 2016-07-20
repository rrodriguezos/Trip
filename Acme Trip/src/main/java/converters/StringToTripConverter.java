package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.TripRepository;

import domain.Trip;

@Component
@Transactional
public class StringToTripConverter implements Converter<String, Trip> {

	@Autowired
	TripRepository tripRepository;

	@Override
	public Trip convert(String text) {
		Trip result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = tripRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}