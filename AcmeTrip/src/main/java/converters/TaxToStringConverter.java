package converters; 

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tax; 

@Component 
@Transactional 
public class TaxToStringConverter implements Converter<Tax, String>{ 

	@Override 
	public String convert(Tax comment){ 
		String result; 
		if(comment == null){ 
			result = null; 
		}else{ 
			result = String.valueOf(comment.getId()); 
		} 
		return result; 
	} 

} 
