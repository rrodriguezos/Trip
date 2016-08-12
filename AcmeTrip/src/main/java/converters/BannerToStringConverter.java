package converters; 

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Banner; 

@Component 
@Transactional 
public class BannerToStringConverter implements Converter<Banner, String>{ 

	@Override 
	public String convert(Banner comment){ 
		String result; 
		if(comment == null){ 
			result = null; 
		}else{ 
			result = String.valueOf(comment.getId()); 
		} 
		return result; 
	} 

} 
