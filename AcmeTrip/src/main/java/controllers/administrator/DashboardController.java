/* AdministratorController.java
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 * 
 */

package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import services.UserService;
import controllers.AbstractController;
import domain.User;

@Controller
@RequestMapping("dashboard/administrator")
public class DashboardController extends AbstractController {
	//Services -----------------------------------------------------
	@Autowired
	UserService userService;
	
	@Autowired
	TripService tripService;
	
	// Constructors -----------------------------------------------------------
	
	public DashboardController() {
		super();
	}
		
	// Dashboard-1 ---------------------------------------------------------------		

	@RequestMapping("/dashboard-1")
	public ModelAndView dashboard1() {
		ModelAndView result;
		Integer numberUsers, numberTripsRegistered;
		Collection<User> usersInactiveInLastYear;
		Collection<Object[]> usersCreated80MaximunNumbersOfTrips;
		Double[] averageNumberTripsPerUser, averageNumberDailyPlansPerTrip;
		
		numberUsers = userService.getNumberOfUsers();
		numberTripsRegistered = tripService.getNumberTripsRegistered();
		averageNumberTripsPerUser = userService.getAverageNumberTripsPerUser();
		averageNumberDailyPlansPerTrip = tripService.getAverageNumberDailyPlansPerTrip();
		usersCreated80MaximunNumbersOfTrips = userService.getUsersCreated80MaximunNumbersOfTrips();
		usersInactiveInLastYear = userService.getUsersInactiveInLastYear();
		
		result = new ModelAndView("administrator/dashboard-1");
		result.addObject("numberUsers", numberUsers);
		result.addObject("numberTripsRegistered", numberTripsRegistered);
		result.addObject("usersCreated80MaximunNumbersOfTrips", usersCreated80MaximunNumbersOfTrips);
		result.addObject("usersInactiveInLastYear", usersInactiveInLastYear);
		result.addObject("averageNumberTripsPerUser", averageNumberTripsPerUser);
		result.addObject("averageNumberDailyPlansPerTrip", averageNumberDailyPlansPerTrip);
		
		return result;
	}
}