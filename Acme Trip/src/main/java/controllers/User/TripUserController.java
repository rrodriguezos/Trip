package controllers.User;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CommentService;
import services.TripService;
import services.UserService;
import controllers.AbstractController;
import domain.Comment;
import domain.Trip;
import domain.User;

@Controller
@RequestMapping("/trip/user/")
public class TripUserController extends AbstractController {

	// Services -----------------------------
	@Autowired
	private TripService tripService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CommentService commentService;

	// Constructor --------------------------
	public TripUserController() {
		super();
	}

	// List--------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		boolean showDisjoin = true;

		Collection<Trip> trips = tripService.findAll();
		User user = userService.findByPrincipal();

		trips = tripService.findTripsByUser(user.getId());

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("user", user);
		result.addObject("showDisjoin", showDisjoin);
		result.addObject("actor", "/user");
		result.addObject("userId", user.getId());
		result.addObject("userTrips", user.getTrips());
		result.addObject("requestURI", "trip/user/list.do");

		return result;

	}
	
	@RequestMapping(value = "/mytrips")
	public ModelAndView myTrips() {

		ModelAndView result;

		Collection<Trip> trips = tripService.findByPrincipal();

		result = new ModelAndView("trip/user/mytrips");
		result.addObject("trips", trips);
		result.addObject("requestUri", "/trip/user/mytrips.do");

		return result;
	}

	// Creation
	// ---------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Trip trip;
		trip = tripService.create();
		result = createEditModelAndView(trip);
		result.addObject("user", userService.findByPrincipal());
		return result;
	}

	// Edition
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tripId){
		ModelAndView result;
		Trip trip;
		trip = tripService.findOne(tripId);
		
		result = createEditModelAndView(trip);
		result.addObject("trip", trip);
		return result;
				}

	// Save
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Trip trip, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		int checkOverlap = 0;
		User user;
		user = userService.findByPrincipal();
		Assert.isTrue(user == trip.getUser());
		
		boolean checkStartEndDates = false;
		if (trip.getStartDate() != null && trip.getEndDate() != null) {
			if (trip.getId() == 0) {
				checkOverlap = tripService.findOverlapByUser(trip);
			}
			checkStartEndDates = trip.getStartDate().after(trip.getEndDate());
		}

		if (binding.hasErrors() || checkOverlap != 0 || checkStartEndDates) {
			result = createEditModelAndView(trip);
			if (checkOverlap != 0 || checkStartEndDates) {
				result.addObject("message2", "trip.overlap.error");
			}
		} else {
			try {
				tripService.save(trip);
				result = new ModelAndView("redirect:/trip/list.do");
				result.addObject("requestUri", "/trip/list.do");
				redirectAttrs.addFlashAttribute("message", "trip.commit.ok");
				result.addObject("userId", userService.findByPrincipal().getId());
				result.addObject("user", userService.findByPrincipal());

			} catch (Throwable oops) {
				result = createEditModelAndView(trip, "trip.commit.error");
			}
		}

		return result;
	}

//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
//	public ModelAndView saveEdit(@Valid Trip trip, BindingResult binding,
//			RedirectAttributes redirectAttrs) {
//		ModelAndView result;
//
//		if (binding.hasErrors()) {
//			System.out.print(binding.hasErrors());
//			System.out.print(binding.toString());
//			result = createEditModelAndView(trip, "trip.commit.error");
//		} else {
//			try {
//				tripService.save(trip);
//				redirectAttrs.addFlashAttribute("message", "trip.commit.ok");
//				result = new ModelAndView("redirect:/trip/user/list.do");
//				result.addObject("userId", userService.findByPrincipal()
//						.getId());
//				result.addObject("user", userService.findByPrincipal());
//			} catch (Throwable oops) {
//				result = createEditModelAndView(trip, "trip.commit.error");
//
//			}
//		}
//
//		return result;
//	}

	// Delete
	// -----------------------------------------------------------------------
	
	@RequestMapping(value="/edit",method = RequestMethod.POST, params="delete")
	public ModelAndView delete(@Valid Trip trip, BindingResult binding){
		ModelAndView result;
		User user;
		user = userService.findByPrincipal();
		Assert.isTrue(user == trip.getUser());
		
		if(binding.hasErrors()){
			System.out.print(binding.getFieldError());
			System.out.print(binding.getAllErrors());
			result = createEditModelAndView(trip,binding.toString());
		}else{
			try{
				System.out.print(binding.getFieldError());
				System.out.print(binding.getAllErrors());
				tripService.delete(trip);
				result = new ModelAndView("redirect:/trip/list.do");
				result.addObject("requestUri", "/trip/list.do");
				result.addObject("userId", userService.findByPrincipal().getId());
				result.addObject("user", userService.findByPrincipal());
			}catch(Throwable oops){
				result = createEditModelAndView(trip,"trip.commit.error");
			}
		}
		return result;	
	}
	
	@RequestMapping(value = "/copyPaste", method = RequestMethod.GET)
	public ModelAndView copy(@RequestParam int tripId) {
		ModelAndView result;
		
		Trip trip = tripService.findOne(tripId);

		tripService.copyPasteTrip(trip);
		
		result = new ModelAndView("redirect:/trip/user/list.do");
		result.addObject("requestUri", "/trip/user/mytrips.do");

		return result;
	}

	// Join a
	// Trip------------------------------------------------------------------
	@RequestMapping(value = "/joinTrip", method = RequestMethod.GET)
	public ModelAndView joinTrip(@RequestParam int tripId) {

		ModelAndView result;
		Trip trip;
		Collection<Trip> trips;

		trip = tripService.findOne(tripId);
		tripService.joinTrip(trip);
		trips = tripService.findAllTripsJoinUser();

		result = new ModelAndView("trip/list");

		result.addObject("trips", trips);
		result.addObject("requestURI", "trip/user/list.do");

		return result;

	}

	// DisJoin a
	// Event------------------------------------------------------------------
	@RequestMapping(value = "/disjoinTrip", method = RequestMethod.GET)
	public ModelAndView DisjoinEvent(@RequestParam int tripId) {

		ModelAndView result;
		Trip trip;
		Collection<Trip> trips;
		User principal;
		Boolean showdisjoin = true;
		boolean my = true;

		trip = tripService.findOne(tripId);
		tripService.DisjoinTrip(trip);
		trips = tripService.findAllTripsJoinUser();
		principal = userService.findByPrincipal();

		result = new ModelAndView("trip/list");

		result.addObject("trips", trips);
		result.addObject("my", my);
		result.addObject("principal", principal);
		result.addObject("showdisjoin", showdisjoin);
		result.addObject("requestURI", "trip/user/list.do");

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

	// Ancillary methods -------------------------------------------------

	protected ModelAndView createEditModelAndView(Trip trip) {
		ModelAndView result;

		result = createEditModelAndView(trip, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Trip trip, String message) {
		ModelAndView result;
		
		result = new ModelAndView("trip/user/edit");
		result.addObject("trip", trip);
		result.addObject("message2", message);
		
		return result;
	}

}
