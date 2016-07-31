package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SlotRepository;
import security.LoginService;
import domain.DailyPlan;
import domain.Slot;

@Service
@Transactional
public class SlotService {

	// Managed repository --------------------
	@Autowired
	private SlotRepository slotRepository;

	// Supporting services -------------------
	@Autowired
	private DailyPlanService dailyPlanService;

	@Autowired
	private MessageService messageService;

	// Constructors --------------------------
	public SlotService() {
		super();
	}

	// Simple CRUD methods -------------------
	public Slot findOne(int slotId) {
		Assert.isTrue(slotId != 0);
		Slot result = slotRepository.findOne(slotId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Slot> findAll() {
		return slotRepository.findAll();
	}

	public void save(Slot slot) {
		Assert.isTrue(slot.getDailyplan().getTrip().getUser().getUserAccount()
				.equals(LoginService.getPrincipal()));

		String s;
		String b;
		if (slot.getId() == 0) {
			s = "Create a new Slot of Trip "
					+ slot.getDailyplan().getTrip().getTitle();
			b = "Slot added to my trip "
					+ slot.getDailyplan().getTrip().getTitle();
		} else {
			s = "Edited a new Slot of Trip "
					+ slot.getDailyplan().getTrip().getTitle();
			b = "I edited a Slot of my Trip "
					+ slot.getDailyplan().getTrip().getTitle();
		}
		messageService.broadcastAlertTripMessage(slot.getDailyplan().getTrip(),
				s, b);

		slotRepository.saveAndFlush(slot);
	}

	public Slot create() {
		Slot result = new Slot();

		return result;
	}

	public void delete(Slot slot) {
		Assert.isTrue(slot.getDailyplan().getTrip().getUser().getUserAccount()
				.equals(LoginService.getPrincipal()));

		String s = "Deleted a Slot of Trip "
				+ slot.getDailyplan().getTrip().getTitle();
		String b = "I deleted a Slot of my Trip "
				+ slot.getDailyplan().getTrip().getTitle();

		messageService.broadcastAlertTripMessage(slot.getDailyplan().getTrip(),
				s, b);

		slotRepository.delete(slot);
	}

	// Other business methods ----------------

	public int checkOverlapping(Slot slot) {
		int result = slotRepository.checkOverlap(slot.getDailyplan().getId(),
				slot.getStartTime(), slot.getEndTime());
		return result;
	}

	public Collection<Slot> slotByDailyPlan(int dailyPlanId) {
		return slotRepository.slotByDailyPlan(dailyPlanId);
	}

	public Collection<Slot> slotByActivity(int activityId) {
		return slotRepository.slotByActivity(activityId);
	}

}
