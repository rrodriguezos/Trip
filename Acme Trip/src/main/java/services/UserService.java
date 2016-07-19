package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Administrator;
import domain.User;

import repositories.UserRepository;
import security.Authority;
import utilities.DPMessage;
import utilities.DPUtils;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class UserService {
	
	//Managed repository ----------------------
		@Autowired
		private UserRepository userRepository;
		
		//Supporting services ---------------------
		
		@Autowired
		private FolderService folderService;
		
		@Autowired
		private AdministratorService administratorService;
		
		//Constructors ----------------------------
		public UserService() {
			super();
		}
		
		//Simple CRUD methods ---------------------
		
		public Collection <User> findAll(){
			return userRepository.findAll();
		}
		
		public User create(){
			User result;
			result = new User();
			return result;
		}
		
		public User save(User user) {
			if(user.getId()==0){
				Assert.isTrue(!DPUtils.hasRole(Authority.USER,Authority.ADMIN), DPMessage.ALREADY_REGISTERED);
				Md5PasswordEncoder password = new Md5PasswordEncoder();
				String encodedPassword = password.encodePassword(user.getUserAccount().getPassword(), null);
				user.getUserAccount().setPassword(encodedPassword);
				Assert.notNull(user);
			}
			return userRepository.save(user);
		}
		
		//Other business methods ------------------
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
		

	
		public int totalNumberofUsersRegistered(){
			Administrator administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
			int numberOfUsers = findAll().size();
			return numberOfUsers;
		}

		
		
		public Collection<User> usersWhoRegisteredAtLeast80Maximum(){
			Administrator administrator = administratorService.findByPrincipal();
			Assert.notNull(administrator);
			Collection<User> result = userRepository.usersWhoRegisteredAtLeast80Maximum();
			return result;
		}
		
		
		
//		public User reconstruct(CustomerRegisterForm customerForm) {
//			User result = create();
//
//			result.setBookings(new ArrayList<Booking>());
//			result.setFeePayments(new ArrayList<FeePayment>());
//			result.setComments(new ArrayList<Comment>());
//			folderService.generateSystemFolders(result);
//			result.setLink(customerForm.getLink());
//			result.setName(customerForm.getName());
//			result.setNick(customerForm.getNick());
//			result.setPhone(customerForm.getPhone());
//			result.setPhoto(customerForm.getPhoto());
//			result.setSocialNetwork(customerForm.getSocialNetwork());
//			result.setSurname(customerForm.getSurname());
//			
//			Authority auth = new Authority();
//			auth.setAuthority("CUSTOMER");
//			Collection<Authority> lia = new ArrayList<Authority>();
//			lia.add(auth);
//			UserAccount ua = new UserAccount();
//			ua.setAuthorities(lia);
//			ua.setUsername(customerForm.getUsername());
//			ua.setPassword(customerForm.getPassword());
//			result.setUserAccount(ua);
//			return result;
//		}
//		
//		public Customer reconstruct(CustomerForm customerForm) {
//			Customer result = findByPrincipal();
//
//			result.setLink(customerForm.getLink());
//			result.setName(customerForm.getName());
//			result.setNick(customerForm.getNick());
//			result.setPhone(customerForm.getPhone());
//			result.setPhoto(customerForm.getPhoto());
//			result.setSocialNetwork(customerForm.getSocialNetwork());
//			result.setSurname(customerForm.getSurname());
//			
//			if (!customerForm.getPassword().equals("")){			
//				Md5PasswordEncoder password = new Md5PasswordEncoder();
//				String encodedPassword = password.encodePassword(customerForm.getPassword(), null);
//				result.getUserAccount().setPassword(encodedPassword);
//			}
//			return result;
//		}
		
		
		


}
