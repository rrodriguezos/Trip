package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;
import domain.ChargeRecord;
import domain.Tax;

import repositories.TaxRepository;
import security.Authority;
import utilities.DPMessage;
import utilities.DPUtils;

@Service
@Transactional
public class TaxService {
	
	
	// Managed Repository ------------------------------
		@Autowired
		private TaxRepository taxRepository;

		// Supporting Services -----------------------------
		@Autowired
		private BannerService bannerService;
		@Autowired
		private CampaignService campaignService;
	
		// Constructors ------------------------------------
		public TaxService() {
			super();
		}

		// Simple CRUD Methods -----------------------------
		public Tax create() {
			Assert.isTrue(DPUtils.hasRole(Authority.ADMIN), DPMessage.NO_PERMISSIONS);
			Tax result = new Tax();
			result.setBanners(new ArrayList<Banner>());
			result.setChargeRecords(new ArrayList<ChargeRecord>());
			return result;
		}
		
		public Tax save(Tax tax) {
			Assert.isTrue(DPUtils.hasRole(Authority.ADMIN), DPMessage.NO_PERMISSIONS);
			return taxRepository.save(tax);
		}

		public void delete(Tax tax){
			Assert.isTrue(DPUtils.hasRole(Authority.ADMIN), DPMessage.NO_PERMISSIONS);			
			taxRepository.delete(tax);
		}
		
		public Collection<Tax> findAll(){
			return taxRepository.findAll();
		}
		
		public Tax findOne(int id) {
			return taxRepository.findOne(id);
		}
		
		// Other Business Methods ---------------------------
		
		public Collection<Tax> taxesByChargeRecord(int chargeRecordId){
			return taxRepository.taxesByChargeRecord(chargeRecordId);
		}
		
		public Collection<Tax> taxesByBanner(int bannerId){
			return taxRepository.taxesByBanner(bannerId);
		}
		
		
		

}
