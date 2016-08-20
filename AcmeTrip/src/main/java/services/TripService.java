package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Comment;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;
import repositories.SlotRepository;
import repositories.TripRepository;

@Service
@Transactional
public class TripService {

	// Managed repository -------------------
	@Autowired
	private TripRepository tripRepository;

	@Autowired
	private SlotRepository slotRepository;

	// Supporting Services ------------------
	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private DailyPlanService dailyPlanService;

	@Autowired
	private AdministratorService administratorService;

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
			String subjectEspanol;
			String bodyEspanol;

			String subjectEnglish;
			String bodyEnglish;

			subjectEspanol = "Modificación del viaje " + trip.getTitle();
			bodyEspanol = "He hecho un cambio en mi viaje " + trip.getTitle();

			subjectEnglish = "Edition of the trip " + trip.getTitle();
			bodyEnglish = "I have made a change in my trip " + trip.getTitle();

			messageService.broadcastAlertTripMessage(trip, subjectEnglish,
					bodyEnglish);
			messageService.broadcastAlertTripMessage(trip, subjectEspanol,
					bodyEspanol);
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
		String subjectEspanol;
		String bodyEspanol;
		String subjectEnglish;
		String bodyEnglish;

		subjectEspanol = "Eliminación del viaje " + trip.getTitle();
		bodyEspanol = "He eliminado mi viaje " + trip.getTitle();

		subjectEnglish = "Elimination of the trip  " + trip.getTitle();
		bodyEnglish = "I have deleted my trip  " + trip.getTitle();

		messageService.broadcastAlertTripMessage(trip, subjectEnglish,
				bodyEnglish);
		messageService.broadcastAlertTripMessage(trip, subjectEspanol,
				bodyEspanol);
		trip.getComments().clear();
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

	public Collection<Trip> findTripByKeyword(String key) {
		Collection<Trip> result;
		result = tripRepository.findTripByKeyword(key);
		Collection<Trip> result1;
		result1 = tripRepository.findTripByDailyKeyword(key);
		Collection<Trip> result2;
		result2 = tripRepository.findTripBySlotKeyword(key);
		Collection<Slot> result3Slot;
		result3Slot = slotRepository.findTripByActivityKeyword(key);
		Collection<Trip> result3 = new ArrayList<>();
		for (Slot sl : result3Slot) {
			result3.add(sl.getDailyPlan().getTrip());
		}
		for (Trip t : result1) {
			if (!result.contains(t)) {
				result.add(t);
			}
		}
		for (Trip l : result2) {
			if (!result.contains(l)) {
				result.add(l);
			}
		}
		for (Trip j : result3) {
			if (!result.contains(j)) {
				result.add(j);
			}
		}
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

	public int totalNumberOfTripsRegistered() {
		Administrator admin = administratorService.findByPrincipal();
		Assert.notNull(admin);
		int result = findAll().size();
		return result;
	}

	public Double standardDeviationOfTripsByUsers() {
		return tripRepository.standardDeviationOfTripsByUser();
	}

	public Double averageNumberOfTripsByUsers() {
		return tripRepository.averageNumberOfTripsByUser();
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

	public Collection<Trip> tripsSubscribedByUser(int userId) {
		Collection<Trip> result;

		result = tripRepository.findAllTripsSubscrito(userId);

		return result;
	}

	public Trip tripByDailyplan(int dailyPlanId) {

		return tripRepository.tripByDailyplan(dailyPlanId);

	}
}
