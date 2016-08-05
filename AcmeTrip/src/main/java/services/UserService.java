package services;

import java.util.Collection;
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
import domain.Administrator;
import domain.Comment;
import domain.Folder;
import domain.Trip;
import domain.User;
import forms.UserRegisterForm;

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

	@Autowired
	private AdministratorService administratorService;

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

	// other methods
	// ----------------------------------------------------------------------

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

	public User reconstruct(UserRegisterForm userForm) {
		User res;
		res = create();
		Assert.isTrue(userForm.getPassword().equals(
				userForm.getConfirmPassword()));
		Assert.isTrue(userForm.getAccept());
		res.setName(userForm.getName());
		res.setPhone(userForm.getPhone());
		res.setSurname(userForm.getSurname());
		res.setEmailAddress(userForm.getEmailAddress());
		res.getUserAccount().setUsername(userForm.getUsername());
		res.getUserAccount().setPassword(userForm.getPassword());

		return res;
	}

	public UserRegisterForm copyUser() {
		UserRegisterForm result;
		User user;

		result = new UserRegisterForm();
		user = findByPrincipal();

		result.setName(user.getName());
		result.setSurname(user.getSurname());
		result.setId(user.getId());
		result.setEmailAddress(user.getEmailAddress());
		result.setPhone(user.getPhone());
		result.setUsername(user.getUserAccount().getUsername());
		result.setPassword(user.getUserAccount().getPassword());
		result.setConfirmPassword(user.getUserAccount().getPassword());
		result.setPasswordPast(user.getUserAccount().getPassword());
		result.setAccept(true);

		return result;
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

	public Boolean passActual(UserRegisterForm userForm) {
		User user;
		String passActual;
		Boolean result;

		user = findByPrincipal();

		Md5PasswordEncoder encoder;
		encoder = new Md5PasswordEncoder();

		passActual = encoder.encodePassword(userForm.getPasswordPast(), null);

		result = user.getUserAccount().getPassword().equals(passActual);

		return result;
	}

	public void reconstructPerfil(UserRegisterForm userForm) {
		User result;

		result = findByPrincipal();

		Assert.isTrue(userForm.getPassword().equals(
				userForm.getConfirmPassword()));

		result.setName(userForm.getName());
		result.setSurname(userForm.getSurname());
		result.setPhone(userForm.getPhone());
		result.setEmailAddress(userForm.getEmailAddress());
		if (userForm.getPassword() != "") {
			Md5PasswordEncoder encoder1;
			encoder1 = new Md5PasswordEncoder();

			result.getUserAccount().setPassword(
					encoder1.encodePassword(userForm.getPassword(), null));
		}

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
