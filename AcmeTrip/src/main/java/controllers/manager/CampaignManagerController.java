package controllers.manager;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

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
import domain.Banner;
import domain.Campaign;
import domain.CreditCard;
import domain.Manager;
import forms.BannerForm;
import services.BannerService;
import services.CampaignService;
import services.ManagerService;

@Controller
@RequestMapping("/campaign/manager")
public class CampaignManagerController extends AbstractController {

	// Supporting services -------------------------------

	@Autowired
	private CampaignService campaignService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private BannerService bannerService;

	// Constructors --------------------------------------

	public CampaignManagerController() {
		super();
	}

	// Create --------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Campaign> campaigns;

		campaigns = campaignService.findAllFromPrincipal();

		result = new ModelAndView();
		result.addObject("campaigns", campaigns);

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int campaignId) {
		ModelAndView result;
		Campaign campaign;

		campaign = campaignService.findOne(campaignId);
		Boolean edita = campaign.getStartMoment().after(new Date(System.currentTimeMillis()));
		result = new ModelAndView();
		result.addObject("campaign", campaign);
		result.addObject("edita", edita);
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int campaignId) {
		ModelAndView result;
		Campaign campaign = campaignService.findOne(campaignId);
		Boolean enFecha = true;
		if (campaign.getStartMoment().after(new Date(System.currentTimeMillis()))) {
			enFecha = true;
		}
		if (!enFecha) {
			Boolean edita = campaign.getStartMoment().after(new Date(System.currentTimeMillis()));
			result = new ModelAndView("campaign/manager/display.do?campaignId" + campaignId);
			result.addObject("campaign", campaign);
			result.addObject("edita", edita);
			if (!enFecha) {
				result.addObject("message", "trip.commit.error");
			}
		} else {
			try {
				campaignService.delete(campaign);
				result = new ModelAndView("redirect:/campaign/manager/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(campaign, "trip.commit.error");
			}
		}
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Campaign campaign;

		campaign = campaignService.create();

		result = createEditModelAndView(campaign);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int campaignId) {
		ModelAndView result;
		Campaign campaign;
		campaign = campaignService.findOne(campaignId);
		result = createEditModelAndView(campaign);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Campaign campaign, BindingResult binding, RedirectAttributes redir) {
		ModelAndView result;
		boolean checkDates = false;
		if (campaign.getStartMoment() != null && campaign.getEndMoment() != null) {
			checkDates = campaign.getStartMoment().after(campaign.getEndMoment());
		}

		if (binding.hasErrors() || checkDates) {
			result = createEditModelAndView(campaign);
			if (checkDates) {
				result.addObject("message", "campaign.dates.error");
			}
		} else {
			try {
				Campaign nueva = campaignService.save(campaign);
				result = new ModelAndView("redirect:/campaign/manager/tarjetas.do?campaignId=" + nueva.getId());
				redir.addFlashAttribute("message", "trip.commit.ok");

			} catch (Throwable oops) {
				result = createEditModelAndView(campaign, "trip.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/tarjetas", method = RequestMethod.GET)
	public ModelAndView tarjetas(@RequestParam int campaignId) {
		ModelAndView result;
		Assert.notNull(campaignId);
		result = new ModelAndView("campaign/manager/tarjetas");
		result.addObject("campaignId", campaignId);
		Boolean tieneTarjeta = false;
		Manager principal = managerService.findByPrincipal();
		if (!principal.getCreditCards().isEmpty()) {
			for (CreditCard c : principal.getCreditCards()) {
				if(c.getCampaign()==null)
					tieneTarjeta = true;
			}
		}
		result.addObject("tieneTarjetas", tieneTarjeta);
		return result;
	}

	@RequestMapping(value = "/newCreditCard", method = RequestMethod.GET)
	public ModelAndView nuevaTarjeta(@RequestParam int campaignId) {
		ModelAndView result;
		Assert.notNull(campaignId);
		result = new ModelAndView("campaign/manager/newCreditCard");
		Campaign campaign = campaignService.findOne(campaignId);
		campaign.setCreditCard(null);
		result.addObject("campaign", campaign);
		return result;
	}

	@RequestMapping(value = "/newCreditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView saveNewCc(@Valid Campaign campaign, BindingResult binding, RedirectAttributes redir) {
		ModelAndView result;
		Assert.notNull(campaign);
		if (binding.hasErrors()) {
			result = new ModelAndView("campaign/manager/newCreditCard?campaignId=" + campaign.getId());
			result.addObject("campaign", campaign);
			result.addObject("message2", "campaign.creditExist.error");
		} else {
			try {
				managerService.anadeTarjeta(campaign.getCreditCard(), campaign);
				result = new ModelAndView("redirect:/campaign/manager/createBanner.do?campaignId=" + campaign.getId());
				redir.addFlashAttribute("message", "trip.commit.ok");

			} catch (Throwable oops) {
				System.out.println(oops.getLocalizedMessage());
				result = new ModelAndView("campaign/manager/newCreditCard");
				result.addObject("campaign", campaign);
				result.addObject("message2", "campaign.creditExist.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/existingCreditCard", method = RequestMethod.GET)
	public ModelAndView tarjetaExistente(@RequestParam int campaignId) {
		ModelAndView result;
		Assert.notNull(campaignId);
		Manager principal = managerService.findByPrincipal();
		Collection<CreditCard> tarjetasRegistradas = principal.getCreditCards();
		Collection<CreditCard> tarjetasPaUsar = new LinkedList<CreditCard>();
		for (CreditCard c : tarjetasRegistradas) {
			if(c.getCampaign()==null)
				tarjetasPaUsar.add(c);
		}
		result = new ModelAndView("campaign/manager/existingCreditCard");
		Campaign campaign = campaignService.findOne(campaignId);
		result.addObject("campaign", campaign);
		result.addObject("tarjetasPaUsar", tarjetasPaUsar);

		return result;
	}

	@RequestMapping(value = "/existingCreditCard", method = RequestMethod.POST, params = "save")
	public ModelAndView saveExis(@Valid Campaign campaign, BindingResult binding, RedirectAttributes redir) {
		ModelAndView result;
		if (binding.hasErrors()) {
			Manager principal = managerService.findByPrincipal();
			Collection<CreditCard> tarjetasRegistradas = principal.getCreditCards();
			Collection<CreditCard> tarjetasPaUsar = new LinkedList<CreditCard>();
			for (CreditCard c : tarjetasRegistradas) {
				if(c.getCampaign()==null)
					tarjetasPaUsar.add(c);
			}
			result = new ModelAndView("campaign/manager/existingCreditCard");
			result.addObject("tarjetasPaUsar", tarjetasPaUsar);
			result.addObject("message2", "campaign.creditExist.error");
		} else {
			try {
				campaignService.anadeTarjeta(campaign);
				result = new ModelAndView("redirect:/campaign/manager/createBanner.do?campaignId=" + campaign.getId());
				redir.addFlashAttribute("message", "trip.commit.ok");

			} catch (Throwable oops) {
				Manager principal = managerService.findByPrincipal();
				Collection<CreditCard> tarjetasRegistradas = principal.getCreditCards();
				result = new ModelAndView("campaign/manager/existingCreditCard");
				Collection<CreditCard> tarjetasPaUsar = new LinkedList<CreditCard>();
				for (CreditCard c : tarjetasRegistradas) {
					if(c.getCampaign()==null)
						tarjetasPaUsar.add(c);
				}
				result.addObject("tarjetasPaUsar", tarjetasPaUsar);
				result.addObject("message2", "campaign.creditExist.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
	public ModelAndView saveEdit(@Valid Campaign campaign, BindingResult binding, RedirectAttributes redir) {
		ModelAndView result;
		boolean checkDates = false;
		if (campaign.getStartMoment() != null && campaign.getEndMoment() != null) {
			checkDates = campaign.getStartMoment().after(campaign.getEndMoment());
		}

		if (binding.hasErrors() || checkDates) {
			result = createEditModelAndView(campaign);
			if (checkDates) {
				result.addObject("message2", "campaign.dates.error");
			}
		} else {
			try {
				campaignService.saveEdit(campaign);
				result = new ModelAndView("redirect:/campaign/manager/list.do");
				redir.addFlashAttribute("message", "trip.commit.ok");

			} catch (Throwable oops) {
				result = createEditModelAndView(campaign, "trip.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/createBanner", method = RequestMethod.GET)
	public ModelAndView createBanner(@RequestParam int campaignId) {
		ModelAndView result;
		Assert.notNull(campaignId);
		result = new ModelAndView("campaign/manager/createBanner");
		BannerForm bannerForm = new BannerForm();
		bannerForm.setCampId(campaignId);
		result.addObject("bannerForm", bannerForm);

		return result;
	}

	@RequestMapping(value = "/createBanner", method = RequestMethod.POST, params = "save")
	public ModelAndView saveBanner(@Valid BannerForm bannerForm, BindingResult binding, RedirectAttributes redir) {
		ModelAndView result;
		if (binding.hasErrors()) {
			result = new ModelAndView();
			result.addObject("bannerForm", bannerForm);
			result.addObject("message", "campaign.banner.error");
		} else {
			try {
				Banner banner = bannerService.formToBanner(bannerForm);
				bannerService.save(banner);
				result = new ModelAndView("redirect:/campaign/manager/list.do");
				redir.addFlashAttribute("message", "trip.commit.ok");

			} catch (Throwable oops) {
				result = new ModelAndView();
				result.addObject("bannerForm", bannerForm);
				result.addObject("message", "trip.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	// --------------------------------------------------------

	protected ModelAndView createEditModelAndView(Campaign campaign) {
		ModelAndView result;

		result = createEditModelAndView(campaign, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Campaign campaign, String message) {
		ModelAndView result;

		result = new ModelAndView("campaign/manager/edit");
		result.addObject("campaign", campaign);
		result.addObject("message", message);

		return result;
	}

}
