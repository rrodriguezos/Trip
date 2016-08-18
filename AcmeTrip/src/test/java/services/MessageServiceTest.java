package services;

import java.util.Collection;

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
import domain.Actor;
import domain.DailyPlan;
import domain.Message;
import domain.Message.MessagePriority;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService messageService;

	@Autowired
	private ActorService actorService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// 12.2 Exchange messages with other users.
	// creado exitosamente El user1 le manda un mensaje al user2
	@Test
	public void testCreateMessages1() {

		authenticate("user1");
		Message message;
		Actor recipient;
		Actor sender;

		recipient = actorService.findOne(10);
		sender = actorService.findByPrincipal();

		message = messageService.create();

		message.setBody("Mensaje 1");
		message.setSubject("Mensaje 1 body");
		message.setRecipient(recipient);
		message.setSender(sender);
		message.setMessagePriority(MessagePriority.NEUTRAL);

		message = messageService.saveToSend(message);
		messageService.save(message);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// message campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateMessages2() {
		authenticate("user1");
		Message message;
		Actor recipient;
		Actor sender;

		recipient = actorService.findOne(10);
		sender = actorService.findByPrincipal();

		message = messageService.create();

		message.setBody("");
		message.setSubject("Mensaje 1 body");
		message.setRecipient(recipient);
		message.setSender(sender);
		message.setMessagePriority(MessagePriority.NEUTRAL);

		message = messageService.saveToSend(message);
		messageService.save(message);

		unauthenticate();
	}

	// enviado al mismo actor que lo envia
	@Test(expected = ConstraintViolationException.class)
	public void testCreateMessages3() {
		authenticate("user1");
		Message message;
		Actor recipient;
		Actor sender;

		recipient = actorService.findOne(9);
		sender = actorService.findByPrincipal();

		message = messageService.create();

		message.setBody("");
		message.setSubject("Mensaje 1 body");
		message.setRecipient(recipient);
		message.setSender(sender);
		message.setMessagePriority(MessagePriority.NEUTRAL);

		message = messageService.saveToSend(message);
		messageService.save(message);

		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deleteMessage1() {
		authenticate("user1");
		Message message;

		message = messageService.findOne(42);

		messageService.delete(message);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un actor no autentificado eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessage2() {
		authenticate("none");
		Message message;

		message = messageService.findOne(42);

		messageService.delete(message);

		unauthenticate();
	}

	// Eliminamos un message que no es messageId

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessage3() {
		authenticate("admin");
		Message message;

		message = messageService.findOne(878754);

		messageService.delete(message);

		unauthenticate();
	}
	
	// Listing requirement 1

			@Test
			public void testFindMessages() {
				Collection<Message> messages = messageService.findAll();
				Assert.isTrue(messages.size() == 6);
			}

}
