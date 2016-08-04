/* UserController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import controllers.AbstractController;
import domain.Manager;
import forms.ManagerForm;
import services.ManagerService;

@Controller
@RequestMapping("/manager/administrator")
public class ManagerAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private ManagerService managerService;
	
	// Constructors -----------------------------------------------------------

	public ManagerAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ManagerForm managerForm;
		
		managerForm = new ManagerForm();
		
		result = new ModelAndView("manager/create");
		result.addObject("managerForm", managerForm);
		return result;
	}
	
	
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid ManagerForm managerForm, BindingResult binding){
		ModelAndView result;
		Manager manager;
		Boolean verificarPass;
		
		//Verificate match the passwords
		verificarPass = managerForm.getPassword().equals(managerForm.getPasswordRepeat());
		
		if(binding.hasErrors() || !verificarPass){
			result = createEditModelAndView(managerForm);
			if (!verificarPass) {
				result.addObject("message2", "manager.commit.password");
			}
		}else{
			try{
				manager = managerService.reconstruct(managerForm);
				managerService.save(manager);
				result = new ModelAndView("redirect:/");
			}catch(Throwable oops){
				result = createEditModelAndView(managerForm);
				if (oops instanceof DataIntegrityViolationException) {
					result.addObject("message2","manager.commit.duplicatedUsername");
				} else {
					result.addObject("message2", "manager.commit.error");
				}
			}
		}
		
		return result;
	}
	
	//List ------------------------------------------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Manager> managers;
		
		managers = managerService.findAll();
		
		result = new ModelAndView("manager/list");
		result.addObject("managers", managers);
		result.addObject("requestUri", "manager/administrator/list.do");
		
		return result;
	}
	
	//Display -----------------------------------------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(int managerId){
		ModelAndView result;
		Manager manager;
		
		manager = managerService.findOne(managerId);
		
		result = new ModelAndView("manager/display");
		result.addObject("manager", manager);
		
		return result;
	}
	
	
	// Ancillary methods --------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(ManagerForm managerForm){
		ModelAndView result;
		
		result = createEditModelAndView(managerForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(ManagerForm managerForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("manager/create");
		
		result.addObject("manager", managerForm);
		result.addObject("message2", message);
		return result;
	}
}