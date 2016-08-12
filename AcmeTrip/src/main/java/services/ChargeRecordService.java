package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChargeRecordRepository;
import domain.Administrator;
import domain.ChargeRecord;

@Service
@Transactional
public class ChargeRecordService {
	
	
	//Managed repository ----------------------
		@Autowired
		private ChargeRecordRepository chargeRecordRepository;
		
		//Supporting services ---------------------
		@Autowired
		private AdministratorService administratorService;
		
		@Autowired
		private ManagerService managerService;
		
		@Autowired
		private CreditCardService creditCardService;

		
		//Constructor -----------------------------
		public ChargeRecordService() {
			super();
		}
		
		//Simple CRUD methods ---------------------
		public ChargeRecord findOne(int chargeRecordId) {
			Assert.isTrue(chargeRecordId > 0);
			ChargeRecord result = chargeRecordRepository.findOne(chargeRecordId);
			Assert.notNull(result);
			return result;
		}

		public ChargeRecord save(ChargeRecord chargeRecord) {
			Assert.notNull(chargeRecord);	
			return chargeRecordRepository.saveAndFlush(chargeRecord);
		}

		
		public Collection<ChargeRecord> findAll() {
			Collection<ChargeRecord> result = chargeRecordRepository.findAll();
			Assert.notNull(result);
			return result;
		}

		public Collection<ChargeRecord> findAllFromManagerPrincipal() {
			return chargeRecordRepository.findFromManagerPrincipal(managerService.findByPrincipal().getId());
		}
		
		
		//Other business methods ------------------
		
		
//		public Collection<ChargeRecord> findChargeRecordsByCreditCard(int creditCardId) {
//			CreditCard ccard = creditCardService.findOne(creditCardId);
//			Collection<ChargeRecord> result = chargeRecordRepository.findChargeRecordsByCreditCard(ccard.getId());
//			Assert.notNull(result);
//			return result;
//		}
		
		
		
		
//		public Barter reconstruct(BarterForm barterForm) {
//			Barter barter = new Barter();
//			User user = userService.findByPrincipal();
//			barter.setRegistrationMoment(new Date(System.currentTimeMillis()-1000));
//			barter.setTitle(barterForm.getTitle());
//			barter.setCancelled(false);
//			barter.setUser(user);
//			barter.setRelatedBarters(new ArrayList<Barter>());
//			barter.setMatches(new ArrayList<Match>());
//			Item offeredItem = new Item();
//			Item requestedItem = new Item();
//			offeredItem.setDescription(barterForm.getOfferedItemDescription());
//			offeredItem.setName(barterForm.getOfferedItemName());
//			offeredItem.setPictures(barterForm.getOfferedItemPictures());
//			requestedItem.setDescription(barterForm.getRequestedItemDescription());
//			requestedItem.setName(barterForm.getRequestedItemName());
//			requestedItem.setPictures(barterForm.getRequestedItemPictures());
//			barter.setOfferedItem(offeredItem);
//			barter.setRequestedItem(requestedItem);
//			return barter;
//		}
		

}
