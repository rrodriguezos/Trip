package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;

import domain.Activity;




@Controller
@RequestMapping("/activity")
public class ActivityController  extends AbstractController  {
	
	//Services ---------------------
			@Autowired
			private ActivityService activityService;
			
			//Constructor ------------------
			public ActivityController() {
				super();
			}
			
			//Listing by gym ---------------
			@RequestMapping(value="/listBySlot", method = RequestMethod.GET)
			public ModelAndView listBySlot(@RequestParam int slotId) {
				ModelAndView result;
				Activity activity = activityService.activityBySlot(slotId);
				result = new ModelAndView("activity/list");
				result.addObject("activity", activity);
				result.addObject("requestURI", "activity/listBySlot.do");
				return result;
			}

}
