package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.ChargeRecord;
import domain.CreditCard;

import repositories.ChargeRecordRepository;

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

		public void save(ChargeRecord chargeRecord) {
			Assert.notNull(chargeRecord);	
			Administrator admin = administratorService.findByPrincipal();
//			Assert.isTrue(admin.getId()==chargeRecord.getUser().getId());
			chargeRecordRepository.save(chargeRecord);
			chargeRecordRepository.flush();
		}

		
		public Collection<ChargeRecord> findAll() {
			Collection<ChargeRecord> result = chargeRecordRepository.findAll();
			Assert.notNull(result);
			return result;
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
