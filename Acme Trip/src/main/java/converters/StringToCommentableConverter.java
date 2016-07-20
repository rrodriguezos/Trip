package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.CommentableRepository;
import domain.Commentable;

public class StringToCommentableConverter implements Converter<String, Commentable> {

	@Autowired
	CommentableRepository commentableRepository;

	@Override
	public Commentable convert(String text) {
		Commentable result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = commentableRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
