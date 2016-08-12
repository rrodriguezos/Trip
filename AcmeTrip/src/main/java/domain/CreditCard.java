package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class CreditCard extends DomainEntity {
	
	// Constructors -----------------------------------------------------------
	
		public CreditCard() {
			super();
		}

		// Attributes -------------------------------------------------------------
		
		private String holderName;
		private String brandName;
		private String creditCardNumber;
		private int expirationMonth;
		private int expirationYear;
		private int cvvCode;

		@NotBlank
		public String getHolderName(){
			return holderName;
		}
		public void setHolderName(String holderName){
			this.holderName = holderName;
		}
		@NotBlank
		public String getBrandName(){
			return brandName;
		}
		public void setBrandName(String brandName){
			this.brandName = brandName;
		}
		@NotBlank
		@CreditCardNumber
		public String getCreditCardNumber(){
			return creditCardNumber;
		}
		public void setCreditCardNumber(String creditCardNumber){
			this.creditCardNumber = creditCardNumber;
		}
		
		@Range(min = 1, max = 12)
		public int getExpirationMonth(){
			return expirationMonth;
		}
		public void setExpirationMonth(int expirationMonth){
			this.expirationMonth = expirationMonth;
		}
		
		@Range(min = 2016, max = 9999)
		public int getExpirationYear(){
			return expirationYear;
		}
		public void setExpirationYear(int expirationYear){
			this.expirationYear = expirationYear;
		}
		@Range(min = 100, max = 999)
		public int getCvvCode(){
			return cvvCode;
		}
		public void setCvvCode(int cvvCode){
			this.cvvCode = cvvCode;
		}

		// Relationships ----------------------------------------------------------
		
		private Manager manager;
		
		@Valid
		@ManyToOne(optional=false)
		public Manager getManager() {
			return manager;
		}

		public void setManager(Manager manager) {
			this.manager = manager;
		}
		
		private Collection<ChargeRecord> chargeRecords;
		
		@Valid
		@OneToMany(mappedBy = "creditCard")
		public Collection<ChargeRecord> getChargeRecords() {
			return chargeRecords;
		}
		
		public void setChargeRecords(Collection<ChargeRecord> chargeRecords) {
			this.chargeRecords = chargeRecords;
		}
		

		private Campaign campaign;
		
		@Valid
		@OneToOne(optional=true)
		public Campaign getCampaign() {
			return campaign;
		}
		public void setCampaign(Campaign campaign) {
			this.campaign = campaign;
		}
		



}
