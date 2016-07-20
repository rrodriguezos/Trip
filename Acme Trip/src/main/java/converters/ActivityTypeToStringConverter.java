package converters;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ActivityType;

@Component
@Transactional
public class ActivityTypeToStringConverter implements Converter<ActivityType, String> {

	@Override
	public String convert(ActivityType activityType) {
		String result;

		if (activityType == null)
			result = null;
		else
			result = String.valueOf(activityType.getId());

		return result;
	}

}
