package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.TripService;

import domain.DailyPlan;
import domain.Trip;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController  {
	
	
	//Services ---------------------
	@Autowired
	private TripService tripService;
	
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

}
