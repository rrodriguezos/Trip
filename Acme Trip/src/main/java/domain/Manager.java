package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import java.util.Collection;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	// ------------ Attributes ------------

	// ------------ Relationships ------------

	private Collection<CreditCard> creditCards;
	private Collection<Campaign> campaigns;

	private Collection<Activity> activities;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Collection<Activity> activities) {
		this.activities = activities;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<CreditCard> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(Collection<CreditCard> creditCards) {
		this.creditCards = creditCards;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Campaign> getCampaigns() {
		return campaigns;
	}

	public void setCampaigns(Collection<Campaign> campaigns) {
		this.campaigns = campaigns;
	}

}
