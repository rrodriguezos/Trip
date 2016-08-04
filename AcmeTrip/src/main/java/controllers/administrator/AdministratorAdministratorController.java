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
import domain.Administrator;
import forms.AdministratorForm;
import services.AdministratorService;

@Controller
@RequestMapping("/administrator/administrator")
public class AdministratorAdministratorController extends AbstractController {
	
	// Services ---------------------------------------------------------------
	@Autowired
	private AdministratorService administratorService;
	
	// Constructors -----------------------------------------------------------

	public AdministratorAdministratorController() {
		super();
	}

	// Creation ---------------------------------------------------------------		

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		AdministratorForm administratorForm;
		
		administratorForm = new AdministratorForm();
		
		result = new ModelAndView("administrator/create");
		result.addObject("administratorForm", administratorForm);
		return result;
	}
	
	
	// Edition ----------------------------------------------------------------
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params="save")
	public ModelAndView save(@Valid AdministratorForm administratorForm, BindingResult binding){
		ModelAndView result;
		Administrator administrator;
		Boolean verificarPass;
		
		//Verificate match the passwords
		verificarPass = administratorForm.getPassword().equals(administratorForm.getPasswordRepeat());
		
		if(binding.hasErrors() || !verificarPass){
			result = createEditModelAndView(administratorForm);
			if (!verificarPass) {
				result.addObject("message2", "administrator.commit.password");
			}
		}else{
			try{
				administrator = administratorService.reconstruct(administratorForm);
				administratorService.save(administrator);
				result = new ModelAndView("redirect:/");
			}catch(Throwable oops){
				result = createEditModelAndView(administratorForm);
				if (oops instanceof DataIntegrityViolationException) {
					result.addObject("message2","administrator.commit.duplicatedUsername");
				} else {
					result.addObject("message2", "administrator.commit.error");
				}
			}
		}
		
		return result;
	}
	
	//List ------------------------------------------------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView result;
		Collection<Administrator> administrators;
		
		administrators = administratorService.findAll();
		
		result = new ModelAndView("administrator/list");
		result.addObject("administrators", administrators);
		result.addObject("requestUri", "administrator/administrator/list.do");
		
		return result;
	}
	
	//Display -----------------------------------------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(int administratorId){
		ModelAndView result;
		Administrator administrator;
		
		administrator = administratorService.findOne(administratorId);
		
		result = new ModelAndView("administrator/display");
		result.addObject("administrator", administrator);
		
		return result;
	}
	
	
	// Ancillary methods --------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm){
		ModelAndView result;
		
		result = createEditModelAndView(administratorForm, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(AdministratorForm administratorForm, String message){
		ModelAndView result;
		
		result = new ModelAndView("administrator/create");
		
		result.addObject("administrator", administratorForm);
		result.addObject("message2", message);
		return result;
	}
}