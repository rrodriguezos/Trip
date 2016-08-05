package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Activity;
import domain.Comment;
import domain.Trip;

import services.ActivityService;
import services.CommentService;
import services.TripService;

@Controller
@RequestMapping("/activity")
public class ActivityController extends AbstractController {
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private TripService tripService;
	
	@Autowired
	private CommentService commentService;
		
	// Constructors -----------------------------------------------------------
	public ActivityController() {
		super();
	}
	
	// List -----------------------------------------------------------	
	@RequestMapping(value = "/list")
	public ModelAndView listAppropriated() {
		ModelAndView result;
		Collection<Activity> activities;

		activities = activityService.findAllAppropriated();

		result = new ModelAndView("activity/list");
		result.addObject("activities", activities);
		result.addObject("requestUri", "activity/list.do");

		return result;
	}
	
	//Display -----------------------------------------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(int activityId){
		ModelAndView result;
		Activity activity;
		Collection<Trip> trips;
		Collection<Comment> comments;
		
		activity = activityService.findOne(activityId);
		trips = tripService.findTripByActivity(activityId);
		comments = commentService.findCommentsByCommentableId(activityId);
		
		result = new ModelAndView("activity/display");
		result.addObject("activity", activity);
		result.addObject("trips", trips);
		result.addObject("comments", comments);
		
		return result;
	}

}