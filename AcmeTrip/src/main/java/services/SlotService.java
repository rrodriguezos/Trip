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
		
		String subjectEspanol;
		String bodyEspanol;
		
		String subjectEnglish;
		String bodyEnglish;
		if(slot.getId()==0){
			subjectEspanol = "Creación de la ranura  "+slot.getTitle()+" del viaje " + slot.getDailyPlan().getTrip().getTitle();
			bodyEspanol = "Se ha añadido la ranura "+slot.getTitle()+" del viaje " + slot.getDailyPlan().getTrip().getTitle();
			
			subjectEnglish = "Creation of the slot  "+slot.getTitle()+" of the trip " + slot.getDailyPlan().getTrip().getTitle();
			bodyEnglish = "Added slot  "+slot.getTitle()+" of the trip " + slot.getDailyPlan().getTrip().getTitle();
		} else {
			subjectEspanol = "Modificación de la ranura "+slot.getTitle()+" del viaje " + slot.getDailyPlan().getTrip().getTitle();
			bodyEspanol = "Se ha modificado la ranura "+slot.getTitle()+" del viaje" + slot.getDailyPlan().getTrip().getTitle();
			
			subjectEnglish = "Edition of the slot  "+slot.getTitle()+" of the trip " + slot.getDailyPlan().getTrip().getTitle();
			bodyEnglish = "The slot "+slot.getTitle()+" has been modified  of the trip " + slot.getDailyPlan().getTrip().getTitle();
		}
		messageService.broadcastAlertTripMessage(slot.getDailyPlan().getTrip(), subjectEnglish, bodyEnglish);
		messageService.broadcastAlertTripMessage(slot.getDailyPlan().getTrip(), subjectEspanol, bodyEspanol);
		
		slotRepository.saveAndFlush(slot);
	}

	public void delete(Slot slot){
		checkPrincipal(slot.getDailyPlan().getTrip().getUser());
		String subjectEspanol;
		String bodyEspanol;
		
		String subjectEnglish;
		String bodyEnglish;

		subjectEspanol = "Eliminación de un slot de la excursión " + slot.getDailyPlan().getTrip().getTitle();
		bodyEspanol = "He eliminado un slot en mi excursión " + slot.getDailyPlan().getTrip().getTitle();
		
		subjectEnglish = "Elimination the slot of the trip  " + slot.getDailyPlan().getTrip().getTitle();
		bodyEnglish = "I have deleted the slot of the trip  " + slot.getDailyPlan().getTrip().getTitle();

		messageService.broadcastAlertTripMessage(slot.getDailyPlan().getTrip(), subjectEspanol, bodyEspanol);
		messageService.broadcastAlertTripMessage(slot.getDailyPlan().getTrip(), subjectEnglish, bodyEnglish);
		
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
	
	public int comprobrarSolape(Slot slot) {
		int result;
		result = slotRepository.comprobrarSolape(slot.getDailyPlan().getId(), slot.getStartTime(), slot.getEndTime());
		return result;
	}


	public Collection<Slot> slotByActivity(int activityId) {
		return slotRepository.slotByActivity(activityId);
	}
} 
