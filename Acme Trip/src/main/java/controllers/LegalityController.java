package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legality")
public class LegalityController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public LegalityController() {
		super();
	}

	// Action ---------------------------------------------------------------

	@RequestMapping("/legality")
	public ModelAndView legal() {
		ModelAndView result;

		result = new ModelAndView("legality/legality");

		return result;
	}

}
