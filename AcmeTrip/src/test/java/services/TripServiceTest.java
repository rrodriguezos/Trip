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

import utilities.AbstractTest;
import domain.Trip;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TripServiceTest extends AbstractTest {

	@Autowired
	private TripService tripService;

	@Autowired
	private HelpService helpService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// A user who is authenticated as an user
	// Manage his or her trips, which involves listing, registering, modifying,
	// and deleting them.
	// creado exitosamente
	@Test
	public void testCreateTripAsUser() {
		authenticate("user1");
		Trip trip = tripService.create();
		trip.setTitle("titulo 1");

		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		trip.setDescription("Descripcion 1");
		tripService.save(trip);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// trip campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateTripAsUserNegative1() {
		authenticate("user1");
		Trip trip = tripService.create();
		trip.setTitle("");

		String startDateString = "";
		String endDateString = "";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		trip.setDescription("");
		tripService.save(trip);
		unauthenticate();
	}

	// trip con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateTripAsUserNegative2() {
		authenticate("manager1");
		Trip trip = tripService.create();
		trip.setTitle("Titulo 1");

		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		trip.setDescription("Descripcion 1");
		tripService.save(trip);
		unauthenticate();
	}

	// trip sin sesión iniciada
	@Test(expected = IllegalArgumentException.class)
	public void testCreateTripAsUserNegative3() {
		authenticate("");
		Trip trip = tripService.create();
		trip.setTitle("titulo 1");

		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);
		trip.setDescription("Descripcion 1");
		tripService.save(trip);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deletTrip() {
		authenticate("user1");
		tripService.delete(tripService.findOne(76));
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un admin eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deleteTrip2() {
		authenticate("admin");
		tripService.delete(tripService.findOne(76));
		unauthenticate();
	}

	// Eliminamos un trip que no es tripId

	@Test(expected = IllegalArgumentException.class)
	public void deleteTrip3() {
		authenticate("admin");
		tripService.delete(tripService.findOne(98465));
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	public void editTrip1() {

		authenticate("user1");

		Trip trip = tripService.findOne(76);
		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);

		trip.setTitle("title edit");
		trip.setDescription("Description edit");

		tripService.save(trip);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void editTrip2() {

		authenticate("user1");

		Trip trip = tripService.findOne(76);
		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);

		trip.setTitle("");
		trip.setDescription("Description edit");

		tripService.save(trip);

		unauthenticate();
	}
	
	//editar con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void editTrip3() {

		authenticate("manager2");

		Trip trip = tripService.findOne(76);
		String startDateString = "04/04/2016 02:00";
		String endDateString = "04/05/2016 02:00";

		Date startDate = helpService.formatStringToDate(startDateString);
		Date endDate = helpService.formatStringToDate(endDateString);
		trip.setStartDate(startDate);
		trip.setEndDate(endDate);

		trip.setTitle("Titulo 1");
		trip.setDescription("Description edit");

		tripService.save(trip);

		unauthenticate();
	}
	
}