package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Type extends DomainEntity {

	//Constructor ----------------------------------------------
	public Type(){
		super();
	}
	
	//Attributes -------------------------------------------------
	private String name;
	
	@NotBlank
	@Column(unique=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	//Relationships ---------------------------------------------------
	private Collection<Activity> activities;

	@Valid
	@NotNull
	@OneToMany(mappedBy="type")
	public Collection<Activity> getActivities() {
		return activities;
	}
	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
	
}
