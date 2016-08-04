package services;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.Comment;
import domain.Folder;
import forms.AdministratorForm;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdministratorService {

	// Managed repository -------------------------

	@Autowired
	private AdministratorRepository administratorRepository;

	// Supporting Services -------------------------
	@Autowired
	private FolderService folderService;
	
	@Autowired
	private UserAccountService userAccountService;

	// Constructors -------------------------------
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods -----------------------------
	public Administrator create() {
		checkPrincipalAdministrator();
		
		UserAccount useraccount;
		Administrator result;
		Collection<Folder> folders;
		Collection<Comment> comments;

		Authority aut = new Authority();

		aut.setAuthority("ADMINISTRATOR");
		useraccount = userAccountService.create();

		result = new Administrator();

		useraccount.addAuthority(aut);
		result.setUserAccount(useraccount);
		
		comments = new LinkedList<Comment>();
		result.setComments(comments);
		
		folders = new LinkedList<Folder>();
		result.setFolders(folders);
		
		return result;
	}

	public Collection<Administrator> findAll() {
		checkPrincipalAdministrator();
		
		Collection<Administrator> result;

		result = administratorRepository.findAll();

		return result;
	}

	public Administrator findOne(int administratorId) {
		Administrator result;

		result = administratorRepository.findOne(administratorId);

		return result;
	}

	public void save(Administrator administrator) {
		checkPrincipalAdministrator();
		
		Boolean create;
		create = false;

		// Comprobamos si se está creando el user
		if (administrator.getId() == 0) {
			Md5PasswordEncoder encoder;

			create = true;
			encoder = new Md5PasswordEncoder();

			administrator.getUserAccount().setPassword(
					encoder.encodePassword(administrator.getUserAccount().getPassword(),
							null));

		}
		

		administrator = administratorRepository.save(administrator);
		Assert.notNull(administrator);
		if (create) {
			folderService.foldersByDefect(administrator);

		}
	
	}
	
	//Other bussiness methods -----------------------------------------------------
	// Reconstruir un User, para el registro en la sistema
	public Administrator reconstruct(AdministratorForm administratorForm) {
		Administrator res;

		res = create();

		// Comprobamos que las contraseñas sean iguales
		Assert.isTrue(
				administratorForm.getPassword().equals(administratorForm.getPasswordRepeat()));

		// Insertamos todos los datos en el user
		res.setName(administratorForm.getName());
		res.setPhone(administratorForm.getPhone());
		res.setSurname(administratorForm.getSurname());
		res.setEmailAddress(administratorForm.getEmail());

		res.getUserAccount().setUsername(administratorForm.getUsername());
		res.getUserAccount().setPassword(administratorForm.getPassword());

		return res;
	}
		
	public Administrator findByPrincipal(){
		Administrator result;
		UserAccount userAccount;
		
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		result = findByUserAccount(userAccount);
		Assert.notNull(result);

		return result;
	}
	
	public Administrator findByUserAccount(UserAccount userAccount) {
		Assert.notNull(userAccount);

		Administrator result;

		result = administratorRepository.findByUserAccountId(userAccount.getId());		

		return result;
	}
	
	private void checkPrincipalAdministrator(){
		Administrator administrator;
		Authority authority;
	
		administrator = findByPrincipal();
		Assert.isTrue(administrator != null);
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		
		Assert.isTrue(administrator.getUserAccount().getAuthorities().contains(authority));
	}

}
