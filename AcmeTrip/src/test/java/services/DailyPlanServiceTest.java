package services;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.DailyPlan;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class DailyPlanServiceTest extends AbstractTest {

	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private TripService tripService;
	
	@Autowired
	private HelpService helpService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// creado exitosamente
	@Test
	public void testCreateDailyPlan1() {

		authenticate("user1");
		DailyPlan dplan = dailyPlanService.create();
		Trip trip = tripService.findOne(76);
		dplan.setTrip(trip);
		dplan.setTitle("titulo 1");
		String weekDayString = "05/04/2016";
		Date weekDay = helpService.formatStringToDateWithoutHour(weekDayString);
		dplan.setWeekDay(weekDay);
		dplan.setDescription("Descripcion 1");

		Assert.isTrue(dailyPlanService.checkOverlapping(dplan));

		dailyPlanService.save(dplan);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// dailyPlan campos en blanco
	@Test(expected = IllegalArgumentException.class)
	public void testCreateDailyPlan2() {
		authenticate("user1");
		DailyPlan dplan = dailyPlanService.create();
		Trip trip = tripService.findOne(76);
		dplan.setTrip(trip);
		dplan.setTitle("");
		Date weekDay = new Date(04 / 04 / 2016);
		dplan.setWeekDay(weekDay);
		dplan.setDescription("Descripcion 1");

		Assert.isTrue(dailyPlanService.checkOverlapping(dplan));

		dailyPlanService.save(dplan);

		unauthenticate();
	}

	// crear un dailyplan con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateDailyPlan3() {
		authenticate("manager1");
		DailyPlan dplan = dailyPlanService.create();
		Trip trip = tripService.findOne(76);
		dplan.setTrip(trip);
		dplan.setTitle("Titulo 1");
		Date weekDay = new Date(04 / 04 / 2016);
		dplan.setWeekDay(weekDay);
		dplan.setDescription("Descripcion 1");

		Assert.isTrue(dailyPlanService.checkOverlapping(dplan));

		dailyPlanService.save(dplan);

		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deletDailyPlan1() {
		authenticate("user1");
		DailyPlan dailyPlan;

		dailyPlan = dailyPlanService.findOne(77);

		dailyPlanService.delete(dailyPlan);

		unauthenticate();

	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un admin eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deletDailyPlan2() {
		authenticate("admin");
		DailyPlan dailyPlan;

		dailyPlan = dailyPlanService.findOne(77);

		dailyPlanService.delete(dailyPlan);

		unauthenticate();
	}

	// Eliminamos un trip que no es tripId

	@Test(expected = NullPointerException.class)
	public void deletDailyPlan3() {
		authenticate("user1");
		DailyPlan dailyPlan;

		dailyPlan = dailyPlanService.findOne(887954);

		dailyPlanService.delete(dailyPlan);

		unauthenticate();
	}

}
