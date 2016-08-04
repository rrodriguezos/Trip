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

import domain.Activity;
import domain.Slot;
import domain.DailyPlan;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CreateSlotTest extends AbstractTest {

	@Autowired
	private SlotService slotService;
	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private HelpService helpService;
	@Autowired
	private ActivityService activityService;

	private String actor;
	private int dailyPlanId;
	private int activityId;
	private String title;
	private String startTime;
	private String endTime;
	private String description;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ "user1", 57, 68, "titulo", "04/04/2016 22:00",
						"04/04/2016 22:00", "Descripcion" },
				{ "user1", 60, 68, "titulo", "04/04/2016 22:00",
						"04/04/2016 22:00", "Descripcion" },
				{ "user1", 56, 68, "qwer", "04/02/2016 22:00",
						"04/04/2016 22:00", "Descripcion" },
				{ "user1", 56, 68, "titulo", "04/22/2016 22:00",
						"04/04/2016 22:00", "Descripcion" },

		});
	}

	public CreateSlotTest(String actor, int dailyPlanId, int activityId,
			String title, String startTime, String endTime, String description) {
		this.dailyPlanId = dailyPlanId;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.description = description;
		this.actor = actor;
		this.activityId = activityId;

	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 4 posibles creaciones de Slot. Para ello
	 *             se utilizan las propiedades dailyPlanId, title, day,
	 *             description y actor. En el primer caso probamos crear un slot
	 *             con los datos correctos, en el segundo caso probamos a crear
	 *             un slot a un dailyPlan que no pertenece al usuario logueado,
	 *             dando esta acción como errónea. Para los casos negativos
	 *             hemos capturado la excepción IllegalArgumentException.
	 */

	@Test
	public void testCreateSlot() {
		try {
			authenticate(actor);

			Slot slot = slotService.create();
			Activity act = activityService.findOne(activityId);
			DailyPlan dailyPlan = dailyPlanService.findOne(dailyPlanId);

			Date start = helpService.formatStringToDate(startTime);
			Date end = helpService.formatStringToDate(endTime);

			slot.setDailyPlan(dailyPlan);
			slot.setTitle(title);
			slot.setDescription(description);
			slot.setStartTime(start);
			slot.setEndTime(end);
			slot.setActivity(act);

			Assert.isTrue(slotService.checkOverlapping(slot) == 0);

			slotService.save(slot);

			unauthenticate();

		} catch (IllegalArgumentException e1) {
			System.out.println(e1);

		} catch (UnexpectedException e2) {
			System.out.println(e2);

			throw e2;
		}

	}
}
