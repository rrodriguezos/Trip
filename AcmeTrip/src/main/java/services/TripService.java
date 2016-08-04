package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripRepository;
import domain.Comment;
import domain.DailyPlan;
import domain.Trip;
import domain.User;

@Service
@Transactional
public class TripService {

	// Managed repository -------------------
	@Autowired
	private TripRepository tripRepository;

	// Supporting Services ------------------
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private DailyPlanService dailyPlanService;

	// COnstructors -------------------------
	public TripService() {
		super();
	}

	// Simple CRUD methods--------------------

	public Trip create() {
		Trip result;
		Collection<DailyPlan> dailyPlans;
		Collection<String> photos;
		Collection<User> users;

		Collection<Comment> comments;

		User user;
		
		result = new Trip();
		
		dailyPlans = new ArrayList<DailyPlan>();
		result.setDailyPlans(dailyPlans);
		
		photos = new ArrayList<String>();
		result.setPhotos(photos);
		
		users = new ArrayList<User>();
		result.setUsers(users);
		
		user = userService.findByPrincipal();
		result.setUser(user);
		
		comments = new ArrayList<Comment>();
		result.setComments(comments);

		return result;
	}

	public Collection<Trip> findAll() {
		Collection<Trip> result;

		result = tripRepository.findAll();

		return result;
	}

	public Trip findOne(int tripId) {
		Trip result;

		result = tripRepository.findOne(tripId);

		return result;
	}

	public void save(Trip trip) {
		Assert.notNull(trip);
		checkPrincipal(trip.getUser());
		
		Collection<DailyPlan> dailyPlans;
		
		if(trip.getId() != 0){
			String subject;
			String body;
			
			subject = "Modificación de la excursión " + trip.getTitle();
			body = "He hecho un cambio en mi excursión " + trip.getTitle();
		
			messageService.automaticMessage(trip, subject, body);
		}
		dailyPlans = tripRepository.getDailyPlansOutDates(trip.getId(), trip.getStartDate(), trip.getEndDate());
		
		for(DailyPlan d: dailyPlans){
			trip.getDailyPlans().remove(d);
			dailyPlanService.delete(d);
		}
		
		tripRepository.save(trip);
	}

	public void delete(Trip trip) {
		Assert.notNull(trip);
		checkPrincipal(trip.getUser());
		String subject;
		String body;

		subject = "Eliminación de la excursión " + trip.getTitle();
		body = "He eliminado mi excursión " + trip.getTitle();
		
		messageService.automaticMessage(trip, subject, body);
		
		tripRepository.delete(trip);
	}

	// Other Methods--------------------
	private void checkPrincipal(User u){
		User user;
	
		user = userService.findByPrincipal();
		Assert.isTrue(user != null);
		
		Assert.isTrue(user.equals(u));
	}
	
	public void copyTrip(Trip originalTrip){
		Trip result;
		
		result = create();
		result.setTitle("Copy of " + originalTrip.getTitle());
		result.setDescription(originalTrip.getDescription());
		result.setStartDate(originalTrip.getStartDate());
		result.setEndDate(originalTrip.getEndDate());
		result.setPhotos(originalTrip.getPhotos());
		
		save(result);
	}
	
	// Repository Methods--------------------

	public Collection<Trip> findTripByString(String key) {
		Collection<Trip> result;

		result = tripRepository.findTripByString(key);

		return result;
	}

	public Collection<Trip> findTripByUser(int userId) {
		Collection<Trip> result;

		result = tripRepository.findTripByUser(userId);

		return result;
	}

	public Collection<Trip> findTripByActivity(int activityId) {
		Collection<Trip> result;

		result = tripRepository.findTripByActivity(activityId);

		return result;
	}

	public Collection<Trip> tripByUserLogged() {
		Collection<Trip> result;
		User user;

		user = userService.findByPrincipal();

		result = tripRepository.findTripByUser(user.getId());

		return result;
	}
	
	
	public int findOverlappedByUser(Trip trip){
		int result;
		
		result = tripRepository.findOverlappedByUser(trip.getUser().getId(), trip.getStartDate(), trip.getEndDate());
		
		return result;
	}
	
	// Dashboard C2
	public Integer getNumberTripsRegistered(){
		Integer result;
		
		result = tripRepository.getNumberTripsRegistered();
		
		return result;
	}
	
	// Dashboard C4
	public Double[] getAverageNumberDailyPlansPerTrip(){
		Double[] result;
		
		result = tripRepository.getAverageNumberDailyPlansPerTrip();
		
		return result;
	}
} 

