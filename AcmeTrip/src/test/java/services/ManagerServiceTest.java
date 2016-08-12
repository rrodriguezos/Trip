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
import forms.ManagerForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ManagerServiceTest extends AbstractTest {

	@Autowired
	private ManagerService managerService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// Crear manager
	// creado exitosamente
	@Test
	public void testCreateManager1() {
		authenticate("admin");
		ManagerForm managerForm;

		managerForm = new ManagerForm();

		managerForm.setPassword("userTest");
		managerForm.setConfirmPassword("userTest");
		managerForm.setUsername("userTest");
		managerForm.setName("Rafael");
		managerForm.setSurname("Rodriguez");
		managerForm.setPhone("+34644512313");
		managerForm.setEmailAddress("rafarod@gmail.com");
		managerService.reconstruct(managerForm);

	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// crear admin con username vacio
	@Test(expected = ConstraintViolationException.class)
	public void testCreateManager2() {
		authenticate("admin");
		ManagerForm managerForm;

		managerForm = new ManagerForm();

		managerForm.setPassword("");
		managerForm.setConfirmPassword("");
		managerForm.setUsername("");
		managerForm.setName("");
		managerForm.setSurname("");
		managerForm.setPhone("+34644512313");
		managerForm.setEmailAddress("");
		managerService.reconstruct(managerForm);
	}

	// telefono con patron erroneo
	@Test(expected = IllegalArgumentException.class)
	public void testCreateManager3() {
		authenticate("admin");
		ManagerForm managerForm;

		managerForm = new ManagerForm();

		managerForm.setPassword("userTest");
		managerForm.setConfirmPassword("userTest");
		managerForm.setUsername("userTest");
		managerForm.setName("Rafael");
		managerForm.setSurname("Rodriguez");
		managerForm.setPhone("644512313");
		managerForm.setEmailAddress("rafarod@gmail.com");
		managerService.reconstruct(managerForm);

	}

}
