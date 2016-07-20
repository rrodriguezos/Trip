package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DailyPlanRepository;

import domain.DailyPlan;

@Component
@Transactional
public class StringToDailyPlanConverter implements Converter<String, DailyPlan> {

	@Autowired
	DailyPlanRepository dailyPlanRepository;

	@Override
	public DailyPlan convert(String text) {
		DailyPlan result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = dailyPlanRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
