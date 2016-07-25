package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import domain.Slot;

import services.SlotService;


@Controller
@RequestMapping("/slot")
public class SlotController extends AbstractController  {
	
	//Services ---------------------
			@Autowired
			private SlotService slotService;
			
			//Constructor ------------------
			public SlotController() {
				super();
			}
			
			//Listing by gym ---------------
			@RequestMapping(value="/listByDailyplan", method = RequestMethod.GET)
			public ModelAndView listByDailyplan(@RequestParam int dailyplanId) {
				ModelAndView result;
				Collection<Slot> slots = slotService.slotByDailyPlan(dailyplanId);
				result = new ModelAndView("slot/list");
				result.addObject("slots", slots);
				result.addObject("requestURI", "slot/listByDailyplan.do");
				return result;
			}


}
