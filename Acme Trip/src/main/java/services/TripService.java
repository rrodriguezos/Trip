package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import utilities.DPMessage;
import utilities.DPUtils;


import org.springframework.util.Assert;

import domain.Comment;
import domain.DailyPlan;
import domain.Trip;

import repositories.MessageRepository;
import repositories.TripRepository;
import security.Authority;


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

			public void delete(Trip trip){
				Assert.isTrue(DPUtils.hasRole(Authority.USER), DPMessage.NO_PERMISSIONS);
				for (DailyPlan dp: trip.getDailyplans()) {
					dailyPlanService.delete(dp);
				}
				for (Comment tc: trip.getComments()) {
					commentService.delete(tc);
				}
				trip.getDailyplans().removeAll(trip.getDailyplans());
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
			
			//Other business methods -----------------------
			public Double standardDeviationOfTripsByUsers() {
				return tripRepository.standardDeviationOfTripsByUser();
			}
			
			public Double averageNumberOfTripsByActors(){
				return tripRepository.averageNumberOfTripsByUser();
			}

}
