package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Manager;
import domain.User;

@Repository
public interface ManagerRepository  extends JpaRepository<Manager, Integer>{
	
	@Query("select m from Manager m where m.userAccount.id = ?1")
	Manager findByUserAccount(int userAccountId);
	
	

		@Query("select min(m.campaigns.size) from Manager m")
		Integer minimumNumberOfCampaignsPerManager();
		

		@Query("select max(m.campaigns.size) from Manager m")
		Integer maximumNumberOfCampaignsPerManager();
		

		@Query("select avg(m.campaigns.size) from Manager m")	
		Double averageNumberOfCampaignsPerManager();
		
		@Query("select m from Manager m where m.campaigns.size = (select max(ms.campaigns.size) from Manager ms)")
		Collection<Manager> managersMoreCampaigns();

}
