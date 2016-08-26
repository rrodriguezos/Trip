package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BannerService;
import services.DailyPlanService;
import services.TripService;
import services.UserService;
import domain.Banner;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;

@Controller
@RequestMapping("/dailyPlan")
public class DailyPlanController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private DailyPlanService dailyPlanService;
	@Autowired
	private TripService tripService;
	@Autowired
	private UserService userService;
	@Autowired
	private BannerService bannerService;

	// Constructors -----------------------------------------------------------
	public DailyPlanController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list(@RequestParam int tripId) {

		ModelAndView result;
		Collection<DailyPlan> dailyPlans;
		Trip trip;
		User user;
		Boolean mytrip;
		dailyPlans = dailyPlanService.dailyPlansByTrip(tripId);
		result = new ModelAndView("dailyPlan/list");
		result.addObject("dailyPlans", dailyPlans);
		result.addObject("tripId", tripId);
		mytrip = false;
		try {
			user = userService.findByPrincipal();
			trip = tripService.findOne(tripId);

			mytrip = user.equals(trip.getUser());

		} catch (Throwable oops) {

		}
		result.addObject("mytrip", mytrip);
		// Empieza el banner
		Collection<Banner> todosBanners = bannerService.findAll();
		Collection<Banner> bannersActivos = new LinkedList<Banner>();
		for (Banner b : todosBanners) {
			if (b.getCampaign().getStartMoment().before(new Date(System.currentTimeMillis()))
					|| b.getCampaign().getEndMoment().after(new Date(System.currentTimeMillis()))) {
				bannersActivos.add(b);
			}
		}
		Boolean tieneBanner = false;
		Collection<Banner> bannersParaUsar = new LinkedList<Banner>();
		for (Banner c : bannersActivos) {
			if (c.getDisplay() < c.getMaxTimesDisplayed()) {
				for (String palabra : c.getKeyWords()) {
					Collection<Trip> tripis = tripService.findTripByKeyword(palabra);
					if (!tripis.isEmpty()) {
						tieneBanner = true;
						if (!bannersParaUsar.contains(c))
							bannersParaUsar.add(c);
					}
				}
			}
		}
		if (tieneBanner) {
			Integer a = bannersParaUsar.size();
			double random = Math.random();
			long ra = Math.round(a * random);
			Banner banner = new Banner();
			int contado = 1;
			for (Banner ban : bannersParaUsar) {
				if (contado == ra) {
					banner = ban;
				}
				contado++;
			}
			if (banner.getId() == 0) {
				banner = bannersParaUsar.iterator().next();
			}
			if (banner.getId() != 0) {
				bannerService.aumentaVisita(banner);
				result.addObject("tieneBanner", tieneBanner);
				result.addObject("banner", banner);
			}
		}
		// Acaba el banner

		return result;
	}
	
	// Listing by navigate from Slot ---------------
		@RequestMapping(value = "/navigateBySlot", method = RequestMethod.GET)
		public ModelAndView navigateBySlot(@RequestParam int slotId) {
			ModelAndView result;
			DailyPlan dailyplan = dailyPlanService.dailyPlanBySlot(slotId);
			result = new ModelAndView("dailyPlan/listAll");
			result.addObject("dailyplan", dailyplan);
			result.addObject("requestURI", "dailyPlan/navigateBySlot.do");
			
			// Empieza el banner
			Collection<Banner> todosBanners = bannerService.findAll();
			Collection<Banner> bannersActivos = new LinkedList<Banner>();
			for (Banner b : todosBanners) {
				if (b.getCampaign().getStartMoment().before(new Date(System.currentTimeMillis()))
						|| b.getCampaign().getEndMoment().after(new Date(System.currentTimeMillis()))) {
					bannersActivos.add(b);
				}
			}
			Boolean tieneBanner = false;
			Collection<Banner> bannersParaUsar = new LinkedList<Banner>();
			for (Banner c : bannersActivos) {
				if (c.getDisplay() < c.getMaxTimesDisplayed()) {
					for (String palabra : c.getKeyWords()) {
						Collection<Trip> tripis = tripService.findTripByKeyword(palabra);
						if (!tripis.isEmpty()) {
							tieneBanner = true;
							if (!bannersParaUsar.contains(c))
								bannersParaUsar.add(c);
						}
					}
				}
			}
			if (tieneBanner) {
				Integer a = bannersParaUsar.size();
				double random = Math.random();
				long ra = Math.round(a * random);
				Banner banner = new Banner();
				int contado = 1;
				for (Banner ban : bannersParaUsar) {
					if (contado == ra) {
						banner = ban;
					}
					contado++;
				}
				if (banner.getId() == 0) {
					banner = bannersParaUsar.iterator().next();
				}
				if (banner.getId() != 0) {
					bannerService.aumentaVisita(banner);
					result.addObject("tieneBanner", tieneBanner);
					result.addObject("banner", banner);
				}
			}
			// Acaba el banner
			return result;
		}
		
		// Display --------------------------------------------------------
				@RequestMapping(value="/display", method=RequestMethod.GET)
				public ModelAndView display(int dailyPlanId){
					ModelAndView result;
					DailyPlan dailyPlan;
					
					dailyPlan = dailyPlanService.findOne(dailyPlanId);
					
					result = new ModelAndView("dailyPlan/display");
					result.addObject("dailyPlan", dailyPlan);
					// Empieza el banner
					Collection<Banner> todosBanners = bannerService.findAll();
					Collection<Banner> bannersActivos = new LinkedList<Banner>();
					for (Banner b : todosBanners) {
						if (b.getCampaign().getStartMoment().before(new Date(System.currentTimeMillis()))
								|| b.getCampaign().getEndMoment().after(new Date(System.currentTimeMillis()))) {
							bannersActivos.add(b);
						}
					}
					Boolean tieneBanner = false;
					Collection<Banner> bannersParaUsar = new LinkedList<Banner>();
					for (Banner c : bannersActivos) {
						if (c.getDisplay() < c.getMaxTimesDisplayed()) {
							for (String palabra : c.getKeyWords()) {
								Collection<Trip> tripis = tripService.findTripByKeyword(palabra);
								if (!tripis.isEmpty()) {
									tieneBanner = true;
									if (!bannersParaUsar.contains(c))
										bannersParaUsar.add(c);
								}
							}
						}
					}
					if (tieneBanner) {
						Integer a = bannersParaUsar.size();
						double random = Math.random();
						long ra = Math.round(a * random);
						Banner banner = new Banner();
						int contado = 1;
						for (Banner ban : bannersParaUsar) {
							if (contado == ra) {
								banner = ban;
							}
							contado++;
						}
						if (banner.getId() == 0) {
							banner = bannersParaUsar.iterator().next();
						}
						if (banner.getId() != 0) {
							bannerService.aumentaVisita(banner);
							result.addObject("tieneBanner", tieneBanner);
							result.addObject("banner", banner);
						}
					}
					// Acaba el banner
					
					return result;
				}

}
