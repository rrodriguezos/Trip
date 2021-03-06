package services; 

import java.util.ArrayList;
import java.util.Collection; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.util.Assert; 

import repositories.ActivityRepository;
import security.Authority;

import domain.Activity; 
import domain.Actor;
import domain.Comment;
import domain.Slot;
import domain.User;

@Service 
@Transactional 
public class ActivityService { 

	//Managed repository -------------------
	@Autowired
	private ActivityRepository activityRepository;


	//Supporting Services ------------------
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActorService actorService;

	//COnstructors -------------------------
	public ActivityService(){
		super();
	}


	//Simple CRUD methods--------------------

	public Activity create(){
		Activity result;
		User user;
		Collection<String> photos;
		Collection<Comment> comments;
		Collection<Slot> slots;

		result = new Activity();
		
		photos = new ArrayList<String>();
		result.setPhotos(photos);
		
		user = userService.findByPrincipal();
		result.setUser(user);
		
		comments = new ArrayList<Comment>();
		result.setComments(comments);
		
		slots = new ArrayList<Slot>();
		result.setSlots(slots);
		
		result.setAppropriated(true);

		return result;
	}

	public Collection<Activity> findAll(){
		Collection<Activity> result;

		result = activityRepository.findAll();

		return result;
	}

	public Activity findOne(int activityId){
		Activity result;

		result = activityRepository.findOne(activityId);

		return result;
	}

	public void save(Activity activity){
		Assert.notNull(activity);

		activityRepository.saveAndFlush(activity);
	}

	public void delete(Activity activity){
		activityRepository.delete(activity);
	}


	//Other Methods---------------------------------------------------------
	public Collection<Activity> findAllAppropriated(){
		Collection<Activity> result;

		result = activityRepository.findAllAppropriated();

		return result;
	}
	
	public void changeFlagActivity(int commentId){
		Assert.notNull(commentId);
		checkPrincipalManager();
		Activity comment;
		
		comment = findOne(commentId);
		
		comment.setAppropriated(!comment.getAppropriated());
		
		save(comment);
	}
	
	private void checkPrincipalManager(){
		Actor actor;
		Authority authority;
	
		actor = actorService.findByPrincipal();
		Assert.isTrue(actor != null);

		
		authority = new Authority();
		authority.setAuthority("MANAGER");
		
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
	}


	public Collection<Activity> activitiesByActivityType(int activitytypeId) {
		return activityRepository.activitiesByActivityType(activitytypeId);
	}


	public Activity activityBySlot(int slotId) {
		return activityRepository.activityBySlot(slotId);
	}

	
	//Repository Methods--------------------
} 
