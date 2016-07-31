package controllers.User;

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
import services.DailyPlanService;
import services.SlotService;
import controllers.AbstractController;
import domain.Activity;
import domain.DailyPlan;
import domain.Slot;

@Controller
@RequestMapping("/slot/user/")
public class SlotUserController extends AbstractController {

	// Services ---------------------
	@Autowired
	private SlotService slotService;
	
	@Autowired
	private ActivityService activityService;

	@Autowired
	private DailyPlanService dailyplanService;

	// Constructor ------------------
	public SlotUserController() {
		super();
	}

	// Creation
	// ---------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int dailyplanId) {
		ModelAndView result;
		Slot slot;
		DailyPlan dailyPlan;

		slot = slotService.create();
		dailyPlan = dailyplanService.findOne(dailyplanId);
		slot.setDailyplan(dailyPlan);

		result = createEditModelAndView(slot);

		return result;
	}

	// Save
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Slot slot, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		int checkOverlap = 0;
		boolean startEndDates1 = false, startEndDates2 = false;
		if (slot.getStartTime() != null && slot.getEndTime() != null) {
			checkOverlap = slotService.checkOverlapping(slot);
			startEndDates1 = slot.getDailyplan().getWeekDay()
					.equals(slot.getStartTime())
					|| slot.getDailyplan().getWeekDay()
							.equals(slot.getEndTime());
			startEndDates2 = slot.getStartTime().after(slot.getEndTime());
		}

		if (binding.hasErrors() || startEndDates1 || startEndDates2
				|| checkOverlap != 0) {
			result = createEditModelAndView(slot);
			if (startEndDates1) {
				result.addObject("message", "slot.overlap.error");
			} else if (checkOverlap != 0) {
				result.addObject("message", "slot.overlap.error2");
			} else if (startEndDates2) {
				result.addObject("message", "slot.overlap.error3");
			}
		} else {
			try {
				slotService.save(slot);
				result = new ModelAndView("redirect:/slot/list.do?dailyPlanId="
						+ slot.getDailyplan().getId());
				redirectAttrs.addFlashAttribute("message", "slot.commit.ok");

			} catch (Throwable oops) {
				result = createEditModelAndView(slot);
				result.addObject("message", "slot.commit.error");
			}
		}

		return result;
	}

	// Delete-----------------------------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int slotId) {
		ModelAndView result;

		Slot slot = slotService.findOne(slotId);

		slotService.delete(slot);

		result = new ModelAndView("redirect:/trip/user/mytrips.do");
		result.addObject("requestUri", "/trip/user/mytrips.do");

		return result;
	}

	// Ancillary methods
	// --------------------------------------------------------

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
		result.addObject("message", message);

		return result;
	}

}
