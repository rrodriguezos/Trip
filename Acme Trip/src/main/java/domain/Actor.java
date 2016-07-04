package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import security.UserAccount;

@Entity
public abstract class Actor extends DomainEntity {
	
	//Constructors -----------------------------
		public Actor() {
			super();
		}
	
	//Attributes -------------------------------
		private String name;
		private String surname;
		private String phone;
		
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
		//@Pattern(regexp = "^(\\d{9})$")
		public String getPhone() {
			return phone;
		}
		
		public void setPhone(String phone) {
			this.phone = phone;
		}
		//Relationships --------------------------
		private UserAccount userAccount;
		private Collection <Folder> folders;
		private Collection<Comment> comments;
		
		@NotNull
		@Valid
		@OneToOne(cascade = CascadeType.ALL, optional=false)
		public UserAccount getUserAccount() {
			return userAccount;
		}
		
		public void setUserAccount(UserAccount userAccount) {
			this.userAccount = userAccount;
		}

		@Valid
		@NotNull
		@NotEmpty
		@OneToMany(cascade = CascadeType.ALL, mappedBy="actor")
		public Collection <Folder> getFolders() {
			return folders;
		}

		public void setFolders(Collection <Folder> folders) {
			this.folders = folders;
		}
		
		@Valid
		@NotNull
		@OneToMany(mappedBy = "actor")
		public Collection<Comment> getComments() {
			return comments;
		}
		
		public void setComments(Collection<Comment> comments) {
			this.comments = comments;
		}

}
