package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;


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
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getName() {
		return name;
	}		
	public void setName(String name) {
		this.name = name;
	}		
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getSurname() {
		return surname;
	}		
	public void setSurname(String surname) {
		this.surname = surname;
	}		

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@Pattern(regexp="(\\+[0-9]{1,2} ([0-9]{0,3} [0-9]{4,9}|[0-9]{4,9})|^$)")
	public String getPhone() {
		return phone;
	}		
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	@Email
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	

}
