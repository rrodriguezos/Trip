package controllers.User;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;

import domain.User;
import forms.ActorForm;

import services.UserService;

@Controller
@RequestMapping("/user/user")
public class UserUserController  extends AbstractController {
	//Services --------------------------------------------------------------
		@Autowired
		private UserService userService;

		//Constructor ------------------
		public UserUserController() {
			super();
		}

		//List----------------------------------------------------------------
		@RequestMapping("/list")
		public ModelAndView list() {
			ModelAndView result;
			User user = userService.findByPrincipal();
			
			result = new ModelAndView("actor/list");
			result.addObject("user", user);	
			result.addObject("actor", "user");	
			result.addObject("requestURI", "user/user/list.do");	

			return result;		}		
		

		

		//Edit----------------------------------------------------------------------------
		@RequestMapping(value="/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			ModelAndView result;
			User user = userService.findByPrincipal();
			ActorForm actorForm = new ActorForm();

			result = createEditModelAndView(actorForm,null);
			result.addObject("user", user);
			return result;
		}
		
		//Save----------------------------------------------------------------------------
		@RequestMapping(value="/save", method=RequestMethod.POST)
		public ModelAndView save(@Valid ActorForm actorForm, BindingResult binding, RedirectAttributes redirectAttrs) {
			ModelAndView result;
			User editedUser;
			if (binding.hasErrors()) {
				result = createEditModelAndView(actorForm, "actor.commit.not.valid");
			} else if(userService.isWrongPassword(actorForm.getConfirmPassword())){
				result = createEditModelAndView(actorForm, "actor.wrong.password");
			} else {
				try {
					editedUser = userService.reconstruct(actorForm);
					userService.save(editedUser);
					redirectAttrs.addFlashAttribute("message", "actor.commit.ok");
					result = new ModelAndView("redirect:list.do");
				} catch (Throwable oops) {
					result = createEditModelAndView(actorForm, "actor.commit.error");
				}
			}
			return result;
		}

		private ModelAndView createEditModelAndView(ActorForm actorForm, String message) {
			ModelAndView result;

			result = new ModelAndView("actor/edit");
			result.addObject("actorForm", actorForm);
			result.addObject("message", message);
			result.addObject("actor", "actorForm");
			result.addObject("actionURI", "user/user/save.do");
			result.addObject("cancelURI", "user/user/list.do");
			return result;
		}

}
