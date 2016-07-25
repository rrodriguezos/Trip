package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ChargeRecord;;

@Repository
public interface ChargeRecordRepository extends JpaRepository<ChargeRecord, Integer>  {
	
	
//	@Query("select c from ChargeRecord c where c.creditCard.id = ?1")
//	Collection<ChargeRecord> findChargeRecordsByCreditCard(int ccardId);
//	
//	
//	//no esta bien
//	@Query("select avg(c.creditCard.chargeRecord.amountMoney) from Campaing c")
//	Double averageNumberOfCommentsPerGym();

}
