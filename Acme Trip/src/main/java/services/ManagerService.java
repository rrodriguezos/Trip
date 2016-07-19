package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ManagerRepository;
import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import utilities.DPMessage;
import utilities.DPUtils;
import security.UserAccount;
import domain.Manager;
import domain.User;

@Service
@Transactional
public class ManagerService {
	
	//Managed repository ----------------------
			@Autowired
			private ManagerRepository managerRepository;
			
			//Supporting services ---------------------
			
			@Autowired
			private FolderService folderService;
			
			//Constructors ----------------------------
			public ManagerService() {
				super();
			}
			
			//Simple CRUD methods ---------------------
			
			public Collection <Manager> findAll(){
				return managerRepository.findAll();
			}
			
			public Manager create(){
				Manager result;
				result = new Manager();
				return result;
			}
			
			public Manager save(Manager manager) {
				if(manager.getId()==0){
					Assert.isTrue(!DPUtils.hasRole(Authority.MANAGER,Authority.ADMIN), DPMessage.ALREADY_REGISTERED);
					Md5PasswordEncoder password = new Md5PasswordEncoder();
					String encodedPassword = password.encodePassword(manager.getUserAccount().getPassword(), null);
					manager.getUserAccount().setPassword(encodedPassword);
					Assert.notNull(manager);
				}
				return managerRepository.save(manager);
			}
			
			//Other business methods ------------------
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

//			public User reconstruct(CustomerRegisterForm customerForm) {
//				User result = create();
	//
//				result.setBookings(new ArrayList<Booking>());
//				result.setFeePayments(new ArrayList<FeePayment>());
//				result.setComments(new ArrayList<Comment>());
//				folderService.generateSystemFolders(result);
//				result.setLink(customerForm.getLink());
//				result.setName(customerForm.getName());
//				result.setNick(customerForm.getNick());
//				result.setPhone(customerForm.getPhone());
//				result.setPhoto(customerForm.getPhoto());
//				result.setSocialNetwork(customerForm.getSocialNetwork());
//				result.setSurname(customerForm.getSurname());
//				
//				Authority auth = new Authority();
//				auth.setAuthority("CUSTOMER");
//				Collection<Authority> lia = new ArrayList<Authority>();
//				lia.add(auth);
//				UserAccount ua = new UserAccount();
//				ua.setAuthorities(lia);
//				ua.setUsername(customerForm.getUsername());
//				ua.setPassword(customerForm.getPassword());
//				result.setUserAccount(ua);
//				return result;
//			}
//			
//			public Customer reconstruct(CustomerForm customerForm) {
//				Customer result = findByPrincipal();
	//
//				result.setLink(customerForm.getLink());
//				result.setName(customerForm.getName());
//				result.setNick(customerForm.getNick());
//				result.setPhone(customerForm.getPhone());
//				result.setPhoto(customerForm.getPhoto());
//				result.setSocialNetwork(customerForm.getSocialNetwork());
//				result.setSurname(customerForm.getSurname());
//				
//				if (!customerForm.getPassword().equals("")){			
//					Md5PasswordEncoder password = new Md5PasswordEncoder();
//					String encodedPassword = password.encodePassword(customerForm.getPassword(), null);
//					result.getUserAccount().setPassword(encodedPassword);
//				}
//				return result;
//			}
			
			
			

}
