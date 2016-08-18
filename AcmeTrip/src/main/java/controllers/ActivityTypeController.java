package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityTypeService;
import domain.ActivityType;

@Controller
@RequestMapping("/activitytype")
public class ActivityTypeController extends AbstractController   {
	
	//Services ---------------------
	@Autowired
	private ActivityTypeService activityTypeService;
	
	//Constructor ------------------
	public ActivityTypeController() {
		super();
	}
	// Listing activity Types  ---------------------------------------------------------------		
	
			@RequestMapping(value = "/activitytypes", method = RequestMethod.GET)
			public ModelAndView listActivityTypes() {
				ModelAndView result;
				Collection <ActivityType> activitytypes = activityTypeService.findAll();
				
				result = new ModelAndView("activitytype/activitytypes");
				result.addObject("activitytypes", activitytypes);
				result.addObject("requestURI", "activitytype/activitytypes.do");

				return result;
			}
			
	
//	//Listing by Activity ---------------
//	@RequestMapping(value="/listByActivity", method = RequestMethod.GET)
//	public ModelAndView listByActivity(@RequestParam int activityId) {
//		ModelAndView result;
//		ActivityType activitytype = activitytypeService.activityTypeByActivity(activityId);
//		result = new ModelAndView("activitytype/list");
//		result.addObject("activitytype", activitytype);
//		result.addObject("requestURI", "activitytype/listByActivity.do");
//		return result;
//	}


}
