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
public class DeleteFolderTest extends AbstractTest{
	
	@Autowired
	private FolderService folderService;
	
	private int folderId;
	private String authenticate;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { {22, "admin" },
				{256650, "user1" }, {17, "admin" }});

	}
	
	public DeleteFolderTest(int folderId, String authenticate){
		this.folderId = folderId;
		this.authenticate = authenticate;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 3 posibles acciones de eliminar un Folder. Para ello se utilizan las propiedades
	 * folderId y authenticate. Se llama para ello a la función delete del Servicio. En el primer caso probamos eliminar 
	 * un objeto cuyo propietario no es el que intenta hacerlo, no llevandose a cabo, en el seguundo intentamos eliminar
	 *  un objeto que no es un folder, y en el tercero, eliminamos uno correctamente
	 *  
	 *Capturamos las excepciones TransactionSystemException, IllegalArgumentException.
	 */
	
	@Test
	public void testDeleteFolder() {

		try {
			
			authenticate(authenticate);
			Folder folder;
			
			folder = folderService.findOne(folderId);
			
			folderService.delete(folder);
			
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
