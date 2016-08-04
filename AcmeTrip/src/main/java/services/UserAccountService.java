package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import security.UserAccount;
import security.UserAccountRepository;

@Service
@Transactional
public class UserAccountService {

	// Managed repositories------------------------------------

	@Autowired
	private UserAccountRepository userAccountRepository;

	// Constructor---------------------------------------------

	public UserAccountService() {
		super();
	}

	// Methods-------------------------------------------------

	// Simple CRUDS methods--------------------------------

	public UserAccount create() {

		UserAccount result = new UserAccount();

		return result;

	}

	public void save(UserAccount userAccount) {

		Assert.notNull(userAccount);

		userAccountRepository.save(userAccount);

	}

}
