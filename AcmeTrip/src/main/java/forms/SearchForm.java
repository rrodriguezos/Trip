package forms;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Access(AccessType.PROPERTY)
public class SearchForm {

	private String text;

	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
