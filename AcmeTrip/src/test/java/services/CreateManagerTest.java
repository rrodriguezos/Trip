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

import forms.ManagerForm;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CreateManagerTest extends AbstractTest{
	
	@Autowired
	private ManagerService managerService;
	
	private String password;
	private String passwordRepeat;
	private String username;
	private String name;
	private String surname;
	private String phone;
	private String email;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] {
				{ "userprueba", "userprua", "userprueba", "Pepe", "Perez", "+34696685547", "pepe@pepe.pep"},
				{ "userprueba", "userprueba", "", "Pepe", "Perez", "+34696685547", "pepe@pepe.pep"},
				{ "userprueba", "userprueba", "manager", "Pepe", "Perez", "+34696685547", "pepe@pepe.pep"},
				{ "userprueba", "userprueba", "userprueba", "Pepe", "Perez", "+34696685547", "pepe@.pep"},
				{ "userprueba", "userprueba", "userprueba", "Pepe", "Perez", "+34696685547", "pepe@pepe.pep"},
				{ "userprueba", "userprueba", "userprueba", "Pepe", "", "+34696685547", "pepe@pepe.pep"},
				{ "userprueba", "userprueba", "userprueba", "Pepe", "Perez", "696685547", "pepe@pepe.pep"},
				{ "userprueba", "userprueba", "userprueba", "Pepe", "Perez", "+34696685547", "pepe@pepe.pep"}});

	}
	
	public CreateManagerTest(String password, String passwordRepeat, String username, String name, String surname, String phone, String email){
		this.password = password;
		this.passwordRepeat = passwordRepeat;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 11 posibles acciones de crear un Admin. Para ello se utilizan las propiedades password,
	 * passwordRepeat, username, name, surname, phone y email. Se llama para ello a la función reconstruct del Servicio. 
	 * En el primer caso probamos crear un manager con las contraseñas diferentes(fallido)
	 * En segundo lugar se intenta crear con el campo username a vacio(fallido)
	 * En el tercer caso se intenta crear con el campo password a vacio (fallido)
	 * En el cuarto caso se intenta crear con un username ya existente (fallido)
	 * En el quinto  caso se intenta con un correo no valido (fallido)
	 * En el sexto caso se intenta con el campo surname a vacio (fallido)
	 * En el septimo caso se intenta crear con un telefono que no pasa el patron (fallido)
	 * En el octavo caso se crea correctamente.
	 * 
	 * 
	 *Capturamos las excepciones TransactionSystemException, IllegalArgumentException.
	 */
	
	@Test
	public void testCreateMessage() {

		try {

			ManagerForm managerForm;
			
			managerForm = new ManagerForm();
			
			managerForm.setPassword(password);
			managerForm.setConfirmPassword(passwordRepeat);
			managerForm.setUsername(username);
			managerForm.setName(name);
			managerForm.setSurname(surname);
			managerForm.setPhone(phone);
			managerForm.setEmailAddress(email);
			
			
			managerService.reconstruct(managerForm);

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
