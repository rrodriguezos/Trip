package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import repositories.CommentRepository;
import domain.Comment;

public class StringToCommentConverter implements Converter<String, Comment> {

	@Autowired
	CommentRepository commentRepository;

	@Override
	public Comment convert(String text) {
		Comment result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = commentRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
