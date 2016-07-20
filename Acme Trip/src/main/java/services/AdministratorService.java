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
import domain.Administrator;
import forms.AdministratorForm;

@Service
@Transactional
public class AdministratorService {
	
	
	//Managed repository -----------------------
		@Autowired
		private AdministratorRepository administratorRepository;
		
		//Supporting services ----------------------
		
		
		//Constructors -----------------------------
		public AdministratorService() {
			super();
		}
		
		//Simple CRUD methods ----------------------
		
		//An administrators must be able to register a new trainer to the system
		public Administrator create(){
			Administrator result = new Administrator();
			
			Authority auth = new Authority();
			auth.setAuthority("ADMIN");
			Collection<Authority> lia = new ArrayList<Authority>();
			lia.add(auth);
			UserAccount ua = new UserAccount();
			ua.setAuthorities(lia);
			result.setUserAccount(ua);
			return result;
		}
		
		
		public Collection<Administrator> findAll(){
			return administratorRepository.findAll();
		}
		
		public Administrator save(Administrator administrator){
			Administrator ad = findByPrincipal();
			Assert.notNull(ad);
			return administratorRepository.save(administrator);
		}
		
		//Other business methods -------------------
		public Administrator findByPrincipal() {
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
			result = administratorRepository.findByUserAccount(userAccount.getId());
			return result;
		}

		public Administrator reconstruct(AdministratorForm administratorForm) {
			Administrator result = findByPrincipal();

			result.setName(administratorForm.getName());
			result.setPhone(administratorForm.getPhone());
			result.setSurname(administratorForm.getSurname());
			result.setEmailAddress(administratorForm.getEmailAddress());
			
			if (!administratorForm.getPassword().equals("")){			
				Md5PasswordEncoder password = new Md5PasswordEncoder();
				String encodedPassword = password.encodePassword(administratorForm.getPassword(), null);
				result.getUserAccount().setPassword(encodedPassword);
			}
			return result;
		}
		

}
