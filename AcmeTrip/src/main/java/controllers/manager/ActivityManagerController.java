package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityService;

import controllers.AbstractController;

@Controller
@RequestMapping("/activity/manager")
public class ActivityManagerController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public ActivityManagerController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private ActivityService activityService;
	
	//Appropriated --------------------------------------------------------
	@RequestMapping(value="/appropriated")
	public ModelAndView appropiated(@RequestParam int activityId){
		ModelAndView result;
		
		activityService.changeAppropriated(activityId);
		
		result = new ModelAndView("redirect:/activity/display.do?activityId="+activityId);
		
		return result;
	}
}
