package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Activity extends Commentable {
	
	// ------------ Attributes ------------ 
		private String description;
		private String title;
		private Collection<String> photos;
		private Boolean isAppropiate;
		
		
		@NotBlank
		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		@NotBlank
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		
		@ElementCollection
		public Collection<String> getPhotos() {
			return photos;
		}

		public void setPhotos(Collection<String> photos) {
			this.photos = photos;
		}

		@NotNull
		public Boolean getIsAppropiate() {
			return isAppropiate;
		}

		public void setIsAppropiate(Boolean isAppropiate) {
			this.isAppropiate = isAppropiate;
		}
		
		//Relationships --------------------------
		
		private Collection<Slot> slots;
		
		@Valid
		@NotNull
		@OneToMany(mappedBy = "activity")
		public Collection<Slot> getSlots() {
			return slots;
		}
		
		public void setSlots(Collection<Slot> slots) {
			this.slots = slots;
		}
		
		
		private ActivityType activityType;

		@NotNull
		@Valid
		@ManyToOne(optional=false)
		public ActivityType getActivityType() {
			return activityType;
		}

		public void setActivityType(ActivityType activityType) {
			this.activityType = activityType;
		}
		
		private User user;

		@NotNull
		@Valid
		@ManyToOne(optional=false)
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
}
