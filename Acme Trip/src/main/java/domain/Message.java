package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.EnumType;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes={@Index(columnList=("messagePriority"))})
public class Message extends DomainEntity {
	
	//Constructors -----------------------------
			public Message() {
				super();
			}
			
			//Attributes -------------------------------
			
			public enum MessagePriority
			{
				LOW,NEUTRAL,HIGH;
			}
			private MessagePriority messagePriority;
			private String subject;
			private String body;
			private Date moment;
			private boolean star;

			
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
			@Enumerated(EnumType.STRING)
			public MessagePriority getMessagePriority() {
				return messagePriority;
			}

			public void setMessagePriority(MessagePriority messagePriority) {
				this.messagePriority = messagePriority;
			}
			
			public boolean getStar() {
				return star;
			}
			
			public void setStar(boolean star) {
				this.star = star;
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
