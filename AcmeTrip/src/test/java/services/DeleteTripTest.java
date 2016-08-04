package services;

import java.util.Arrays;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.mchange.v1.util.UnexpectedException;

import domain.Trip;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DeleteTripTest extends AbstractTest {

	@Autowired
	private TripService tripService;

	private int tripId;
	private String authenticate;

	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { { 56, "user1" },
				{ 98465, "user1" }, { 57, "admin" }, { 60, "manager1" } });

	}

	public DeleteTripTest(int tripId, String authenticate) {
		this.tripId = tripId;
		this.authenticate = authenticate;
	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 4 posibles acciones de eliminar un Trip.
	 *             Para ello se utilizan las propiedades tripId y authenticate.
	 *             Se llama para ello a la función delete del Servicio. En el
	 *             primer caso eliminamos correctamente el trip, en el segundo
	 *             caso probamos eliminar un objeto que no es un trip, no
	 *             llevandose a cabo, en la tercera ocasión lo intenta hacer un
	 *             actor logueado como administrador, siendo erroneo tambien y
	 *             en cuarto lugar lo intenta hacer un actor logueado como
	 *             manager.
	 * 
	 *             Capturamos las excepciones NullPointerException,
	 *             IllegalArgumentException.
	 */

	@Test
	public void testDeleteTrip() {

		try {

			authenticate(authenticate);
			Trip trip;

			trip = tripService.findOne(tripId);

			tripService.delete(trip);

			unauthenticate();

		} catch (NullPointerException e1) {
			System.out.println(e1);
		} catch (IllegalArgumentException e2) {
			System.out.println(e2);
		} catch (UnexpectedException e3) {
			System.out.println(e3);
			throw e3;
		}
	}

}
