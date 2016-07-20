package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Slot;

@Component
@Transactional
public class SlotToStringConverter implements Converter<Slot, String> {

	@Override
	public String convert(Slot slot) {
		String result;

		if (slot == null)
			result = null;
		else
			result = String.valueOf(slot.getId());

		return result;
	}

}