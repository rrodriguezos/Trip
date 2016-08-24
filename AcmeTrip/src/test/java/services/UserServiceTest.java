package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.User;
import forms.UserRegisterForm;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class UserServiceTest extends AbstractTest {

	// Service under test ------------------------
	@Autowired
	private UserService userService;

	/* *****************************************************
	 * *An actor who is not authenticated must be able to:* Register
	 * to the system as a user.
	 * ******************************************************
	 */

	/**
	 * Positive: A non authenticated registers as a user
	 */
	@Test
	public void registerUserPositiveTest() {
		int actual = userService.findAll().size();
		unauthenticate();
		UserRegisterForm registerForm = new UserRegisterForm();
		registerForm.setName("Name Test");
		registerForm.setSurname("Surname Test");
		registerForm.setPhone("+34 (95) 758400");
		registerForm.setUsername("Username Test");
		registerForm.setPassword("Password Test");
		registerForm.setConfirmPassword("Password Test");
		registerForm.setAccept(true);
		registerForm.setPasswordPast("Password Test");
		registerForm.setEmailAddress("test@gmail.com");
		User user = userService.reconstruct(registerForm);
		userService.save(user);
		int expected = userService.findAll().size();
		Assert.isTrue(expected == actual + 1);
	}

	/**
	 * Negative: An authenticated none attempts to register as a user
	 */
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void registerUserAsAdminNegativeTest() {
		authenticate("none");
		UserRegisterForm registerForm = new UserRegisterForm();
		registerForm.setName("Name Test");
		registerForm.setSurname("Surname Test");
		registerForm.setPhone("+34 (95) 758400");
		registerForm.setUsername("Username Test");
		registerForm.setPassword("Password Test");
		registerForm.setConfirmPassword("Password Test");
		registerForm.setAccept(true);
		registerForm.setEmailAddress("test@gmail.com");
		registerForm.setPasswordPast("Password Test");
		User user = userService.reconstruct(registerForm);
		userService.save(user);
	}

	/**
	 * Negative: A non authenticated attempts to register as a user submitting
	 * wrong confirmation password
	 */
	@Test(expected = IllegalArgumentException.class)
	@Rollback(true)
	public void registerUserWrongFieldsNegativeTest() {
		unauthenticate();
		UserRegisterForm registerForm = new UserRegisterForm();
		registerForm.setName("Name Test");
		registerForm.setSurname("Surname Test");
		registerForm.setPhone("+34 (95) 758400");
		registerForm.setUsername("Username Test");
		registerForm.setPassword("Password Test");
		registerForm.setConfirmPassword("Different Password Test");
		User user = userService.reconstruct(registerForm);
		userService.save(user);
	}

	/* *****************************************************
	 * List and see the profile of the users who have registered
	 * to the system, which consists* of the contact data plus the list of trips
	 * that they have registered.
	 * ******************************************************
	 */

	/**
	 * Positive: A non authenticated can list the registered users
	 */
	@Test
	public void listingUsersAsNonAuthenticatedTest() {
		unauthenticate();
		Collection<User> users = userService.findAll();
		Assert.notNull(users);
		if (!users.isEmpty()) {
			for (User u : users) {
				Assert.notNull(u.getTrips());
			}
		}
	}

	/**
	 * Positive: An administrator can list the registered users
	 */
	@Test
	public void listingUsersAsAdministratorTest() {
		authenticate("admin");
		Collection<User> users = userService.findAll();
		Assert.notNull(users);
		if (!users.isEmpty()) {
			for (User u : users) {
				Assert.notNull(u.getTrips());
			}
		}
	}

}
