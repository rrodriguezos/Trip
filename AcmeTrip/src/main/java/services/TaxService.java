package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repositories.TaxRepository;
import domain.Banner;
import domain.ChargeRecord;
import domain.Tax;

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
			Tax result = new Tax();
			result.setBanners(new ArrayList<Banner>());
			result.setChargeRecords(new ArrayList<ChargeRecord>());
			return result;
		}
		
		public Tax save(Tax tax) {
			return taxRepository.save(tax);
		}

		public void delete(Tax tax){		
			taxRepository.delete(tax);
		}
		
		public Collection<Tax> findAll(){
			return taxRepository.findAll();
		}
		
		public Tax findOne(int id) {
			return taxRepository.findOne(id);
		}
		
		// Other Business Methods ---------------------------
		
//		public Collection<Tax> taxesByChargeRecord(int chargeRecordId){
//			return taxRepository.taxesByChargeRecord(chargeRecordId);
//		}
//		
//		public Collection<Tax> taxesByBanner(int bannerId){
//			return taxRepository.taxesByBanner(bannerId);
//		}
		
		
		

}
