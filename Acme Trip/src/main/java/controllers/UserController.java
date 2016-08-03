package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.TripService;
import services.UserService;
import domain.Trip;
import domain.User;
import forms.UserRegisterForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	@Autowired
	private TripService tripService;

	// Constructor ---------------------------------
	public UserController() {
		super();
	}

	// Listing-------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users;

		users = userService.findAll();

		result = new ModelAndView("actor/list");
		result.addObject("users", users);
		result.addObject("actor", users);
		result.addObject("requestUri", "user/list.do");

		return result;
	}

	// Creation ------------------------------------
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		UserRegisterForm userRegisterForm;

		userRegisterForm = new UserRegisterForm();
		result = createEditModelAndView(userRegisterForm, null);
		return result;

	}

	// Edition --------------------------------------
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ModelAndView save(@Valid UserRegisterForm userRegisterForm,
			BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		User user;
		if (binding.hasErrors()) {
			String msg = !userRegisterForm.getPassword().equals(
					userRegisterForm.getConfirmPassword()) ? "actor.password.notequal"
					: "actor.commit.not.valid";
			result = createEditModelAndView(userRegisterForm, msg);
		} else {
			try {
				user = userService.reconstruct(userRegisterForm);
				userService.save(user);
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				result = new ModelAndView("redirect:/security/login.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(userRegisterForm,
						"actor.commit.error");
			}
		}
		return result;
	}

	// Display -----------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(int userId) {
		ModelAndView result;
		User user;
		Collection<Trip> trips;

		user = userService.findOne(userId);
		trips = tripService.findAllTripsByUserId(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("trips", trips);

		return result;
	}

	// Ancillary methods -----------------------------

	protected ModelAndView createEditModelAndView(UserRegisterForm userRegisterForm) {
		ModelAndView result;

		result = createEditModelAndView(userRegisterForm, null);

		return result;
	}

	private ModelAndView createEditModelAndView(
			UserRegisterForm userRegisterForm, String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("userRegisterForm", userRegisterForm);
		result.addObject("message", message);
		result.addObject("actor", "userRegisterForm");
		result.addObject("actionURI", "user/save.do");
		result.addObject("cancelURI", "welcome/index.do");
		return result;
	}

}
