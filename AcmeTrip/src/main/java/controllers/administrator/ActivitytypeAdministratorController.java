package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActivityTypeService;

import controllers.AbstractController;
import domain.ActivityType;

@Controller
@RequestMapping("/activitytype/administrator")
public class ActivitytypeAdministratorController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public ActivitytypeAdministratorController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private ActivityTypeService typeService;
	
	//Create--------------------------------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		ActivityType activitytype;
		
		activitytype = typeService.create();
		
		result = new ModelAndView("activitytype/create");
		result.addObject("activitytype", activitytype);
		result.addObject("requestUri", "activitytype/administrator/edit.do");
		
		return result;
	}
	
	//Edit--------------------------------------------------------------------
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int typeId){
		ModelAndView result;
		ActivityType activitytype;
		
		activitytype = typeService.findOne(typeId);

		result = new ModelAndView("activitytype/edit");
		result.addObject("activitytype", activitytype);
		result.addObject("requestUri", "activitytype/administrator/edit.do");

		return result;
	}
	// Save---------------------------------------------------------------------------
	@RequestMapping(value="/edit",method = RequestMethod.POST, params="save")
	public ModelAndView edit(@Valid ActivityType activitytype, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = new ModelAndView("activitytype/edit");
			result.addObject("activitytype", activitytype);
			result.addObject("requestUri", "activitytype/administrator/edit.do");
		}else{
			try{
				typeService.save(activitytype);
				result = new ModelAndView("redirect:/activitytype/administrator/list.do");
			}catch(Throwable oops){
				result = new ModelAndView("activitytype/create");
				result.addObject("activitytype", activitytype);
				result.addObject("requestUri", "activitytype/administrator/edit.do");
				
				if (oops instanceof DataIntegrityViolationException) {
					result.addObject("message2","type.commit.duplicatedName");
				} else {
					result.addObject("message2", "type.commit.error");
				}
			}
		}
		return result;
	}
	
	// List -------------------------------------------------------------------
	@RequestMapping("/list")
	public ModelAndView list() {
		ModelAndView result;
		Collection<ActivityType> activitytypes;
		
		activitytypes = typeService.findAll();
		
		result = new ModelAndView("activitytype/list");
		result.addObject("requestUri","/activitytype/administrator/list.do");
		result.addObject("activitytypes", activitytypes);
		
		return result;
	}
}
