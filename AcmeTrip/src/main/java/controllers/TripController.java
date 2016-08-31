package controllers;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Banner;
import domain.Comment;
import domain.Trip;
import domain.User;
import services.BannerService;
import services.CommentService;
import services.TripService;
import services.UserService;

@Controller
@RequestMapping("/trip")
public class TripController extends AbstractController {

	// Supporting services ----------------------------------------------------
	@Autowired
	private TripService tripService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@Autowired
	private BannerService bannerService;

	// Constructors -----------------------------------------------------------
	public TripController() {
		super();
	}

	// List -----------------------------------------------------------
	@RequestMapping(value = "/list")
	public ModelAndView list() {

		ModelAndView result;
		Collection<Trip> trips;

		trips = tripService.findAll();

		result = new ModelAndView("trip/list");
		result.addObject("trips", trips);
		result.addObject("requestUri", "/trip/list.do");

		return result;
	}

	// Display -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int tripId) {
		ModelAndView result;
		Trip trip;
		Collection<Comment> comments;
		User user;
		Boolean mytrip;
		Boolean joined;
		Boolean logeado;
		trip = tripService.findOne(tripId);
		mytrip = false;
		joined = false;
		logeado = false;

		try {
			user = userService.findByPrincipal();
			if (user != null) {
				logeado = true;
			}
			if (user.equals(trip.getUser())) {
				mytrip = true;
			}
			if (tripService.findAllTripsSuscrito(user.getId()).contains(trip)) {
				joined = true;
			}
		} catch (Throwable oops) {
			mytrip = false;
			joined = false;
			logeado = false;
		}

		comments = commentService.findCommentsByCommentableId(tripId);

		trip = tripService.findOne(tripId);
		comments = commentService.findCommentsByCommentableId(tripId);

		result = new ModelAndView("trip/display");
		result.addObject("trip", trip);

		result.addObject("mytrip", mytrip);
		result.addObject("joined", joined);
		result.addObject("logeado", logeado);

		result.addObject("comments", comments);

		// Empieza el banner
		Collection<Banner> todosBanners = bannerService.findAll();
		Collection<Banner> bannersActivos = new LinkedList<Banner>();
		for (Banner b : todosBanners) {
			if (b.getCampaign().getStartMoment()
					.before(new Date(System.currentTimeMillis()))
					|| b.getCampaign().getEndMoment()
							.after(new Date(System.currentTimeMillis()))) {
				bannersActivos.add(b);
			}
		}
		Boolean tieneBanner = false;
		Collection<Banner> bannersParaUsar = new LinkedList<Banner>();
		for (Banner c : bannersActivos) {
			if (c.getDisplay() < c.getMaxTimesDisplayed()) {
				for (String palabra : c.getKeyWords()) {
					Collection<Trip> tripis = tripService
							.findTripByKeyword(palabra);
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

	// Listing by navigate from DailyPlan
	// ---------------------------------------------------
	@RequestMapping(value = "/navigateByDailyPlan", method = RequestMethod.GET)
	public ModelAndView navigateByDailyPlan(@RequestParam int dailyPlanId) {
		ModelAndView result;
		Trip trip = tripService.tripByDailyplan(dailyPlanId);

		result = new ModelAndView("trip/listAll");
		result.addObject("trip", trip);
		result.addObject("requestURI", "trip/navigateByDailyPlan.do");
		return result;
	}
}
