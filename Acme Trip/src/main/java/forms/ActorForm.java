package forms;


import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ActorForm {
	
	//Attributes -------------------------------
	private String name;
	private String surname;
	private String phone;
	private String emailAddress;
	private String confirmPassword;
	
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
	@Size(min = 5, max = 32)
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
