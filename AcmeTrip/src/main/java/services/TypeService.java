package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Activity;
import domain.Actor;
import domain.ActivityType;

import repositories.TypeRepository;
import security.Authority;

@Transactional
@Service
public class TypeService {
	
	//Constructor ---------------------------------------------------------------
	public TypeService(){
		super();
	}

	//Managed Repository-----------------------------------------------------------
	@Autowired
	private TypeRepository typeRepository;
	
	//Suported Services------------------------------------------------------------
	@Autowired
	private ActorService actorService;
	
	//CRUDs Methods ---------------------------------------------------------------
	public ActivityType create(){
		checkPrincipalAdministratorOrManager();
		ActivityType result;
		Collection<Activity> activities;
		
		activities = new ArrayList<Activity>();
		
		result = new ActivityType();
		result.setActivities(activities);
		
		return result;
	}
	
	public ActivityType findOne(int typeId){
		ActivityType result;
		
		result = typeRepository.findOne(typeId);
		Assert.notNull(result);
		
		return result;
	}
	
	public ActivityType save(ActivityType type){
		checkPrincipalAdministratorOrManager();
		Assert.notNull(type);
		
		//CONCURRENCY CHECK 
		if(type.getId()!=0){
			ActivityType typeCheck = typeRepository.findOne(type.getId());
			Assert.isTrue(type.getVersion() == typeCheck.getVersion());
		}
		
		ActivityType result;

		result = typeRepository.save(type);
		
		return result;
	}
	
	public Collection<ActivityType> findAll() {
		
		Collection<ActivityType> result;

		result = typeRepository.findAll();

		return result;
	}
	
	//Other bussiness methods -----------------------------------------------------
	private void checkPrincipalAdministratorOrManager(){
		Actor actor;
		Authority authority, authority2;
	
		actor = actorService.findByPrincipal();
		Assert.isTrue(actor != null);
		
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		
		authority2 = new Authority();
		authority2.setAuthority("MANAGER");
		
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority) || actor.getUserAccount().getAuthorities().contains(authority2));
	}
}
