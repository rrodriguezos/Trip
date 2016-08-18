package services;

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
public class ActivityTypeServiceTest extends AbstractTest {

	@Autowired
	private ActivityTypeService activityTypeService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	//14.3 Manage activity types.
	// creado exitosamente
	@Test
	public void testCreateActivityType1() {
		authenticate("manager1");
		ActivityType activityType = activityTypeService.create();
		activityType.setName("Name test");

		activityTypeService.save(activityType);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// trip campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateActivityType2() {
		authenticate("manager1");
		ActivityType activityType = activityTypeService.create();
		activityType.setName("");

		activityTypeService.save(activityType);
		unauthenticate();
	}

	// trip con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateActivityType3() {
		authenticate("user1");
		ActivityType activityType = activityTypeService.create();
		activityType.setName("Name test");

		activityTypeService.save(activityType);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	@Test
	public void editActivityType1() {

		authenticate("manager1");

		ActivityType activityType = activityTypeService.findOne(84);

		activityType.setName("Edit Name");

		activityTypeService.save(activityType);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void editActivityType2() {
		authenticate("manager1");

		ActivityType activityType = activityTypeService.findOne(84);

		activityType.setName("");

		activityTypeService.save(activityType);

		unauthenticate();
	}

	// editar con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void editActivityType3() {

		authenticate("user1");

		ActivityType activityType = activityTypeService.findOne(84);

		activityType.setName("Edit Name");

		activityTypeService.save(activityType);

		unauthenticate();
	}

	// 4. A user who is not authenticated must be able to:
	// 1. Navigate through the activity types
	@Test
	public void testFindTrips() {
		Collection<ActivityType> types = activityTypeService.findAll();
		Assert.isTrue(types.size() == 2);
	}
	
	// Listing requirement 1

		@Test
		public void testFindActivityType() {
			Collection<ActivityType> activitieTypes = activityTypeService.findAll();
			Assert.isTrue(activitieTypes.size() == 2);
		}
	
	
	//Edition requirement 1
		@Test
		public void editionActivityType1() {

			authenticate("manager1");

			ActivityType activityType = activityTypeService.findOne(84);

			activityType.setName("Edition Name");

			activityTypeService.save(activityType);

			unauthenticate();
		}

}
