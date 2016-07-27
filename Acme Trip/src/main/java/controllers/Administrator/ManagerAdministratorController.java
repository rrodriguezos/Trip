package controllers.Administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ManagerService;

import controllers.AbstractController;

import domain.Manager;
import forms.RegisterForm;
import forms.UserRegisterForm;

@Controller
@RequestMapping("/manager/administrator")
public class ManagerAdministratorController extends AbstractController {
	//Services -------------------
	@Autowired
	private ManagerService managerService;
	
	//Constructor ----------------
	public ManagerAdministratorController() {
		super();
	}

	//Creation ------------------------------------
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		UserRegisterForm registerForm;
		
		registerForm = new UserRegisterForm();
		result = createEditModelAndView(registerForm, null);
		return result;
		
	}
	//Edition --------------------------------------
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public ModelAndView save(@Valid UserRegisterForm registerForm, BindingResult binding, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		if (binding.hasErrors()) {
			String msg = !registerForm.getPassword()
					.equals(registerForm.getConfirmPassword())
					?"actor.password.notequal":"actor.commit.not.valid";
			result = createEditModelAndView(registerForm, msg);				
		} else {
			try {
				Manager manager = managerService.reconstruct(registerForm);
				managerService.save(manager);
				redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
				result = new ModelAndView("redirect:/welcome/index.do");
			} catch (Throwable oops) {

				result = createEditModelAndView(registerForm, "actor.commit.error");

			}
		}
		return result;
	}

	//Ancillary methods -----------------------------
	private ModelAndView createEditModelAndView(UserRegisterForm registerForm, String message) {
		ModelAndView result;
	
		result = new ModelAndView("actor/edit");
		result.addObject("registerForm", registerForm);
		result.addObject("message", message);
		result.addObject("actor", "registerForm");
		result.addObject("actionURI", "manager/administrator/save.do");
		return result;
	}

}