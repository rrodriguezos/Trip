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
import org.springframework.util.Assert;

import utilities.AbstractTest;

import com.mchange.v1.util.UnexpectedException;

import domain.Manager;
import forms.ManagerForm;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class ManagerServiceTest extends AbstractTest{
	
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
				{ "userTest", "usert", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "admin", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"}});

	}
	
	//14.1. Register managers and other administrators.
	
	public ManagerServiceTest(String password, String passwordRepeat, String username, String name, String surname, String phone, String email){
		this.password = password;
		this.passwordRepeat = passwordRepeat;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
	}
	
		//1� crear manager con pass diferentes
	//2� crear manager con username vacio
	//3�crear con pass vacio
	//4� crear con username existente
	//5� crear con un correo no valido
	//6�con campo username vacio
	//7�telefono con patron erroneo
	//8� se crea exitosamente
	
	@Test
	public void testCreateManager() {

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
	
	// Listing requirement 1

	@Test
	public void testFindManagers() {
		Collection<Manager> managers = managerService.findAll();
		Assert.isTrue(managers.size() == 2);
	}

}
