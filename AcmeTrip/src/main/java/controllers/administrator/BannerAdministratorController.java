package controllers.administrator;

import java.util.Collection;

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
import forms.PriceForm;
import services.BannerService;
import services.TaxService;

@Controller
@RequestMapping("/banner/administrator")
public class BannerAdministratorController extends AbstractController {

	// Services -----------------------
	@Autowired
	private BannerService bannerService;

	@Autowired
	private TaxService taxService;

	// Constructor --------------------
	public BannerAdministratorController() {
		super();
	}

	// List ------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Banner> banners;

		banners = bannerService.findAllActives();

		result = new ModelAndView("banner/administrator/list");
		result.addObject("banners", banners);
		result.addObject("requestUri", "banner/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner = bannerService.findOne(bannerId);
		PriceForm priceForm = new PriceForm();
		priceForm.setId(bannerId);
		priceForm.setPrecio(banner.getPrice());
		result = new ModelAndView("banner/administrator/edit");
		result.addObject("priceForm", priceForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid PriceForm priceForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("priceForm", priceForm);
			result.addObject("message", "manager.commit.error");
		} else {
			try {
				Banner banner = bannerService.priceFormToBanner(priceForm);
				result = new ModelAndView("redirect:/banner/administrator/selectTax.do?bannerId=" + banner.getId());
			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("priceForm", priceForm);
				result.addObject("message", "manager.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/selectTax", method = RequestMethod.GET)
	public ModelAndView selectTax(@RequestParam int bannerId) {
		ModelAndView result;
		Banner banner = bannerService.findOne(bannerId);
		result = new ModelAndView("banner/administrator/selectTax");
		result.addObject("banner", banner);
		Collection<Tax> taxs = taxService.findAll();
		result.addObject("taxs", taxs);
		return result;
	}

	@RequestMapping(value = "/selectTax", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Banner banner, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println(binding.toString());
			result = new ModelAndView();
			result.addObject("banner", banner);
			result.addObject("message", "manager.commit.error");
			Collection<Tax> taxs = taxService.findAll();
			result.addObject("taxs", taxs);
		} else {
			try {
				bannerService.saveFinal(banner);
				result = new ModelAndView("redirect:/banner/administrator/list.do");
			} catch (Throwable oops) {
				System.out.println(oops.getMessage());
				result = new ModelAndView();
				result.addObject("banner", banner);
				result.addObject("message", "manager.commit.error");
				Collection<Tax> taxs = taxService.findAll();
				result.addObject("taxs", taxs);
			}
		}

		return result;
	}

}
