package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import com.lowagie.text.Element;

@Entity
@Access(AccessType.PROPERTY)
public class Banner extends DomainEntity {

	public Banner() {
		super();
	}

	// ------------ Attributes ------------

	private String photo;
	private int maxTimesDisplayed;
	private Double priceByDisplayed;
	private Collection<String> keyWords;

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

	public Double getPriceByDisplayed() {
		return priceByDisplayed;
	}

	public void setPriceByDisplayed(Double priceByDisplayed) {
		this.priceByDisplayed = priceByDisplayed;
	}

	@NotNull
	@ElementCollection
	public Collection<String> getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(Collection<String> keyWords) {
		this.keyWords = keyWords;
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

	private Collection<Tax> taxes;

	@Valid
	@ManyToMany
	public Collection<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(Collection<Tax> taxes) {
		this.taxes = taxes;
	}

	private ChargeRecord chargeRecord;

	@Valid
	@OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	public ChargeRecord getChargeRecord() {
		return chargeRecord;
	}

	public void setChargeRecord(ChargeRecord chargeRecord) {
		this.chargeRecord = chargeRecord;
	}

}
