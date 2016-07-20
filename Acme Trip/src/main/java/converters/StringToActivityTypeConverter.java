package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ActivityTypeRepository;

import domain.ActivityType;

@Component
@Transactional
public class StringToActivityTypeConverter implements Converter<String, ActivityType> {

	@Autowired
	ActivityTypeRepository activityTypeRepository;

	@Override
	public ActivityType convert(String text) {
		ActivityType result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = activityTypeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
