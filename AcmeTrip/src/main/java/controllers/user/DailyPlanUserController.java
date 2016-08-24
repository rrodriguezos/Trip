package controllers.user;

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
@RequestMapping("/dailyPlan/user")
public class DailyPlanUserController extends AbstractController {

	// Supporting services -------------------------------

	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private TripService tripService;

	// Constructors --------------------------------------

	public DailyPlanUserController() {
		super();
	}

	// Create --------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int tripId) {
		ModelAndView result;
		DailyPlan dailyPlan;
		Trip trip;

		dailyPlan = dailyPlanService.create();
		trip = tripService.findOne(tripId);
		dailyPlan.setTrip(trip);

		result = createEditModelAndView(dailyPlan);

		return result;
	}

	// Save --------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid DailyPlan dailyPlan, BindingResult binding,
			RedirectAttributes redir) {
		ModelAndView result;
		Boolean comprobrarSolapamientoFechas;

		comprobrarSolapamientoFechas = dailyPlanService
				.comprobrarSolape(dailyPlan);

		if (binding.hasErrors() || !comprobrarSolapamientoFechas) {
			result = createEditModelAndView(dailyPlan);
			if (!comprobrarSolapamientoFechas) {
				result.addObject("message2", "dailyPlan.overlapping.error");
			}
		} else {
			try {
				dailyPlanService.save(dailyPlan);
				result = new ModelAndView("redirect:/dailyPlan/list.do?tripId="
						+ dailyPlan.getTrip().getId());
				redir.addFlashAttribute("message2", "dailyPlan.commit.ok");

			} catch (Throwable oops) {
				result = createEditModelAndView(dailyPlan);

				result.addObject("message2", "dailyPlan.commit.error");
			}
		}

		return result;
	}

	// Delete ---------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam int dailyPlanId) {
		ModelAndView result;

		DailyPlan dailyPlan = dailyPlanService.findOne(dailyPlanId);

		dailyPlanService.delete(dailyPlan);

		result = new ModelAndView("redirect:/trip/user/mylist.do");
		result.addObject("requestUri", "/trip/user/mylist.do");

		return result;
	}

	// Ancillary methods--------------------------------------------------------

	protected ModelAndView createEditModelAndView(DailyPlan dailyPlan) {
		ModelAndView result;

		result = createEditModelAndView(dailyPlan, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(DailyPlan dailyPlan,
			String message) {
		ModelAndView result;

		result = new ModelAndView("dailyPlan/create");

		result.addObject("dailyPlan", dailyPlan);
		result.addObject("message2", message);

		return result;
	}

}
