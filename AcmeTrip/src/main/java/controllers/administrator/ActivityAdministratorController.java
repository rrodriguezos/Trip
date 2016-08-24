package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;

import controllers.AbstractController;

@Controller
@RequestMapping("/activity/administrator")
public class ActivityAdministratorController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public ActivityAdministratorController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private ActivityService activityService;
	
	//Appropriated --------------------------------------------------------
	@RequestMapping(value="/appropriated")
	public ModelAndView appropriated(@RequestParam int activityId){
		ModelAndView result;
		
		activityService.changeStateActivity(activityId);
		
		result = new ModelAndView("redirect:/activity/display.do?activityId="+activityId);
		
		return result;
	}
}
