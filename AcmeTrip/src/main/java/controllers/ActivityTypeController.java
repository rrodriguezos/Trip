package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityTypeService;
import domain.Activity;
import domain.ActivityType;
import domain.Trip;

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
			
			// List -----------------------------------------------------------
			@RequestMapping(value = "/list")
			public ModelAndView list() {

				ModelAndView result;
				Collection<ActivityType> activityTypes;

				activityTypes = activityTypeService.findAll();

				result = new ModelAndView("activitytype/list");
				result.addObject("activityTypes", activityTypes);
				result.addObject("requestUri", "/activitytype/list.do");

				return result;
			}
			
			// ListByActivity -----------------------------------------------------------
			@RequestMapping(value = "/listByActivity")
			public ModelAndView listBySlot(@RequestParam int activityId) {
				ModelAndView result;
				ActivityType activitytype;
				activitytype = activityTypeService.activityTypeByActivity(activityId);
				
				

				result = new ModelAndView("activitytype/list");
				result.addObject("activitytypes", activitytype);
				result.addObject("requestUri", "activitytype/list.do");

				return result;
			}
			
	


}
