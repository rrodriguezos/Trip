package controllers.Manager;

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
import controllers.AbstractController;
import domain.Activity;


@Controller
@RequestMapping("/activity/manager")
public class ActivityManagerController extends AbstractController  {
	
	//Services -------------------
		@Autowired
		private ActivityService activityService;
		
		//Constructor ----------------
		public ActivityManagerController() {
			super();
		}
		
		//Listing barters ------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			
			Collection<Activity> activities = activityService.findAll();
			
			result = new ModelAndView("activity/list");
			result.addObject("activities", activities);
			result.addObject("requestURI", "activity/manager/list.do");
			
			return result;
		}
		
		//Mark innappropiate activity ----------------
		@RequestMapping(value="/mark", method=RequestMethod.GET)
		public ModelAndView mark(@RequestParam int activityId) {
			ModelAndView result;
			Activity activity = activityService.findOne(activityId);			
				try {
					activityService.flagActivityAsInappropriate(activityId);
					result = list();
					result.addObject("message", "activity.commit.ok");
				} catch(Throwable oops) {
					result = list();
					result.addObject("message", "activity.commit.error");
				}
				return result;
			}	
		
		
		
		
		@RequestMapping(value = "/save", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid  Activity activity, BindingResult binding) {
			ModelAndView result;
			
			if (binding.hasErrors()) {
				result = createEditModelAndView(activity, "activity.commit.error");
			}else {
				try {
					activityService.save(activity);
					result = new ModelAndView("redirect:list.do");
				} catch(Throwable oops) {
					result = createEditModelAndView(activity, "activity.commit.error");
				}
			}
			
			return result;
		}
		
		//Ancillary methods -----------
		protected ModelAndView createEditModelAndView(Activity activity, String message) {
			ModelAndView result;
			
			Collection<Activity> activities = activityService.findAll();
			
			result = new ModelAndView("activity/create");
			result.addObject("activity", activity);
			result.addObject("message", message);
			result.addObject("activities", activities);
			result.addObject("actionURI", "activity/manager/save.do");
			result.addObject("modelAttribute", "activity");
			
			return result;
		}

}
