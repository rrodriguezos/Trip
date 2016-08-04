
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Type;

@Component
@Transactional
public class TypeToStringConverter implements Converter<Type,String>{

	@Override
	public String convert(Type type) {
		String result;
		if(type == null){
			result = null;
		}else{
			result = String.valueOf(type.getId());
		}
		return result;
	}
}