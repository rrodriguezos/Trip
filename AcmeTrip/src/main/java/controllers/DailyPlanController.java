package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.DailyPlan;
import domain.Trip;
import domain.User;


import services.DailyPlanService;
import services.TripService;
import services.UserService;


@Controller
@RequestMapping("/dailyPlan")
public class DailyPlanController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private TripService tripService;
	@Autowired
	private UserService userService;
	// Constructors -----------------------------------------------------------
	public DailyPlanController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam int tripId) {

		ModelAndView result;
		Collection<DailyPlan> dailyPlans;
		Trip trip;
		User user;
		Boolean mytrip;
		dailyPlans = dailyPlanService.dailyPlansByTrip(tripId);
		result = new ModelAndView("dailyPlan/list");
		result.addObject("dailyPlans", dailyPlans);
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
