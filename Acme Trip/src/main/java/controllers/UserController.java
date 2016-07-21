package controllers;

import java.util.Collection;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.UserAccount;
import services.UserService;
import domain.User;
import forms.UserRegisterForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	// Constructor ---------------------------------
	public UserController() {
		super();
	}

	// Listing-------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<User> users = userService.findAll();
		result = new ModelAndView("actor/list");

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context == null ? null : context
				.getAuthentication();
		Object principal = authentication == null ? null : authentication
				.getPrincipal();
		UserAccount ua = principal instanceof UserAccount ? (UserAccount) principal
				: null;
		if (ua != null) {
			User user = userService.findByUserAccount(ua);
			result.addObject("user", user);
		}

		result.addObject("users", users);
		result.addObject("actor", "users");
		result.addObject("requestURI", "user/list.do");

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
					userRegisterForm.getRepeatedPassword()) ? "actor.password.notequal"
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

	// Ancillary methods -----------------------------
	private ModelAndView createEditModelAndView(
			UserRegisterForm userRegisterForm, String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("userRegisterForm", userRegisterForm);
		result.addObject("message", message);
		result.addObject("actor", "userRegisterForm");
		result.addObject("actionURI", "user/save.do");
		return result;
	}

}
