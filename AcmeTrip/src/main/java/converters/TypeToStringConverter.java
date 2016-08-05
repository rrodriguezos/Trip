
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.ActivityType;

@Component
@Transactional
public class TypeToStringConverter implements Converter<ActivityType,String>{

	@Override
	public String convert(ActivityType type) {
		String result;
		if(type == null){
			result = null;
		}else{
			result = String.valueOf(type.getId());
		}
		return result;
	}
}