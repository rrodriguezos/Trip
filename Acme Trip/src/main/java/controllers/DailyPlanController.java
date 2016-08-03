package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;

import services.DailyPlanService;
import services.TripService;
import services.UserService;

@Controller
@RequestMapping("/dailyplan")
public class DailyPlanController extends AbstractController {

	// Services ---------------------
	@Autowired
	private DailyPlanService dailyplanService;
	@Autowired
	private UserService userService;
	@Autowired
	private TripService tripService;

	// Constructor ------------------
	public DailyPlanController() {
		super();
	}

	// Listing by trip ---------------
	@RequestMapping(value = "/listByTrip", method = RequestMethod.GET)
	public ModelAndView listByTrip(@RequestParam int tripId) {
		ModelAndView result;
		Collection<DailyPlan> dailyplans = dailyplanService
				.dailyPlansByTrip(tripId);
		result = new ModelAndView("dailyplan/list");
		result.addObject("dailyplans", dailyplans);
		result.addObject("requestURI", "dailyplan/listByTrip.do");
		return result;
	}

	// Listing by navigate from Slot ---------------
	@RequestMapping(value = "/navigateBySlot", method = RequestMethod.GET)
	public ModelAndView navigateBySlot(@RequestParam int slotId) {
		ModelAndView result;
		DailyPlan dailyplan = dailyplanService.dailyPlanBySlot(slotId);
		result = new ModelAndView("dailyplan/listAll");
		result.addObject("dailyplan", dailyplan);
		result.addObject("requestURI", "dailyplan/navigateBySlot.do");
		return result;
	}


	// List --------------------------------------------------------------------------------------
		@RequestMapping(value = "/list")
		public ModelAndView list(@RequestParam int tripId) {

			ModelAndView result;
			Collection<DailyPlan> dailyplans;
			Trip trip;
			User user;
			Boolean mytrip;
			dailyplans = dailyplanService.dailyPlansByTrip(tripId);
			result = new ModelAndView("dailyplan/list");
			result.addObject("dailyplans", dailyplans);
			result.addObject("tripId", tripId);
			mytrip = false;
			try{
				user = userService.findByPrincipal();
				trip = tripService.findOne(tripId);

				mytrip = user.equals(trip.getUser());
				
			}catch(Throwable oops){
					
			}
			result.addObject("mytrip", mytrip);
			
		

			return result;
		}


}
