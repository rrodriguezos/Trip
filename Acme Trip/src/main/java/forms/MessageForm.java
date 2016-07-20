package forms;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

public class MessageForm {
	
	//Constructors -----------------------------
		public MessageForm() {
			super();
		}
			
		//Attributes -------------------------------
		private String subject;
		private String body;
		private int recipient;
		
			

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

}
