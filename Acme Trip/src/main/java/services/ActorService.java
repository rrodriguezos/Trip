package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository -------------------
	@Autowired
	private ActorRepository actorRepository;

	// Supporting services ------------------
	@Autowired
	private ManagerService managerService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private UserService userService;

	// Constructor --------------------------
	public ActorService() {
		super();
	}

	// Simple CRUD methods ------------------

	// Other business methods ---------------
	public Actor findByPrincipal() {
		Actor result;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);
		return result;
	}

	public Actor findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;
		Actor user = userService.findByUserAccount(userAccount);
		Actor admin = administratorService.findByUserAccount(userAccount);
		Actor manager = managerService.findByUserAccount(userAccount);
		if (user == null) {
			result = admin;
			if (admin == null) {
				result = manager;
			}
		} else {
			result = user;
		}
		Assert.notNull(result);
		return result;

	}

	public Collection<Actor> findAll() {
		Collection<Actor> result = actorRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	public Actor findOne(int id) {
		Assert.isTrue(id != 0);
		Actor result = actorRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

}
