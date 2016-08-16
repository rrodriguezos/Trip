package services;

import java.util.Collection;

import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.ChargeRecord;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/datasource.xml",
		"classpath:spring/config/packages.xml" })
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class ChargeRecordServiceTest extends AbstractTest {
	
	@Autowired
	private ChargeRecordService chargeRecordService;
	
	@Autowired
	private CreditCardService creditCardService;
	
	//8. An actor who is authenticated as a manager must be able to:
	// 8.2 Display the charge records that are associated with his or her credit cards
		@Test
		public void testFindChargeRecords() {
			authenticate("manager1");
			CreditCard cCard1 = creditCardService.findOne(19);
			CreditCard cCard2 = creditCardService.findOne(24);
			CreditCard cCard3 = creditCardService.findOne(29);
			CreditCard cCard4 = creditCardService.findOne(34);
			
			Collection<ChargeRecord> cRecords1 = cCard1.getChargeRecords();
			Collection<ChargeRecord> cRecords2 = cCard2.getChargeRecords();
			Collection<ChargeRecord> cRecords3 = cCard3.getChargeRecords();
			Collection<ChargeRecord> cRecords4 = cCard4.getChargeRecords();
			
			
			Assert.isTrue(cRecords1.size() == 1);
			Assert.isTrue(cRecords2.size() == 1);
			Assert.isTrue(cRecords3.size() == 1);
			Assert.isTrue(cRecords4.size() == 1);		
			
			
		}
		
		

}
