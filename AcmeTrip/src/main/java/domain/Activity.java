package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList="appropriated") })
public class Activity extends Commentable {

	//Constructor ----------------------------------------------
	public Activity(){
		super();
	}
	
	//Attributes -------------------------------------------------
	private String title;
	private String description;
	private Collection<String> photos;
	private boolean appropriated;
	
	@NotBlank
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@ElementCollection
	public Collection<String> getPhotos() {
		return photos;
	}
	public void setPhotos(Collection<String> photos) {
		this.photos = photos;
	}
	
	public boolean getAppropriated() {
		return appropriated;
	}
	
	public void setAppropriated(boolean appropriated) {
		this.appropriated = appropriated;
	}
	
	//Relationships ---------------------------------------------------
	private User user;
	private Type type;
	private Collection<Slot> slots;
	private Manager manager;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="activity")
	public Collection<Slot> getSlots() {
		return slots;
	}
	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}
	
	@Valid
	@ManyToOne(optional = true)
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
