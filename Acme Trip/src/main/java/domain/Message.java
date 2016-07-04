package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={
		@Index(columnList=("spam")),
		@Index(columnList=("messageState"))})
public class Message extends DomainEntity {
	
	//Constructors -----------------------------
			public Message() {
				super();
			}
			
			//Attributes -------------------------------
			
			public enum MessageState
			{
				LOW,NEUTRAL,HIGH;
			}
			private MessageState messageState;
			private String subject;
			private String body;
			private Date moment;
			private Boolean spam;
			
			@Past
			@NotNull
			@Temporal(TemporalType.TIMESTAMP)
			@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
			public Date getMoment() {
				return moment;
			}
			public void setMoment(Date moment) {
				this.moment = moment;
			}
			
			@NotBlank
			public String getSubject() {
				return subject;
			}		
			public void setSubject(String subject) {
				this.subject = subject;
			}
			@SafeHtml(whitelistType = WhiteListType.NONE)
			@NotBlank
			public String getBody() {
				return body;
			}
			
			public void setBody(String body) {
				this.body = body;
			}
			
			@NotNull
			public Boolean getSpam() {
				return spam;
			}
			public void setSpam(Boolean spam) {
				this.spam = spam;
			}
			
			@NotNull
			@Enumerated(EnumType.STRING)
			public MessageState getMessageState() {
				return messageState;
			}

			public void setMessageState(MessageState messageState) {
				this.messageState = messageState;
			}

			
			
			//Relationships --------------------------
			
			private Folder folder;
			private Actor sender;
			private Actor recipient;
			
			
			@NotNull
			@Valid
			@ManyToOne(optional=false)
			public Actor getSender() {
				return sender;
			}
			public void setSender(Actor sender) {
				this.sender = sender;
			}

			@NotNull
			@Valid
			@ManyToOne(optional=false)
			public Actor getRecipient() {
				return recipient;
			}
			public void setRecipient(Actor recipient) {
				this.recipient = recipient;
			}

			@NotNull
			@Valid
			@ManyToOne(optional=false)
			public Folder getFolder() {
				return folder;
			}
			public void setFolder(Folder folder) {
				this.folder = folder;
			}


}
