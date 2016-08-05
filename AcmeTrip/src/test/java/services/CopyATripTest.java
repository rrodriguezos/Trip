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
public class CopyATripTest  extends AbstractTest{
	
	@Autowired
	private TripService tripService;
	
	private int tripId;
	private String authenticate;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { { 1651566, "user1" },
				{ 56, "user1" }, { 57, "admin" }, { 60, "user1" } });

	}
	
	public CopyATripTest(int tripId, String authenticate){
		this.tripId = tripId;
		this.authenticate = authenticate;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 4 posibles acciones de copiar un Trip. Para ello se utilizan las propiedades
	 * tripId y authenticate. Se llama para ello a la función copyTrip del Servicio. En el primer caso probamos copiar 
	 * un objeto que no es un trip, no llevandose a cabo, en el segundo intentamos copiar un trip que pertenece al usuario
	 * que intenta la accion, en la tercera ocasión lo intenta hacer un actor logueado como administrador, siendo erroneo tambien
	 *  y en cuarto lugar se copia sin problemas.
	 * 
	 *Capturamos las excepciones NullPointerException, IllegalArgumentException.
	 */
	
	@Test
	public void testCopyTrip() {

		try {
			
			authenticate(authenticate);
			Trip trip;
			
			trip = tripService.findOne(tripId);
			
			tripService.copyPasteTrip(trip);
			
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
