package services;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Folder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class FolderServiceTest extends AbstractTest {

	@Autowired
	private FolderService folderService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE
	// ----------------------------------------------------
	// A user who is authenticated as an user
	// Actors can create as many message folders as they wish
	// creado exitosamente
	@Test
	public void testCreateFolder1() {
		authenticate("admin");
		Folder folder = folderService.create();
		folder.setName("Carpeta 1");
		folderService.save(folder);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE
	// ----------------------------------------------------
	// folder campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateFolder2() {
		authenticate("user1");
		Folder folder = folderService.create();
		folder.setName("");
		folderService.save(folder);
		unauthenticate();
	}

	// trip con actores no autorizados
	@Test(expected = IllegalArgumentException.class)
	public void testCreateFolder3() {
		authenticate("empty");
		Folder folder = folderService.create();
		folder.setName("Carpeta 1");
		folderService.save(folder);
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminado correctamente

	@Test
	public void deletFolder1() {
		authenticate("user1");
		folderService.delete(folderService.findOne(76));
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES DELETE
	// ----------------------------------------------------
	// Eliminamos una folder no perteneciente al que lo hace

	@Test(expected = TransactionSystemException.class)
	public void deleteFolder2() {
		authenticate("admin");
		folderService.delete(folderService.findOne(47));
		unauthenticate();
	}

	// Eliminamos un trip que no es tripId

	@Test(expected = IllegalArgumentException.class)
	public void deleteFolder3() {
		authenticate("admin");
		folderService.delete(folderService.findOne(556350));
		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES EDITION
	// ----------------------------------------------------

	// editado correctamente
	public void editFolder1() {

		authenticate("admin");

		Folder folder = folderService.findOne(35);
		folder.setName("Carpeta");
		folderService.save(folder);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES EDITION
	// ----------------------------------------------------
	// Edicion campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void editFolder2() {

		authenticate("admin");

		Folder folder = folderService.findOne(35);
		folder.setName("");
		folderService.save(folder);

		unauthenticate();
	}

	// editar un objeto que no es folder
	@Test(expected = IllegalArgumentException.class)
	public void editFolder3() {

		authenticate("admin");

		Folder folder = folderService.findOne(221587);
		folder.setName("Carpeta");
		folderService.save(folder);

		unauthenticate();
	}

}
