package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DailyPlanRepository;
import security.LoginService;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;

@Service
@Transactional
public class DailyPlanService {

	// Managed repository --------------------
	@Autowired
	private DailyPlanRepository dailyPlanRepository;

	// Supporting services -------------------
	@Autowired
	private TripService tripService;

	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	// Constructors --------------------------
	public DailyPlanService() {
		super();
	}

	// Simple CRUD methods -------------------
	public DailyPlan findOne(int dailyPlanId) {
		Assert.isTrue(dailyPlanId != 0);
		DailyPlan result = dailyPlanRepository.findOne(dailyPlanId);
		Assert.notNull(result);
		return result;
	}

	public Collection<DailyPlan> findAll() {
		return dailyPlanRepository.findAll();
	}

	// public DailyPlan save(DailyPlan dPlan){
	// Assert.isTrue(dPlan.getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
	// DailyPlan plan = dailyPlanRepository.saveAndFlush(dPlan);
	// Trip barquito2 = tripService.findOne(dPlan.getTrip().getId());
	// Collection<DailyPlan> planesMolones = barquito2.getDailyplans();
	// planesMolones.add(dPlan);
	// barquito2.setDailyplans(planesMolones);
	// tripService.saveAguasArriba(barquito2);
	// return plan;
	// }
	public DailyPlan save(DailyPlan dailyplan) {
		checkPrincipal(dailyplan.getTrip().getUser());
		Assert.notNull(dailyplan);

		String subject;
		String body;
		if (dailyplan.getId() == 0) {
			subject = "Create a new Daily Plan of Trip "
					+ dailyplan.getTrip().getTitle();
			body = "Daily plan added to my trip "
					+ dailyplan.getTrip().getTitle();
		} else {
			subject = "Edited a new Daily Plan of Trip "
					+ dailyplan.getTrip().getTitle();
			body = "I edited a Daily Plan of my Trip "
					+ dailyplan.getTrip().getTitle();
		}
		messageService.broadcastAlertTripMessage(dailyplan.getTrip(), subject,
				body);

		dailyplan.setTrip(dailyplan.getTrip());
		dailyPlanRepository.save(dailyplan);
		dailyPlanRepository.flush();
		return dailyplan;
	}

	public DailyPlan create() {
		DailyPlan result = new DailyPlan();

		result.setSlots(new ArrayList<Slot>());
		result.setPhotos(new ArrayList<String>());
		return result;
	}

	public void delete(DailyPlan dPlan) {
		Assert.isTrue(dPlan.getTrip().getUser().getUserAccount()
				.equals(LoginService.getPrincipal()));
		String subject;
		String body;

		subject = "Deleted a Daily Plan of Trip " + dPlan.getTrip().getTitle();
		body = "I deleted a Daily Plan of my Trip "
				+ dPlan.getTrip().getTitle();

		messageService
				.broadcastAlertTripMessage(dPlan.getTrip(), subject, body);

		dailyPlanRepository.delete(dPlan);
	}

	// Other business methods ----------------

	public Collection<DailyPlan> dailyPlansByTrip(int tripId) {
		return dailyPlanRepository.dailyPlansByTrip(tripId);
	}

	public DailyPlan dailyPlanBySlot(int slotId) {
		return dailyPlanRepository.dailyPlanBySlot(slotId);
	}

	public Double standardDeviationOfDailyPlansByTrip() {
		return dailyPlanRepository.standardDeviationOfDailyPlansByTrip();
	}

	public Double averageNumberOfDailyPlansByTrip() {
		return dailyPlanRepository.averageNumberOfDailyPlansByTrip();
	}

	public DailyPlan findByPrincipal() {
		return dailyPlanRepository.findByUserAccountID(LoginService
				.getPrincipal().getId());
	}

	public void saveAguasArriba(DailyPlan dailis) {
		dailyPlanRepository.saveAndFlush(dailis);
	}

	public Boolean checkOverlap(DailyPlan dPan) {

		Boolean result = false;

		User principal = userService.findByPrincipal();

		Assert.isTrue(dPan.getTrip().getUser().equals(principal));

		result = dPan.getWeekDay() != null
				&& (dailyPlanRepository.findDPlanBetweenTripDate(dPan.getTrip()
						.getId(), dPan.getWeekDay()) == 1);

		return result;
	}

	private void checkPrincipal(User u) {
		User user;
		user = userService.findByPrincipal();
		Assert.isTrue(user != null);
		Assert.isTrue(user.equals(u));
	}

}
