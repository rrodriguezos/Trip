package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import security.Authority;
import utilities.DPMessage;
import utilities.DPUtils;
import domain.Activity;
import domain.Comment;
import domain.Manager;
import domain.Slot;
import domain.User;

@org.springframework.stereotype.Service
@Transactional
public class ActivityService {

	// Managed Repository ------------------------------
	@Autowired
	private ActivityRepository activityRepository;

	// Supporting Services -----------------------------
	@Autowired
	private UserService userService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private CommentService commentService;

	// Constructors ------------------------------------
	public ActivityService() {
		super();
	}

	// Simple CRUD Methods -----------------------------
	public Activity findOne(int activityId) {
		Assert.isTrue(activityId != 0);
		Activity result = activityRepository.findOne(activityId);
		Assert.notNull(result);
		return result;
	}

	public Activity create() {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		Activity result = new Activity();
		User user = userService.findByPrincipal();
		result.setUser(user);
		result.setSlots(new ArrayList<Slot>());
		result.setComments(new ArrayList<Comment>());
		result.setIsAppropiate(true);
		result.setPhotos(new ArrayList<String>());
		return result;
	}

	public Activity save(Activity activity) {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		return activityRepository.save(activity);
	}

	public void delete(Activity activity) {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);

		activity.getSlots().removeAll(activity.getSlots());
		for (Comment tc : activity.getComments()) {
			commentService.delete(tc);
		}
		activityRepository.delete(activity);
	}

	public Collection<Activity> findAll() {
		return activityRepository.findAll();
	}

	// Other Business Methods ---------------------------

	public Collection<Activity> findAllByPrincipal() {
		Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
		User user = userService.findByPrincipal();
		Collection<Activity> result = activityRepository.activitiesByUser(user
				.getId());
		Assert.notNull(result);
		return result;
	}

	public Activity activityBySlot(int slotId) {
		return activityRepository.activityBySlot(slotId);
	}

	public Collection<Activity> activitiesByActivityType(int activitytypeId) {
		return activityRepository.activitiesByActivityType(activitytypeId);
	}

	public Collection<Activity> findIsAppropriated() {
		Collection<Activity> result;

		result = activityRepository.findIsAppropriated();

		return result;
	}

	public void flagActivityAsInappropriate(int activityId) {
		Manager manager = managerService.findByPrincipal();
		Assert.notNull(manager);
		Activity activity = findOne(activityId);
		Assert.isTrue(activity.getIsAppropiate());
		activity.setIsAppropiate(false);
	}
}
