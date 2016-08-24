package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CommentService;

import controllers.AbstractController;
import domain.Activity;
import domain.Comment;
import domain.Trip;

@Controller
@RequestMapping("/comment/administrator")
public class CommentAdministratorController extends AbstractController {

	// Constructor --------------------------------------------------------
	public CommentAdministratorController() {
		super();
	}

	// Services -----------------------------------------------------------
	@Autowired
	private CommentService commentService;

	// Appropriated --------------------------------------------------------
	@RequestMapping(value = "/appropriated")
	public ModelAndView appropriated(@RequestParam int commentId) {
		ModelAndView result;
		Comment comment;

		comment = commentService.findOne(commentId);

		commentService.changeStateComment(commentId);

		if (comment.getCommentable() instanceof Trip) {
			result = new ModelAndView("redirect:/trip/display.do?tripId="
					+ comment.getCommentable().getId());
		} else if (comment.getCommentable() instanceof Activity) {
			result = new ModelAndView(
					"redirect:/activity/display.do?activityId="
							+ comment.getCommentable().getId());
		} else {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}
}
