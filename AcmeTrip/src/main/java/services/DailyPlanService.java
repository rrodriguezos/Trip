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
		
		String subject;
		String body;
		if(dailyPlan.getId()==0){
			subject = "Creación de un plan diario en la excursión " + dailyPlan.getTrip().getTitle();
			body = "He añadido un plan diario en mi excursión " + dailyPlan.getTrip().getTitle();
		} else {
			subject = "Modificación de un plan diario de la excursión " + dailyPlan.getTrip().getTitle();
			body = "He modificado un plan diario de mi excursión " + dailyPlan.getTrip().getTitle();
		}
		messageService.automaticMessage(dailyPlan.getTrip(), subject, body);
		
		dailyPlanRepository.save(dailyPlan);
	}

	public void delete(DailyPlan dailyPlan){
		checkPrincipal(dailyPlan.getTrip().getUser());
		
		String subject;
		String body;

		subject = "Eliminación de un plan diario de la excursión " + dailyPlan.getTrip().getTitle();
		body = "He eliminado un plan diario en mi excursión " + dailyPlan.getTrip().getTitle();

		messageService.automaticMessage(dailyPlan.getTrip(), subject, body);
		
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
} 
