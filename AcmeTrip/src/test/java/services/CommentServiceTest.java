package services;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import utilities.AbstractTest;
import domain.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class CommentServiceTest extends AbstractTest {

	@Autowired
	private CommentService commentService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private TripService tripService;
	
	@Autowired
	private ActivityService activityService;

	// ----------------------------------------------------
	// POSITIVE TEST CASES FLAG
	// ----------------------------------------------------
	// Flag exitosamente

	@Test
	public void testFlagComment1() {
		authenticate("admin");

		commentService.changeAppropriated(92);

		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES FLAG
	// ----------------------------------------------------
	// Flag un commentario que no es comentario
	@Test(expected = IllegalArgumentException.class)
	public void testFlagComment2() {
		authenticate("admin");

		commentService.changeAppropriated(48271);

		unauthenticate();
	}

	// flag un comentario por un usuario no autorizado
	@Test(expected = IllegalArgumentException.class)
	public void testFlagComment3() {
		authenticate("user1");

		commentService.changeAppropriated(92);

		unauthenticate();
	}

	// ----------------------------------------------------
	// POSITIVE TEST CASES CREATE TRIP
	// ----------------------------------------------------

	// creado exitosamente
	@Test
	public void testCreateCommentTrip1() {
		authenticate("admin");
		Comment comment = new Comment();
		comment.setActor(actorService.findByPrincipal());
		comment.setCommentable(tripService.findAll().iterator().next());
		comment.setAppropriated(true);
		comment.setMoment(new Date(System.currentTimeMillis() - 1000));
		comment.setTitle("Create comment trip");
		comment.setText("Comment on Trip");
		commentService.save(comment);
		unauthenticate();
	}

	// ----------------------------------------------------
	// NEGATIVE TEST CASES CREATE TRIP
	// ----------------------------------------------------
	// comment campos en blanco
	@Test(expected = ConstraintViolationException.class)
	public void testCreateCommentTrip2() {
		authenticate("admin");
		Comment comment = new Comment();
		comment.setActor(actorService.findByPrincipal());
		comment.setCommentable(tripService.findAll().iterator().next());
		comment.setAppropriated(true);
		comment.setMoment(new Date(System.currentTimeMillis() - 1000));
		comment.setTitle("");
		comment.setText("");
		commentService.save(comment);
		unauthenticate();
	}

	// actor inexistente
	@Test(expected = IllegalArgumentException.class)
	public void testCreateCommentTrip3() {
		authenticate("none");
		Comment comment = new Comment();
		comment.setActor(actorService.findByPrincipal());
		comment.setCommentable(tripService.findAll().iterator().next());
		comment.setAppropriated(true);
		comment.setMoment(new Date(System.currentTimeMillis() - 1000));
		comment.setTitle("");
		comment.setText("");
		commentService.save(comment);
		unauthenticate();
	}
	
	// ----------------------------------------------------
		// POSITIVE TEST CASES CREATE ACTIVITY
		// ----------------------------------------------------

		// creado exitosamente
		@Test
		public void testCreateCommentActivity1() {
			authenticate("admin");
			Comment comment = new Comment();
			comment.setActor(actorService.findByPrincipal());
			comment.setCommentable(activityService.findAll().iterator().next());
			comment.setAppropriated(true);
			comment.setMoment(new Date(System.currentTimeMillis() - 1000));
			comment.setTitle("Create comment activity");
			comment.setText("Comment on activity");
			commentService.save(comment);
			unauthenticate();
		}

		// ----------------------------------------------------
		// NEGATIVE TEST CASES CREATE ACTIVITY
		// ----------------------------------------------------
		// comment campos en blanco
		@Test(expected = ConstraintViolationException.class)
		public void testCreateCommentActivity2() {
			authenticate("admin");
			Comment comment = new Comment();
			comment.setActor(actorService.findByPrincipal());
			comment.setCommentable(activityService.findAll().iterator().next());
			comment.setAppropriated(true);
			comment.setMoment(new Date(System.currentTimeMillis() - 1000));
			comment.setTitle("");
			comment.setText("");
			commentService.save(comment);
			unauthenticate();
		}

		// actor inexistente
		@Test(expected = IllegalArgumentException.class)
		public void testCreateCommentActivity3() {
			authenticate("none");
			Comment comment = new Comment();
			comment.setActor(actorService.findByPrincipal());
			comment.setCommentable(activityService.findAll().iterator().next());
			comment.setAppropriated(true);
			comment.setMoment(new Date(System.currentTimeMillis() - 1000));
			comment.setTitle("Create comment activity");
			comment.setText("Comment on activity");
			commentService.save(comment);
			unauthenticate();
		}

}
