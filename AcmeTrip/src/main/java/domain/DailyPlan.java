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
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = { @Index(columnList="title"), @Index(columnList="description")})
public class DailyPlan extends DomainEntity {

	//Constructor ----------------------------------------------
	public DailyPlan(){
		super();
	}
	
	//Attributes -------------------------------------------------
	private Date weekDay;
	private String title;
	private String description;
	private Collection<String> photos;
	
	@NotBlank
	@SafeHtml(whitelistType=WhiteListType.NONE)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
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
	@SafeHtml(whitelistType=WhiteListType.NONE)
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
	
	//Relationships ---------------------------------------------------
	private Trip trip;
	private Collection<Slot> slots;
	
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Trip getTrip() {
		return trip;
	}
	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	@Valid
	@NotNull
	@OneToMany(cascade={CascadeType.REMOVE,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH},mappedBy="dailyPlan")
	public Collection<Slot> getSlots() {
		return slots;
	}
	public void setSlots(Collection<Slot> slots) {
		this.slots = slots;
	}
	
}
