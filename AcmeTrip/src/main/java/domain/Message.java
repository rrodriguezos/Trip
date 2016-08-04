package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList="favorite"), @Index(columnList="priority")})
public class Message extends DomainEntity implements Cloneable{

	// Constructors -----------------------------------------------------------
	public Message(){
		super();
	}
	
	// Attributes -------------------------------------------------------------
	private String subject;
	private String body;
	private Date moment;
	private Integer priority;
	private boolean favorite;
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	@SafeHtml(whitelistType=WhiteListType.NONE)
	@NotBlank
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
	@DateTimeFormat(pattern="dd/MM/yyyy HH:mm")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@Past
	public Date getMoment() {
		return moment;
	}
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotNull
	@Range(min=0, max=2)
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public boolean getFavorite() {
		return favorite;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}

	// Relationships ------------------------------------------------------------
	private Actor sender;
	private Actor recipient;
	private Folder folder;
	
	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Actor getSender() {
		return sender;
	}
	public void setSender(Actor sender) {
		this.sender = sender;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Actor getRecipient() {
		return recipient;
	}
	public void setRecipient(Actor recipient) {
		this.recipient = recipient;
	}
	
	@ManyToOne(optional=false)
	@NotNull
	@Valid
	public Folder getFolder() {
		return folder;
	}
	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
	//Clone --------------------------------------------------------------------
	public Message clone(){
		Message copia = null;
		try{
			copia= (Message) super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return copia;
	}
}
