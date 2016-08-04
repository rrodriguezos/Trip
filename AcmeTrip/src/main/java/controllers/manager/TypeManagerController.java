package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.TypeService;

import controllers.AbstractController;
import domain.Type;

@Controller
@RequestMapping("/type/manager")
public class TypeManagerController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public TypeManagerController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private TypeService typeService;
	
	//Create-------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		Type type;
		
		type = typeService.create();
		result = new ModelAndView("type/create");
		result.addObject("type", type);
		result.addObject("requestUri", "type/manager/edit.do");
		
		return result;
	}
	
	//Editing ------------------------------------------------
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int typeId){
		ModelAndView result;
		Type type;
		
		type = typeService.findOne(typeId);

		result = new ModelAndView("type/edit");
		result.addObject("type", type);
		result.addObject("requestUri", "type/manager/edit.do");

		return result;
	}

	@RequestMapping(value="/edit",method = RequestMethod.POST, params="save")
	public ModelAndView edit(@Valid Type type, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = new ModelAndView("type/edit");
			result.addObject("type", type);
			result.addObject("requestUri", "type/manager/edit.do");
		}else{
			try{
				typeService.save(type);
				result = new ModelAndView("redirect:/type/manager/list.do");
			}catch(Throwable oops){
				result = new ModelAndView("type/edit");
				result.addObject("type", type);
				result.addObject("requestUri", "type/manager/edit.do");
				result.addObject("message2","type.commit.error");
			}
		}
		return result;
	}
	
	// List -------------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<Type> types;
		
		types = typeService.findAll();
		
		result = new ModelAndView("type/list");
		result.addObject("requestUri","/type/manager/list.do");
		result.addObject("types", types);
		
		return result;
	}
	
	

}
