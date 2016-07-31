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
import services.UserService;

import domain.Comment;
import domain.DailyPlan;
import domain.Trip;
import domain.User;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController  {
	
	
	//Services ---------------------
	@Autowired
	private TripService tripService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;
	
	//Constructor ------------------
	public TripController() {
		super();
	}
	
	//Listing by navigate from DailyPlan ---------------
	@RequestMapping(value="/navigateByDailyplan", method = RequestMethod.GET)
	public ModelAndView navigateByDailyPlan(@RequestParam int dailyplanId) {
		ModelAndView result;
		Trip trip = tripService.tripByDailyplan(dailyplanId);
		
		result = new ModelAndView("trip/listAll");
		result.addObject("trip", trip);
		result.addObject("requestURI", "trip/navigateByDailyplan.do");
		return result;
	}
	
	//List by User ---------------
	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ModelAndView listByUser(@RequestParam int userId) {
		ModelAndView result;
		Collection<Trip> trips = tripService.findTripsByUser(userId);
		
		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/list.do");
		return result;
	}
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int tripId) {
		ModelAndView result;
		Trip trip;
		Collection<Comment> comments;
		User user;


		trip = tripService.findOne(tripId);
		Boolean isMyTrip = false;
		Boolean joinedTrip = false;
		Boolean principal = false;
		
		try{
			user = userService.findByPrincipal();
			if(user!=null){
				principal = true;
			}
			if(user.equals(trip.getUser())){
				isMyTrip = true;
			}
			if(tripService.findAllTripsSuscrito(user.getId())
					.contains(trip)){
				joinedTrip = true;
			}
		}catch(Throwable oops){
			isMyTrip = false;
			joinedTrip = false;
			principal = false;
		}

		comments = commentService.findCommentsByCommentableId(tripId);

		trip = tripService.findOne(tripId);
		comments = commentService.findCommentsByCommentableId(tripId);

		result = new ModelAndView("trip/display");
		result.addObject("trip", trip);

		result.addObject("isMyTrip", isMyTrip);
		result.addObject("joinedTrip", joinedTrip);
		result.addObject("principal", principal);
		

		result.addObject("comments", comments);

		return result;

	}

}
