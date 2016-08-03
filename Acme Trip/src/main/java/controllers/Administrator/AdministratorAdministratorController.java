package controllers.Administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;

import services.AdministratorService;
import services.ManagerService;
import domain.Administrator;
import domain.Manager;
import forms.RegisterForm;
import forms.UserRegisterForm;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {
	// Services -------------------
	@Autowired
	private AdministratorService administratorService;

	// Constructor ----------------
	public AdministratorAdministratorController() {
		super();
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
		if (binding.hasErrors()) {
			String msg = !userRegisterForm.getPassword().equals(
					userRegisterForm.getConfirmPassword()) ? "actor.password.notequal"
					: "actor.commit.not.valid";
			result = createEditModelAndView(userRegisterForm, msg);
		} else {
			try {
				Administrator administrator = administratorService
						.reconstruct(userRegisterForm);
				administratorService.save(administrator);
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {


				result = createEditModelAndView(userRegisterForm,
						"actor.commit.error");
			}
		}
		return result;
	}

	// Ancillary methods -----------------------------
	private ModelAndView createEditModelAndView(UserRegisterForm userRegisterForm,
			String message) {
		ModelAndView result;

		result = new ModelAndView("actor/edit");
		result.addObject("userRegisterForm", userRegisterForm);
		result.addObject("message", message);
		result.addObject("actor", "userRegisterForm");
		result.addObject("actionURI", "administrator/administrator/save.do");
		return result;
	}

}