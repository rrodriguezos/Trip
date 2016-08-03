package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList="title"), @Index(columnList="description")})
public class DailyPlan extends DomainEntity {

	public DailyPlan() {
		super();
	}

	// ------------ Attributes ------------


	private Date weekDay;
	private String description;
	private String title;
	private Collection<String> photos;

	@DateTimeFormat(pattern="dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	public Date getWeekDay() {
		return weekDay;
	}
	public void setWeekDay(Date weekDay) {
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

	
	@Valid
	@NotNull
	@ManyToOne(optional = true)
	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	private Collection<Slot> slots;

	@Valid
	@NotNull
	@OneToMany(cascade={CascadeType.REMOVE,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},mappedBy="dailyplan")
	public Collection<Slot> getSlots() {
		return slots;
	}

	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}
}
