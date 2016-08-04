package services;

import java.util.Collection;
import java.util.LinkedList;

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
import domain.Comment;
import domain.Folder;
import domain.Manager;
import forms.ManagerForm;

@Service
@Transactional
public class ManagerService {

	// Managed repository -------------------------

	@Autowired
	private ManagerRepository managerRepository;

	// Supporting Services -------------------------

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private AdministratorService administratorService;

	// Constructors -------------------------------
	public ManagerService() {
		super();
	}

	// Simple CRUD methods -----------------------------

	public Manager create() {
		checkPrincipalAdministrator();
		
		UserAccount useraccount;
		Manager result;
		Collection<Folder> folders;
		Collection<Comment> comments;
		Collection<Activity> activities;

		Authority aut = new Authority();

		aut.setAuthority("MANAGER");
		useraccount = userAccountService.create();

		result = new Manager();

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);
		
		activities = new LinkedList<Activity>();
		result.setActivities(activities);
		
		comments = new LinkedList<Comment>();
		result.setComments(comments);
		
		folders = new LinkedList<Folder>();
		result.setFolders(folders);
		
		return result;
	}

	public Collection<Manager> findAll() {
		checkPrincipalAdministrator();
		
		Collection<Manager> result;

		result = managerRepository.findAll();

		return result;
	}

	public Manager findOne(int managerId) {
		Manager result;

		result = managerRepository.findOne(managerId);

		return result;
	}

	public void save(Manager manager) {

		Boolean create;
		create = false;

		// Comprobamos si se est� creando el user
		if (manager.getId() == 0) {
			Md5PasswordEncoder encoder;

			create = true;
			encoder = new Md5PasswordEncoder();

			manager.getUserAccount().setPassword(
					encoder.encodePassword(manager.getUserAccount().getPassword(),
							null));

		}
		

		manager = managerRepository.save(manager);
		Assert.notNull(manager);
		if (create) {
			folderService.foldersByDefect(manager);

		}
	
	}

	// other methods ------------------------------

	// User logging in the system
	public Manager findByPrincipal() {
		UserAccount userAccount;
		Manager result;
		int id;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		id = userAccount.getId();
		result = managerRepository.findByUserAccountId(id);
		Assert.notNull(result);

		return result;

	}

	// Reconstruir un Manager, para el registro en la sistema
	public Manager reconstruct(ManagerForm managerForm) {
		Manager res;

		res = create();

		// Comprobamos que las contrase�as sean iguales
		Assert.isTrue(
				managerForm.getPassword().equals(managerForm.getPasswordRepeat()));

		// Insertamos todos los datos en el user
		res.setName(managerForm.getName());
		res.setPhone(managerForm.getPhone());
		res.setSurname(managerForm.getSurname());
		res.setEmailAddress(managerForm.getEmail());

		res.getUserAccount().setUsername(managerForm.getUsername());
		res.getUserAccount().setPassword(managerForm.getPassword());

		return res;
	}
	
	private void checkPrincipalAdministrator(){
		Administrator administrator;
		Authority authority;
	
		administrator = administratorService.findByPrincipal();
		Assert.isTrue(administrator != null);
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		
		Assert.isTrue(administrator.getUserAccount().getAuthorities().contains(authority));
	}

	public Manager findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager result;
		result = managerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

}
