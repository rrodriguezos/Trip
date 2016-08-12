package converters; 

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ChargeRecord; 

@Component 
@Transactional 
public class ChargeRecordToStringConverter implements Converter<ChargeRecord, String>{ 

	@Override 
	public String convert(ChargeRecord comment){ 
		String result; 
		if(comment == null){ 
			result = null; 
		}else{ 
			result = String.valueOf(comment.getId()); 
		} 
		return result; 
	} 

} 
