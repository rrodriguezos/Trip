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

import domain.Actor;
import domain.Message;
import domain.Message.MessagePriority;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CreateMessageTest extends AbstractTest {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private ActorService actorService;

	private String subject;
	private String body;
	private String sender;
	private int recipientId;

	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] {
				{ "Mensaje 1", "", "user2", 9},
				{ "", "Mensaje 1", "user2", 9},
				{ "Mensaje 1", "Mensaje 1", "user1", 9 },
				{ "Mensaje 1", "Mensaje 1", "user1", 8} });

	}
	
	public CreateMessageTest(String subject, String body, String sender, int recipient){
		this.subject = subject;
		this.body = body;
		this.sender = sender;
		this.recipientId = recipient;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 4 posibles acciones de crear un Mensaje. Para ello se utilizan las propiedades
	 * subject, bodyn sender y recipient. Se llama para ello a la función create del Servicio. En el primer caso probamos crear 
	 * un objeto cuyo subject esta en blanco, no llevandose a cabo, en el segundo intentamos crear un mensaje con el cuerpo vacio
	 * , en la tercera ocasión lo intentamos enviar al mismo usuario que lo envia. Por ultimo, se crea correctamente
	 * 
	 *Capturamos las excepciones TransactionSystemException, IllegalArgumentException.
	 */
	
	@Test
	public void testCreateMessage() {

		try {
			
			authenticate(sender);
			Message message;
			Actor recipient;
			Actor sender;
			
			recipient = actorService.findOne(recipientId);
			sender = actorService.findByPrincipal();
			
			message = messageService.create();
			
			message.setBody(body);
			message.setSubject(subject);
			message.setRecipient(recipient);
			message.setSender(sender);
			message.setMessagePriority(MessagePriority.NEUTRAL);
			
			message = messageService.saveToSend(message);
			messageService.save(message);
			
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
