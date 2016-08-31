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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActivityService;
import services.SlotService;
import services.DailyPlanService;

import controllers.AbstractController;
import domain.Activity;
import domain.Slot;
import domain.DailyPlan;

@Controller
@RequestMapping("/slot/user")
public class SlotUserController extends AbstractController {

	// Supporting services -------------------------------

	@Autowired
	private SlotService slotService;
	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private ActivityService activityService;

	// Constructors --------------------------------------

	public SlotUserController() {
		super();
	}

	// Create ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int dailyPlanId) {
		ModelAndView result;
		Slot slot;
		DailyPlan dailyPlan;

		slot = slotService.create();
		dailyPlan = dailyPlanService.findOne(dailyPlanId);
		slot.setDailyPlan(dailyPlan);

		result = createEditModelAndView(slot);

		return result;
	}

	// Save ---------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Slot slot, BindingResult binding,
			RedirectAttributes redir) {
		ModelAndView result;
		int comprobrarSolape = 0;
		boolean comprobrarFecha1 = false, comprobrarFecha2 = false;
		if (slot.getStartTime() != null && slot.getEndTime() != null) {
			comprobrarSolape = slotService.comprobrarSolape(slot);
			comprobrarFecha1 = slot.getDailyPlan().getWeekDay()
					.equals(slot.getStartTime())
					|| slot.getDailyPlan().getWeekDay()
							.equals(slot.getEndTime());
			comprobrarFecha2 = slot.getStartTime().after(slot.getEndTime());
		}

		if (binding.hasErrors() || comprobrarFecha1 || comprobrarFecha2
				|| comprobrarSolape != 0) {
			result = createEditModelAndView(slot);
			if (comprobrarFecha1) {
				result.addObject("message2", "slot.solape.error");
			} else if (comprobrarSolape != 0) {
				result.addObject("message2", "slot.solape.error2");
			} else if (comprobrarFecha2) {
				result.addObject("message2", "slot.solape.error3");
			}
		} else {
			try {
				slotService.save(slot);
				result = new ModelAndView("redirect:/slot/list.do?dailyPlanId="
						+ slot.getDailyPlan().getId());
				redir.addFlashAttribute("message2", "slot.commit.ok");

			} catch (Throwable oops) {
				result = createEditModelAndView(slot);
				result.addObject("message2", "slot.commit.error");
			}
		}

		return result;
	}

	// Delete-------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int slotId) {
		ModelAndView result;

		Slot slot = slotService.findOne(slotId);

		slotService.delete(slot);

		result = new ModelAndView("redirect:/trip/user/mylist.do");
		result.addObject("requestUri", "/trip/user/mylist.do");

		return result;
	}

	// Ancillary methods---------------------------------------------------------------

	protected ModelAndView createEditModelAndView(Slot slot) {
		ModelAndView result;

		result = createEditModelAndView(slot, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Slot slot, String message) {
		ModelAndView result;
		Collection<Activity> activities;

		activities = activityService.findAll();

		result = new ModelAndView("slot/create");

		result.addObject("activities", activities);
		result.addObject("slot", slot);
		result.addObject("message2", message);

		return result;
	}

}
