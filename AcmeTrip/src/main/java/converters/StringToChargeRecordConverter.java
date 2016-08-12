package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ChargeRecord;
import repositories.ChargeRecordRepository;

@Component
@Transactional
public class StringToChargeRecordConverter implements Converter<String, ChargeRecord> {

	@Autowired
	ChargeRecordRepository campaignRepository;

	@Override
	public ChargeRecord convert(String text) {
		ChargeRecord result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = campaignRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
