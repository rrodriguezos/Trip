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

import domain.Folder;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CreateFolderTest extends AbstractTest{
	
	@Autowired
	private FolderService folderService;
	
	private String name;
	private String authenticate;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { { "", "user1" },
				{ "carpeta", "none" }, {"carpeta", "admin" }});

	}
	
	public CreateFolderTest(String name, String authenticate){
		this.name = name;
		this.authenticate = authenticate;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 3 posibles acciones de crear un Folder. Para ello se utilizan las propiedades
	 * name y authenticate. Se llama para ello a la función create del Servicio. En el primer caso probamos crear 
	 * un objeto cuyo nombre esta en blanco, no llevandose a cabo, en el segundo intentamos crear un folder con usuario que no existe
	 * , en la tercera ocasión lo intenta hacer un actor logueado, llevandose a cabo la accion correctamente
	 * 
	 *Capturamos las excepciones TransactionSystemException, IllegalArgumentException.
	 */
	@Test
	public void testCreateFolder() {

		try {
			
			authenticate(authenticate);
			Folder folder;
			
			folder = folderService.create();
			
			folder.setName(name);
			
			folderService.save(folder);
			
			unauthenticate();

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
