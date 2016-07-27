package utilities;

import java.util.Collection;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import security.Authority;
import security.LoginService;
import domain.DomainEntity;

public class DPUtils {
	
	private static final String TICKER_CHARACTERS_ALL = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ_";
	private static final String TICKER_CHARACTERS_DIGITS = "0123456789";
	
	public static boolean hasRole(String ...privileges){
		
		if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) {
			return false;
		}
		
		if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated() || SecurityContextHolder.getContext().getAuthentication() == null) {
			return false;
		}
		
		for(String privilege : privileges){
			for(Authority auth : LoginService.getPrincipal().getAuthorities()){
				if(auth.getAuthority().equals(privilege)){
					return true;
				}
			}
		}
		return false;
	}
	
	public static <T extends DomainEntity> boolean inCollection(Collection<T> collection, T item){
		boolean res = false;
		
		for(DomainEntity tmp : collection){
			if(item.getId() == tmp.getId()){
				res = true;
				break;
			}
		}
		
		return res;
	}
	
	public static String calculateNextOrderTicker(String previousTicker) { 
		
		if(previousTicker == null) {
			return "000000-0000";
		}
		
		String prev = previousTicker.replaceAll("-", "");
		char[] array = prev.toCharArray();
		
		for(int i = prev.length() - 1; i >= 0; i--) {
			String chars = (i>5) ? TICKER_CHARACTERS_ALL : TICKER_CHARACTERS_DIGITS;
			char ch = Character.toUpperCase(prev.charAt(i));
			int next = chars.indexOf(""+ch) + 1;
			
			if(next == chars.length()) {
				array[i] = chars.charAt(0);
			} else {
				array[i] = chars.charAt(next);
				break;
			}
		}
		
		String res = new String(array);
		return res.substring(0, 6) + "-" + res.substring(6,10);
	}

}
