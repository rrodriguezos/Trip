package converters; 

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Tax;
import repositories.TaxRepository; 

@Component 
@Transactional 
public class StringToTaxConverter implements Converter<String, Tax>{ 

	@Autowired 
	TaxRepository campaignRepository; 

	@Override 
	public Tax convert(String text){ 
		Tax result; 
		int id; 

		try{ 
			if(StringUtils.isEmpty(text)){ 
				result = null; 
			}else{ 
				id = Integer.valueOf(text); 
				result = campaignRepository.findOne(id); 
			} 
		}catch (Throwable oops) { 
			throw new IllegalArgumentException(oops); 
		} 

		return result; 
	} 

} 
