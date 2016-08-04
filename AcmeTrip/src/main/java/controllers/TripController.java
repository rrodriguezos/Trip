package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;
import domain.Trip;
import domain.User;

import services.CommentService;
import services.TripService;
import services.UserService;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private TripService tripService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	// Constructors -----------------------------------------------------------
	public TripController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result;
		Collection<Trip> trips;

		trips = tripService.findAll();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestUri", "/trip/list.do");

		return result;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "searchTrip")
	public ModelAndView list(@Valid String search) {
		ModelAndView result;
		Collection<Trip> trips;

		trips = tripService.findTripByString(search);

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);

		return result;
	}

//	@RequestMapping(value = "/display", method = RequestMethod.GET)
//	public ModelAndView display(@RequestParam int tripId) {
//		ModelAndView result;
//		Trip trip;
//		Collection<Comment> comments;
//		User user;
//		Boolean mytrip;
//		Boolean subscribed;
//		Boolean princi;
//
//		trip = tripService.findOne(tripId);
//		mytrip = false;
//		subscribed = false;
//		princi = false;
//		
//		try{
//			user = userService.findByPrincipal();
//			if(user!=null){
//				princi = true;
//			}
//			if(user.equals(trip.getUser())){
//				mytrip = true;
//			}
//			if(tripService.tripsSubscriptionsByUser(user.getId())
//					.contains(trip)){
//				subscribed = true;
//			}
//		}catch(Throwable oops){
//			mytrip = false;
//			subscribed = false;
//			princi = false;
//		}
//
//		comments = commentService.findCommentsByCommentableId(tripId);
//
//		trip = tripService.findOne(tripId);
//		comments = commentService.findCommentsByCommentableId(tripId);
//
//		result = new ModelAndView("trip/display");
//		result.addObject("trip", trip);
//
//		result.addObject("mytrip", mytrip);
//		result.addObject("subscribed", subscribed);
//		result.addObject("princi", princi);
//		
//
//		result.addObject("comments", comments);
//
//		return result;
//
//	}
}
