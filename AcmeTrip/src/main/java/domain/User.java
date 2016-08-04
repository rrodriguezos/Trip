package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class User extends Actor {

	// Constructors...................
	public User() {
		super();
	}
	//Attributes ----------------------------------------------------------

	
		//Relationships --------------------------------------------------------
		private Collection<Trip> trips;
		private Collection<Activity> activities;

		@Valid
		@NotNull
		@OneToMany(mappedBy="user")
		public Collection<Trip> getTrips() {
			return trips;
		}
		public void setTrips(Collection<Trip> trips) {
			this.trips = trips;
		}
		
		@Valid
		@NotNull
		@OneToMany(mappedBy="user")
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