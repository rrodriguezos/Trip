package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.ActivityService;

@Controller
@RequestMapping("/activity/manager")
public class ActivityManagerController extends AbstractController {

	// Constructor --------------------------------------------------------
	public ActivityManagerController() {
		super();
	}

	// Services -----------------------------------------------------------
	@Autowired
	private ActivityService activityService;

	// Flag Activity --------------------------------------------------------
	@RequestMapping(value = "/appropriated")
	public ModelAndView appropiated(@RequestParam int activityId) {
		ModelAndView result;

		activityService.changeFlagActivity(activityId);

		result = new ModelAndView("redirect:/activity/display.do?activityId="
				+ activityId);

		return result;
	}
}
