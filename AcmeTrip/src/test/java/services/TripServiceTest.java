package services;

import java.util.Collection;
import java.util.Date;
import java.util.List;

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

	// editar con actores no autorizados
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

	// ----------------------------------------------------
	// POSITIVE TEST CASES COPY
	// ----------------------------------------------------
	// copiado exitosamente
	@Test
	public void testCopyTrip1() {
		authenticate("user1");
		Trip trip;

		trip = tripService.findOne(80);

		tripService.copyPasteTrip(trip);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES COPY
	// ----------------------------------------------------
	// copia erronea
	@Test(expected = NullPointerException.class)
	public void testCopyTrip2() {
		authenticate("user1");
		Trip trip;

		trip = tripService.findOne(1542);

		tripService.copyPasteTrip(trip);

		unauthenticate();
	}

	// lo hace un actor logueado como administrador, siendo erroneo tambien
	@Test(expected = IllegalArgumentException.class)
	public void testCopyTrip3() {
		authenticate("admin");
		Trip trip;

		trip = tripService.findOne(80);

		tripService.copyPasteTrip(trip);

		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES SUBSCRIPTION
	// ----------------------------------------------------
	// suscrito exitosamente
	@Test
	public void testSubscriptionTrip1() {
		authenticate("user1");
		Trip trip;

		trip = tripService.findOne(80);
		tripService.joinTrip(trip);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES SUBSCRIPTION
	// ----------------------------------------------------
	// suscrito erroneamente a un trip que no es trip
	@Test(expected = NullPointerException.class)
	public void testSubscriptionTrip2() {
		authenticate("user1");
		Trip trip;

		trip = tripService.findOne(7846);
		tripService.joinTrip(trip);

		unauthenticate();
	}

	// lo hace un actor logueado como administrador, siendo erroneo tambien
	@Test(expected = IllegalArgumentException.class)
	public void testSubscriptionTrip3() {
		authenticate("admin");
		Trip trip;

		trip = tripService.findOne(80);
		tripService.joinTrip(trip);

		unauthenticate();
	}
	
	//4. A user who is not authenticated must be able to:
			//1. Navigate through the trips
		@Test
		public void testFindTrips(){
			Collection <Trip> trips = tripService.findAll();
			Assert.isTrue(trips.size()==2);		
		}
		

		//8. A user who is not authenticated must be able to:	
		//Searching trips 
		@Test
		public void testSearchTrip(){
			List <Trip> trips = (List<Trip>) tripService.findTripByKeyword("Title");
			Assert.isTrue(trips.size()==2);
		}

}