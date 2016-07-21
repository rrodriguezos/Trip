package controllers;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/policy")
public class PolicyController {
	
	// Services ---------------------
	
	// Constructors ----------------
	public PolicyController() {
		super();
	}

	@RequestMapping(value="/agree", method = RequestMethod.GET)
	public ModelAndView setCurrency(HttpServletResponse response){
		Cookie cookie = new Cookie("PolicyAgreement", "true");
	    cookie.setPath("/");
		response.addCookie(cookie);
		return new ModelAndView("redirect:/welcome/index.do");
	}

}
