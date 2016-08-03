package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TripRepository;
import security.Authority;
import security.LoginService;
import utilities.DPMessage;
import utilities.DPUtils;
import domain.Activity;
import domain.ActivityType;
import domain.Administrator;
import domain.Comment;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;

@org.springframework.stereotype.Service
@Transactional
public class TripService {

	// Managed Repository ------------------------------
	@Autowired
	private TripRepository tripRepository;

	// Supporting Services -----------------------------
	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private DailyPlanService dailyPlanService;

	@Autowired
	private ActivityTypeService activityTypeService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private MessageService messageService;

	// Constructors ------------------------------------
	public TripService() {
		super();
	}

	// Simple CRUD Methods -----------------------------

	public Trip create() {
		Trip result;
		Collection<DailyPlan> dailyPlans;
		Collection<String> photos;
		Collection<User> users;
		Collection<Comment> comments;
		User user;

		result = new Trip();

		dailyPlans = new ArrayList<DailyPlan>();
		result.setDailyplans(dailyPlans);

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

	public Trip save(Trip trip) {
		if(trip.getUser().getId()!= userService.findByPrincipal().getId()|| trip.getId() != 0 ){
			
			return tripRepository.save(trip);
			
		}else{	
		Collection<DailyPlan> dailyplans = tripRepository
				.getDailyplansWrongsDates(trip.getId(), trip.getStartDate(),
						trip.getEndDate());
		for(DailyPlan d: dailyplans){
			trip.getDailyplans().remove(d);
			dailyPlanService.delete(d);
		}

		return tripRepository.save(trip);
	}
	}

	public void delete(Trip trip) {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		String sEspañol;
		String bEspañol;

		sEspañol = "Elimiación del viaje  " + trip.getTitle();
		bEspañol = "He elimiado mi viaje " + trip.getTitle();

		messageService.broadcastAlertTripMessage(trip, sEspañol,bEspañol);
		
		String sEnglish;
		String bEnglish;

		sEnglish = "Deleting of trip " + trip.getTitle();
		bEnglish = "I have deleted my trip " + trip.getTitle();

		messageService.broadcastAlertTripMessage(trip, sEnglish,bEnglish);
		
		for (Comment tripComment: trip.getComments()) {
			commentService.delete(tripComment);
		}
		for (DailyPlan a: trip.getDailyplans()) {
			dailyPlanService.delete(a);
		}
	

		trip.getDailyplans().removeAll(trip.getDailyplans());
		trip.getUsers().removeAll(trip.getUsers());
		trip.getComments().removeAll(trip.getComments());
		tripRepository.delete(trip);
	}

	public Collection<Trip> findAll() {
		Collection<Trip> result = tripRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Trip findOne(int id) {
		return tripRepository.findOne(id);
	}

	public Collection<Trip> findByPrincipal() {
		return tripRepository.findByUserAccountID(LoginService.getPrincipal()
				.getId());
	}

	public void copyPasteTrip(Trip originTrip) {
		Trip result;

		result = create();
		result.setTitle("Copy of " + originTrip.getTitle());
		result.setDescription(originTrip.getDescription());
		result.setStartDate(originTrip.getStartDate());
		result.setEndDate(originTrip.getEndDate());
		result.setPhotos(originTrip.getPhotos());

		save(result);
	}

	// Other business methods -----------------------

	private void checkPrincipal(User u) {
		User user;

		user = userService.findByPrincipal();
		Assert.isTrue(user != null);

		Assert.isTrue(user.equals(u));
	}

	public Double standardDeviationOfTripsByUsers() {
		return tripRepository.standardDeviationOfTripsByUser();
	}

	public Double averageNumberOfTripsByUsers() {
		return tripRepository.averageNumberOfTripsByUser();
	}

	public Collection<Trip> findTripsByUser(int userId) {
		User user = userService.findOne(userId);
		Collection<Trip> result = tripRepository.findTripsByUser(user.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<Trip> findAllTripsByUserId(int userId) {
		Collection<Trip> all;
		all = tripRepository.findAllTripsByUserId(userId);

		return all;
	}

	public int totalNumberOfTripsRegistered() {
		Administrator admin = administratorService.findByPrincipal();
		Assert.notNull(admin);
		int result = findAll().size();
		return result;
	}

	public Trip tripByDailyplan(int dailyPlanId) {

		return tripRepository.tripByDailyplan(dailyPlanId);

	}
	public Collection<Trip> findTripsByActivity(int activityId) {
		Collection<Trip> result;

		result = tripRepository.findTripsByActivity(activityId);

		return result;
	}


	public Collection<Trip> tripsByActivityType(int activityTypeId) {
		Collection<Trip> trips = null;
		Collection<Slot> slots = null;
		Collection<DailyPlan> dailyPlans = null;

		ActivityType atype = activityTypeService.findOne(activityTypeId);

		Collection<Activity> activities = atype.getActivities();

		for (Activity acts : activities) {
			slots.addAll(acts.getSlots());

		}
		for (Slot slt : slots) {
			dailyPlans.add(slt.getDailyplan());

		}
		for (DailyPlan dplan : dailyPlans) {
			trips.add(dplan.getTrip());

		}
		return trips;

	}

	public int findOverlapByUser(Trip trip) {
		int result;

		result = tripRepository.findOverlapByUser(trip.getUser().getId(),
				trip.getStartDate(), trip.getEndDate());

		return result;
	}

	public Collection<Trip> searchByKeyword(String keyword) {

		return tripRepository.searchByKeyword(keyword);
	}

	public void checkPrincipalByJoinedUser(Trip trip) {

		User user;

		user = userService.findByPrincipal();

		Assert.isTrue(trip.getUsers().contains(user));

	}

	public void joinTrip(Trip trip) {
		
		if(trip.getUser().getId()!= userService.findByPrincipal().getId()|| trip.getId() != 0 ){
			String subjectEspañol;
			String bodyEspañol;

			subjectEspañol = "Nuevo usuario unido al viaje" + trip.getTitle();
			bodyEspañol =  "User: " + userService.findByPrincipal().getName()+" acaba de unirse al viaje " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEspañol, bodyEspañol);
			
			String subjectEnglish;
			String bodyEnglish;

			subjectEnglish = "New user join to trip " + trip.getTitle();
			bodyEnglish =  "User: " + userService.findByPrincipal().getName()+" just join the trip " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEnglish, bodyEnglish);
		}

		User user;

		user = userService.findByPrincipal();

		trip.getUsers().add(user);
		user.getTripSubscribes().add(trip);

		save(trip);
		userService.save(user);

	}

	public void DisjoinTrip(Trip trip) {
		
		if(trip.getUser().getId()!= userService.findByPrincipal().getId()|| trip.getId() != 0 ){
			String subjectEspañol;
			String bodyEspañol;

			subjectEspañol = "Un usuario se borró del viaje " + trip.getTitle();
			bodyEspañol =  "Usuario: " + userService.findByPrincipal().getName()+" acaba de borrarse del viaje " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEspañol, bodyEspañol);
			
			String subjectEnglish;
			String bodyEnglish;

			subjectEnglish = "A user disjoin from the trip " + trip.getTitle();
			bodyEnglish =  "User: " + userService.findByPrincipal().getName()+" just disjoin from the trip " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEnglish, bodyEnglish);
		}

		User user;

		checkPrincipalByJoinedUser(trip);

		user = userService.findByPrincipal();

		trip.getUsers().remove(user);
		user.getTripSubscribes().remove(trip);

		save(trip);
		userService.save(user);

	}

	public Collection<Trip> findAllTripsCreatedByUserId() {

		Collection<Trip> all;
		User user;
		int userId;

		user = userService.findByPrincipal();
		userId = user.getId();
		all = tripRepository.findAllTripsCreatedByUserId(userId);

		return all;

	}

	public Collection<Trip> findAllTripsSuscrito(int userId) {
		Collection<Trip> result;

		result = tripRepository.findAllTripsSubscrito(userId);

		return result;
	}

	public Collection<Trip> findAllTripsJoinUser() {
		Collection<Trip> all;
		Collection<Trip> createdByUser;
		Collection<Trip> myTrips;
		User user;

		all = findAll();
		user = userService.findByPrincipal();
		myTrips = new ArrayList<Trip>();
		createdByUser = tripRepository
				.findAllTripsCreatedByUserId(user.getId());

		for (Trip itero : all) {
			if (itero.getUsers().contains(user)) {
				myTrips.add(itero);
			}
		}

		for (Trip itero : createdByUser) {
			if (!myTrips.contains(itero)) {
				myTrips.add(itero);
			}
		}

		return myTrips;
	}

	public void saveAguasArriba(Trip barquito2) {
		tripRepository.saveAndFlush(barquito2);
	}

}
