package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChargeRecordRepository;
import domain.Administrator;
import domain.ChargeRecord;

@Service
@Transactional
public class ChargeRecordService {
	
	
	//Managed repository ----------------------
		@Autowired
		private ChargeRecordRepository chargeRecordRepository;
		
		//Supporting services ---------------------
		@Autowired
		private AdministratorService administratorService;
		
		@Autowired
		private ManagerService managerService;
		
		@Autowired
		private CreditCardService creditCardService;

		
		//Constructor -----------------------------
		public ChargeRecordService() {
			super();
		}
		
		//Simple CRUD methods ---------------------
		public ChargeRecord findOne(int chargeRecordId) {
			Assert.isTrue(chargeRecordId > 0);
			ChargeRecord result = chargeRecordRepository.findOne(chargeRecordId);
			Assert.notNull(result);
			return result;
		}

		public ChargeRecord save(ChargeRecord chargeRecord) {
			Assert.notNull(chargeRecord);	
			return chargeRecordRepository.saveAndFlush(chargeRecord);
		}

		
		public Collection<ChargeRecord> findAll() {
			Collection<ChargeRecord> result = chargeRecordRepository.findAll();
			Assert.notNull(result);
			return result;
		}

		public Collection<ChargeRecord> findAllFromManagerPrincipal() {
			return chargeRecordRepository.findFromManagerPrincipal(managerService.findByPrincipal().getId());
		}
		
		

		
		

}
