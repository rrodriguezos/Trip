package services;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.ActivityType;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ActivityServiceTest extends AbstractTest {

	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityTypeService activityTypeService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// 14.2 Flag activities as inappropriate.
	// Flag existosamente

	@Test
	public void testFlagActivity1() {
		authenticate("manager1");

		activityService.changeStateActivity(86);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// Flag una actividad que no es actividad
	@Test(expected = NullPointerException.class)
	public void testFlagActivity2() {
		authenticate("manager1");

		activityService.changeStateActivity(8746);

		unauthenticate();
	}

	// flag un comentario por un usuario no autorizado
	@Test(expected = IllegalArgumentException.class)
	public void testFlagActivity3() {
		authenticate("user1");

		activityService.changeStateActivity(92);

		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// creado exitosamente
	@Test
	public void testCreateActivity1() {
		authenticate("user1");
		Activity activity = activityService.create();
		activity.setTitle("titulo 1");
		activity.setDescription("Description 1");
		Collection<String> photos = new ArrayList<String>();
		activity.setPhotos(photos);
		ActivityType aType = activityTypeService.findOne(84);
		activity.setActivityType(aType);

		activityService.save(activity);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// activity campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateActivityNegative1() {
		authenticate("user1");
		Activity activity = activityService.create();
		activity.setTitle("");
		activity.setDescription("");
		activityService.save(activity);
		unauthenticate();
	}

	// activity con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateActivityNegative2() {
		authenticate("admin");
		Activity activity = activityService.create();
		activity.setTitle("titulo 1");
		Collection<String> photos = new ArrayList<String>();
		activity.setPhotos(photos);
		ActivityType aType = activityTypeService.findOne(84);
		activity.setActivityType(aType);
		activity.setDescription("Description 1");

		activityService.save(activity);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	@Test
	public void editActivity1() {

		authenticate("user1");

		Activity activity = activityService.findOne(86);

		activity.setTitle("title edit");
		ActivityType aType = activityTypeService.findOne(85);
		activity.setActivityType(aType);
		activity.setDescription("Description edit");

		activityService.save(activity);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void editActivity2() {

		authenticate("user1");

		Activity activity = activityService.findOne(87);
		ActivityType aType = activityTypeService.findOne(85);
		activity.setActivityType(aType);

		activity.setTitle(" ");
		activity.setDescription(" ");

		activityService.save(activity);

		unauthenticate();
	}

	// editar con actores no autentificdo
	@Test(expected = IllegalArgumentException.class)
	public void editActivity3() {

		authenticate("");

		Activity activity = activityService.findOne(87);

		activity.setTitle("title edit");
		ActivityType aType = activityTypeService.findOne(85);
		activity.setActivityType(aType);
		activity.setDescription("Description edit");

		activityService.save(activity);

		unauthenticate();
	}

	// Listing requirement 1

	@Test
	public void testFindActivity() {
		Collection<Activity> activities = activityService.findAll();
		Assert.isTrue(activities.size() == 3);
	}

	// Edition requirement 1
	@Test
	public void editionActivity1() {

		authenticate("user1");

		Activity activity = activityService.findOne(86);

		activity.setTitle("title edition");
		ActivityType aType = activityTypeService.findOne(85);
		activity.setActivityType(aType);
		activity.setDescription("Description edition");

		activityService.save(activity);

		unauthenticate();
	}

	// Edition requirement 2
	@Test
	public void editionActivity2() {

		authenticate("user1");

		Activity activity = activityService.findOne(88);

		activity.setTitle("title edition");
		ActivityType aType = activityTypeService.findOne(84);
		activity.setActivityType(aType);
		activity.setDescription("Description edition");

		activityService.save(activity);

		unauthenticate();
	}

}
