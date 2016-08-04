package services;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Activity;
import domain.Comment;
import domain.Folder;
import domain.Trip;
import domain.User;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	// Managed repository -------------------------

	@Autowired
	private UserRepository userRepository;

	// Supporting Services -------------------------

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private FolderService folderService;

	// Constructors -------------------------------
	public UserService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public User create() {
		UserAccount useraccount;
		User result;
		Collection<Folder> folders;
		Collection<Trip> trips;
		Collection<Activity> activities;
		Collection<Comment> comments;

		Authority aut = new Authority();

		aut.setAuthority("USER");
		useraccount = userAccountService.create();

		result = new User();

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);

		trips = new LinkedList<Trip>();
		result.setTrips(trips);
		
		activities = new LinkedList<Activity>();
		result.setActivities(activities);
		
		comments = new LinkedList<Comment>();
		result.setComments(comments);
		
		folders = new LinkedList<Folder>();
		result.setFolders(folders);
		
		return result;
	}

	public Collection<User> findAll() {
		Collection<User> result;

		result = userRepository.findAll();

		return result;
	}

	public User findOne(int userId) {
		User result;

		result = userRepository.findOne(userId);

		return result;
	}

	public void save(User user) {

		Boolean create;
		create = false;

		// Comprobamos si se está creando el user
		if (user.getId() == 0) {
			Md5PasswordEncoder encoder;

			create = true;
			encoder = new Md5PasswordEncoder();

			user.getUserAccount().setPassword(
					encoder.encodePassword(user.getUserAccount().getPassword(),
							null));

		}
		

		user = userRepository.save(user);
		Assert.notNull(user);
		if (create) {
			folderService.foldersByDefect(user);

		}
	
	}

	// other methods ------------------------------

	// User logging in the system
	public User findByPrincipal() {
		UserAccount userAccount;
		User result;
		int id;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		id = userAccount.getId();
		result = userRepository.findByUserAccountId(id);
		Assert.notNull(result);

		return result;

	}

	// Reconstruir un User, para el registro en la sistema
	public User reconstruct(UserForm userForm) {
		User res;

		res = create();
		// Comprobamos que las contraseñas sean iguales
		Assert.isTrue(userForm.getPassword().equals(userForm.getPasswordRepeat()));

		// Comprobamos que el usuario acepta las condiciones y términos del
		// servicio
		 Assert.isTrue(userForm.getAccept());

		// Insertamos todos los datos en el user
		res.setName(userForm.getName());
		res.setPhone(userForm.getPhone());
		res.setSurname(userForm.getSurname());
		res.setEmailAddress(userForm.getEmail());

		res.getUserAccount().setUsername(userForm.getUsername());
		res.getUserAccount().setPassword(userForm.getPassword());

		return res;
	}
	
	public UserForm copyUser() {
		UserForm result;
		User user;

		result = new UserForm();
		user = findByPrincipal();

		result.setName(user.getName());
		result.setSurname(user.getSurname());
		result.setId(user.getId());
		result.setEmail(user.getEmailAddress());
		result.setPhone(user.getPhone());
		result.setUsername(user.getUserAccount().getUsername());
		result.setPassword(user.getUserAccount().getPassword());
		result.setPasswordRepeat(user.getUserAccount().getPassword());
		result.setPasswordActual(user.getUserAccount().getPassword());
		result.setAccept(true);

		return result;
	}
	
	// Dashboard C1
	public Integer getNumberOfUsers(){
		Integer result;
		
		result = userRepository.getNumberOfUsers();
		
		return result;
	}
	
	// Dashboard C3
	public Double[] getAverageNumberTripsPerUser(){
		Double[] result;
		
		result = userRepository.getAverageNumberTripsPerUser();
		
		return result;
	}
	
	// Dashboard C5
	public Collection<Object[]> getUsersCreated80MaximunNumbersOfTrips(){
		Collection<Object[]> result;
		
		result = userRepository.getUsersCreated80MaximunNumbersOfTrips();
		
		return result;
	}
	
	// Dashboard C6
	public Collection<User> getUsersInactiveInLastYear(){
		Collection<User> result;
		Date date1, date2;
		
		date1 = new Date();
		date1.setYear(date1.getYear()-1);
		Assert.notNull(date1);
		
		date2 = new Date();
		Assert.notNull(date2);
		
		result = userRepository.getUsersInactiveInLastYear(date1, date2);
		
		return result;
	}
	
	public Boolean passActual(UserForm userForm){
		User user;
		String passActual;
		Boolean result;
		
		user = findByPrincipal();
		
		// comprobamos que la contraseña actual es correcta

		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();

		passActual = encoder.encodePassword(userForm.getPasswordActual(), null);

		result = user.getUserAccount().getPassword().equals(passActual);
		
		return result;
	}
	
	// reconstruir un user al modificar su perfil
	public void reconstructPerfil(UserForm userForm) {
		User result;

		result = findByPrincipal();

		// comprobamos que las contraseñas son iguales

		Assert.isTrue(
				userForm.getPassword().equals(userForm.getPasswordRepeat()));

		// Insertamos todos los datos en el user.

		result.setName(userForm.getName());
		result.setSurname(userForm.getSurname());
		result.setPhone(userForm.getPhone());
		result.setEmailAddress(userForm.getEmail());


		// HASHEAR LA CONTRASEÑA
		if (userForm.getPassword() != "") {
			Md5PasswordEncoder encoder1;
			encoder1 = new Md5PasswordEncoder();

			result.getUserAccount().setPassword(
					encoder1.encodePassword(userForm.getPassword(), null));
		}

		// Mejorar la seguridad

		Assert.isTrue(findByPrincipal().getId() == (userForm.getId()));

		save(result);

	}
	

	public User findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		User result;
		result = userRepository.findByUserAccountId(userAccount.getId());
		return result;
	}
	
	public Collection<User> usersSusTrip(int tripId) {

		return userRepository.usersSusTrip(tripId);

	}



}
