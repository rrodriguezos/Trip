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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;

import com.mchange.v1.util.UnexpectedException;

import domain.DailyPlan;
import domain.Trip;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CreateDailyPlanTest extends AbstractTest {

	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private TripService tripService;
	@Autowired
	private HelpService helpService;

	private String actor;
	private int tripId;
	private String title;
	private String day;
	private String description;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "user1", 56, "titulo", "04/04/2016", "Descripcion" },
				{ "user1", 60, "titulo", "04/05/2016", "Descripcion" },
				{ "user1", 56, "qwer", "04/02/2016", "Descripcion" },
				{ "user1", 56, "titulo", "04/22/2016", "Descripcion" },

		});
	}

	public CreateDailyPlanTest(String actor, int tripId, String title,
			String day, String description) {
		this.tripId = tripId;
		this.title = title;
		this.day = day;
		this.description = description;
		this.actor = actor;

	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 4 posibles creaciones de DailyPlan. Para
	 *             ello se utilizan las propiedades tripId, title, day,
	 *             description y actor. En el primer caso probamos crear un
	 *             dailyPlan con los datos correctos, en el segundo caso
	 *             probamos a crear un dailyPlan a un trip que no pertenece al
	 *             usuario logueado, dando esta acción como errónea. Para los
	 *             casos negativos hemos capturado la excepción
	 *             IllegalArgumentException.
	 */

	@Test
	public void testCreateDailyPlan() {
		try {
			authenticate(actor);

			DailyPlan dailyPlan = dailyPlanService.create();
			Trip trip = tripService.findOne(tripId);
			Date dayx = helpService.formatStringToDateWithoutHour(day);
			dailyPlan.setTrip(trip);
			dailyPlan.setTitle(title);
			dailyPlan.setDescription(description);
			dailyPlan.setWeekDay(dayx);

			Assert.isTrue(dailyPlanService.checkOverlapping(dailyPlan));

			dailyPlanService.save(dailyPlan);

			unauthenticate();

		} catch (IllegalArgumentException e1) {
			System.out.println(e1);

		} catch (UnexpectedException e2) {
			System.out.println(e2);

			throw e2;
		}

	}
}
