package services; 

import java.util.ArrayList;
import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.DailyPlanRepository;

import domain.DailyPlan; 
import domain.Slot;
import domain.User;

@Service 
@Transactional 
public class DailyPlanService { 

	//Managed repository -------------------
	@Autowired
	private DailyPlanRepository dailyPlanRepository;


	//Supporting Services ------------------
	@Autowired
	private UserService userService;
	
	@Autowired
	private MessageService messageService;

	//COnstructors -------------------------
	public DailyPlanService(){
		super();
	}


	//Simple CRUD methods--------------------

	public DailyPlan create(){
		DailyPlan result;
		Collection<String> photos;
		Collection<Slot> slots;

		result = new DailyPlan();
		
		photos = new ArrayList<String>();
		result.setPhotos(photos);
		
		slots = new ArrayList<Slot>();
		result.setSlots(slots);

		return result;
	}

	public Collection<DailyPlan> findAll(){
		Collection<DailyPlan> result;

		result = dailyPlanRepository.findAll();

		return result;
	}

	public DailyPlan findOne(int dailyPlanId){
		DailyPlan result;

		result = dailyPlanRepository.findOne(dailyPlanId);

		return result;
	}

	public void save(DailyPlan dailyPlan){
		Assert.notNull(dailyPlan);
		checkPrincipal(dailyPlan.getTrip().getUser());
		
		String subjectEspanol;
		String bodyEspanol;
		String subjectEnglish;
		String bodyEnglish;
		if(dailyPlan.getId()==0){
			subjectEspanol = "Nuevo plan diario al viaje " + dailyPlan.getTrip().getTitle();
			bodyEspanol = "Se añadió el plan diario"+dailyPlan.getTitle()+" en el viaje " + dailyPlan.getTrip().getTitle();
			
			subjectEnglish = "New Daily Plan " + dailyPlan.getTrip().getTitle();
			bodyEnglish = "Added a new Daily Plan "+dailyPlan.getTitle()+" to the " + dailyPlan.getTrip().getTitle();
			
		} else {
			subjectEspanol = "Edición del un plan diario del viaje " + dailyPlan.getTrip().getTitle();
			bodyEspanol = "Se ha modificado el plan diario"+dailyPlan.getTitle()+" del viaje " + dailyPlan.getTrip().getTitle();
			
			subjectEnglish = "Edition of the Daily Plan  " + dailyPlan.getTrip().getTitle();
			bodyEnglish = "The daily plan has been modified "+dailyPlan.getTitle()+" of the  " + dailyPlan.getTrip().getTitle();
		}
		messageService.broadcastAlertTripMessage(dailyPlan.getTrip(), subjectEnglish, bodyEnglish);
		messageService.broadcastAlertTripMessage(dailyPlan.getTrip(), subjectEspanol, bodyEspanol);
		
		dailyPlanRepository.saveAndFlush(dailyPlan);
	}

	public void delete(DailyPlan dailyPlan){
		checkPrincipal(dailyPlan.getTrip().getUser());
		
		String subjectEspanol;
		String bodyEspanol;
		String subjectEnglish;
		String bodyEnglish;

		subjectEspanol = "Eliminación de un plan diario del viaje " + dailyPlan.getTrip().getTitle();
		bodyEspanol = "Se ha eliminado el plan diario"+dailyPlan.getTitle()+" del viaje " + dailyPlan.getTrip().getTitle();
		
		subjectEnglish = "Elimination of a daily plan"+dailyPlan.getTitle()+" of the trip " + dailyPlan.getTrip().getTitle();
		bodyEnglish = "It has eliminated a plan daily "+dailyPlan.getTitle()+" of the travel " + dailyPlan.getTrip().getTitle();
		
		messageService.broadcastAlertTripMessage(dailyPlan.getTrip(), subjectEnglish, bodyEnglish);
		messageService.broadcastAlertTripMessage(dailyPlan.getTrip(), subjectEspanol, bodyEspanol);
		
		dailyPlanRepository.delete(dailyPlan);
	}


	//Other Methods--------------------
	
	private void checkPrincipal(User u){
		User user;
	
		user = userService.findByPrincipal();
		Assert.isTrue(user != null);
		
		Assert.isTrue(user.equals(u));
	}
	
	public Collection<DailyPlan> dailyPlansByTrip(int tripId){
		Collection<DailyPlan> result;
		
		result = dailyPlanRepository.dailyPlayByTrip(tripId);
		return result;
	}
	
	public Boolean checkOverlapping(DailyPlan dailyPlan){
		User principal;
		Boolean result;
		
		result = false;
		
		principal = userService.findByPrincipal();
		
		Assert.isTrue(dailyPlan.getTrip().getUser().equals(principal));
		
		result = dailyPlan.getWeekDay() != null && (dailyPlanRepository.findDailyPlanDateBetweenTripDates(dailyPlan.getTrip().getId(), dailyPlan.getWeekDay()) == 1);
		
		return result;
	}
	
	public Double standardDeviationOfDailyPlansByTrip() {
		return dailyPlanRepository.standardDeviationOfDailyPlansByTrip();
	}


	public Double averageNumberOfDailyPlansByTrip() {
		return dailyPlanRepository.averageNumberOfDailyPlansByTrip();
	}
} 
