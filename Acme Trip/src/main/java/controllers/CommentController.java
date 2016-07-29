package controllers;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.CommentService;
import domain.Comment;
import forms.CommentForm;

@Controller
@RequestMapping("/comment")
@SessionAttributes("commentableId")
public class CommentController extends AbstractController {
	
	//Services -------------------------
	@Autowired
	private CommentService commentService;
	
	//Constructor ----------------------
	public CommentController() {
		super();
	}
	
	//Listing comments by commentable ----------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int id) {
		ModelAndView result;
		Collection<Comment> comments = commentService.findCommentsByCommentableId(id);
		result = new ModelAndView("comment/list");
		result.addObject("comments", comments);
		result.addObject("requestURI", "comment/list.do");
		result.addObject("id", id);
		return result;		
	}
	
	
	//Creation ---------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int commentableId) {
		ModelAndView result;
		
		CommentForm commentForm = new CommentForm();
		result = createEditModelAndView(commentForm, null, commentableId);
		result.addObject("commentableId", commentableId);
		return result;		
	}
	
	//Edition ----------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid CommentForm commentForm, BindingResult binding,
				@ModelAttribute("commentableId") int commentableId, SessionStatus status, RedirectAttributes redirectAttrs) {
			
			ModelAndView result;
			
			if (binding.hasErrors()) {
				result = createEditModelAndView(commentForm, "comment.commit.error", commentableId);
			} else{
				try {
					System.out.print(binding.getFieldError());
					System.out.print(binding.getGlobalError());
					Comment comment = commentService.reconstruct(commentForm, commentableId);
					commentService.save(comment);	
					System.out.print(binding.getFieldError());
					System.out.print(binding.getGlobalError());
					redirectAttrs.addFlashAttribute("message", "comment.commit.ok");		
					result = new ModelAndView("redirect:list.do?id="+commentableId);
					status.setComplete();
				} catch (Throwable oops) {
					System.out.print(binding.getFieldError());
					System.out.print(binding.getGlobalError());
					result = createEditModelAndView(commentForm, "comment.commit.error", commentableId);				
				}
			}
			return result;
		}



	//Ancillary methods -------------
	
	protected ModelAndView createEditModelAndView(CommentForm commentForm, String message, int id) {
		ModelAndView result;
				
		result = new ModelAndView("comment/edit");
		result.addObject("commentForm", commentForm);
		result.addObject("message", message);
		result.addObject("actionURI", "comment/edit.do");
		result.addObject("cancelURL", "comment/list.do?id="+id);
		
		return result;
	}
	

}
