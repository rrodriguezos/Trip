package forms;
import javax.persistence.Column;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


public class RegisterForm extends ActorForm {
	
	//Attributes -------------------------------
	private String username;
	private String password;

	@NotBlank
	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@NotBlank
	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
