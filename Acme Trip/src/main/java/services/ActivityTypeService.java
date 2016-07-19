package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import repositories.ActivityTypeRepository;
import repositories.SlotRepository;
import domain.ActivityType;
import domain.Slot;

@Service
@Transactional
public class ActivityTypeService {

	// Managed repository --------------------
	@Autowired
	private ActivityTypeRepository activityRepository;

	// Supporting services -------------------
	@Autowired
	private ActivityService activityService;

	// Constructors --------------------------
	public ActivityTypeService() {
		super();
	}

	// Simple CRUD methods -------------------
	public ActivityType findOne(int activityTypeId) {
		Assert.isTrue(activityTypeId != 0);
		ActivityType result = activityRepository.findOne(activityTypeId);
		Assert.notNull(result);
		return result;
	}

	public Collection<ActivityType> findAll() {
		return activityRepository.findAll();
	}

	// Other business methods ----------------

	public ActivityType activityTypeByActivity(int activityId) {
		return activityRepository.activityTypeByActivity(activityId);
	}

}
