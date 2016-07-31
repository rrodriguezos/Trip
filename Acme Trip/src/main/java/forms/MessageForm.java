package forms;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

import domain.Message.MessagePriority;

public class MessageForm {
	
	//Constructors -----------------------------
		public MessageForm() {
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
		private int recipient;
		private boolean star;
		
			

		@NotBlank
		public String getSubject() {
			return subject;
		}
			
		public void setSubject(String subject) {
			this.subject = subject;
		}

		@NotBlank
		public String getBody() {
			return body;
		}		
		public void setBody(String body) {
			this.body = body;
		}
		
		@Min(0)
		public int getRecipient() {
			return recipient;
		}
		public void setRecipient(int recipient) {
			this.recipient = recipient;
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

}
