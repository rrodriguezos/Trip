package services;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utilities.DPMessage;
import utilities.DPUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.ActivityType;
import domain.Administrator;
import domain.Comment;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;
import domain.User;

import repositories.ActivityRepository;
import security.Authority;

@Service
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
		
		public Activity create(ActivityType aType) {
			Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
			Activity result = new Activity();
			result.setActivityType(aType);
			result.setSlots(new ArrayList<Slot>());
			result.setComments(new ArrayList<Comment>());
			return result;
		}

		public Activity save(Activity activity) {
			Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
			return activityRepository.save(activity);
		}

		public void delete(Activity activity) {
			Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
			

			activity.getSlots().removeAll(activity.getSlots());
			activityRepository.delete(activity);
		}

		
	

		
		// Other Business Methods ---------------------------
		
		public Collection<Activity> findAllByPrincipal(){
			Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
			User user = userService.findByPrincipal();
			Collection<Activity> result = activityRepository.activitiesByUser(user.getId());
			Assert.notNull(result);
			return result;
		}
		
		public Activity activityBySlot(int slotId){
			return activityRepository.activityBySlot(slotId);
		}
		
		public void flagActivityAsInappropriate(Activity activity) {
			Assert.notNull(activity);
			Administrator admin = administratorService.findByPrincipal();
			Assert.notNull(admin);
			
			activity.setIsAppropiate(false);
			
			
			activityRepository.saveAndFlush(activity);
		}
}
		
		
