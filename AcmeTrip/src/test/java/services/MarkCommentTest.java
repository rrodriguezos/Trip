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
	
	//1ºFlag un commentario que no es comentario
	//2ºflag un comentario que no es un comentario
	//3º Flag existosamente
	
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
