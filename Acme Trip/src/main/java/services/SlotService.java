package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.DailyPlanRepository;
import repositories.SlotRepository;
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
			
			
			
			//Other business methods ----------------
			
			public Collection<Slot> slotByDailyPlan(int dailyPlanId){
				return slotRepository.slotByDailyPlan(dailyPlanId);
			}
			
			public Collection<Slot> slotByActivity(int activityId){
				return slotRepository.slotByActivity(activityId);
			}
			
			

}
