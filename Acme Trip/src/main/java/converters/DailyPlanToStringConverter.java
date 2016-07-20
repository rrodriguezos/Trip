package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.DailyPlan;

@Component
@Transactional
public class DailyPlanToStringConverter implements Converter<DailyPlan, String> {

	@Override
	public String convert(DailyPlan dailyPlan) {
		String result;

		if (dailyPlan == null)
			result = null;
		else
			result = String.valueOf(dailyPlan.getId());

		return result;
	}

}
