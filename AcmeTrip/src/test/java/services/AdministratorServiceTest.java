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

import com.mchange.v1.util.UnexpectedException;

import domain.ActivityType;
import domain.Administrator;

import forms.AdministratorForm;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class AdministratorServiceTest extends AbstractTest{
	
	@Autowired
	private AdministratorService adminService;
	
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
				{ "userTest", "userprua", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "admin", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "", "+34644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "644512313", "rafarod@gmail.com"},
				{ "userTest", "userTest", "userTest", "Rafael", "Rodriguez", "+34644512313", "rafarod@gmail.com"}});

	}
	//14.1. Register managers and other administrators.
	public AdministratorServiceTest(String password, String passwordRepeat, String username, String name, String surname, String phone, String email){
		this.password = password;
		this.passwordRepeat = passwordRepeat;
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.email = email;
	}
	
	//1º crear admin con pass diferentes
	//2º crear admin con username vacio
	//3ºcrear con pass vacio
	//4º crear con username existente
	//5º crear con un correo no valido
	//6ºcon campo username vacio
	//7ºtelefono con patron erroneo
	//8º se crea exitosamente

	
	@Test
	public void testCreateMessage() {

		try {

			AdministratorForm adminForm;
			
			adminForm = new AdministratorForm();
			
			adminForm.setPassword(password);
			adminForm.setConfirmPassword(passwordRepeat);
			adminForm.setUsername(username);
			adminForm.setName(name);
			adminForm.setSurname(surname);
			adminForm.setPhone(phone);
			adminForm.setEmailAddress(email);
			
			
			adminService.reconstruct(adminForm);

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
			public void testFindAdministrator() {
				Collection<Administrator> admins = adminService.findAll();
				Assert.isTrue(admins.size() == 1);
			}


}
