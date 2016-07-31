package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;


import domain.Activity;
import domain.Administrator;
import domain.Comment;
import domain.Folder;
import domain.Manager;
import domain.Trip;
import domain.User;
import forms.ActorForm;
import forms.UserForm;
import forms.UserRegisterForm;

import repositories.UserRepository;
import security.Authority;
import utilities.DPMessage;
import utilities.DPUtils;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {

	// Managed repository ----------------------
	@Autowired
	private UserRepository userRepository;

	// Supporting services ---------------------

	@Autowired
	private FolderService folderService;

	@Autowired
	private AdministratorService administratorService;

	// Constructors ----------------------------
	public UserService() {
		super();
	}

	// Simple CRUD methods ---------------------

	public Collection<User> findAll() {
		Collection<User> result = userRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public User create() {

		User result = new User();

		Authority auth = new Authority();
		auth.setAuthority("USER");
		Collection<Authority> lia = new ArrayList<Authority>();
		lia.add(auth);
		result.setActivities(new ArrayList<Activity>());
		result.setTrips(new ArrayList<Trip>());
		result.setTripSubscribes(new ArrayList<Trip>());
		result.setComments(new ArrayList<Comment>());
		result.setFolders(new ArrayList<Folder>());
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		result.setUserAccount(ua);
		
		return result;
	}

	public User save(User user) {
		if (user.getId() == 0) {
			Assert.isTrue(!DPUtils.hasRole(Authority.USER, Authority.ADMIN),
					DPMessage.ALREADY_REGISTERED);
			Md5PasswordEncoder password = new Md5PasswordEncoder();
			String encodedPassword = password.encodePassword(user
					.getUserAccount().getPassword(), null);
			user.getUserAccount().setPassword(encodedPassword);
			Assert.notNull(user);
		}
		return userRepository.save(user);
	}

	// Other business methods ------------------
	public User findByPrincipal() {
		User result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public User findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;
		result = userRepository.findByUserAccount(userAccount.getId());
		return result;
	}

	public User findOne(Integer profileId) {
		return userRepository.findOne(profileId);
	}

	public int totalNumberofUsersRegistered() {
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		int numberOfUsers = findAll().size();
		return numberOfUsers;
	}

	public Collection<User> usersWhoRegisteredAtLeast80Maximum() {
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Collection<User> result = userRepository
				.usersWhoRegisteredAtLeast80Maximum();
		return result;
	}

	public User reconstruct(UserRegisterForm userForm) {
		User result = create();

		result.setTrips(new ArrayList<Trip>());
		result.setTripSubscribes(new ArrayList<Trip>());
		result.setActivities(new ArrayList<Activity>());
		result.setComments(new ArrayList<Comment>());
		folderService.generateSystemFolders(result);
		result.setEmailAddress(userForm.getEmailAddress());
		result.setName(userForm.getName());
		result.setPhone(userForm.getPhone());
		result.setSurname(userForm.getSurname());

		Authority auth = new Authority();
		auth.setAuthority("USER");
		Collection<Authority> lia = new ArrayList<Authority>();
		lia.add(auth);
		UserAccount ua = new UserAccount();
		ua.setAuthorities(lia);
		ua.setUsername(userForm.getUsername());
		ua.setPassword(userForm.getPassword());
		result.setUserAccount(ua);
		return result;
	}


	public User reconstruct(ActorForm actorForm) {
		User principal = findByPrincipal();
		Assert.notNull(principal);
		principal.setName(actorForm.getName());
		principal.setSurname(actorForm.getSurname());
		principal.setPhone(actorForm.getPhone());
		return principal;
	}
	
	
	public boolean isWrongPassword(String confirmPassword) {
		User u = findByPrincipal();
		Assert.notNull(u);
		Md5PasswordEncoder password = new Md5PasswordEncoder();
		String encodedPassword = password.encodePassword(confirmPassword, null);
		return !u.getUserAccount().getPassword().equals(encodedPassword);
	}
	
	public Collection<User> usersSusTrip(int tripId){
		
		return userRepository.usersSusTrip(tripId);
		

	}


}
