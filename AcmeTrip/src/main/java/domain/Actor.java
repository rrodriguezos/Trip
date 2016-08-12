package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import security.UserAccount;



@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String name;
	private String surname;
	private String phone;
	private String emailAddress;
	

	// Constructors...................

	public Actor() {
		super();
	}

	// Getters and Setters........


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

	@SafeHtml(whitelistType=WhiteListType.NONE)
	@Pattern(regexp="(\\+[0-9]{1,2} ([0-9]{0,3} [0-9]{4,9}|[0-9]{4,9})|^$)") 
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


	// Relationships ----------------------------------------------------------
	private Collection<Folder> folders;
	private UserAccount userAccount;
	private Collection<Comment> comments;

	@OneToMany(mappedBy="actor")
	@NotNull
	@Valid
	public Collection<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Collection<Folder> folders) {
		this.folders = folders;
	}
	
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@NotNull
	@Valid
	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="actor")
	public Collection<Comment> getComments() {
		return comments;
	}
	
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}

}
