package services; 

import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.SlotRepository;

import domain.Slot; 
import domain.User;

@Service 
@Transactional 
public class SlotService { 

	//Managed repository -------------------
	@Autowired
	private SlotRepository slotRepository;


	//Supporting Services ------------------
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	//COnstructors -------------------------
	public SlotService(){
		super();
	}


	//Simple CRUD methods--------------------

	public Slot create(){
		Slot result;

		result = new Slot();

		return result;
	}

	public Collection<Slot> findAll(){
		Collection<Slot> result;

		result = slotRepository.findAll();

		return result;
	}

	public Slot findOne(int slotId){
		Slot result;

		result = slotRepository.findOne(slotId);

		return result;
	}

	public void save(Slot slot){
		Assert.notNull(slot);
		checkPrincipal(slot.getDailyPlan().getTrip().getUser());
		
		String subject;
		String body;
		if(slot.getId()==0){
			subject = "Creaci�n de un slot en la excursi�n " + slot.getDailyPlan().getTrip().getTitle();
			body = "He a�adido un slot en mi excursi�n " + slot.getDailyPlan().getTrip().getTitle();
		} else {
			subject = "Modificaci�n de un slot de la excursi�n " + slot.getDailyPlan().getTrip().getTitle();
			body = "He modificado un slot de mi excursi�n " + slot.getDailyPlan().getTrip().getTitle();
		}
		messageService.broadcastAlertTripMessage(slot.getDailyPlan().getTrip(), subject, body);
		
		slotRepository.save(slot);
	}

	public void delete(Slot slot){
		checkPrincipal(slot.getDailyPlan().getTrip().getUser());
		
		String subject;
		String body;

		subject = "Eliminaci�n de un slot de la excursi�n " + slot.getDailyPlan().getTrip().getTitle();
		body = "He eliminado un slot en mi excursi�n " + slot.getDailyPlan().getTrip().getTitle();

		messageService.broadcastAlertTripMessage(slot.getDailyPlan().getTrip(), subject, body);
		
		slotRepository.delete(slot);
	}
	
	//Other Methods--------------------
	private void checkPrincipal(User u){
		User user;
	
		user = userService.findByPrincipal();
		Assert.isTrue(user != null);
		
		Assert.isTrue(user.equals(u));
	}
	
	public Collection<Slot> slotsByDailyPlan(int dailyPlanId) {
		Collection<Slot> result;
		result = slotRepository.slotsByDailyPlan(dailyPlanId);
		return result;
	}
	
	public int checkOverlapping(Slot slot) {
		int result;
		result = slotRepository.checkOverlapping(slot.getDailyPlan().getId(), slot.getStartTime(), slot.getEndTime());
		return result;
	}
} 
