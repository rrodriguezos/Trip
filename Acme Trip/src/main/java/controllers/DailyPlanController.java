package controllers;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.DailyPlan;

import services.DailyPlanService;
import services.TripService;

@Controller
@RequestMapping("/dailyplan")
public class DailyPlanController  extends AbstractController {
	
	//Services ---------------------
		@Autowired
		private DailyPlanService dailyplanService;
		
		//Constructor ------------------
		public DailyPlanController() {
			super();
		}
		
		//Listing by gym ---------------
		@RequestMapping(value="/listByTrip", method = RequestMethod.GET)
		public ModelAndView listByTrip(@RequestParam int tripId) {
			ModelAndView result;
			Collection<DailyPlan> dailyplans = dailyplanService.dailyPlansByTrip(tripId);
			result = new ModelAndView("dailyplan/list");
			result.addObject("dailyplans", dailyplans);
			result.addObject("requestURI", "dailyplan/listByTrip.do");
			return result;
		}


}
