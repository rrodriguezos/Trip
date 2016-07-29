package controllers.User;

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

import services.SlotService;
import services.TripService;
import services.UserService;
import controllers.AbstractController;
import domain.Slot;

@Controller
@RequestMapping("/slot/user/")
public class SlotUserController extends AbstractController {

	// Services ---------------------
	@Autowired
	private SlotService slotService;

	@Autowired
	private UserService userService;

	@Autowired
	private TripService tripService;

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
		Assert.notNull(dailyplanId);
		slot = slotService.create(dailyplanId);
		result = createEditModelAndView(slot, null);
		return result;
	}

	// Save
	// -----------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Slot slot, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.print(binding.hasErrors());
			System.out.print(binding.toString());
			result = createEditModelAndView(slot, "daily.commit.error");
		} else {
			try {
				slotService.save(slot);
				result = new ModelAndView("redirect:/trip/user/list.do");
				result.addObject("userId", userService.findByPrincipal().getId());
				result.addObject("user", userService.findByPrincipal());
			} catch (Throwable oops) {
				result = createEditModelAndView(slot, "trip.commit.error");

			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveYotro")
	public ModelAndView saveYotro(@Valid Slot slot, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.print(binding.hasErrors());
			System.out.print(binding.toString());
			result = createEditModelAndView(slot, "daily.commit.error");
		} else {
			try {
				slotService.save(slot);
				result = new ModelAndView("redirect:/slot/user/create.do?dailyplanId="+slot.getDailyplan().getId());
				result.addObject("dailyplanId", slot.getDailyplan().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(slot, "trip.commit.error");

			}
		}

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveYDaily")
	public ModelAndView saveYDaily(@Valid Slot slot, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.print(binding.hasErrors());
			System.out.print(binding.toString());
			result = createEditModelAndView(slot, "daily.commit.error");
		} else {
			try {
				slotService.save(slot);
				result = new ModelAndView("redirect:/dailyplan/user/create.do?tripId="+slot.getDailyplan().getTrip().getId());
				result.addObject("dailyplanId", slot.getDailyplan().getId());
			} catch (Throwable oops) {
				result = createEditModelAndView(slot, "trip.commit.error");
			}
		}

		return result;
	}

	protected ModelAndView createEditModelAndView(Slot slot,
			String message) {
		ModelAndView result;

		result = new ModelAndView("slot/user/edit");
		result.addObject("slot", slot);
		result.addObject("message", message);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "cancel")
	public ModelAndView cancel(Slot slot, BindingResult binding,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;

		try {
			tripService.delete(slot.getDailyplan().getTrip());
			result = new ModelAndView("redirect:/trip/user/list.do");
			result.addObject("user", userService.findByPrincipal());
			result.addObject("userId", userService.findByPrincipal().getId());
		} catch (Throwable oops) {
			result = createEditModelAndView(slot, "slot.delete.error");
		}
		return result;
	}

}
