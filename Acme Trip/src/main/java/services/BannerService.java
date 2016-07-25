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

import repositories.BannerRepository;

@Service
@Transactional
public class BannerService {

	// Managed repository ----------------------
	@Autowired
	private BannerRepository bannerRepository;

	// Supporting services ---------------------
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private CampaignService campaignService;

	// Constructor -----------------------------
	public BannerService() {
		super();
	}

	// Simple CRUD methods ---------------------
	public Banner findOne(int bannerId) {
		Assert.isTrue(bannerId > 0);
		Banner result = bannerRepository.findOne(bannerId);
		Assert.notNull(result);
		return result;
	}

	

	public Collection<Banner> findAll() {
		Collection<Banner> result = bannerRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods ------------------
	

//	public Collection<Banner> searchByKeyword(String keyword) {
//		Assert.notNull(keyword);
//		return bannerRepository.searchByKeyword(keyword);
//	}

	

//	public Collection<Banner> findBannersByCampaign(int campaignId) {
//		Campaign campaign = campaignService.findOne(campaignId);
//		Collection<Banner> result = bannerRepository.findBannersByCampaign(campaign
//				.getId());
//		Assert.notNull(result);
//		return result;
//	}

	
//	public Barter reconstruct(BarterForm barterForm) {
//		Barter barter = new Barter();
//		User user = userService.findByPrincipal();
//		barter.setRegistrationMoment(new Date(System.currentTimeMillis() - 1000));
//		barter.setTitle(barterForm.getTitle());
//		barter.setCancelled(false);
//		barter.setUser(user);
//		barter.setRelatedBarters(new ArrayList<Barter>());
//		barter.setMatches(new ArrayList<Match>());
//		Item offeredItem = new Item();
//		Item requestedItem = new Item();
//		offeredItem.setDescription(barterForm.getOfferedItemDescription());
//		offeredItem.setName(barterForm.getOfferedItemName());
//		offeredItem.setPictures(barterForm.getOfferedItemPictures());
//		requestedItem.setDescription(barterForm.getRequestedItemDescription());
//		requestedItem.setName(barterForm.getRequestedItemName());
//		requestedItem.setPictures(barterForm.getRequestedItemPictures());
//		barter.setOfferedItem(offeredItem);
//		barter.setRequestedItem(requestedItem);
//		return barter;
//	}

}
