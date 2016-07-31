package controllers.User;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.DailyPlanService;
import services.TripService;
import controllers.AbstractController;
import domain.DailyPlan;
import domain.Trip;

@Controller
@RequestMapping("/dailyplan/user")
public class DailyPlanUserController extends AbstractController {

	// Services ----------------------
	@Autowired
	private DailyPlanService dailyplanService;
	@Autowired
	private TripService tripService;

	// Constructor -------------------
	public DailyPlanUserController() {
		super();
	}

	// Create ------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int tripId) {
		ModelAndView result;
		DailyPlan dailyplan = dailyplanService.create();
		dailyplan.setTrip(tripService.findOne(tripId));
		
		result = createEditModelAndView(dailyplan);

		return result;
	}

	// Save ----------------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid DailyPlan dailyPlan, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Boolean checkOverlap;
		
		checkOverlap = dailyplanService.checkOverlap(dailyPlan);
		
		if (binding.hasErrors() || !checkOverlap) {
			result = createEditModelAndView(dailyPlan);
			if(!checkOverlap){
				result.addObject("message2","dailyPlan.overlapping.error");
			}
		} else {
			try {
				dailyplanService.save(dailyPlan);
				result = new ModelAndView("redirect:/dailyPlan/list.do?tripId="+dailyPlan.getTrip().getId());
				redirectAttrs.addFlashAttribute("message2", "dailyPlan.commit.ok");

			} catch (Throwable oops) {
				result = createEditModelAndView(dailyPlan);

				result.addObject("message2", "dailyPlan.commit.error");
			}
		}

		return result;
	}
	
	// Delete -------------------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int dailyPlanId) {
		ModelAndView result;
		
		DailyPlan dailyPlan = dailyplanService.findOne(dailyPlanId);

		dailyplanService.delete(dailyPlan);
		
		result = new ModelAndView("redirect:/trip/user/mytrips.do");
		result.addObject("requestUri", "/trip/user/mytrips.do");

		return result;
	}

	// Ancillary methods ------------------------------
	protected ModelAndView createEditModelAndView(DailyPlan dailyplan) {
		ModelAndView result;
		result = createEditModelAndView(dailyplan, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(DailyPlan dailyplan,
			String message) {
		ModelAndView result;
		Trip trip = tripService.findOne(dailyplan.getTrip().getId());

		result = new ModelAndView("dailyPlan/create");
		result.addObject("dailyplan", dailyplan);
		result.addObject("trip", trip);
		result.addObject("message", message);
		result.addObject("message2", message);

		return result;

	}

	// // Services ---------------------
	// @Autowired
	// private DailyPlanService dailyplanService;
	//
	// @Autowired
	// private UserService userService;
	//
	// @Autowired
	// private TripService tripService;
	//
	// // Constructor ------------------
	// public DailyPlanUserController() {
	// super();
	// }
	//
	// // Creation
	// // ---------------------------------------------------------------------
	// @RequestMapping(value = "/create", method = RequestMethod.GET)
	// public ModelAndView create(@RequestParam int tripId) {
	// ModelAndView result;
	// DailyPlan dailyplan;
	// Assert.notNull(tripId);
	// dailyplan = dailyplanService.create(tripId);
	// result = new ModelAndView("dailyplan/user/edit");
	// result.addObject("daipla", dailyplan);
	// return result;
	// }
	//
	// // Save
	// //
	// -----------------------------------------------------------------------
	// @RequestMapping(value = "/edit", method = RequestMethod.POST, params =
	// "save")
	// public ModelAndView saveAss(@Valid DailyPlan daipla, BindingResult
	// binding) {
	// ModelAndView result;
	// if (binding.hasErrors()) {
	// System.out.print(binding.hasErrors());
	// System.out.print(binding.toString());
	// result = new ModelAndView("/dailyplan/user/edit");
	// result.addObject("daipla", daipla);
	// result.addObject("message", "daily.commit.error");
	// } else {
	// try {
	// DailyPlan fijo = dailyplanService.save(daipla);
	// result = new
	// ModelAndView("redirect:/slot/user/create.do?dailyplanId="+fijo.getId());
	// } catch (Throwable oops) {
	// result = new ModelAndView("dailyplan/user/edit");
	// result.addObject("daipla", daipla);
	// result.addObject("message", "trip.commit.error");
	//
	// }
	// }
	//
	// return result;
	// }
	//
	// @RequestMapping(value = "/edit", method = RequestMethod.POST, params =
	// "cancel")
	// public ModelAndView cancel(DailyPlan daily, BindingResult binding,
	// RedirectAttributes redirectAttrs) {
	// ModelAndView result;
	//
	// try {
	// tripService.delete(daily.getTrip());
	// result = new ModelAndView("redirect:/trip/user/list.do");
	// result.addObject("user", userService.findByPrincipal());
	// result.addObject("userId", userService.findByPrincipal().getId());
	// } catch (Throwable oops) {
	// result = new ModelAndView("dailyplan/user/edit");
	// result.addObject("daipla", daily);
	// result.addObject("message", "daily.delete.error");
	// }
	// return result;
	// }

}
