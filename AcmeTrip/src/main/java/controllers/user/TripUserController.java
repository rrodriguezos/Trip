package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/trip/user")
public class TripUserController extends AbstractController{

	// Supporting services -------------------------------

		@Autowired
		private TripService tripService;
		
		@Autowired
		private UserService userService;
		
		
		
		// Constructors --------------------------------------

		public TripUserController() {
			super();
		}
		
		// Create --------------------------------------------

		@RequestMapping(value = "/create", method = RequestMethod.GET)
		public ModelAndView create() {
			ModelAndView result;
			Trip trip;

			trip = tripService.create();
		
			result = createEditModelAndView(trip);

			return result;
		}
		@RequestMapping(value="/edit",method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam int tripId){
			ModelAndView result;
			Trip trip;
			trip = tripService.findOne(tripId);
			
			result = createEditModelAndView(trip);
			result.addObject("trip", trip);
			return result;
		}
		
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid Trip trip, BindingResult binding, RedirectAttributes redir) {
			ModelAndView result;
			int checkOverlapping = 0;
			boolean checkDates = false;
			if(trip.getStartDate()!= null && trip.getEndDate() != null){
				if(trip.getId()==0){
					checkOverlapping = tripService.findOverlappedByUser(trip);
				}
				checkDates = trip.getStartDate().after(trip.getEndDate());
			}
			
			if (binding.hasErrors() || checkOverlapping!=0 || checkDates) {
				result = createEditModelAndView(trip);
				if(checkOverlapping!=0 || checkDates){
					result.addObject("message2","trip.overlapping.error");
				}
			} else {
				try {
					tripService.save(trip);
					result = new ModelAndView("redirect:/trip/mylist.do");
					result.addObject("requestUri", "/trip/mylist.do");
					redir.addFlashAttribute("message", "trip.commit.ok");

				} catch (Throwable oops) {
					result = createEditModelAndView(trip,
							"trip.commit.error");
				}
			}

			return result;
		}
		// List ----------------------------------------------

		@RequestMapping(value = "/mylist")
		public ModelAndView list() {

			ModelAndView result;
			Collection<Trip> trips;

			trips = tripService.tripByUserLogged();

			result = new ModelAndView("trip/user/mylist");
			result.addObject("trips", trips);
			result.addObject("requestUri", "/trip/user/mylist.do");

			return result;
		}
	
		
		
		@RequestMapping(value="/edit",method = RequestMethod.POST, params="delete")
		public ModelAndView delete(@Valid Trip trip, BindingResult binding){
			ModelAndView result;
			
			if(binding.hasErrors()){
				result = createEditModelAndView(trip,binding.toString());
			}else{
				try{
					tripService.delete(trip);
					result = new ModelAndView("redirect:/trip/list.do");
					result.addObject("requestUri", "/trip/list.do");
				}catch(Throwable oops){
					result = createEditModelAndView(trip,"trip.commit.error");
				}
			}
			return result;	
		}
		
		
		@RequestMapping(value = "/copyPaste", method = RequestMethod.GET)
		public ModelAndView copy(@RequestParam int tripId) {
			ModelAndView result;
			
			Trip trip = tripService.findOne(tripId);

			tripService.copyPasteTrip(trip);
			
			result = new ModelAndView("redirect:/trip/user/mylist.do");
			result.addObject("requestUri", "/trip/user/mylist.do");

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

			trip = tripService.findOne(tripId);
			tripService.DisjoinTrip(trip);
			trips = tripService.findAllTripsJoinUser();
			principal = userService.findByPrincipal();

			result = new ModelAndView("trip/list");

			result.addObject("trips", trips);
			result.addObject("principal", principal);
			result.addObject("requestUri", "trip/list.do");

			return result;

		}
		
		@RequestMapping(value = "/subscribed")
		public ModelAndView subscriptions() {

			ModelAndView result;
			Collection<Trip> trips;
			User user;

			user = userService.findByPrincipal();
			trips = tripService.tripsSubscribedByUser(user.getId());
			

			result = new ModelAndView("trip/list");
			result.addObject("trips", trips);
			result.addObject("requestUri", "/trip/user/subscribed.do");

			return result;
		}
		// Ancillary methods
				// --------------------------------------------------------

			protected ModelAndView createEditModelAndView(Trip trip) {
				ModelAndView result;

				result = createEditModelAndView(trip, null);

				return result;
			}

			protected ModelAndView createEditModelAndView(Trip trip, String message) {
				ModelAndView result;
				
				result = new ModelAndView("trip/user/edit");
				result.addObject("trip", trip);
				result.addObject("message2", message);
				
				return result;
			}


}
