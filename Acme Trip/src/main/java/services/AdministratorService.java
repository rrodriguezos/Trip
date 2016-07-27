package services;



import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Activity;
import domain.Administrator;
import domain.Comment;
import domain.Trip;
import domain.User;
import forms.AdministratorForm;
import forms.RegisterForm;
import forms.UserRegisterForm;

@Service
@Transactional
public class AdministratorService {
	
	
	//Managed repository -----------------------
		@Autowired
		private AdministratorRepository administratorRepository;
		
		
		//Supporting services ----------------------
		
		
		@Autowired
		private FolderService folderService;
		
		
		//Constructors -----------------------------
		public AdministratorService() {
			super();
		}
		
		//Simple CRUD methods ----------------------
		
		public Administrator create(){
			Administrator principal = this.findByPrincipal();
			Assert.notNull(principal);
			Administrator result = new Administrator();

			Authority auth = new Authority();
			auth.setAuthority("ADMINISTRATOR");
			Collection<Authority> lia = new ArrayList<Authority>();
			lia.add(auth);
			UserAccount ua = new UserAccount();
			ua.setAuthorities(lia);
			result.setUserAccount(ua);
			
			return result;
		}
		
		public Collection<Administrator> findAll() {
			Assert.notNull(this.findByPrincipal());
			Collection<Administrator> result = administratorRepository.findAll();
			Assert.notNull(result);
			return result;
		}

		public void save(Administrator administrator) {
			checkPrincipal();
			Assert.notNull(administrator);
			Assert.isTrue(administrator.getName() != ""
					&& administrator.getSurname() != "" && administrator.getEmailAddress()!= "");
			administratorRepository.saveAndFlush(administrator);
		}

		// Other Business Methods ---------------------------
		public Administrator findByUserAccount(UserAccount userAccount) {
			Assert.notNull(userAccount);
			Administrator result;
			result = administratorRepository.findByUserAccount(userAccount.getId());
			return result;
		}
		
		public Administrator findByPrincipal() {
			Administrator result;
			UserAccount userAccount;
			userAccount = LoginService.getPrincipal();
			Assert.notNull(userAccount);
			result = findByUserAccount(userAccount);
			Assert.notNull(result);
			return result;
		}

		public Administrator reconstruct(UserRegisterForm registerForm) {
			Administrator result = new Administrator();

			result.setComments(new ArrayList<Comment>());
			folderService.generateSystemFolders(result);
			result.setEmailAddress(registerForm.getEmailAddress());
			result.setName(registerForm.getName());
			result.setPhone(registerForm.getPhone());
			result.setSurname(registerForm.getSurname());

			Authority auth = new Authority();
			auth.setAuthority("ADMIN");
			Collection<Authority> lia = new ArrayList<Authority>();
			lia.add(auth);
			UserAccount ua = new UserAccount();
			ua.setAuthorities(lia);
			ua.setUsername(registerForm.getUsername());
			ua.setPassword(registerForm.getPassword());
			result.setUserAccount(ua);
			return result;
		}
		public void checkPrincipal() {
			Assert.isTrue(findByPrincipal() != null);
		}
		

}
