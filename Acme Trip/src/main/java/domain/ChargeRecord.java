package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Collection;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
public class ChargeRecord extends DomainEntity  {

	public ChargeRecord() {
		super();
	}
	
	// ------------ Attributes ------------ 
	
	private Date createMoment;
	private double amountMoney;
	
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreateMoment() {
		return createMoment;
	}
	public void setCreateMoment(Date createMoment) {
		this.createMoment = createMoment;
	}
	
	@Min(0)
	@Digits(integer=6,fraction=2)
	public double getAmountMoney() {

//		if (banner.getCampaign().getIsActive()== false) {
//			amountMoney=0;
//		}else{
//			double taxPlus = banner.getPriceByDisplayed() * (banner.getTax().getTaxType()/100);
//			double onesPlusTax = taxPlus + banner.getPriceByDisplayed();
//			
//			
//		}
		return amountMoney;
	}
	public void setAmountMoney(double amountMoney) {
		this.amountMoney = amountMoney;
	}
	
	
	
	
	// Relationships ----------------------------------------------------------
	
	private CreditCard creditCard;
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	public CreditCard getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
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
	
	private Banner banner;
	
	@Valid
	@NotNull
	@OneToOne(optional = false, mappedBy = "chargeRecord")
	public Banner getBanner() {
		return banner;
	}
	public void setBanner(Banner banner) {
		this.banner = banner;
	}
	
	
	
	
}
