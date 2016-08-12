package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import controllers.AbstractController;
import domain.Campaign;
import domain.CreditCard;
import services.CreditCardService;
import services.ManagerService;

@Controller
@RequestMapping("/creditcard/manager")
public class CreditCardManagerController extends AbstractController {
	
	//Constructor --------------------------------------------------------
	public CreditCardManagerController() {
		super();
	}
	
	//Services -----------------------------------------------------------
	@Autowired
	private CreditCardService tarjetaService;
	
	@Autowired
	private ManagerService managerService;
	
	//Appropriated --------------------------------------------------------
	@RequestMapping(value="/list")
	public ModelAndView list(){
		ModelAndView result;
		
		Collection<CreditCard> tarjetas = managerService.findByPrincipal().getCreditCards();
		
		result = new ModelAndView();
		result.addObject("creditcards", tarjetas);
		
		return result;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView nuevaTarjeta() {
		ModelAndView result;
		result = new ModelAndView("creditcard/manager/create");
		CreditCard campaign = tarjetaService.create();
		result.addObject("creditcard", campaign);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView saveNewCc(@Valid CreditCard creditcard, BindingResult binding, RedirectAttributes redir) {
		ModelAndView result;
		Assert.notNull(creditcard);
		if (binding.hasErrors()) {
			result = new ModelAndView("creditcard/manager/create.do");
			result.addObject("creditcard", creditcard);
			result.addObject("message2", "campaign.creditExist.error");
		} else {
			try {
				tarjetaService.save(creditcard);
				result = new ModelAndView("redirect:/creditcard/manager/list.do");
				redir.addFlashAttribute("message", "trip.commit.ok");

			} catch (Throwable oops) {
				System.out.println(oops.getLocalizedMessage());
				result = new ModelAndView("creditcard/manager/create");
				result.addObject("creditcard", creditcard);
				result.addObject("message2", "campaign.creditExist.error");
			}
		}

		return result;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int creditCardId) {
		ModelAndView result;
		Campaign campaign = tarjetaService.findOne(creditCardId).getCampaign();
		Boolean enFecha = true;
		if (campaign!=null) {
			enFecha = true;
		}
		if (!enFecha) {
			result = new ModelAndView("creditcard/manager/list.do");
			Collection<CreditCard> creditcards = tarjetaService.findCreditCardByPrincipal();
			result.addObject("creditcards", creditcards);
			if (!enFecha) {
				result.addObject("message", "trip.commit.error");
			}
		} else {
			try {
				tarjetaService.deleteC(creditCardId);
				result = new ModelAndView("redirect:/creditcard/manager/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("creditcard/manager/list.do");
				Collection<CreditCard> creditcards = tarjetaService.findCreditCardByPrincipal();
				result.addObject("creditcards", creditcards);
			}
		}
		return result;
	}
}
