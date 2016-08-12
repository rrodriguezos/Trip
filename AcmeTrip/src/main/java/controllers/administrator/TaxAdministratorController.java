package controllers.administrator;

import java.util.Collection;
import java.util.LinkedList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Banner;
import domain.Tax;
import services.TaxService;

@Controller
@RequestMapping("/tax/administrator")
public class TaxAdministratorController extends AbstractController {

	// Services -----------------------
	@Autowired
	private TaxService taxService;

	// Constructor --------------------
	public TaxAdministratorController() {
		super();
	}

	// List ------------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Tax> taxs;

		taxs = taxService.findAll();

		result = new ModelAndView("tax/administrator/list");
		result.addObject("taxs", taxs);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Tax tax = new Tax();
		tax.setBanners(new LinkedList<Banner>());

		result = new ModelAndView("tax/administrator/create");
		result.addObject("tax", tax);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Tax tax, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("tax", tax);
			result.addObject("message", "manager.commit.error");
		} else {
			try {
				taxService.save(tax);
				result = new ModelAndView("redirect:/tax/administrator/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("tax", tax);
				result.addObject("message", "manager.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int taxId) {
		ModelAndView result;
		Tax tax = taxService.findOne(taxId);

		result = new ModelAndView("tax/administrator/edit");
		result.addObject("tax", tax);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid Tax tax, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("tax", tax);
			result.addObject("message", "manager.commit.error");
		} else {
			try {
				taxService.saveEdit(tax);
				result = new ModelAndView("redirect:/tax/administrator/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("tax", tax);
				result.addObject("message", "manager.commit.error");
			}
		}

		return result;
	}

}
