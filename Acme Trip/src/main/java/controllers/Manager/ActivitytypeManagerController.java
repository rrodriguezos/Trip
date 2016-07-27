package controllers.Manager;



import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import services.ActivityTypeService;
import controllers.AbstractController;

import domain.ActivityType;;

@Controller
@RequestMapping("/activitytype/manager")
public class ActivitytypeManagerController extends AbstractController {
	//Services ---------------------
		@Autowired
		private ActivityTypeService activitytypeService;

		
		//Constructor ------------------
		public ActivitytypeManagerController() {
			super();
		}
		
		//List -------------------------
		@RequestMapping(value="/list", method = RequestMethod.GET)
		public ModelAndView list() {
			ModelAndView result;
			Collection<ActivityType> activitytypes = activitytypeService.findAll();
			result = new ModelAndView("activitytype/list");
			result.addObject("activitytypes", activitytypes);
			result.addObject("requestURI", "activitytype/manager/list.do");
			return result;
		}
		
		//Create ------------------------
		@RequestMapping(value="/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			ActivityType activityType = activitytypeService.create();
			result = createEditModelAndView(activityType);
			return result;
		}
		
		//Edition ------------------------
		@RequestMapping(value="/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int activitytypeId) {
			ModelAndView result;
			ActivityType activityType = activitytypeService.findOne(activitytypeId);
			Assert.notNull(activityType);
			result = createEditModelAndView(activityType);
			return result;
		}
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="save")
		public ModelAndView save(@Valid ActivityType activityType, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			if (binding.hasErrors()) {
				result = createEditModelAndView(activityType);
			} else {
				try {
					activitytypeService.save(activityType);
					redirectAttrs.addFlashAttribute("message", "activitytype.commit.ok");
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(activityType, "activitytype.commit.error");
				}
			}
			return result;
		}
		
		@RequestMapping(value="/edit", method=RequestMethod.POST, params="delete")
		public ModelAndView delete(ActivityType activityType, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			try {
				activitytypeService.delete(activityType);
				redirectAttrs.addFlashAttribute("message", "activitytype.commit.ok");
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(activityType, "activitytype.commit.error");
			}
			return result;
		}
		
		//Ancillary methods ------------------------------
		protected ModelAndView createEditModelAndView(ActivityType activitytype) {
			ModelAndView result;
			result = createEditModelAndView(activitytype, null);
			return result;
		}

		protected ModelAndView createEditModelAndView(ActivityType activitytype, String message) {
			ModelAndView result;


			result = new ModelAndView("activitytype/edit");
			result.addObject("activitytype", activitytype);

			result.addObject("message", message);

			return result;

		}


}
