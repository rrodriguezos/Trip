package services;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;

import com.mchange.v1.util.UnexpectedException;

import forms.UserRegisterForm;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class UserServiceTest extends AbstractTest {

	@Autowired
	private UserService userService;

	private String password;
	private String username;
	private String name;
	private String phone;
	private String email;

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						{ "user1", "user1", "Rafa", "+34669988745",
								"etsii@gmail.com" },
						{ "user10", "user10", "", "+34669988745",
								"etsii@gmail.com" },
						{ "user89", "user89", "Antonio", "+34669988745",
								"etsii@gmail.com" },
						{ "user25", "user25", "Alex", "+34669988745",
								"etsiigmail.com" }

				});
	}

	public UserServiceTest(String password, String username, String name,
			String phone, String email) {
		this.password = password;
		this.username = username;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	// 1ºeditado exitosamente
	// 2ºeditamos con datos erroneos
	// 3ºeditamos con campos vacios

	@Test
	public void testEditUser() {
		try {
			authenticate(username);

			UserRegisterForm userForm = userService.copyUser();

			userForm.setName(name);
			userForm.setPhone(phone);
			userForm.setSurname("Carlos");
			userForm.setPassword(password);
			userForm.setUsername(username);
			userForm.setEmailAddress(email);
			userForm.setPasswordPast(password);
			userForm.setConfirmPassword(password);

			userService.reconstruct(userForm);

			unauthenticate();

		} catch (DataIntegrityViolationException e) {
			System.out.println(e);
		} catch (TransactionSystemException e1) {
			System.out.println(e1);
		} catch (IllegalArgumentException e1) {
			System.out.println(e1);

		} catch (UnexpectedException e2) {
			System.out.println(e2);

			throw e2;
		}

	}
}
