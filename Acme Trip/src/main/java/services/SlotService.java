package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DailyPlanRepository;
import repositories.SlotRepository;
import security.LoginService;
import domain.DailyPlan;
import domain.Slot;
import domain.Trip;


@Service
@Transactional
public class SlotService {
	
	
	//Managed repository --------------------
			@Autowired
			private SlotRepository slotRepository;
			
			//Supporting services -------------------
			@Autowired
			private DailyPlanService dailyPlanService;
			
			//Constructors --------------------------
			public SlotService() {
				super();
			}
			
			//Simple CRUD methods -------------------
			public Slot findOne(int slotId) {
				Assert.isTrue(slotId != 0);
				Slot result = slotRepository.findOne(slotId);
				Assert.notNull(result);
				return result;
			}

			public Collection<Slot> findAll() {
				return slotRepository.findAll();
			}
			
			
			public Slot save(Slot slot){
				Assert.isTrue(slot.getDailyplan().getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
				slot.setDailyplan(dailyPlanService.findByPrincipal());
				
				return slotRepository.save(slot);
				
			}
			
			
			public Slot create(){
				Slot result = new Slot();

				Assert.isTrue(dailyPlanService.findByPrincipal() != null, "noDailyPlanAssociated");
				result.setDailyplan(dailyPlanService.findByPrincipal());
				return result;
			}
				
			
			public void delete(Slot slot){
				Assert.isTrue(slot.getDailyplan().getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
				DailyPlan dPlan = dailyPlanService.findByPrincipal();
				dPlan.getSlots().remove(slot);
				dailyPlanService.save(dPlan);
			}
			
			
			
			//Other business methods ----------------
			
			public Collection<Slot> slotByDailyPlan(int dailyPlanId){
				return slotRepository.slotByDailyPlan(dailyPlanId);
			}
			
			public Collection<Slot> slotByActivity(int activityId){
				return slotRepository.slotByActivity(activityId);
			}
			
			

}
