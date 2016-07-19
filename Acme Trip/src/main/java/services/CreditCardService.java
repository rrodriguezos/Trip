package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Manager;

import repositories.CreditCardRepository;

@Service
@Transactional
public class CreditCardService {

	// Managed repository ----------------------
	@Autowired
	private CreditCardRepository creditCardRepository;

	// Supporting services ---------------------

	@Autowired
	private ManagerService managerService;

	// Constructor -----------------------------
	public CreditCardService() {
		super();
	}

	// Simple CRUD methods ---------------------
	public CreditCard findOne(int creditCardId) {
		Assert.isTrue(creditCardId > 0);
		CreditCard result = creditCardRepository.findOne(creditCardId);
		Assert.notNull(result);
		return result;
	}

	public void save(CreditCard creditCard) {
		Assert.notNull(creditCard);
		Manager manager = managerService.findByPrincipal();
		Assert.isTrue(manager.getId() == creditCard.getManager().getId());
		creditCardRepository.save(creditCard);
		creditCardRepository.flush();
	}

	public Collection<CreditCard> findAll() {
		Collection<CreditCard> result = creditCardRepository.findAll();
		Assert.notNull(result);
		return result;
	}

	// Other business methods ------------------	

		

	

	public Collection<CreditCard> findCreditCardByPrincipal() {
		Manager manager = managerService.findByPrincipal();
		Collection<CreditCard> result = creditCardRepository.findCreditCardsByManager(manager
				.getId());
		Assert.notNull(result);
		return result;
	}

	public Collection<CreditCard> findCreditCardsByManager(int managerId) {
		Manager manager = managerService.findOne(managerId);
		Collection<CreditCard> result = creditCardRepository.findCreditCardsByManager(manager
				.getId());
		Assert.notNull(result);
		return result;
	}



//	public Barter reconstruct(BarterForm barterForm) {
//		Barter barter = new Barter();
//		User user = managerService.findByPrincipal();
//		barter.setRegistrationMoment(new Date(System.currentTimeMillis() - 1000));
//		barter.setTitle(barterForm.getTitle());
//		barter.setCancelled(false);
//		barter.setUser(user);
//		barter.setRelatedBarters(new ArrayList<Barter>());
//		barter.setMatches(new ArrayList<Match>());
//		Item offeredItem = new Item();
//		Item requestedItem = new Item();
//		offeredItem.setDescription(barterForm.getOfferedItemDescription());
//		offeredItem.setName(barterForm.getOfferedItemName());
//		offeredItem.setPictures(barterForm.getOfferedItemPictures());
//		requestedItem.setDescription(barterForm.getRequestedItemDescription());
//		requestedItem.setName(barterForm.getRequestedItemName());
//		requestedItem.setPictures(barterForm.getRequestedItemPictures());
//		barter.setOfferedItem(offeredItem);
//		barter.setRequestedItem(requestedItem);
//		return barter;
//	}

}
