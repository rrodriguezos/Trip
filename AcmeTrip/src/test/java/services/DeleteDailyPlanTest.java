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

import domain.DailyPlan;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DeleteDailyPlanTest extends AbstractTest {

	@Autowired
	private DailyPlanService dailyPlanService;

	private int dailyPlanId;
	private String authenticate;

	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { { 57, "user1" },
				{ 98465, "user1" }, { 57, "admin" }, { 62, "user1" } });

	}

	public DeleteDailyPlanTest(int dailyPlanId, String authenticate) {
		this.dailyPlanId = dailyPlanId;
		this.authenticate = authenticate;
	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 4 posibles acciones de eliminar un DailyPlan.
	 *             Para ello se utilizan las propiedades dailyPlanId y authenticate.
	 *             Se llama para ello a la función delete del Servicio. En el
	 *             primer caso eliminamos correctamente el dailyPlan, en el segundo
	 *             caso probamos eliminar un objeto que no es un dailyPlan, no
	 *             llevandose a cabo, en la tercera ocasión lo intenta hacer un
	 *             actor logueado como administrador, siendo erroneo tambien y
	 *             en cuarto lugar lo intenta hacer un actor logueado como
	 *             manager.
	 * 
	 *             Capturamos las excepciones NullPointerException,
	 *             IllegalArgumentException.
	 */

	@Test
	public void testDeleteDailyPlan() {

		try {

			authenticate(authenticate);
			DailyPlan dailyPlan;

			dailyPlan = dailyPlanService.findOne(dailyPlanId);

			dailyPlanService.delete(dailyPlan);

			unauthenticate();

//		} catch (NullPointerException e1) {
//			System.out.println(e1);
//		} catch (IllegalArgumentException e2) {
//			System.out.println(e2);
		} catch (UnexpectedException e3) {
			System.out.println(e3);
			throw e3;
		}
	}

}
