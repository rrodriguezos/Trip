package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import forms.AdministratorForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AdministratorServiceTest extends AbstractTest {

	@Autowired
	private AdministratorService administratorService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// Crear administrador
	// creado exitosamente
	@Test
	public void testCreateAdmin1() {
		authenticate("admin");
		AdministratorForm adminForm;

		adminForm = new AdministratorForm();

		adminForm.setPassword("userTest");
		adminForm.setConfirmPassword("userTest");
		adminForm.setUsername("userTest");
		adminForm.setName("Rafael");
		adminForm.setSurname("Rodriguez");
		adminForm.setPhone("+34644512313");
		adminForm.setEmailAddress("rafarod@gmail.com");
		administratorService.reconstruct(adminForm);

	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// crear admin con username vacio
	@Test(expected = ConstraintViolationException.class)
	public void testCreateAdmin2() {
		authenticate("admin");
		AdministratorForm adminForm;

		adminForm = new AdministratorForm();

		adminForm.setPassword("userTest");
		adminForm.setConfirmPassword("userTest");
		adminForm.setUsername("");
		adminForm.setName("Rafael");
		adminForm.setSurname("Rodriguez");
		adminForm.setPhone("+34644512313");
		adminForm.setEmailAddress("rafarod@gmail.com");
		administratorService.reconstruct(adminForm);
	}

	// telefono con patron erroneo
	@Test(expected = IllegalArgumentException.class)
	public void testCreateAdmin3() {
		authenticate("admin");
		AdministratorForm adminForm;

		adminForm = new AdministratorForm();

		adminForm.setPassword("userTest");
		adminForm.setConfirmPassword("userTest");
		adminForm.setUsername("userTest");
		adminForm.setName("Rafael");
		adminForm.setSurname("Rodriguez");
		adminForm.setPhone("644512313");
		adminForm.setEmailAddress("rafarod@gmail.com");
		administratorService.reconstruct(adminForm);

	}

}
