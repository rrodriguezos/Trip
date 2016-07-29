package controllers.User;

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

import services.TripService;
import services.UserService;
import controllers.AbstractController;
import domain.Trip;
import domain.User;

@Controller
@RequestMapping("/trip/user/")
public class TripUserController extends AbstractController {
	
	//Services -----------------------------
		@Autowired
		private TripService tripService;	

		@Autowired
		private UserService userService;
		
		//Constructor --------------------------
		public TripUserController() {
			super();
		}
		
		// List--------------------------------------------------------------

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView list() {

			ModelAndView result;
			boolean showDisjoin = true;

			Collection<Trip> trips = tripService.findAll();
			User user = userService.findByPrincipal();
	
			trips = tripService.findTripsByUser(user.getId());
			
			result = new ModelAndView("trip/list");
			result.addObject("trips", trips);
			result.addObject("user", user);
			result.addObject("showDisjoin", showDisjoin);
			result.addObject("actor", "/user");
			result.addObject("userId", user.getId());
			result.addObject("userTrips", user.getTrips());
			result.addObject("requestURI", "trip/user/list.do");
			
			

			return result;

		}
		
		//Creation ---------------------------------------------------------------------
		@RequestMapping(value="/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Trip trip;
			trip = tripService.create();
			result = createEditModelAndView(trip, null);
			result.addObject("user", userService.findByPrincipal());
			return result;
		}
		
		//Edition -----------------------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam Integer tripId) {
			Trip trip = tripService.findOne(tripId);
			Assert.notNull(trip);
			User user = userService.findByPrincipal();
			if (user.equals(tripService.findOne(tripId).getUser())) {
				trip = tripService.findOne(tripId);
			} else {
				throw new IllegalArgumentException("NotPrincipal");
			}
			return createEditModelAndView(trip,null);


		}

		//Save -----------------------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Trip trip, BindingResult binding,
				 RedirectAttributes redirectAttrs) {
			ModelAndView result;	
			if (binding.hasErrors()) {
				System.out.print(binding.hasErrors());
				System.out.print(binding.toString());
				result = createEditModelAndView(trip, "trip.commit.error");
			} else {
				try {
					Trip fijo = tripService.save(trip);
					result = new ModelAndView("redirect:/dailyplan/user/create.do?tripId="+fijo.getId());
				} catch (Throwable oops) {	
					result = createEditModelAndView(trip, "trip.commit.error");

				}
			}

			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "saveEdit")
		public ModelAndView saveEdit(@Valid Trip trip, BindingResult binding,
				 RedirectAttributes redirectAttrs) {
			ModelAndView result;	

			if (binding.hasErrors()) {
				System.out.print(binding.hasErrors());
				System.out.print(binding.toString());
				result = createEditModelAndView(trip, "trip.commit.error");
			} else {
				try {
					tripService.save(trip);
					redirectAttrs.addFlashAttribute("message", "trip.commit.ok");	
					result = new ModelAndView("redirect:/trip/user/list.do");
					result.addObject("userId", userService.findByPrincipal().getId());
					result.addObject("user", userService.findByPrincipal());
				} catch (Throwable oops) {	
					result = createEditModelAndView(trip, "trip.commit.error");

				}
			}

			return result;
		}
		//Delete -----------------------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
		public ModelAndView delete(Trip trip, BindingResult binding,
				 RedirectAttributes redirectAttrs) {
			ModelAndView result;

			try {			
				tripService.delete(trip);		
				redirectAttrs.addFlashAttribute("message", "trip.commit.ok");	
				result = new ModelAndView("redirect:/trip/user/list.do");		
			} catch (Throwable oops) {
				result = createEditModelAndView(trip, "trip.delete.error");
			}
			return result;
		}
		
		// Join a
		// Trip------------------------------------------------------------------
		@RequestMapping(value = "/joinTrip", method = RequestMethod.GET)
		public ModelAndView joinTrip(@RequestParam int tripId) {

			ModelAndView result;
			Trip trip;
			Collection<Trip> trips;


			trip = tripService.findOne(tripId);
			tripService.joinTrip(trip);
			trips = tripService.findAllTripsJoinUser();

			result = new ModelAndView("trip/list");

			result.addObject("trips", trips);
			result.addObject("requestURI", "trip/user/list.do");

			return result;

		}

		// DisJoin a
		// Event------------------------------------------------------------------
		@RequestMapping(value = "/disjoinTrip", method = RequestMethod.GET)
		public ModelAndView DisjoinEvent(@RequestParam int tripId) {

			ModelAndView result;
			Trip trip;
			Collection<Trip> trips;
			User principal;
			Boolean showdisjoin = true;
			boolean my = true;
			

			trip = tripService.findOne(tripId);
			tripService.DisjoinTrip(trip);
			trips = tripService.findAllTripsJoinUser();
			principal = userService.findByPrincipal();

			result = new ModelAndView("trip/list");

			result.addObject("trips", trips);
			result.addObject("my", my);
			result.addObject("principal", principal);
			result.addObject("showdisjoin", showdisjoin);
			result.addObject("requestURI", "trip/user/list.do");
			

			return result;

		}
		
		
		//Ancillary methods -------------------------------------------------
		
		protected ModelAndView createEditModelAndView(Trip trip, String message) {
			ModelAndView result;		

					
			result = new ModelAndView("trip/user/edit");
			result.addObject("trip", trip);
			result.addObject("message", message);

			
			return result;
		}

}
