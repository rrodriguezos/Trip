package converters; 

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Banner;
import repositories.BannerRepository; 

@Component 
@Transactional 
public class StringToBannerConverter implements Converter<String, Banner>{ 

	@Autowired 
	BannerRepository campaignRepository; 

	@Override 
	public Banner convert(String text){ 
		Banner result; 
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
