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

import com.mchange.v1.util.UnexpectedException;

import utilities.AbstractTest;

@RunWith(Parameterized.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class MarkCommentTest extends AbstractTest{
	
	@Autowired
	private CommentService commentService;
	
	private int commentId;
	private String authenticate;
	
	@Parameters
	public static Collection<Object[]> data() {

		return Arrays.asList(new Object[][] { { 1651566, "user1" },
				{ 74, "none" }, { 74, "admin" } });

	}
	
	public MarkCommentTest(int commentId, String authenticate){
		this.commentId = commentId;
		this.authenticate = authenticate;
	}
	
	/**
	 * 
	 * @throws En este test se prueban 3 posibles acciones de marcar un Comment. Para ello se utilizan las propiedades
	 * commentId y authenticate. Se llama para ello a la función copyTrip del Servicio. En el primer caso probamos marcar 
	 * un objeto que no es un comment, no llevandose a cabo, en el segundo intentamos marcar un Comment 
	 * sin que el actor este logueado, en la tercera ocasión se hace correctamente
	 * 
	 *Capturamos las excepciones NullPointerException, IllegalArgumentException.
	 */
	
	@Test
	public void testMarkcomment() {

		try {
			
			authenticate(authenticate);

			commentService.changeAppropriated(commentId);
			
			unauthenticate();

		} catch (NullPointerException e1) {
			System.out.println(e1);
		} catch (IllegalArgumentException e2) {
			System.out.println(e2);
		} catch (UnexpectedException e3) {
			System.out.println(e3);
			throw e3;
		}
	}


}
