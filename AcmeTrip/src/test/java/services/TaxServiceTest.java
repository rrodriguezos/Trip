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
import domain.Tax;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class TaxServiceTest extends AbstractTest {

	@Autowired
	private TaxService taxService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------

	// creado exitosamente
	@Test
	public void testCreateTax1() {
		authenticate("admin");
		Tax tax = taxService.create();
		tax.setTaxType(22.0);

		taxService.save(tax);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// tax null
	@Test(expected = ConstraintViolationException.class)
	public void testCreateTax2() {
		authenticate("admin");
		Tax tax = taxService.create();
		tax.setTaxType(null);

		taxService.save(tax);
		unauthenticate();
	}

	// tax con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateTax3() {
		authenticate("user1");
		Tax tax = taxService.create();
		tax.setTaxType(22.0);

		taxService.save(tax);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deletTax1() {
		authenticate("admin");
		taxService.delete(taxService.findOne(32));
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Lo intenta un user eliminar

	@Test(expected = IllegalArgumentException.class)
	public void deletTax2() {
		authenticate("user1");
		taxService.delete(taxService.findOne(32));
		unauthenticate();
	}

	// Eliminamos un tax que no es taxId

	@Test(expected = IllegalArgumentException.class)
	public void deletTax3() {
		authenticate("admin");
		taxService.delete(taxService.findOne(98465));
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	@Test
	public void editTax1() {

		authenticate("admin");

		Tax tax = taxService.findOne(32);
		tax.setTaxType(50.00);

		taxService.saveEdit(tax);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos null
	@Test(expected = ConstraintViolationException.class)
	public void editTax2() {

		authenticate("admin");

		Tax tax = taxService.findOne(32);
		tax.setTaxType(null);

		taxService.saveEdit(tax);

		unauthenticate();
	}

	// editar con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void editTax3() {

		authenticate("none");

		Tax tax = taxService.findOne(32);
		tax.setTaxType(25.00);

		taxService.saveEdit(tax);

		unauthenticate();
	}

}
