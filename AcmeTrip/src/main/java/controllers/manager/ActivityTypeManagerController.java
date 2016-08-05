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
import domain.ActivityType;

@Controller
@RequestMapping("/activitytype/manager")
public class ActivityTypeManagerController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public ActivityTypeManagerController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private TypeService typeService;
	
	//Create-------------------------------------------
	@RequestMapping(value="/create", method=RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView result;
		ActivityType activitytype;
		
		activitytype = typeService.create();
		result = new ModelAndView("activitytype/create");
		result.addObject("activitytype", activitytype);
		result.addObject("requestUri", "activitytype/manager/edit.do");
		
		return result;
	}
	
	//Editing ------------------------------------------------
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int activitytypeId){
		ModelAndView result;
		ActivityType activitytype;
		
		activitytype = typeService.findOne(activitytypeId);

		result = new ModelAndView("activitytype/edit");
		result.addObject("activitytype", activitytype);
		result.addObject("requestUri", "activitytype/manager/edit.do");

		return result;
	}

	@RequestMapping(value="/edit",method = RequestMethod.POST, params="save")
	public ModelAndView edit(@Valid ActivityType activitytype, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = new ModelAndView("activitytype/edit");
			result.addObject("activitytype", activitytype);
			result.addObject("requestUri", "activitytype/manager/edit.do");
		}else{
			try{
				typeService.save(activitytype);
				result = new ModelAndView("redirect:/activitytype/manager/list.do");
			}catch(Throwable oops){
				result = new ModelAndView("type/edit");
				result.addObject("activitytype", activitytype);
				result.addObject("requestUri", "activitytype/manager/edit.do");
				result.addObject("message2","activitytype.commit.error");
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
		result.addObject("requestUri","/activitytype/manager/list.do");
		result.addObject("activitytypes", activitytypes);
		
		return result;
	}
	
	

}
