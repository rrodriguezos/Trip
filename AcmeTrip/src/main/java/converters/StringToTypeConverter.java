
package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.TypeRepository;
import domain.ActivityType;

@Component
@Transactional
public class StringToTypeConverter implements Converter<String,ActivityType>{
	
	@Autowired
	TypeRepository typeRepository;

	@Override
	public ActivityType convert(String text) {
		ActivityType result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = typeRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}