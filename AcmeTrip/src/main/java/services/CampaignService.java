package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
import repositories.CreditCardRepository;
import security.Authority;
import domain.Actor;
import domain.Administrator;
import domain.Banner;
import domain.Campaign;
import domain.ChargeRecord;
import domain.CreditCard;
import domain.Manager;

@Service
@Transactional
public class CampaignService {

	// Managed repository ----------------------
	@Autowired
	private CampaignRepository campaignRepository;

	@Autowired
	private CreditCardRepository creditCardRepository;
	// Supporting services ---------------------
	@Autowired
	private ChargeRecordService chargeService;
	@Autowired
	private ManagerService managerService;

	@Autowired
	private BannerService bannerService;

	@Autowired
	private AdministratorService administratorService;
	
	@Autowired
	private ActorService actorService;

	// Constructor -----------------------------
	public CampaignService() {
		super();
	}

	// Simple CRUD methods ---------------------

	public Campaign create() {
		Campaign result = new Campaign();
		result.setBanners(new ArrayList<Banner>());
		result.setManager(managerService.findByPrincipal());

		return result;
	}

	public Campaign findOne(int campaignId) {
		Assert.isTrue(campaignId > 0);
		Campaign result = campaignRepository.findOne(campaignId);
		Assert.notNull(result);
		return result;
	}

	public Campaign save(Campaign campaign) {
		Assert.notNull(campaign);
		Manager manager = managerService.findByPrincipal();
		Assert.isTrue(manager.getId() == campaign.getManager().getId());
		Campaign camp = campaignRepository.saveAndFlush(campaign);
		return camp;
	}

	public Campaign saveEdit(Campaign campaign) {
		Assert.notNull(campaign);
		Campaign camp = campaignRepository.saveAndFlush(campaign);
		return camp;
	}

	public Collection<Campaign> findAll() {
		Collection<Campaign> result = campaignRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Double standardDeviationOfNumberDaysCampaignsLast() {
		return campaignRepository.standardDeviationOfNumberDaysCampaignsLast();
	}

	public Double averageNumberDaysCampaignsLast() {
		return campaignRepository.averageNumberDaysCampaignsLast();
	}

	public void anadeTarjeta(Campaign campaign) {
		CreditCard tarjeta = campaign.getCreditCard();
		tarjeta.setCampaign(campaign);
		creditCardRepository.save(tarjeta);
		campaignRepository.save(campaign);
	}

	public void delete(Campaign campaign) {
		checkPrincipalManager(campaign.getManager());
		campaignRepository.delete(campaign);

	}

	private void checkPrincipalManager(Manager manager) {

		manager = managerService.findByPrincipal();
		Assert.isTrue(manager != null);

		Assert.isTrue(manager.equals(manager));
	}

	private void checkPrincipalAdministrator() {
		Actor actor;
		Authority authority;

		actor = actorService.findByPrincipal();
		Assert.isTrue(actor != null);

		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");

		Assert.isTrue(actor.getUserAccount().getAuthorities()
				.contains(authority));
	}

	public Collection<Campaign> findAllFromPrincipal() {
		return managerService.findByPrincipal().getCampaigns();
	}

	public void generateChargeRecords() {
		checkPrincipalAdministrator();
		Date now = new Date(System.currentTimeMillis());
		Collection<Campaign> campaigns = this.findAll();
		for (Campaign c : campaigns) {
			if (c.getStartMoment().before(now) && c.getEndMoment().after(now)) {
				for (Banner b : c.getBanners()) {
					if (b.getDisplay() != 0) {
						CreditCard credit = creditCardRepository.findOne(c
								.getCreditCard().getId());
						ChargeRecord charge = new ChargeRecord();
						charge.setCreditCard(credit);
						charge.setBanner(b);
						charge.setCreateMoment(new Date(System
								.currentTimeMillis() - 1000));
						Double dinero = b.getPrice() * b.getDisplay();
						dinero = dinero + dinero * b.getTax().getTaxType();
						charge.setAmountMoney(dinero);
						ChargeRecord res = chargeService.save(charge);
						Collection<ChargeRecord> charges = credit
								.getChargeRecords();
						charges.add(charge);
						credit.setChargeRecords(charges);
						creditCardRepository.saveAndFlush(credit);
						Collection<ChargeRecord> chargs = b.getChargeRecords();
						chargs.add(res);
						b.setChargeRecords(chargs);
						b.setDisplay(0);
						bannerService.save(b);
					}
				}
			}

		}
	}

	public Double averageCharge() {
		Double averageTotal = 0.0;
		Integer contadorTotal = 0;
		Collection<CreditCard> credits = creditCardRepository.findAll();
		for (CreditCard c : credits) {
			Double avgMedia = 0.0;
			if (c.getChargeRecords().size() > 0) {
				for (ChargeRecord cr : c.getChargeRecords()) {
					avgMedia += cr.getAmountMoney();
				}
				contadorTotal++;
			}
			averageTotal += (avgMedia / c.getChargeRecords().size());
		}
		return averageTotal / contadorTotal;
	}

}
