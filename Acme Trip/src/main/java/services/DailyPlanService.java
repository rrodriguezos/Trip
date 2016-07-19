package services;

import java.util.Collection;

import repositories.DailyPlanRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.DailyPlan;


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
		
		public void delete(DailyPlan dailyPlan) {
			Assert.notNull(dailyPlan);
			dailyPlanRepository.delete(dailyPlan);
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
		
		

}
