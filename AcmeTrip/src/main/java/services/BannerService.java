package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Banner;
import domain.Campaign;
import domain.Tax;
import forms.BannerForm;
import forms.PriceForm;
import repositories.BannerRepository;
import repositories.TaxRepository;

@Service
@Transactional
public class BannerService {

	// Managed repository ----------------------
	@Autowired
	private BannerRepository bannerRepository;

	@Autowired
	private TaxRepository taxRepository;
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
		return result;
	}

	public Banner formToBanner(BannerForm bannerForm) {
		Campaign camp = campaignService.findOne(bannerForm.getCampId());
		Banner banner = new Banner();
		banner.setCampaign(camp);
		banner.setDisplay(0);
		banner.setMaxTimesDisplayed(bannerForm.getMaxTimes());
		banner.setPhoto(bannerForm.getPho());
		banner.setPrice(1.0);
		String[] keywords = bannerForm.getKeyWs().split(" ");
		ArrayList<String> keys = new ArrayList<String>();
		for (int i = 0; i < keywords.length; i++) {
			keys.add(keywords[i]);
		}
		banner.setKeyWords(keys);
		return banner;
	}

	public void save(Banner banner) {
		bannerRepository.saveAndFlush(banner);
	}

	public void aumentaVisita(Banner banner) {
		banner.setDisplay(banner.getDisplay() + 1);
		bannerRepository.saveAndFlush(banner);
	}

	public Collection<Banner> findAllActives() {
		Collection<Banner> result = bannerRepository.findAll();
		Collection<Banner> res = new LinkedList<Banner>();
		for (Banner b : result) {
			if (b.getCampaign().getStartMoment().after(new Date(System.currentTimeMillis()))
					|| b.getCampaign().getEndMoment().after(new Date(System.currentTimeMillis())))
				res.add(b);
		}

		return res;
	}

	public Banner priceFormToBanner(PriceForm priceForm) {
		Banner banner = bannerRepository.findOne(priceForm.getId());
		banner.setPrice(priceForm.getPrecio());
		return bannerRepository.saveAndFlush(banner);
	}

	public void saveFinal(Banner banner) {
		Banner ban = bannerRepository.findOne(banner.getId());
		if(ban.getTax().getTaxType()!=banner.getTax().getTaxType()){
			Tax antigua = taxRepository.findOne(ban.getTax().getId());
			Collection<Banner> antiguos = antigua.getBanners();
			antiguos.remove(ban);
			antigua.setBanners(antiguos);
			taxRepository.saveAndFlush(antigua);
			Tax nueva = taxRepository.findOne(banner.getTax().getId());
			Collection<Banner> nuevos = nueva.getBanners();
			nuevos.add(banner);
			nueva.setBanners(nuevos);
			Tax a = taxRepository.saveAndFlush(nueva);
			banner.setTax(a);
			bannerRepository.save(ban);
		}
	}

	public Collection<Banner> banners10Average() {
		Double media =0.0;
		Collection<Banner> banners = bannerRepository.findAll();
		Collection<Banner> bans = new LinkedList<Banner>();
		for(Banner b: banners){
			media += b.getDisplay()/b.getMaxTimesDisplayed();
		}
		Double mediaMas10 = media+media*(0.1);
		for(Banner b: banners){
			if(b.getDisplay()>mediaMas10){
				bans.add(b);
			}
		}
		return bans;
	}

	public Collection<Banner> banners10LessAverage() {
		Double media =0.0;
		Collection<Banner> banners = bannerRepository.findAll();
		Collection<Banner> bans = new LinkedList<Banner>();
		for(Banner b: banners){
			media += b.getDisplay()/b.getMaxTimesDisplayed();
		}
		Double mediaMen10 = media-media*(0.1);
		for(Banner b: banners){
			if(b.getDisplay()>mediaMen10){
				bans.add(b);
			}
		}
		return bans;
	}


}
