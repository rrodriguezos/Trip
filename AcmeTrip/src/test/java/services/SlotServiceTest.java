package services;

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
import domain.Activity;
import domain.DailyPlan;
import domain.Slot;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class SlotServiceTest extends AbstractTest {
	@Autowired
	private SlotService slotService;
	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private HelpService helpService;
	@Autowired
	private ActivityService activityService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// creado exitosamente
	@Test
	public void testCreateSlot1() {

		authenticate("user1");
		Slot slot = slotService.create();
		Activity act = activityService.findOne(86);
		DailyPlan dailyPlan = dailyPlanService.findOne(77);

		String starTimeString = "04/04/2016 22:00";
		String endTimeString = "04/04/2016 23:00";

		Date startTimeDate = helpService.formatStringToDate(starTimeString);
		Date endTimeDate = helpService.formatStringToDate(endTimeString);

		slot.setDailyPlan(dailyPlan);
		slot.setTitle("titulo 1");
		slot.setDescription("Descripcion 1");
		slot.setStartTime(startTimeDate);
		slot.setEndTime(endTimeDate);
		slot.setActivity(act);

		Assert.isTrue(slotService.checkOverlapping(slot) == 0);

		slotService.save(slot);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// slot campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateSlot2() {
		authenticate("user1");
		Slot slot = slotService.create();
		Activity act = activityService.findOne(86);
		DailyPlan dailyPlan = dailyPlanService.findOne(77);

		String starTimeString = "";
		String endTimeString = "";

		Date startTimeDate = helpService.formatStringToDate(starTimeString);
		Date endTimeDate = helpService.formatStringToDate(endTimeString);

		slot.setDailyPlan(dailyPlan);
		slot.setTitle(" ");
		slot.setDescription(" ");
		slot.setStartTime(startTimeDate);
		slot.setEndTime(endTimeDate);
		slot.setActivity(act);

		Assert.isTrue(slotService.checkOverlapping(slot) == 0);

		slotService.save(slot);

		unauthenticate();
	}

	// crear un slot con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateSlot3() {
		authenticate("manager1");
		Slot slot = slotService.create();
		Activity act = activityService.findOne(86);
		DailyPlan dailyPlan = dailyPlanService.findOne(77);

		String starTimeString = "04/04/2016 22:00";
		String endTimeString = "04/04/2016 23:00";

		Date startTimeDate = helpService.formatStringToDate(starTimeString);
		Date endTimeDate = helpService.formatStringToDate(endTimeString);

		slot.setDailyPlan(dailyPlan);
		slot.setTitle("titulo 1");
		slot.setDescription("Descripcion 1");
		slot.setStartTime(startTimeDate);
		slot.setEndTime(endTimeDate);
		slot.setActivity(act);

		Assert.isTrue(slotService.checkOverlapping(slot) == 0);

		slotService.save(slot);

		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deletSlot1() {
		authenticate("user1");
		Slot slot;
		slot = slotService.findOne(89);

		slotService.delete(slot);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un admin eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deletSlot2() {
		authenticate("admin");
		Slot slot;
		slot = slotService.findOne(89);

		slotService.delete(slot);

		unauthenticate();
	}

	// Eliminamos un slot que no es slotId

	@Test(expected = NullPointerException.class)
	public void deletSlot3() {
		authenticate("user1");
		Slot slot;
		slot = slotService.findOne(88475);

		slotService.delete(slot);

		unauthenticate();
	}

}
