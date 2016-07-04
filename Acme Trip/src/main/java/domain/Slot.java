package domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Slot extends DomainEntity {
	
	public Slot() {
		super();
	}
	
	// ------------ Attributes ------------ 
	private String description;
	private String title;
	private Collection<String> photos;
	private String startTime;
	private String endTime;
	
	
	@NotBlank
	@Pattern(regexp="^([0|1]\\d|2[0-3]):[0-5]\\d$")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	
	@NotBlank
	@Pattern(regexp="^([0|1]\\d|2[0-3]):[0-5]\\d$")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
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
	
	private DailyPlan dailyplan;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public DailyPlan getDailyplan() {
		return dailyplan;
	}

	public void setDailyplan(DailyPlan dailyplan) {
		this.dailyplan = dailyplan;
	}
	
	private Activity activity;

	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
}
