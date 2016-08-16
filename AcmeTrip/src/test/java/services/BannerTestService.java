package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Null;

import org.hibernate.NullPrecedence;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Banner;
import domain.ChargeRecord;
import domain.Tax;
import forms.BannerForm;
import forms.PriceForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class BannerTestService extends AbstractTest {

	@Autowired
	private BannerService bannerService;

	@Autowired
	private CampaignService campaignService;
	@Autowired
	private ChargeRecordService chargeRecordService;

	@Autowired
	private TaxService taxService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------

	// creado exitosamente
	@Test
	public void testCreateBanner1() {
		authenticate("manager1");
		BannerForm bannerForm = new BannerForm();
		bannerForm.setCampId(campaignService.findOne(30).getId());
		String keyword1 = "asdwd1";
		bannerForm.setKeyWs(keyword1);
		bannerForm.setMaxTimes(20);
		bannerForm.setPho("http://calpersloan.com/wp-content/uploads/2016/08/travel-insurance-banner-travel-insurance-banner2.jpg");
		
		Banner banner = bannerService.formToBanner(bannerForm);
		
		banner.setTax(taxService.findOne(32));

		bannerService.save(banner);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// banner campos vacios
	@Test(expected = NullPointerException.class)
	public void testCreateBanner2() {
		authenticate("manager1");
		BannerForm bannerForm = new BannerForm();
		Banner banner = bannerService.formToBanner(bannerForm);
		banner.setKeyWords(new ArrayList<String>());
		banner.setCampaign(campaignService.findOne(30));
		banner.setChargeRecords(new ArrayList<ChargeRecord>());
		banner.setDisplay(5);
		banner.setMaxTimesDisplayed(0);
		banner.setPhoto(null);
		banner.setPrice(25.00);
		banner.setTax(taxService.findOne(32));

		bannerService.save(banner);
		unauthenticate();
	}

	// banner con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateBanner3() {
		authenticate("user1");
		BannerForm bannerForm = new BannerForm();
		Banner banner = bannerService.formToBanner(bannerForm);
		banner.setKeyWords(new ArrayList<String>());
		banner.setCampaign(campaignService.findOne(30));
		banner.setChargeRecords(new ArrayList<ChargeRecord>());
		banner.setDisplay(5);
		banner.setMaxTimesDisplayed(20);
		banner.setPhoto("http://calpersloan.com/wp-content/uploads/2016/08/travel-insurance-banner-travel-insurance-banner2.jpg");
		banner.setPrice(25.00);
		banner.setTax(taxService.findOne(32));

		bannerService.save(banner);
		unauthenticate();
	}

	// ----------------------------------------------------
	// Aumenta visita
	// ----------------------------------------------------
	@Test
	public void aumentaVisitaBanner() {

		authenticate("manager1");
		Banner banner = bannerService.findOne(31);
		bannerService.aumentaVisita(banner);

		Assert.isTrue(banner.getDisplay() == 11);

	}

	// ----------------------------------------------------
	// Activas
	// ----------------------------------------------------
	@Test
	public void ActivasBanner() {
		authenticate("manager1");
		Collection<Banner> banners = bannerService.findAllActives();

		Assert.isTrue(banners.size() == 4);

	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	@Test
	public void editBanner1() {

		authenticate("admin");
		Banner banner = bannerService.findOne(31);
		banner.setPrice(50.00);
		banner.setMaxTimesDisplayed(25);
		String keyword1 ="keyWord1";
		String keyword2 ="keyWord2";
		banner.getKeyWords().add(keyword1);
		banner.getKeyWords().add(keyword2);
		bannerService.save(banner);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos erroneos
	@Test(expected = ConstraintViolationException.class)
	public void editBanner2() {

		authenticate("admin");
		Banner banner = bannerService.findOne(31);
		banner.setPrice(-25.00);
		banner.setMaxTimesDisplayed(0);
		String keyword1 ="keyWord1";
		String keyword2 ="keyWord2";
		banner.getKeyWords().add(keyword1);
		banner.getKeyWords().add(keyword2);
		bannerService.save(banner);

		unauthenticate();
	}

	// editar con actor no autorizado
	@Test(expected = IllegalArgumentException.class)
	public void editBanner3() {

		authenticate("none");
		Banner banner = bannerService.findOne(31);
		banner.setPrice(50.00);
		banner.setMaxTimesDisplayed(25);
		String keyword1 ="keyWord1";
		String keyword2 ="keyWord2";
		banner.getKeyWords().add(keyword1);
		banner.getKeyWords().add(keyword2);
		bannerService.save(banner);

		unauthenticate();
	}

}
