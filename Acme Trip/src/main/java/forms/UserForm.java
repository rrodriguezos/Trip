package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class UserForm {
	
	public UserForm() {
		super();
	}
	
	//Attributes -------------------------------
	private String name;
	private String surname;
	private String phone;
	private String password;
	private String emailAddress;
		
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
		
	@NotBlank
	@Pattern(regexp = "^\\+(\\d|\\d\\d)(\\(\\d\\)|\\(\\d\\d\\)|\\(\\d\\d\\d\\))?(\\d{4,9})$")
	public String getPhone() {
		return phone;
	}
		
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
