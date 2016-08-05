package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;
import services.TypeService;

import controllers.AbstractController;
import domain.Activity;
import domain.ActivityType;

@Controller
@RequestMapping("/activity/user")
public class ActivityUserController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public ActivityUserController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private TypeService typeService;
	
	//Create-------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Activity activity;
		Collection<ActivityType> types;
		
		activity = activityService.create();
		types = typeService.findAll();
		
		result = new ModelAndView("activity/create");
		result.addObject("activity", activity);
		result.addObject("types", types);
		
		return result;
	}
	
	//Editing ------------------------------------------------
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int activityId){
		ModelAndView result;
		Activity activity;
		Collection<ActivityType> types;
		
		activity = activityService.findOne(activityId);
		types = typeService.findAll();

		result = new ModelAndView("activity/edit");
		result.addObject("activity", activity);
		result.addObject("types", types);

		return result;
	}

	@RequestMapping(value="/edit",method = RequestMethod.POST, params="save")
	public ModelAndView edit(@Valid Activity activity, BindingResult binding){
		ModelAndView result;
		Collection<ActivityType> types;
		
		if(binding.hasErrors()){
			types = typeService.findAll();
			
			result = new ModelAndView("activity/edit");
			result.addObject("activity", activity);
			result.addObject("types", types);
		}else{
			try{
				activityService.save(activity);
				result = new ModelAndView("redirect:/activity/list.do");
			}catch(Throwable oops){
				types = typeService.findAll();
				
				result = new ModelAndView("activity/edit");
				result.addObject("activity", activity);
				result.addObject("types", types);
				result.addObject("message2","activity.commit.error");
			}
		}
		return result;
	}
	
	// List -------------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Activity> activities;
		
		activities = activityService.findAllAppropriated();
		
		result = new ModelAndView("activity/list");
		result.addObject("requestUri","/activity/list.do");
		result.addObject("activities", activities);
		
		return result;
	}
	
	

}
