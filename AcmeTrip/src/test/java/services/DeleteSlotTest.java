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

import domain.Slot;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DeleteSlotTest extends AbstractTest {

	@Autowired
	private SlotService slotService;

	private int slotId;
	private String authenticate;

	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { { 70, "user1" }, { 60, "user1" },
				{ 70, "admin" }, { 70, "user2" } });

	}

	public DeleteSlotTest(int slotId, String authenticate) {
		this.slotId = slotId;
		this.authenticate = authenticate;
	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 4 posibles acciones de eliminar un Slot.
	 *             Para ello se utilizan las propiedades slotId y authenticate.
	 *             Se llama para ello a la función delete del Servicio. En el
	 *             primer caso eliminamos correctamente el slot, en el segundo
	 *             caso probamos eliminar un objeto que no es un slot, no
	 *             llevandose a cabo, en la tercera ocasión lo intenta hacer un
	 *             actor logueado como administrador, siendo erroneo tambien y
	 *             en cuarto lugar lo intenta hacer un actor al que no le
	 *             pertenece el Slot.
	 * 
	 *             Capturamos las excepciones NullPointerException,
	 *             IllegalArgumentException.
	 */

	@Test
	public void testDeleteSlot() {

		try {

			authenticate(authenticate);
			Slot slot;

			slot = slotService.findOne(slotId);

			slotService.delete(slot);

			unauthenticate();

		} catch (IllegalArgumentException e2) {
			System.out.println(e2);
		} catch (UnexpectedException e3) {
			System.out.println(e3);
			throw e3;
		}
	}

}
