package controllers;

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

import services.DailyPlanService;
import services.TripService;
import controllers.AbstractController;
import domain.DailyPlan;
import domain.Trip;

@Controller
@RequestMapping("/trip/User")
public class TripUserController extends AbstractController {
	
	//Services -----------------------------
		@Autowired
		private TripService tripService;	
		@Autowired
		private DailyPlanService dailyPlanService;
		
		//Constructor --------------------------
		public TripUserController() {
			super();
		}
		
		//Creation -----------------------------
		@RequestMapping(value="/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Trip trip;
			trip = tripService.create();
			result = createEditModelAndView(trip, null);
			return result;
		}
		
		//Edition -------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam Integer tripId) {
			Trip trip = tripService.findOne(tripId);
			Assert.notNull(trip);
			return createEditModelAndView(trip, null);
		}

		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Trip trip, BindingResult binding,
				 RedirectAttributes redirectAttrs) {
			ModelAndView result;	

			if (binding.hasErrors()) {
				result = createEditModelAndView(trip, "trip.commit.error");
			} else {
				try {
					tripService.save(trip);
					redirectAttrs.addFlashAttribute("message", "trip.commit.ok");	
					result = new ModelAndView("redirect:/trip/list.do");
				} catch (Throwable oops) {				
					result = createEditModelAndView(trip, "trip.commit.error");				
				}
			}

			return result;
		}
				
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Trip trip, BindingResult binding,
				 RedirectAttributes redirectAttrs) {
			ModelAndView result;

			try {			
				tripService.delete(trip);		
				redirectAttrs.addFlashAttribute("message", "trip.commit.ok");	
				result = new ModelAndView("redirect:/trip/list.do");		
			} catch (Throwable oops) {
				result = createEditModelAndView(trip, "trip.delete.error");
			}
			return result;
		}
		
		
		//Ancillary methods ---------------------
		
		protected ModelAndView createEditModelAndView(Trip trip, String message) {
			ModelAndView result;
			Collection<DailyPlan> dailyPlans = dailyPlanService.findAll();
					
			result = new ModelAndView("trip/edit");
			result.addObject("trip", trip);
			result.addObject("message", message);
			result.addObject("dailyPlans", dailyPlans);
			
			return result;
		}

}
