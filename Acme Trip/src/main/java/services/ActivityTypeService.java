package services;

import java.util.ArrayList;
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
import security.Authority;
import utilities.DPMessage;
import utilities.DPUtils;
import domain.Activity;
import domain.ActivityType;
import domain.ChargeRecord;
import domain.CreditCard;
import domain.Manager;
import domain.Slot;

@Service
@Transactional
public class ActivityTypeService {

	// Managed repository --------------------
	@Autowired
	private ActivityTypeRepository activityTypeRepository;

	// Supporting services -------------------
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private ManagerService managerService;

	// Constructors --------------------------
	public ActivityTypeService() {
		super();
	}

	// Simple CRUD methods -------------------
	public ActivityType create() {
		Assert.isTrue(DPUtils.hasRole(Authority.MANAGER), DPMessage.NO_PERMISSIONS);
		ActivityType result = new ActivityType();
		result.setActivities(new ArrayList<Activity>());
		return result;
	}
	
	public void save(ActivityType activityType) {
		Assert.notNull(activityType);
		Manager manager = managerService.findByPrincipal();
//		Assert.isTrue(manager.getId() == activityType.getManager().getId());
		activityTypeRepository.save(activityType);
		activityTypeRepository.flush();
	}
	
	
	
	public ActivityType findOne(int activityTypeId) {
		Assert.isTrue(activityTypeId != 0);
		ActivityType result = activityTypeRepository.findOne(activityTypeId);
		Assert.notNull(result);
		return result;
	}

	public Collection<ActivityType> findAll() {
		return activityTypeRepository.findAll();
	}

	// Other business methods ----------------

	public ActivityType activityTypeByActivity(int activityId) {
		return activityTypeRepository.activityTypeByActivity(activityId);
	}

}
