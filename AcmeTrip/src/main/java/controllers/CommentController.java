package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;

import services.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {
	
	//Constructor -----------------------------------------------------
	public CommentController() {
		super();
	}
	
	
	//Services --------------------------------------------------------
	@Autowired
	private CommentService commentService;
	
	//Display----------------------------------------------------------------
	@RequestMapping(value="/display", method=RequestMethod.GET)
	public ModelAndView display(@RequestParam int commentId){
		ModelAndView result;
		Comment comment;
		
		comment = commentService.findOne(commentId);
		
		result = new ModelAndView("comment/display");
		result.addObject("comment", comment);
		
		return result;
	}
	

}
