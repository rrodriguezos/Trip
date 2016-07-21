package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import utilities.DPMessage;
import utilities.DPUtils;

import org.springframework.util.Assert;

import domain.Activity;
import domain.ActivityType;
import domain.Comment;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;

import repositories.MessageRepository;
import repositories.TripRepository;
import security.Authority;
import security.LoginService;

@Service
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

	// Constructors ------------------------------------
	public TripService() {
		super();
	}

	// Simple CRUD Methods -----------------------------

	public Trip create() {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		Trip result = new Trip();
		result.setDailyplans(new ArrayList<DailyPlan>());
		result.setComments(new ArrayList<Comment>());
		return result;
	}

	public Trip save(Trip trip) {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		return tripRepository.save(trip);
	}

	public void delete(Trip trip) {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		
		
		for (DailyPlan dp : trip.getDailyplans()) {
			dailyPlanService.delete(dp);
		}
		for (Comment tc : trip.getComments()) {
			commentService.delete(tc);
		}
		trip.getDailyplans().removeAll(trip.getDailyplans());
		trip.getUsers().removeAll(trip.getUsers());
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
	
	public Trip findByPrincipal(){
		return tripRepository.findByUserAccountID(LoginService.getPrincipal().getId());
	}

	// Other business methods -----------------------
	public Double standardDeviationOfTripsByUsers() {
		return tripRepository.standardDeviationOfTripsByUser();
	}

	public Double averageNumberOfTripsByActors() {
		return tripRepository.averageNumberOfTripsByUser();
	}
	
	public Collection<Trip> findTripsByUser(int userId) {
		User user = userService.findOne(userId);
		Collection<Trip> result = tripRepository.findTripsByUser(user.getId());
		Assert.notNull(result);
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

	public Collection<Trip> searchByKeyword(String keyword) {

		return tripRepository.searchByKeyword(keyword);
	}
	
	public void checkPrincipalByJoinedUser(Trip trip) {

		User user;

		user = userService.findByPrincipal();

		Assert.isTrue(trip.getUsers().contains(user));

	}
	
	
	public void joinTrip(Trip trip) {

		User user;

		user = userService.findByPrincipal();

		trip.getUsers().add(user);
		user.getTripSubscribes().add(trip);

		save(trip);
		userService.save(user);

	}

	public void DisjoinTrip(Trip trip) {

		User user;

		checkPrincipalByJoinedUser(trip);

		user = userService.findByPrincipal();

		trip.getUsers().remove(user);
		user.getTripSubscribes().remove(trip);

		save(trip);
		userService.save(user);

	}

}
