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
import domain.DailyPlan.WeekDay;
import domain.Slot;
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
		
		
//		public DailyPlan save(DailyPlan dPlan){
//			Assert.isTrue(dPlan.getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
//			DailyPlan plan = dailyPlanRepository.saveAndFlush(dPlan);
//			Trip barquito2 = tripService.findOne(dPlan.getTrip().getId());
//			Collection<DailyPlan> planesMolones = barquito2.getDailyplans();
//			planesMolones.add(dPlan);
//			barquito2.setDailyplans(planesMolones);
//			tripService.saveAguasArriba(barquito2);
//			return plan;
//		}
		public DailyPlan save(DailyPlan dailyplan) {

			Assert.notNull(dailyplan);
			dailyplan.setTrip(dailyplan.getTrip());
			dailyPlanRepository.save(dailyplan);
			dailyPlanRepository.flush();
			return dailyplan;
		}
		
		
//		public DailyPlan create(int tripId){
//			DailyPlan result = new DailyPlan();
//			Trip barquito = tripService.findOne(tripId);
//			Assert.notNull(barquito);
//			result.setTrip(barquito);
//			result.setSlots(new ArrayList<Slot>());
//			return result;
//		}
		public DailyPlan create(int tripId) {
			DailyPlan result = new DailyPlan();
			Trip trip = tripService.findOne(tripId);
			result.setTrip(trip);
			result.setSlots(new ArrayList<Slot>());
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
		
		public DailyPlan dailyPlanBySlot(int slotId){
			return dailyPlanRepository.dailyPlanBySlot(slotId);
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

		public void saveAguasArriba(DailyPlan dailis) {
			dailyPlanRepository.saveAndFlush(dailis);			
		}
		
		

}
