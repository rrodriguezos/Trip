package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	public User() {
		super();
	}

	// Attributes -------------------------------------------------------------

	// Getters Setters----------------------------------------------------

	// Relationships ----------------------------------------------------------
	private Collection<Trip> trips;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Collection<Trip> trips) {
		this.trips = trips;
	}

	private Collection<Activity> activities;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "user")
	public Collection<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}
	
	private Collection<Trip> tripSubscribes;
	
	@Valid
	@ManyToMany(mappedBy = "users")
	public Collection<Trip> getTripSubscribes() {
		return tripSubscribes;
	}

	public void setTripSubscribes(Collection<Trip> tripSubscribes) {
		this.tripSubscribes = tripSubscribes;
	}

}
