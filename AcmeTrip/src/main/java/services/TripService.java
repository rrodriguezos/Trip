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
		
		Collection<DailyPlan> dailyPlans;

		if (trip.getId() != 0) {
			String subject;
			String body;

			subject = "Modificación del viaje " + trip.getTitle();
			body = "He hecho un cambio en mi viaje " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subject, body);
		}
		dailyPlans = tripRepository.getDailyPlansOutDates(trip.getId(),
				trip.getStartDate(), trip.getEndDate());

		for (DailyPlan d : dailyPlans) {
			trip.getDailyPlans().remove(d);
			dailyPlanService.delete(d);
		}

		tripRepository.saveAndFlush(trip);
	}

	public void delete(Trip trip) {
		Assert.notNull(trip);
		checkPrincipal(trip.getUser());
		String subject;
		String body;

		subject = "Eliminación de la excursión " + trip.getTitle();
		body = "He eliminado mi excursión " + trip.getTitle();

		messageService.broadcastAlertTripMessage(trip, subject, body);

		tripRepository.delete(trip);
	}

	// Other Methods--------------------
	private void checkPrincipal(User u) {
		User user;

		user = userService.findByPrincipal();
		Assert.isTrue(user != null);

		Assert.isTrue(user.equals(u));
	}

	public void copyPasteTrip(Trip previousTrip) {
		Trip result;

		result = create();
		result.setTitle("Copy of " + previousTrip.getTitle());
		result.setDescription(previousTrip.getDescription());
		result.setStartDate(previousTrip.getStartDate());
		result.setEndDate(previousTrip.getEndDate());
		result.setPhotos(previousTrip.getPhotos());

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

	public int findOverlappedByUser(Trip trip) {
		int result;

		result = tripRepository.findOverlappedByUser(trip.getUser().getId(),
				trip.getStartDate(), trip.getEndDate());

		return result;
	}

	// Dashboard C2
	public Integer getNumberTripsRegistered() {
		Integer result;

		result = tripRepository.getNumberTripsRegistered();

		return result;
	}

	// Dashboard C4
	public Double[] getAverageNumberDailyPlansPerTrip() {
		Double[] result;

		result = tripRepository.getAverageNumberDailyPlansPerTrip();

		return result;
	}

	public Collection<Trip> findAllTripsSuscrito(int userId) {
		Collection<Trip> result;

		result = tripRepository.findAllTripsSubscrito(userId);

		return result;
	}

	public void joinTrip(Trip trip) {

		if (trip.getUser().getId() != userService.findByPrincipal().getId()
				|| trip.getId() != 0) {
			String subjectEspañol;
			String bodyEspañol;

			subjectEspañol = "Nuevo usuario unido al viaje" + trip.getTitle();
			bodyEspañol = "User: " + userService.findByPrincipal().getName()
					+ " acaba de unirse al viaje " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEspañol,
					bodyEspañol);

			String subjectEnglish;
			String bodyEnglish;

			subjectEnglish = "New user join to trip " + trip.getTitle();
			bodyEnglish = "User: " + userService.findByPrincipal().getName()
					+ " just join the trip " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEnglish,
					bodyEnglish);
		}

		User user;

		user = userService.findByPrincipal();

		trip.getUsers().add(user);
		user.getTripSubscribes().add(trip);

		save(trip);
		userService.save(user);

	}

	public void DisjoinTrip(Trip trip) {

		if (trip.getUser().getId() != userService.findByPrincipal().getId()
				|| trip.getId() != 0) {
			String subjectEspañol;
			String bodyEspañol;

			subjectEspañol = "Un usuario se borró del viaje " + trip.getTitle();
			bodyEspañol = "Usuario: " + userService.findByPrincipal().getName()
					+ " acaba de borrarse del viaje " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEspañol,
					bodyEspañol);

			String subjectEnglish;
			String bodyEnglish;

			subjectEnglish = "A user disjoin from the trip " + trip.getTitle();
			bodyEnglish = "User: " + userService.findByPrincipal().getName()
					+ " just disjoin from the trip " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEnglish,
					bodyEnglish);
		}

		User user;

		checkPrincipalByJoinedUser(trip);

		user = userService.findByPrincipal();

		trip.getUsers().remove(user);
		user.getTripSubscribes().remove(trip);

		save(trip);
		userService.save(user);

	}

	public void checkPrincipalByJoinedUser(Trip trip) {

		User user;

		user = userService.findByPrincipal();

		Assert.isTrue(trip.getUsers().contains(user));

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
}
