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
		
		//Listing by trip ---------------
		@RequestMapping(value="/listByTrip", method = RequestMethod.GET)
		public ModelAndView listByTrip(@RequestParam int tripId) {
			ModelAndView result;
			Collection<DailyPlan> dailyplans = dailyplanService.dailyPlansByTrip(tripId);
			result = new ModelAndView("dailyplan/list");
			result.addObject("dailyplans", dailyplans);
			result.addObject("requestURI", "dailyplan/listByTrip.do");
			return result;
		}
		
		//Listing by navigate from Slot ---------------
		@RequestMapping(value="/navigateBySlot", method = RequestMethod.GET)
		public ModelAndView navigateBySlot(@RequestParam int slotId) {
			ModelAndView result;
			DailyPlan dailyplan = dailyplanService.dailyPlanBySlot(slotId);
			result = new ModelAndView("dailyplan/listAll");
			result.addObject("dailyplan", dailyplan);
			result.addObject("requestURI", "dailyplan/navigateBySlot.do");
			return result;
		}


}
