package services;

import java.util.Collection;
import java.util.Date;

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
import domain.Trip;
import domain.User;
import forms.AdministratorForm;
import forms.UserRegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest extends AbstractTest {

	@Autowired
	private UserService userService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// Crear user
	// creado exitosamente
	@Test
	public void testCreateUser1() {

		UserRegisterForm userRegisteredForm;

		userRegisteredForm = new UserRegisterForm();

		userRegisteredForm.setPassword("userTest");
		userRegisteredForm.setConfirmPassword("userTest");
		userRegisteredForm.setUsername("userTest");
		userRegisteredForm.setName("Rafael");
		userRegisteredForm.setSurname("Rodriguez");
		userRegisteredForm.setPhone("+34644512313");
		userRegisteredForm.setEmailAddress("rafarod@gmail.com");
		userService.reconstruct(userRegisteredForm);

	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// crear user con username vacio
	@Test(expected = ConstraintViolationException.class)
	public void testCreateUser2() {
		UserRegisterForm userRegisteredForm;

		userRegisteredForm = new UserRegisterForm();

		userRegisteredForm.setPassword("userTest");
		userRegisteredForm.setConfirmPassword("userTest");
		userRegisteredForm.setUsername("");
		userRegisteredForm.setName("Rafael");
		userRegisteredForm.setSurname("Rodriguez");
		userRegisteredForm.setPhone("+34644512313");
		userRegisteredForm.setEmailAddress("rafarod@gmail.com");
		userService.reconstruct(userRegisteredForm);
	}

	// telefono con patron erroneo
	@Test(expected = IllegalArgumentException.class)
	public void testCreateUser3() {
		UserRegisterForm userRegisterForm;

		userRegisterForm = new UserRegisterForm();

		userRegisterForm.setPassword("userTest");
		userRegisterForm.setConfirmPassword("userTest");
		userRegisterForm.setUsername("userTest");
		userRegisterForm.setName("Rafael");
		userRegisterForm.setSurname("Rodriguez");
		userRegisterForm.setPhone("644512313");
		userRegisterForm.setEmailAddress("rafarod@gmail.com");
		userService.reconstruct(userRegisterForm);

	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	public void editUser1() {

		authenticate("user1");

		UserRegisterForm userForm = userService.copyUser();

		userForm.setName("Rafa");
		userForm.setPhone("+34669988745");
		userForm.setSurname("Rodriguez");
		userForm.setPassword("user1");
		userForm.setUsername("user1");
		userForm.setEmailAddress("etsii@gmail.com");
		userForm.setPasswordPast("user1");
		userForm.setConfirmPassword("user1");

		userService.reconstruct(userForm);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void editUser2() {

		authenticate("user1");

		UserRegisterForm userForm = userService.copyUser();

		userForm.setName("Rafa");
		userForm.setPhone("+34669988745");
		userForm.setSurname("");
		userForm.setPassword("user1");
		userForm.setUsername("user1");
		userForm.setEmailAddress("etsii@gmail.com");
		userForm.setPasswordPast("user1");
		userForm.setConfirmPassword("user1");

		userService.reconstruct(userForm);

		unauthenticate();
	}

	// editar con campos erroneos
	@Test(expected = IllegalArgumentException.class)
	public void editUser3() {

		authenticate("user1");

		UserRegisterForm userForm = userService.copyUser();

		userForm.setName("Rafa");
		userForm.setPhone("+34669988745");
		userForm.setSurname("Rodriguez");
		userForm.setPassword("user1");
		userForm.setUsername("user1");
		userForm.setEmailAddress("etsii@.com");
		userForm.setPasswordPast("user1");
		userForm.setConfirmPassword("user1");

		userService.reconstruct(userForm);

		unauthenticate();

	}

	// 4. A user who is not authenticated must be able to:
	// 1. List and see the profile of the users who have registered to the system
	@Test
	public void testFindUsers() {
		Collection<User> users = userService.findAll();
		Assert.isTrue(users.size() == 4);
	}
}
