package forms;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;


public class CommentForm {
	
	//Constructors -----------------------------
	public CommentForm() {
		super();
	}
	
	//Attributes -------------------------------
	private String text;
	private Boolean isAppropiate;
	private String title;
	
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	@NotNull
	public Boolean getIsAppropiate() {
		return isAppropiate;
	}
	public void setIsAppropiate(Boolean isAppropiate) {
		this.isAppropiate = isAppropiate;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
