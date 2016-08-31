package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CampaignRepository;
import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Activity;
import domain.Administrator;
import domain.Campaign;
import domain.ChargeRecord;
import domain.Comment;
import domain.CreditCard;
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

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private CampaignRepository campaignRepository;

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
		Collection<Campaign> campaigns;
		Collection<CreditCard> creditCards;

		Authority aut = new Authority();

		aut.setAuthority("MANAGER");
		useraccount = userAccountService.create();

		result = new Manager();

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);

		activities = new LinkedList<Activity>();
		result.setActivities(activities);

		campaigns = new LinkedList<Campaign>();
		result.setCampaigns(campaigns);

		creditCards = new LinkedList<CreditCard>();
		result.setCreditCards(creditCards);

		comments = new LinkedList<Comment>();
		result.setComments(comments);

		folders = new LinkedList<Folder>();
		result.setFolders(folders);

		return result;
	}

	public Collection<Manager> findAll() {

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
		if (manager.getId() == 0) {
			Md5PasswordEncoder encoder;

			create = true;
			encoder = new Md5PasswordEncoder();

			manager.getUserAccount().setPassword(
					encoder.encodePassword(manager.getUserAccount()
							.getPassword(), null));
		}
		manager = managerRepository.saveAndFlush(manager);
		Assert.notNull(manager);
		if (create) {
			folderService.foldersByDefect(manager);

		}
	}

	// other methods
	// -------------------------------------------------------------------------

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

	public Manager reconstruct(ManagerForm managerForm) {
		Manager res;
		res = create();
		Assert.isTrue(managerForm.getPassword().equals(
				managerForm.getConfirmPassword()));

		res.setName(managerForm.getName());
		res.setPhone(managerForm.getPhone());
		res.setSurname(managerForm.getSurname());
		res.setEmailAddress(managerForm.getEmailAddress());

		res.getUserAccount().setUsername(managerForm.getUsername());
		res.getUserAccount().setPassword(managerForm.getPassword());

		return res;
	}

	private void checkPrincipalAdministrator() {
		Administrator administrator;
		Authority authority;

		administrator = administratorService.findByPrincipal();
		Assert.isTrue(administrator != null);
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");

		Assert.isTrue(administrator.getUserAccount().getAuthorities()
				.contains(authority));
	}

	public Manager findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);
		Manager result;
		result = managerRepository.findByUserAccountId(userAccount.getId());
		return result;
	}

	// dashboard
	public Collection<Manager> managersMoreCampaigns() {
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		return managerRepository.managersMoreCampaigns();

	}

	public int minimumNumberOfCampaignsPerManager() {
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Integer res = managerRepository.minimumNumberOfCampaignsPerManager();
		return res == null ? 0 : res;
	}

	public int maximumNumberOfCampaignsPerManager() {
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Integer res = managerRepository.maximumNumberOfCampaignsPerManager();
		return res == null ? 0 : res;
	}

	public double averageNumberOfCampaignsPerManager() {
		Administrator administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		Double res = managerRepository.averageNumberOfCampaignsPerManager();
		return res == null ? 0.0 : res;
	}

	public void anadeTarjeta(CreditCard creditCard, Campaign campaign) {
		Manager principal = this.findByPrincipal();
		creditCard.setChargeRecords(new LinkedList<ChargeRecord>());
		Collection<CreditCard> tarjetas = principal.getCreditCards();
		tarjetas.add(creditCard);
		principal.setCreditCards(tarjetas);
		managerRepository.saveAndFlush(principal);
		creditCard.setManager(this.findByPrincipal());
		creditCard.setCampaign(campaign);
		CreditCard tarjeta = creditCardService.saveCreate(creditCard);
		campaign.setCreditCard(tarjeta);
		campaignRepository.save(campaign);
	}

}
