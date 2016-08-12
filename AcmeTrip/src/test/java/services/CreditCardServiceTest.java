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
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CreditCardServiceTest extends AbstractTest {

	@Autowired
	private CreditCardService creditCardService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------

	// creada exitosamente
	@Test
	public void testCreateCreditCard1() {
		authenticate("manager1");
		CreditCard creditCard = creditCardService.create();
		creditCard.setBrandName("La Caixa");
		creditCard.setCreditCardNumber("4431707235685709");
		creditCard.setCvvCode(147);
		creditCard.setExpirationMonth(05);
		creditCard.setExpirationYear(2020);
		creditCard.setHolderName("Rafael");

		creditCardService.save(creditCard);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// creditCard campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateCreditCard2() {
		authenticate("manager1");
		CreditCard creditCard = creditCardService.create();
		creditCard.setBrandName("La Caixa");
		creditCard.setCreditCardNumber("");
		creditCard.setCvvCode(147);
		creditCard.setExpirationMonth(05);
		creditCard.setExpirationYear(2020);
		creditCard.setHolderName("");

		creditCardService.save(creditCard);
		unauthenticate();
	}

	// creditCard con actor no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCreditCard3() {
		authenticate("user1");
		CreditCard creditCard = creditCardService.create();
		creditCard.setBrandName("La Caixa");
		creditCard.setCreditCardNumber("4431707235685709");
		creditCard.setCvvCode(147);
		creditCard.setExpirationMonth(05);
		creditCard.setExpirationYear(2020);
		creditCard.setHolderName("Rafael");

		creditCardService.save(creditCard);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deletCreditCard1() {
		authenticate("manager1");
		creditCardService.delete(creditCardService.findOne(19));
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un admin eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deletCreditCard2() {
		authenticate("admin");
		creditCardService.delete(creditCardService.findOne(19));
		unauthenticate();
	}

	// Eliminamos un creditcard que no es creditcardId

	@Test(expected = IllegalArgumentException.class)
	public void deletCreditCard3() {
		authenticate("manager1");
		creditCardService.delete(creditCardService.findOne(77458));
		unauthenticate();
	}

	// List credit cards
	@Test
	public void testFindCreditCard() {
		Collection<CreditCard> creditCards = creditCardService.findAll();
		Assert.isTrue(creditCards.size() == 4);
	}

}
