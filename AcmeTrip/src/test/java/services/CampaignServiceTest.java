package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Campaign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CampaignServiceTest extends AbstractTest {

	@Autowired
	private CampaignService campaignService;
	@Autowired
	private HelpService helpService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------

	// creado exitosamente
	@Test
	public void testCreateCampaign1() {
		authenticate("manager1");
		Campaign campaign = campaignService.create();

		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		campaign.setStartMoment(startDate);
		campaign.setEndMoment(endDate);

		campaignService.save(campaign);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// campaign campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateCampaign2() {
		authenticate("manager1");
		Campaign campaign = campaignService.create();

		String startDateString = "";
		String endDateString = "";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		campaign.setStartMoment(startDate);
		campaign.setEndMoment(endDate);

		campaignService.save(campaign);
		unauthenticate();
	}

	// campaign con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCampaign3() {
		authenticate("user1");
		Campaign campaign = campaignService.create();

		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		campaign.setStartMoment(startDate);
		campaign.setEndMoment(endDate);

		campaignService.save(campaign);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deleteCampaign1() {
		authenticate("manager1");
		campaignService.delete(campaignService.findOne(15));
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un admin eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deleteCampaign2() {
		authenticate("admin");
		Campaign campaign = campaignService.findOne(30);
		campaignService.delete(campaign);
		unauthenticate();
	}

	// Eliminamos una campaign que no es campaignId

	@Test(expected = IllegalArgumentException.class)
	public void deleteCampaign3() {
		authenticate("manager1");
		campaignService.delete(campaignService.findOne(7894));
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	@Test
	public void editCampaign1() {

		authenticate("manager1");

		Campaign campaign = campaignService.findOne(15);
		String startDateString = "04/04/2016 03:00";
		String endDateString = "04/05/2016 03:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		campaign.setStartMoment(startDate);
		campaign.setEndMoment(endDate);

		campaignService.save(campaign);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void editCampaign2() {

		authenticate("manager1");

		Campaign campaign = campaignService.findOne(15);
		String startDateString = "";
		String endDateString = "";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		campaign.setStartMoment(startDate);
		campaign.setEndMoment(endDate);

		campaignService.save(campaign);

		unauthenticate();
	}

	// editar con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void editCampaign3() {

		authenticate("user1");

		Campaign campaign = campaignService.findOne(15);
		String startDateString = "04/04/2016 03:00";
		String endDateString = "04/05/2016 03:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		campaign.setStartMoment(startDate);
		campaign.setEndMoment(endDate);

		campaignService.save(campaign);

		unauthenticate();
	}

	// List Campaigns
	@Test
	public void testFindCampaigns() {
		authenticate("manager1");
		Collection<Campaign> campaigns = campaignService.findAll();
		Assert.isTrue(campaigns.size() == 4);
	}
}
