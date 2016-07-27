package interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class PolicyInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object o)
			throws Exception {       
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, 
			Object o, ModelAndView modelAndView) throws Exception {
		Cookie cookie[]=req.getCookies();
		Cookie cook;
		String agree="false";
		if (cookie != null) {
			for (int i = 0; i < cookie.length; i++) {
			    cook = cookie[i];
			    if(cook.getName().equalsIgnoreCase("PolicyAgreement")){
			    	agree=cook.getValue();
			    	break;
			    }  
			}    
		}
		
		modelAndView.addObject("agree", agree);
	}

	@Override
	public void afterCompletion(HttpServletRequest req,	HttpServletResponse res, 
			Object o, Exception e) throws Exception {}

}
