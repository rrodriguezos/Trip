package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SlotRepository;
import security.LoginService;
import domain.DailyPlan;
import domain.Slot;


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
			
			
			public void save(Slot slot){
				Assert.isTrue(slot.getDailyplan().getTrip().getUser().getUserAccount().equals(LoginService.getPrincipal()));
				slotRepository.saveAndFlush(slot);
				DailyPlan dailis = dailyPlanService.findOne(slot.getDailyplan().getId());
				Collection<Slot> eslots = dailis.getSlots();
				eslots.add(slot);
				dailis.setSlots(eslots);
				dailyPlanService.saveAguasArriba(dailis);
			}
			
			
			public Slot create(int dailyplanId){
				Slot result = new Slot();
				Assert.notNull(dailyplanId);
				result.setDailyplan(dailyPlanService.findOne(dailyplanId));
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
