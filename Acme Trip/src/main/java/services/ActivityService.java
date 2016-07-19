package services;
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
		
	

		
		// Other Business Methods ---------------------------
		
		public Collection<Activity> findAllByPrincipal(){
			Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
			User user = userService.findByPrincipal();
			Collection<Activity> result = activityRepository.activitiesByUser(user.getId());
			Assert.notNull(result);
			return result;
		}
		
		
//		public Booking reconstruct(BookingForm bookingForm, int gymId, int serviceId) {
//			Customer customer = userService.findByPrincipal();
//			Assert.notNull(customer);
//			Booking result = new Booking();
//			result.setRequestedMoment(bookingForm.getRequestedMoment());
//			result.setServiceDuration(bookingForm.getServiceDuration());
//			result.setService(serviceService.findOne(serviceId));
//			result.setCustomer(customer);
//			result.setBookingState(BookingState.PENDING);
//			result.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
//			return result;
//		}
//	
}
