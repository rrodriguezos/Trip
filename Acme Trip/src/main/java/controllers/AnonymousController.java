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
import domain.Activity;
import domain.ActivityType;
import domain.Trip;
import domain.DailyPlan;

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
	
	// Trip details ---------------------------------------------------------------		
//	
//	@RequestMapping(value = "/tripDetails", method = RequestMethod.GET) 
//	public ModelAndView listTrip(@RequestParam(defaultValue="0") Integer tripId) {
//		ModelAndView result;
//		Trip trip = tripService.findOne(tripId);
//		Assert.notNull(trip);
//		Collection<DailyPlan> dailyplans = trip.getDailyplans();
//		System.out.println(dailyplans);
//		
//		
//		result = new ModelAndView("dailyplan/list");
//		result.addObject("trip", trip);
//		result.addObject("dailyplans", dailyplans);
//		result.addObject("requestURI", "trip/tripDetails.do");
//		
//		return result;
//	}
	

	
	
	
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
		
	
	
	// Search trips ---------------------------------------------------------------		
	
//	@RequestMapping(value = "/searchTrips", method = RequestMethod.GET)
//	public ModelAndView searchBulletins(@RequestParam(defaultValue="") String q) {
//		ModelAndView result;
//		
//		if("".equals(q)) {
//			result = new ModelAndView("redirect:bartersCatalogue.do");
//		} else {
//			result = new ModelAndView("public/bartersCatalogue");
//			result.addObject("q", q);
//			result.addObject("barters", tripService.searchByKeyword(q));
//			result.addObject("requestURI", "public/searchBarters.do");
//		}
//		
//		return result;
//	}
//	
}