package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tax extends DomainEntity {

	public Tax() {
		super();
	}

	// ------------ Attributes ------------

	@NotNull
	public Double getTaxType() {
		return taxType;
	}

	public void setTaxType(Double taxType) {
		this.taxType = taxType;
	}

	private Double taxType;

	// ------------ Relationships ------------

	private Collection<ChargeRecord> chargeRecords;

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "taxes")
	public Collection<ChargeRecord> getChargeRecords() {
		return chargeRecords;
	}

	public void setChargeRecords(Collection<ChargeRecord> chargeRecords) {
		this.chargeRecords = chargeRecords;
	}

	private Collection<Banner> banners;

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "taxes")
	public Collection<Banner> getBanners() {
		return banners;
	}

	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}

}
