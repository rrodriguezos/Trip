package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;


@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.confirmPassword)")
public class UserRegisterForm {
	
	
	public UserRegisterForm() {
		super();
	}

	// Attributes -------------------------------------------------------------
	private String name;
	private String surname;
	private String phone;
	private String emailAddress;
	private String username;
	private String password;
	private String confirmPassword;
		
	// Getters Setters----------------------------------------------------

	@NotBlank
	public String getName() {
		return name;
	}		
	public void setName(String name) {
		this.name = name;
	}		
	@NotBlank
	public String getSurname() {
		return surname;
	}		
	public void setSurname(String surname) {
		this.surname = surname;
	}		

	@Pattern(regexp = "^\\+(\\d|\\d\\d)(\\(\\d\\)|\\(\\d\\d\\)|\\(\\d\\d\\d\\))?(\\d{4,9})$")
	public String getPhone() {
		return phone;
	}		
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotBlank
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
	@NotBlank
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@NotBlank
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Email
	@NotBlank
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	

}
