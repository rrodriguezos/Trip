/* UserController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Trip;
import domain.User;
import forms.UserRegisterForm;

import services.TripService;
import services.UserService;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private UserService userService;
	@Autowired
	private TripService tripService;
	
	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

		@RequestMapping(value = "/register", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			UserRegisterForm userRegisterForm;
			
			userRegisterForm = new UserRegisterForm();
			
			result = new ModelAndView("user/register");
			result.addObject("userRegisterForm", userRegisterForm);
			return result;
		}
		
		
		// Edition ----------------------------------------------------------------
		
		@RequestMapping(value = "/register", method = RequestMethod.POST, params="save")
		public ModelAndView register(@Valid UserRegisterForm userRegisterForm, BindingResult binding){
			ModelAndView result;
			User user;
			Boolean verificarPass;
			
			//Verificate match the passwords
			verificarPass = userRegisterForm.getPassword().equals(userRegisterForm.getConfirmPassword());
			
			if(binding.hasErrors() || !verificarPass || !userRegisterForm.getAccept()){
				result = createEditModelAndView(userRegisterForm);
				if (!verificarPass) {
					result.addObject("message2", "register.commit.password");
				}
				if(!userRegisterForm.getAccept()){
					 result.addObject("message2", "register.commit.condition");
				}
			}else{
				try{
					user = userService.reconstruct(userRegisterForm);
					userService.save(user);
					result = new ModelAndView("redirect:/");
				}catch(Throwable oops){
					result = new ModelAndView("user/register");
					result.addObject("userRegisterForm", userRegisterForm);
		
					if (oops instanceof DataIntegrityViolationException) {
						result.addObject("message2","register.commit.duplicatedUsername");
					} else {
						result.addObject("message2", "register.commit.error");
					}
				}
			}
			
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit() {
			ModelAndView result;
			UserRegisterForm userRegisterForm;

			userRegisterForm = userService.copyUser();
			Assert.notNull(userRegisterForm);
			result = new ModelAndView("user/edit");

			// De la vista edit.jsp modelAttribute="userForm
			result.addObject("userRegisterForm", userRegisterForm);

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid UserRegisterForm userRegisterForm, BindingResult binding) {
			ModelAndView result;
			Boolean verificarPass;
			Boolean passActual;
			//Verificate match the passwords
			verificarPass = userRegisterForm.getPassword().equals(userRegisterForm.getConfirmPassword());
			//Comprobamos que se ha introducido bien la contraseña actual
			passActual = userService.passActual(userRegisterForm);
			
			if (binding.hasErrors() || !verificarPass || !passActual) {
				result = new ModelAndView("user/edit");
				result.addObject("userRegisterForm", userRegisterForm);
				if (!verificarPass) {
					result.addObject("message2", "register.commit.password");
				}
				if (!passActual) {
					result.addObject("message2", "register.commit.passwordPast");
				}
			} else {
				try {
					userService.reconstructPerfil(userRegisterForm);
					result = new ModelAndView("redirect:/");
				} catch (Throwable oops) {
					result = new ModelAndView("user/edit");
					result.addObject("userRegisterForm", userRegisterForm);
					result.addObject("message2", "register.commit.error");
				}
			}

			return result;
		}
		
		//List ------------------------------------------------------------------
		@RequestMapping(value="/list", method=RequestMethod.GET)
		public ModelAndView list(){
			ModelAndView result;
			Collection<User> users;
			
			users = userService.findAll();
			
			result = new ModelAndView("user/list");
			result.addObject("users", users);
			result.addObject("requestUri", "user/list.do");
			
			return result;
		}
		
		//Display -----------------------------------------------------------------
		@RequestMapping(value="/display", method=RequestMethod.GET)
		public ModelAndView display(int userId){
			ModelAndView result;
			User user;
			Collection<Trip> trips;
			
			user = userService.findOne(userId);
			trips = tripService.findTripByUser(userId);
			
			result = new ModelAndView("user/display");
			result.addObject("user", user);
			result.addObject("trips", trips);
			
			return result;
		}
		
		
		// Ancillary methods --------------------------------------------------------
		
		protected ModelAndView createEditModelAndView(UserRegisterForm userForm){
			ModelAndView result;
			
			result = createEditModelAndView(userForm, null);
			
			return result;
		}
		
		protected ModelAndView createEditModelAndView(UserRegisterForm userForm, String message){
			ModelAndView result;
			
			result = new ModelAndView("user/register");
			
			result.addObject("user", userForm);
			result.addObject("message2", message);
			return result;
		}
}