package services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Commentable;
import domain.Trip;

import utilities.AbstractTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = false)
public class CommentableServiceTest extends AbstractTest {
	
	//Service under test ------------------
	@Autowired
	private CommentableService commentableService;
	
	//Supporting services -----------------
	@Autowired
	private TripService tripService;
	@Autowired
	private ActivityService activityService;
	
	//Positive Tests -------------------------------
	/**
	 * Comentable devuelve un Trip cuando se busca un Commentable
	 * por el id de un trip
	 */
	@Test
	public void testFindOneByTripId(){
		Trip trip = tripService.findAll().iterator().next();
		Commentable commentable = commentableService.findOne(trip.getId());
		Assert.isTrue(commentable instanceof Trip);
	}
	
	/**
	 * Comentable devuelve un Service cuando se busca un Commentable
	 * por el id de un Service
	 */
	@Test
	public void testFindOneByServiceId(){
		Activity activity = activityService.findAll().iterator().next();
		Commentable commentable = commentableService.findOne(activity.getId());
		Assert.isTrue(commentable instanceof Activity);
	}
	
	
	

}
