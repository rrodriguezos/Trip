package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor{

	// Constructors -----------------------------------------------------------
	public Manager(){
		super();
	}
	
	//Relationships ---------------------------------------------------
	private Collection<Activity> activities;
	
	@Valid
	@NotNull
	@OneToMany(mappedBy="manager")
	public Collection<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
}
