package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class DailyPlan extends DomainEntity {
	
	public DailyPlan() {
		super();
	}
	
	// ------------ Attributes ------------ 
	
	public enum WeekDay
	{
		MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY;
	}
	private WeekDay weekDay;
	private String description;
	private String title;
	private Collection<String> photos;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	public WeekDay getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(WeekDay weekDay) {
		this.weekDay = weekDay;
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

	private Trip trip;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	private Collection<Slot> slots;


	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "dailyplan")
	public Collection<Slot> getSlots() {
		return slots;
	}
	
	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}
}
