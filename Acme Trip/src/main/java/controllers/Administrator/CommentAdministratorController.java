package controllers.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;


import services.CommentService;

import domain.Activity;
import domain.Comment;


@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {
	
	//Services -----------------------------
	@Autowired
	private CommentService commentService;
	
	//Constructor --------------------------
	public CommentAdministratorController() {
		super();
	}
	
	//Delete -------------------------------
	@RequestMapping(value="/mark", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int commentId,
			@RequestParam int id, RedirectAttributes redirectAttrs) {
		ModelAndView result;
		try {
			Comment comment = commentService.findOne(commentId);
			commentService.flagCommentAsInappropriate(comment.getId());
			redirectAttrs.addFlashAttribute("message", "comment.commit.ok");		
			result = new ModelAndView("redirect:/comment/list.do?id="+id);
		}  catch(Throwable oops) {
			redirectAttrs.addFlashAttribute("message", "comment.commit.error");	
			result = new ModelAndView("redirect:/comment/list.do?id="+id);
		}
		return result;
	}
	
	

}
