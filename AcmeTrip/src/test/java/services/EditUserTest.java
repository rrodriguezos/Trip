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

import security.UserAccount;
import utilities.AbstractTest;

import com.mchange.v1.util.UnexpectedException;

import domain.User;
import forms.UserRegisterForm;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class EditUserTest extends AbstractTest {

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
						{ "user1", "user1", "Juan", "+34662598657",
								"hola@gmail.com" },
						{ "user10", "user10", "", "+34662598657",
								"hola@gmail.com" },
						{ "user89", "user89", "Pacuelo", "+34662598657",
								"hola@gmail.com" },
						{ "user25", "user25", "Juan", "+34662598657",
								"holagmail.com" }

				});
	}

	public EditUserTest(String password, String username, String name,
			String phone, String email) {
		this.password = password;
		this.username = username;
		this.name = name;
		this.phone = phone;
		this.email = email;
	}

	/**
	 * 
	 * @throws En
	 *             este test se prueban 3 posibles modificaciones de ussuario.
	 *             Para ello se utilizan las propiedades password, username,
	 *             name y phone. En el primer caso probamos modificar un usuario
	 *             con los datos correctos, en el resto de casos, probamos
	 *             editar el perfil del usuario con unos datos erroneos, asi
	 *             como campos vacios. Para los casos negativos hemos capturado
	 *             las excepciones DataIntegrityViolationException y
	 *             TransactionSystemException.
	 */

	@Test
	public void testEditUser() {
		try {
			authenticate(username);
			
			UserRegisterForm userForm = userService.copyUser();
			
			userForm.setName(name);
			userForm.setPhone(phone);
			userForm.setSurname("Euclídeo");
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
