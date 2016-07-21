package services;

import java.util.Collection;

import repositories.DailyPlanRepository;
import security.LoginService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.DailyPlan;
import domain.Trip;


@Service
@Transactional
public class DailyPlanService {
	
	
	//Managed repository --------------------
		@Autowired
		private DailyPlanRepository dailyPlanRepository;
		
		//Supporting services -------------------
		@Autowired
		private TripService tripService;
		
		//Constructors --------------------------
		public DailyPlanService() {
			super();
		}
		
		//Simple CRUD methods -------------------
		public DailyPlan findOne(int dailyPlanId) {
			Assert.isTrue(dailyPlanId != 0);
			DailyPlan result = dailyPlanRepository.findOne(dailyPlanId);
			Assert.notNull(result);
			return result;
		}

		public Collection<DailyPlan> findAll() {
			return dailyPlanRepository.findAll();
		}		
		
		
		public DailyPlan save(DailyPlan dPlan){
			Assert.isTrue(dPlan.getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
			dPlan.setTrip(tripService.findByPrincipal());
			
			return dailyPlanRepository.save(dPlan);
			
		}
		
		
		public DailyPlan create(){
			DailyPlan result = new DailyPlan();

			Assert.isTrue(tripService.findByPrincipal() != null, "noTripAssociated");
			result.setTrip(tripService.findByPrincipal());
			return result;
		}
			
		
		public void delete(DailyPlan dPlan){
			Assert.isTrue(dPlan.getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
			Trip trip = tripService.findByPrincipal();
			trip.getDailyplans().remove(dPlan);
			tripService.save(trip);
		}
		
		
		
		
		//Other business methods ----------------
		
		public Collection<DailyPlan> dailyPlansByTrip(int tripId){
			return dailyPlanRepository.dailyPlansByTrip(tripId);
		}
		

		public Double standardDeviationOfDailyPlansByTrip() {
			return dailyPlanRepository.standardDeviationOfDailyPlansByTrip();
		}
		
		public Double averageNumberOfDailyPlansByTrip(){
			return dailyPlanRepository.averageNumberOfDailyPlansByTrip();
		}
		
		public DailyPlan findByPrincipal(){
			return dailyPlanRepository.findByUserAccountID(LoginService.getPrincipal().getId());
		}
		
		

}
