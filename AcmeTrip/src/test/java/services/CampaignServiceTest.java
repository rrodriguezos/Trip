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
import domain.ChargeRecord;

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
	@Autowired
	private ChargeRecordService chargeService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	//8.An actor who is authenticated as a manager must be able to:
	//3. Manage his or her campaigns, which includes listing, creating, modifying, and deleting
	//them.
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
		unauthenticate();
	}
	// TEST CASES CHARGE RECORDS
	//  POSITIVO
		@Test
		public void testCreateCharge() {
			authenticate("admin");
			Collection<ChargeRecord> chargesAntes = chargeService.findAll();
			Integer antes = chargesAntes.size();
			
			campaignService.generateChargeRecords();
			Collection<ChargeRecord> chargesDespues = chargeService.findAll();
			Integer despues = chargesDespues.size();

			Assert.isTrue(antes<despues);
			unauthenticate();
		}
		
		// ----------------------------------------------------
		// NEGATIVE TEST CASES EDITION 1
		// ----------------------------------------------------
				@Test(expected=IllegalArgumentException.class)
				public void testCreateChargeNoAutenticado() {
					//falta poner control de admin en el servicio
					Collection<ChargeRecord> chargesAntes = chargeService.findAll();
					Integer antes = chargesAntes.size();
					
					campaignService.generateChargeRecords();
					Collection<ChargeRecord> chargesDespues = chargeService.findAll();
					Integer despues = chargesDespues.size();

					Assert.isTrue(antes<despues);
				}
				// ----------------------------------------------------
				// NEGATIVE TEST CASES EDITION 2
				// ----------------------------------------------------
				@Test(expected=IllegalArgumentException.class)
				public void testCreateChargeManager() {
					authenticate("manager1");
					//falta poner control de admin en el servicio
					Collection<ChargeRecord> chargesAntes = chargeService.findAll();
					Integer antes = chargesAntes.size();
					
					campaignService.generateChargeRecords();
					Collection<ChargeRecord> chargesDespues = chargeService.findAll();
					Integer despues = chargesDespues.size();

					Assert.isTrue(antes<despues);
					unauthenticate();
				}
				
				// Edition requirement 1
				@Test
				public void editionCampaign1() {

					authenticate("manager1");

					Campaign campaign = campaignService.findOne(30);
					String startDateString = "20/06/2015 12:00";
					String endDateString = "23/08/2017 12:00";

					Date startDate = helpService.formatStringToDate(startDateString);
					Date endDate = helpService.formatStringToDate(endDateString);
					campaign.setStartMoment(startDate);
					campaign.setEndMoment(endDate);

					campaignService.save(campaign);

					unauthenticate();
				}
				
				// Edition requirement 2
				@Test
				public void editionCampaign2() {

					authenticate("manager1");

					Campaign campaign = campaignService.findOne(30);
					String startDateString = "20/11/2016 15:00";
					String endDateString = "28/11/2016 15:00";

					Date startDate = helpService.formatStringToDate(startDateString);
					Date endDate = helpService.formatStringToDate(endDateString);
					campaign.setStartMoment(startDate);
					campaign.setEndMoment(endDate);

					campaignService.save(campaign);

					unauthenticate();
				}
}
