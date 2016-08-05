package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList="systemFolder"), @Index(columnList="name") })
public class Folder extends DomainEntity {
	
	//Constructor ------------------------------------------------------------
	public Folder(){
		super();
	}
	
	//Attributes --------------------------------------------------------------
	private String name;
	private boolean systemFolder;
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean getSystemFolder() {
		return systemFolder;
	}
	
	public void setSystemFolder(boolean systemFolder) {
		this.systemFolder = systemFolder;
	}
	
	//Relationships -----------------------------------------------------------
	private Actor actor;
	private Collection<Message> messages;
		
	@ManyToOne(optional=false)
	@Valid
	@NotNull
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	
	@NotNull
	@OneToMany(mappedBy="folder", cascade=CascadeType.ALL)
	@Valid
	public Collection<Message> getMessages() {
		return messages;
	}
	public void setMessages(Collection<Message> messages) {
		this.messages = messages;
	}
		
}
