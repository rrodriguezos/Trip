package controllers.actor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;
import controllers.AbstractController;
import domain.Activity;
import domain.Comment;
import domain.Trip;

@Controller
@RequestMapping("/comment/actor")
public class CommentActorController extends AbstractController {
	
	//Contructors----------------------------------------------------
	public CommentActorController() {
		super();
	}
	
	//Supporting services--------------------------------------------
	@Autowired
	private CommentService commentService;
	
	//Editing ------------------------------------------------
	@RequestMapping(value="/edit",method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int commentableId){
		ModelAndView result;
		Comment comment;
		
		comment = commentService.create(commentableId);
		
		result = new ModelAndView("comment/edit");
		result.addObject("actionUri", "comment/actor/edit.do");
		result.addObject("comment", comment);

		return result;
	}

	@RequestMapping(value="/edit",method = RequestMethod.POST, params="save")
	public ModelAndView edit(@Valid Comment comment, BindingResult binding){
		ModelAndView result;
		
		if(binding.hasErrors()){
			result = createEditModelAndView(comment);
		}else{
			try{
				commentService.save(comment);
				if (comment.getCommentable() instanceof Trip){
					result = new ModelAndView("redirect:/trip/display.do?tripId="+comment.getCommentable().getId());
				}else if (comment.getCommentable() instanceof Activity){
					result = new ModelAndView("redirect:/activity/display.do?activityId="+comment.getCommentable().getId());
				}else {
					result = new ModelAndView("redirect:/welcome/index.do");
				}
			}catch(Throwable oops){
				result = createEditModelAndView(comment,"comment.commit.error");
			}
		}
		return result;
	}
	
	//Ancillary methods ----------------------------------------------
	protected ModelAndView createEditModelAndView(Comment comment){
		ModelAndView result;
		
		result = createEditModelAndView(comment, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Comment comment, String message){
		ModelAndView result;
		
		result = new ModelAndView("comment/edit");
		result.addObject("comment", comment);
		result.addObject("actionUri", "comment/actor/edit.do");
		result.addObject("message2",message);
		
		return result;
	}
}
