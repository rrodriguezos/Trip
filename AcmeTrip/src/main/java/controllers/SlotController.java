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
import domain.User;

import services.DailyPlanService;
import services.SlotService;
import services.UserService;

@Controller
@RequestMapping("/slot")
public class SlotController extends AbstractController {
	
	// Supporting services ----------------------------------------------------
	@Autowired
	private SlotService slotService;
	
	@Autowired
	private DailyPlanService dailyPlanService;
	
	@Autowired
	private UserService userService;
		
	// Constructors -----------------------------------------------------------
	public SlotController() {
		super();
	}
	
	// List -----------------------------------------------------------	
		@RequestMapping(value = "/list")
		public ModelAndView list(@RequestParam int dailyPlanId) {
			
			ModelAndView result;
			Collection<Slot> slots;
			DailyPlan daily;
			User user;
			Boolean mytrip;

			slots = slotService.slotsByDailyPlan(dailyPlanId);

			result = new ModelAndView("slot/list");
			result.addObject("slots", slots);
			result.addObject("dailyPlanId",dailyPlanId);
			mytrip = false;
			try{
				user = userService.findByPrincipal();
				daily = dailyPlanService.findOne(dailyPlanId);

				mytrip = user.equals(daily.getTrip().getUser());
				
			}catch(Throwable oops){
					
			}
			
			result.addObject("mytrip", mytrip);

			return result;
		}	
		
	// Display --------------------------------------------------------
		@RequestMapping(value="/display", method=RequestMethod.GET)
		public ModelAndView display(int slotId){
			ModelAndView result;
			Slot slot;
			
			slot = slotService.findOne(slotId);
			
			result = new ModelAndView("slot/display");
			result.addObject("slot", slot);
			
			return result;
		}
		
		// Listing by navigate from activity ---------------
		@RequestMapping(value = "/navigateByActivity", method = RequestMethod.GET)
		public ModelAndView navigateByActivity(@RequestParam int activityId) {
			ModelAndView result;
			Collection<Slot> slots = slotService.slotByActivity(activityId);
			result = new ModelAndView("slot/listAll");
			result.addObject("slots", slots);
			result.addObject("requestURI", "slots/navigateByActivity.do");
			return result;
		}
		
		
	

}
