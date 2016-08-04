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
public class EditFolderTest extends AbstractTest{
	
	@Autowired
	private FolderService folderService;
	
	private int folderId;
	private String name;
	private String authenticate;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { {20, "", "admin" },
				{256650, "asd", "user1" }, {20, "carpeta", "user1" }, {20, "carpeta", "admin" }});

	}
	
	public EditFolderTest(int folderId, String name, String authenticate){
		this.folderId = folderId;
		this.name = name;
		this.authenticate = authenticate;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 4 posibles acciones de editar un Folder. Para ello se utilizan las propiedades
	 * folderId, name y authenticate. Se llama para ello a la función findOne y Save del Servicio. En el primer caso probamos editar 
	 * un objeto cuyo nombre esta en blanco, no llevandose a cabo, en el seguundo intentamos editar un objeto que no es un folder,
	 *  en el tercero intentamos editar un folder con usuario que no es dueño de el. En la cuarta se realiza correctamente
	 *  
	 *Capturamos las excepciones TransactionSystemException, IllegalArgumentException.
	 */
	
	@Test
	public void testEditFolder() {

		try {
			
			authenticate(authenticate);
			Folder folder;
			
			folder = folderService.findOne(folderId);
			
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
