package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Tax extends DomainEntity {

	public Tax() {
		super();
	}

	// ------------ Attributes ------------
	private Double taxType;
	
	
	@NotNull
	public Double getTaxType() {
		return taxType;
	}

	public void setTaxType(Double taxType) {
		this.taxType = taxType;
	}

	

	// ------------ Relationships ------------

	

	private Collection<Banner> banners;

	@Valid
	@OneToMany(mappedBy="tax", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Collection<Banner> getBanners() {
		return banners;
	}

	public void setBanners(Collection<Banner> banners) {
		this.banners = banners;
	}

}
