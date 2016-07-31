package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Activity;
import domain.Administrator;
import domain.Campaign;
import domain.Comment;
import domain.CreditCard;
import domain.Folder;
import domain.Manager;
import forms.UserRegisterForm;

@Service
@Transactional
public class ManagerService {

	// Managed repository ----------------------
	@Autowired
	private ManagerRepository managerRepository;

	// Supporting services ---------------------

	@Autowired
	private FolderService folderService;
	@Autowired
	private AdministratorService administratorService;

	// Constructors ----------------------------
	public ManagerService() {
		super();
	}

	// Simple CRUD methods ---------------------

	public Collection<Manager> findAll() {
		return managerRepository.findAll();
	}

	public Manager create() {
		Administrator principal = administratorService.findByPrincipal();
		Assert.notNull(principal);
		Manager result = new Manager();

		Authority auth = new Authority();
		auth.setAuthority("MANAGER");
		Collection<Authority> lia = new ArrayList<Authority>();
		lia.add(auth);
		result.setActivities(new ArrayList<Activity>());
		result.setComments(new ArrayList<Comment>());
		result.setFolders(new ArrayList<Folder>());
		result.setCampaigns(new ArrayList<Campaign>());
		result.setCreditCards(new ArrayList<CreditCard>());
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		result.setUserAccount(ua);
		
		return result;
	}

	public void save(Manager manager) {
		Assert.notNull(manager);
		if(manager.getId()==0){//Saving as admin
			//Encoding Password
			Administrator principal = administratorService.findByPrincipal();
			Assert.notNull(principal);
			Md5PasswordEncoder password = new Md5PasswordEncoder();
			String encodedPassword = password.encodePassword(manager.getUserAccount().getPassword(), null);
			manager.getUserAccount().setPassword(encodedPassword);
			
		} else {//Only principal can modify its profile
			Manager principal = findByPrincipal();
			Assert.notNull(principal);
			Assert.isTrue(manager.getId()==principal.getId());
		}
		managerRepository.saveAndFlush(manager);
	}

	// Other business methods ------------------
	public Manager findByPrincipal() {
		Manager result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public Manager findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager result;
		result = managerRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public Manager findOne(Integer profileId) {
		return managerRepository.findOne(profileId);
	}

	public Manager reconstruct(UserRegisterForm registerForm) {
		Manager result = new Manager();

		result.setComments(new ArrayList<Comment>());
		result.setCampaigns(new ArrayList<Campaign>());
		result.setCreditCards(new ArrayList<CreditCard>());
		folderService.generateSystemFolders(result);
		result.setEmailAddress(registerForm.getEmailAddress());
		result.setName(registerForm.getName());
		result.setPhone(registerForm.getPhone());
		result.setSurname(registerForm.getSurname());

		Authority auth = new Authority();
		auth.setAuthority("MANAGER");
		Collection<Authority> lia = new ArrayList<Authority>();
		lia.add(auth);
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		ua.setUsername(registerForm.getUsername());
		ua.setPassword(registerForm.getPassword());
		result.setUserAccount(ua);
		return result;
	}
	

	public int minimumNumberOfCampaignsPerManager(){
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);	
		Integer res = managerRepository.minimumNumberOfCampaignsPerManager();
		return res==null?0:res;
	}
	

	public int maximumNumberOfCampaignsPerManager(){
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);	
		Integer res = managerRepository.maximumNumberOfCampaignsPerManager();
		return res==null?0:res;
	}

	public double averageNumberOfCampaignsPerManager(){
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);	
		Double res = managerRepository.averageNumberOfCampaignsPerManager();
		return res==null?0.0:res;
	}

	public void checkPrincipal() {
		Assert.isTrue(findByPrincipal() != null);
	}
	
	public Collection<Manager> managersMoreCampaigns(){
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);	
		return  managerRepository.managersMoreCampaigns();
	
		
	}

}
