package forms;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.ScriptAssert;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
@ScriptAssert(lang = "javascript", script = "_this.password.equals(_this.confirmPassword)")
public class UserRegisterForm {

	private String password;
	private String passwordPast;
	private String confirmPassword;
	private String username;
	private String name;
	private String surname;
	private String phone;
	private String emailAddress;
	private Boolean accept;
	private Integer id;

	// Constructors...................

	public UserRegisterForm() {
		super();
		id = 0;
	}

	// Getters and Setters........

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	@Size(min = 5, max = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	@Size(min = 5, max = 32)
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPasswordPast(){
		return passwordPast;
	}
	
	public void setPasswordPast(String passwordPast){
		this.passwordPast = passwordPast;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@Pattern(regexp="(\\+[0-9]{1,2} ([(][0-9]{0,3}[)] [0-9]{4,9}|[0-9]{4,9})|^$)")
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	@Email
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	@NotNull
	public Boolean getAccept() {
		return accept;
	}

	public void setAccept(Boolean accept) {
		this.accept = accept;
	}

}
