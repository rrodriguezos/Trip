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
import services.ActivityTypeService;

import controllers.AbstractController;
import domain.Activity;
import domain.ActivityType;

@Controller
@RequestMapping("/activity/user")
public class ActivityUserController extends AbstractController {

	// Constructor --------------------------------------------------------
	public ActivityUserController() {
		super();
	}

	// Services -----------------------------------------------------------
	@Autowired
	private ActivityService activityService;

	@Autowired
	private ActivityTypeService activityTypeService;

	// List -------------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Activity> activities;

		activities = activityService.findAllAppropriated();

		result = new ModelAndView("activity/list");
		result.addObject("requestUri", "/activity/list.do");
		result.addObject("activities", activities);

		return result;
	}

	// Create---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Activity activity;
		Collection<ActivityType> activitytypes;

		activity = activityService.create();
		activitytypes = activityTypeService.findAll();

		result = new ModelAndView("activity/create");
		result.addObject("activity", activity);
		result.addObject("activitytypes", activitytypes);

		return result;
	}

	// Edit -------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int activityId) {
		ModelAndView result;
		Activity activity;
		Collection<ActivityType> activitytypes;

		activity = activityService.findOne(activityId);
		activitytypes = activityTypeService.findAll();

		result = new ModelAndView("activity/edit");
		result.addObject("activity", activity);
		result.addObject("activitytypes", activitytypes);

		return result;
	}

	// Save -------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid Activity activity, BindingResult binding) {
		ModelAndView result;
		Collection<ActivityType> activitytypes;
		activitytypes = activityTypeService.findAll();
		if (binding.hasErrors()) {
			activitytypes = activityTypeService.findAll();

			result = new ModelAndView("activity/edit");
			result.addObject("activity", activity);
			result.addObject("activitytypes", activitytypes);
		} else {
			try {
				activityService.save(activity);
				result = new ModelAndView("redirect:/activity/list.do");
			} catch (Throwable oops) {
				activitytypes = activityTypeService.findAll();

				result = new ModelAndView("activity/edit");
				result.addObject("activity", activity);
				result.addObject("activitytypes", activitytypes);
				result.addObject("message2", "activity.commit.error");
			}
		}
		return result;
	}

}
