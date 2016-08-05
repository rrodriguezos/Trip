package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

//	@Query("select c from Campaign c where c.manager.id = ?1")
//	Collection<Campaign> findCampaignsByManager(int managerId);
//
	@Query("select avg(c.endMoment-c.startMoment) from Campaign c")
	Double averageNumberDaysCampaignsLast();

	@Query("select stddev(c.endMoment-c.startMoment) from Campaign c")
	Double standardDeviationOfNumberDaysCampaignsLast();

}
