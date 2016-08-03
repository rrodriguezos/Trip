package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.CommentService;
import services.TripService;

import domain.Activity;
import domain.Comment;
import domain.Trip;




@Controller
@RequestMapping("/activity")
public class ActivityController  extends AbstractController  {
	
	//Services ---------------------
			@Autowired
			private ActivityService activityService;
			
			@Autowired
			private TripService tripService;
			
			@Autowired
			private CommentService commentService;
			
			//Constructor ------------------
			public ActivityController() {
				super();
			}
			
			// List -----------------------------------------------------------	
			@RequestMapping(value = "/list", method = RequestMethod.GET)
			public ModelAndView listAppropriated() {
				ModelAndView result;
				Collection<Activity> activities;

				activities = activityService.findAreAppropriate();

				result = new ModelAndView("activity/list");
				result.addObject("activities", activities);
				result.addObject("requestURI", "activity/list.do");

				return result;
			}
			
			//Listing by slot ---------------
			@RequestMapping(value="/listBySlot", method = RequestMethod.GET)
			public ModelAndView listBySlot(@RequestParam int slotId) {
				ModelAndView result;
				Activity activity = activityService.activityBySlot(slotId);
				result = new ModelAndView("activity/list");
				result.addObject("activity", activity);
				result.addObject("requestURI", "activity/listBySlot.do");
				return result;
			}
			
			//Listing by navigate from activity type ---------------
			@RequestMapping(value="/navigateByActivitytype", method = RequestMethod.GET)
			public ModelAndView navigateByActivitytype(@RequestParam int activitytypeId) {
				ModelAndView result;
				Collection<Activity> activities = activityService.activitiesByActivityType(activitytypeId);
				result = new ModelAndView("activity/listAll");
				result.addObject("activities", activities);
				result.addObject("requestURI", "activities/navigateByActivitytype.do");
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
				trips = tripService.findTripsByActivity(activityId);
				comments = commentService.findCommentsByCommentableId(activityId);
				
				result = new ModelAndView("activity/display");
				result.addObject("activity", activity);
				result.addObject("trips", trips);
				result.addObject("comments", comments);
				
				return result;
			}

}
