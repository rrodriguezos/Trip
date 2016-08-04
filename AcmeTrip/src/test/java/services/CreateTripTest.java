package services;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

import com.mchange.v1.util.UnexpectedException;

import domain.Trip;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CreateTripTest extends AbstractTest {

	@Autowired
	private TripService tripService;

	@Autowired
	private HelpService helpService;

	private String actor;
	private String title;
	private String startTrip;
	private String endTrip;
	private String description;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "user1", "titulo", "04/04/2016 02:00", "04/05/2016 02:00",
						"Descripcion" },
				{ "user1", "", "04/04/2016 02:00", "04/05/2016 02:00",
						"Descripcion" },

				{ "user1", "titulo3", "04/04/2016 ", "04/05/2016 02:00",
						"Descripcion" },

				{ "user1", "titulo4", "", "04/010/2016 02:00", "Descripcion" },

				{ "user1", "titulo5", "04/04/2016 02:00", "04/05/2016 02:00",
						"" },

				{ "user1", "titulo6", "04/04/2016 02:00", "04/05/2016 ",
						"Descripcion" },

				{ "user23", "titulo7", "04/04/2016 02:00", "04/05/2016 02:00",
						"Descripcion" },

				{ "manager1", "titulo8", "04/04/2016 02:00",
						"04/05/2016 02:00", "Descripcion" },
				{ "admin", "titulo9", "04/04/2016 02:00", "04/05/2016 02:00",
						"Descripcion" },

				{ "", "titulo10", "04/04/2016 02:00", "04/05/2016 02:00",
						"Descripcion" },

		});
	}

	public CreateTripTest(String actor, String title, String startTrip,
			String endTrip, String description) {
		this.title = title;
		this.startTrip = startTrip;
		this.endTrip = endTrip;
		this.description = description;
		this.actor = actor;

	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 10 posibles creaciones de Trip. Para
	 *             ello se utilizan las propiedades actor, title, startTrip,
	 *             endTrip y description. En el primer caso probamos crear un
	 *             usuartrip con los datos correctos, en el resto de casos,
	 *             probamos crear trips con campos en blanco, datos erróneos y
	 *             finalmente con actores no autorizados y sin ninguna sesion
	 *             iniciada.
	 *             Para los casos negativos hemos capturado las excepciones
	 *              TransactionSystemException e IllegalArgumentException.
	 */

	@Test
	public void testCreateTrip() {
		try {
			authenticate(actor);

			Trip trip = tripService.create();

			Date start = helpService.formatStringToDate(startTrip);
			Date end = helpService.formatStringToDate(endTrip);

			trip.setTitle(title);
			trip.setDescription(description);
			trip.setStartDate(start);
			trip.setEndDate(end);

			tripService.save(trip);

			unauthenticate();

		} catch (TransactionSystemException e1) {
			System.out.println(e1);
		} catch (IllegalArgumentException e1) {
			System.out.println(e1);

		} catch (UnexpectedException e2) {
			System.out.println(e2);

			throw e2;
		}

	}
}
