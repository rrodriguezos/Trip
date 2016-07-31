package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;
import domain.Campaign;
import domain.ChargeRecord;
import domain.CreditCard;
import domain.Manager;

import repositories.CampaignRepository;
import security.Authority;
import utilities.DPMessage;
import utilities.DPUtils;


@Service
@Transactional
public class CampaignService {
	
	
	//Managed repository ----------------------
		@Autowired
		private CampaignRepository campaignRepository;
		
		//Supporting services ---------------------
		@Autowired
		private AdministratorService administratorService;
		@Autowired
		private ManagerService managerService;
		
		//Constructor -----------------------------
		public CampaignService() {
			super();
		}
		
		//Simple CRUD methods ---------------------
		
		public Campaign create() {
			Assert.isTrue(DPUtils.hasRole(Authority.MANAGER), DPMessage.NO_PERMISSIONS);
			Campaign result = new Campaign();
			result.setBanners(new ArrayList<Banner>());
			return result;
		}
		
		
		public Campaign findOne(int campaignId) {
			Assert.isTrue(campaignId > 0);
			Campaign result = campaignRepository.findOne(campaignId);
			Assert.notNull(result);
			return result;
		}

		public void save(Campaign campaign) {
			Assert.notNull(campaign);	
			Manager manager = managerService.findByPrincipal();
			Assert.isTrue(manager.getId()==campaign.getManager().getId());
			campaignRepository.save(campaign);
			campaignRepository.flush();
		}

		
		public Collection<Campaign> findAll() {
			Collection<Campaign> result = campaignRepository.findAll();
			Assert.notNull(result);
			return result;
		}
		
		
		//Other business methods ------------------
		
		

//		public Collection<Campaign> findCampaignsByPrincipal() {
//			Manager manager = managerService.findByPrincipal();
//			Collection<Campaign> result = campaignRepository.findCampaignsByManager(manager.getId());
//			Assert.notNull(result);
//			return result;
//		}
//		public Collection<Campaign> findCampaignsByManager(int managerId) {
//			Manager manager = managerService.findOne(managerId);
//			Collection<Campaign> result = campaignRepository.findCampaignsByManager(manager.getId());
//			Assert.notNull(result);
//			return result;
//		}
//		
//		
		public Double standardDeviationOfNumberDaysCampaignsLast() {
			return campaignRepository.standardDeviationOfNumberDaysCampaignsLast();
		}
		
		public Double averageNumberDaysCampaignsLast(){
			return campaignRepository.averageNumberDaysCampaignsLast();
		}
		

	
		
//		public Barter reconstruct(BarterForm barterForm) {
//			Barter barter = new Barter();
//			User user = managerService.findByPrincipal();
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
