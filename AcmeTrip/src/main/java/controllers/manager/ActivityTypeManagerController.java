package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.ActivityType;
import services.ActivityTypeService;

@Controller
@RequestMapping("/activitytype/manager")
public class ActivityTypeManagerController extends AbstractController {

	// Constructor --------------------------------------------------------
	public ActivityTypeManagerController() {
		super();
	}

	// Services -----------------------------------------------------------
	@Autowired
	private ActivityTypeService activitytypeService;

	// List -------------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<ActivityType> activitytypes;

		activitytypes = activitytypeService.findAll();

		result = new ModelAndView("activitytype/list");
		result.addObject("requestUri", "/activitytype/manager/list.do");
		result.addObject("activitytypes", activitytypes);

		return result;
	}

	// Create-----------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ActivityType activitytype;

		activitytype = activitytypeService.create();
		result = new ModelAndView("activitytype/create");
		result.addObject("activitytype", activitytype);
		result.addObject("requestUri", "activitytype/manager/edit.do");

		return result;
	}

	// Edit ----------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int activitytypeId) {
		ModelAndView result;
		ActivityType activitytype;

		activitytype = activitytypeService.findOne(activitytypeId);

		result = new ModelAndView("activitytype/edit");
		result.addObject("activitytype", activitytype);
		result.addObject("requestUri", "activitytype/manager/edit.do");

		return result;
	}

	// Save----------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ActivityType activitytype,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			if (activitytype.getId() != 0)
				result = new ModelAndView("activitytype/edit");
			else
				result = new ModelAndView("activitytype/create");
			if (activitytype.getName() == "")
				result.addObject("message2", "activitytype.notblank");
			result.addObject("activitytype", activitytype);
			result.addObject("requestUri", "activitytype/manager/edit.do");
		} else {
			try {
				activitytypeService.save(activitytype);
				result = new ModelAndView(
						"redirect:/activitytype/manager/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("activitytype/edit");
				result.addObject("activitytype", activitytype);
				result.addObject("requestUri", "activitytype/manager/edit.do");
				result.addObject("message2", "activitytype.commit.error");
			}
		}
		return result;
	}

}
