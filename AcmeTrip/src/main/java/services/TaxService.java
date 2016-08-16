package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Banner;
import domain.Tax;
import repositories.TaxRepository;
import security.Authority;

@Service
@Transactional
public class TaxService {

	// Managed Repository ------------------------------
	@Autowired
	private TaxRepository taxRepository;

	// Supporting Services -----------------------------
	
	@Autowired
	private ActorService actorService;
	
	// Constructors ------------------------------------
	public TaxService() {
		super();
	}

	// Simple CRUD Methods -----------------------------
	public Tax create() {
		checkPrincipal();
		Tax result = new Tax();
		result.setBanners(new ArrayList<Banner>());
		return result;
	}

	public Tax save(Tax tax) {
		checkPrincipal();
		Assert.notNull(tax);
		return taxRepository.saveAndFlush(tax);
	}

	public void delete(Tax tax) {
		checkPrincipal();
		Assert.notNull(tax);
		taxRepository.delete(tax);
	}

	public Collection<Tax> findAll() {
		return taxRepository.findAll();
	}

	public Tax findOne(int id) {
		return taxRepository.findOne(id);
	}

	public void saveEdit(Tax tax) {
		Assert.notNull(tax);
		Tax res = taxRepository.findOne(tax.getId());
		res.setTaxType(tax.getTaxType());
		res.setBanners(tax.getBanners());
		taxRepository.saveAndFlush(res);
	}
	
	private void checkPrincipal(){
		Actor actor;
		Authority authority;
	
		actor = actorService.findByPrincipal();
		Assert.isTrue(actor != null);

		
		authority = new Authority();
		authority.setAuthority("ADMINISTRATOR");
		
		Assert.isTrue(actor.getUserAccount().getAuthorities().contains(authority));
	}

	// Other Business Methods ---------------------------

	// public Collection<Tax> taxesByChargeRecord(int chargeRecordId){
	// return taxRepository.taxesByChargeRecord(chargeRecordId);
	// }
	//
	// public Collection<Tax> taxesByBanner(int bannerId){
	// return taxRepository.taxesByBanner(bannerId);
	// }

}
