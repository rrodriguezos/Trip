package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.ActivityTypeService;
import domain.Activity;
import domain.ActivityType;

@Controller
@RequestMapping("/activitytype")
public class ActivityTypeController extends AbstractController   {
	
	//Services ---------------------
	@Autowired
	private ActivityTypeService activitytypeService;
	
	//Constructor ------------------
	public ActivityTypeController() {
		super();
	}
	
	//Listing by Activity ---------------
	@RequestMapping(value="/listByActivity", method = RequestMethod.GET)
	public ModelAndView listByActivity(@RequestParam int activityId) {
		ModelAndView result;
		ActivityType activitytype = activitytypeService.activityTypeByActivity(activityId);
		result = new ModelAndView("activitytype/list");
		result.addObject("activitytype", activitytype);
		result.addObject("requestURI", "activitytype/listByActivity.do");
		return result;
	}


}
