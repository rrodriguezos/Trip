package controllers;



import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityTypeService;
import services.TripService;
import services.DailyPlanService;
import services.UserService;
import domain.Activity;
import domain.ActivityType;
import domain.Trip;
import domain.DailyPlan;
import domain.User;

@Controller
@RequestMapping("/anonymous")
public class AnonymousController extends AbstractController {

	// Services ---------------------
	@Autowired
	private TripService tripService;
	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private ActivityTypeService activityTypeService;
	
	@Autowired
	private UserService userService;
	
	// Constructors -----------------------------------------------------------

	public AnonymousController() {
		super();
	}

	// Listing trips catalogue ---------------------------------------------------------------		
	
	@RequestMapping(value = "/tripsCatalogue", method = RequestMethod.GET)
	public ModelAndView listTrips() {
		ModelAndView result;
		Collection <Trip> trips = tripService.findAll();
		
		
		result = new ModelAndView("anonymous/tripsCatalogue");
		result.addObject("trips", trips);
		result.addObject("requestURI", "anonymous/tripsCatalogue.do");

		return result;
	}
	
	// Listing activity Types  ---------------------------------------------------------------		
	
		@RequestMapping(value = "/activitytypes", method = RequestMethod.GET)
		public ModelAndView listActivityTypes() {
			ModelAndView result;
			Collection <ActivityType> activitytypes = activityTypeService.findAll();
			
			result = new ModelAndView("anonymous/activitytypes");
			result.addObject("activitytypes", activitytypes);
			result.addObject("requestURI", "anonymous/activitytypes.do");

			return result;
		}
		
	
	

	
	
	
	// Listing trips of activity types ---------------------------------------------------------------	
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/activityTypeTrips", method = RequestMethod.GET)
	public ModelAndView listactivityTypeTrips() {
		ModelAndView result;
		Collection <ActivityType> actTypes = activityTypeService.findAll();
		Collection<Trip> tripsResult = null;
		
		for (ActivityType itero : actTypes) {
			Collection<Trip> trips = tripService.tripsByActivityType(itero.getId());
			tripsResult.addAll(trips);
			

		}
		
		result = new ModelAndView("anonymous/activityTypeTrips");
		result.addObject("tripsResult", tripsResult);
		result.addObject("requestURI", "anonymous/activityTypeTrips.do");

		return result;
	}


	
	// Listing tips by user---------------------------------------------------------------		
	
	@RequestMapping(value = "/listTrips", method = RequestMethod.GET)
	public ModelAndView listBartersFromUser(@RequestParam int userId) {
		ModelAndView result;
		Collection<Trip> trips = tripService.findTripsByUser(userId);
		
		
		result = new ModelAndView("anonymous/listTrips");
		result.addObject("trips", trips);
		result.addObject("requestURI", "anonymous/listTrips.do");

		return result;
	}
	
	// Search tips ---------------------------------------------------------------		
	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(defaultValue="") String keyword) {
		ModelAndView result;
		
		if("".equals(keyword)) {
			result = new ModelAndView("redirect:list.do");
		} else {
			result = new ModelAndView("anonymous/tripsCatalogue");
			result.addObject("keyword", keyword);
			result.addObject("trips", tripService.searchByKeyword(keyword));
			result.addObject("requestURI", "anonymous/search.do");
		}
		
		return result;
	}
	
	
	
	
	
}