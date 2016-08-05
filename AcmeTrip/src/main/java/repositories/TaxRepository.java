package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Tax;

@Repository
public interface TaxRepository  extends JpaRepository<Tax, Integer>{
	
	
	
//	@Query("select c.taxes from ChargeRecord c where c.id = ?1")
//	public Collection<Tax> taxesByChargeRecord(int chargeRecordId);
//	
//	@Query("select b.taxes from Banner b where b.id = ?1")
//	public Collection<Tax> taxesByBanner(int bannerId);
//	


}
