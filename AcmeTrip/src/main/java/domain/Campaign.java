package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Campaign extends DomainEntity {
	
	public Campaign() {
		super();
	}
	
	// ------------ Attributes ------------ 
	
	private Date endMoment;
	private Date startMoment;
	private Boolean isActive;
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndMoment() {
		return endMoment;
	}
	public void setEndMoment(Date endMoment) {
		this.endMoment = endMoment;
	}
	
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return startMoment;
	}
	public void setStartMoment(Date startMoment) {
		this.startMoment = startMoment;
	}
	
	@NotNull
	public Boolean getIsActive() {
		return isActive;
	}
	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	// Relationships ----------------------------------------------------------
	
	
	private Manager manager;
	private Collection<Banner> banners;
	private CreditCard creditCard;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}
	
	
	@Valid
	@NotNull
	@OneToMany(mappedBy = "campaign")
	public Collection<Banner> getBanners() {
		return banners;
	}
	
	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}
	
	
	@NotNull
	@Valid
	@OneToOne(optional=true)
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	
	

}
