package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.hibernate.validator.constraints.NotBlank;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Trip extends Commentable {
	
	public Trip() {
		super();
	}
	
	// ------------ Attributes ------------ 
	
	private Date organisedDate;
	private String description;
	private String title;
	private Collection<String> photos;
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getOrganisedDate() {
		return organisedDate;
	}
	public void setOrganisedDate(Date organisedDate) {
		this.organisedDate = organisedDate;
	}

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


	// ------------ Relationships ------------ 

		private User user;

		@NotNull
		@Valid
		@ManyToOne(optional=false)
		public User getCustomer() {
			return user;
		}

		public void setCustomer(User user) {
			this.user = user;
		}
		
		private Collection<DailyPlan> dailyplans;


		
		@Valid
		@NotNull
		@OneToMany(mappedBy = "trip")
		public Collection<DailyPlan> getDailyplans() {
			return dailyplans;
		}
		
		public void setDailyplans(Collection<DailyPlan> dailyplans) {
			this.dailyplans = dailyplans;
		}
	
	

}
