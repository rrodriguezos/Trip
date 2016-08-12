package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	public Banner() {
		super();
	}

	// ------------ Attributes ------------

	private String photo;
	private int maxTimesDisplayed;
	private Collection<String> keyWords;
	private int display;
	private double price;

	@URL
	@NotNull
	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Range(min = 1)
	public int getMaxTimesDisplayed() {
		return maxTimesDisplayed;
	}

	public void setMaxTimesDisplayed(int maxTimesDisplayed) {
		this.maxTimesDisplayed = maxTimesDisplayed;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Collection<String> keyWords) {
		this.keyWords = keyWords;
	}
	
	@Min(0)
	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	@Min(0)
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// Relationships ----------------------------------------------------------

	private Campaign campaign;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Campaign getCampaign() {
		return campaign;
	}

	public void setCampaign(Campaign campaign) {
		this.campaign = campaign;
	}

	private Tax tax;

	@Valid
	@ManyToOne(optional = true, targetEntity = Tax.class, cascade = CascadeType.ALL)
	public Tax getTax() {
		return tax;
	}

	public void setTax(Tax tax) {
		this.tax = tax;
	}

	private Collection<ChargeRecord> chargeRecords;

	@Valid
	@OneToMany(mappedBy = "banner", cascade = CascadeType.ALL)
	public Collection<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(Collection<ChargeRecord> chargeRecord) {
		this.chargeRecords = chargeRecord;
	}

	

}
