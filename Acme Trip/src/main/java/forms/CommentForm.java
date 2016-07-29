package forms;




import org.hibernate.validator.constraints.NotBlank;


public class CommentForm {
	
	//Constructors -----------------------------
	public CommentForm() {
		super();
	}
	
	//Attributes -------------------------------
	private String text;
	private String title;
	
	
	@NotBlank
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
