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
import org.springframework.transaction.TransactionSystemException;

import com.mchange.v1.util.UnexpectedException;

import domain.Message;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class DeleteMessageTest extends AbstractTest{
	
	@Autowired
	private MessageService messageService;
	
	private int messageId;
	private String authenticate;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { {25, "admin" },
				{256650, "user1" }, {25, "user1" }});

	}
	
	public DeleteMessageTest(int messageId, String authenticate){
		this.messageId = messageId;
		this.authenticate = authenticate;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 3 posibles acciones de eliminar un Mensaje. Para ello se utilizan las propiedades
	 * messageId y authenticate. Se llama para ello a la función delete del Servicio. En el primer caso probamos eliminar 
	 * un objeto cuyo propietario no es el que intenta hacerlo, no llevandose a cabo, en el seguundo intentamos eliminar
	 *  un objeto que no es un Message, y en el tercero, eliminamos uno correctamente
	 *  
	 *Capturamos las excepciones TransactionSystemException, IllegalArgumentException.
	 */
	
	@Test
	public void testDeleteMessage() {

		try {
			
			authenticate(authenticate);
			Message message;
			
			message = messageService.findOne(messageId);
			
			messageService.delete(message);
			
			unauthenticate();

		} catch (TransactionSystemException e1) {
			System.out.println(e1);
		} catch (IllegalArgumentException e2) {
			System.out.println(e2);
		} catch (UnexpectedException e3) {
			System.out.println(e3);
			throw e3;
		}

	}
		
}
