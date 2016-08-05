package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query; 
import org.springframework.stereotype.Repository; 

import domain.DailyPlan; 

@Repository 
public interface DailyPlanRepository extends JpaRepository<DailyPlan, Integer>{ 

	
	@Query("select d from DailyPlan d where d.trip.id = ?1")
	Collection<DailyPlan> dailyPlayByTrip(int tripId);

	@Query("select count(t) from Trip t where t.id = ?1 and ?2 between t.startDate and t.endDate")
	Integer findDailyPlanDateBetweenTripDates(int tripId, Date date);
	
	@Query("select avg(t.dailyPlans.size) from Trip t")
	Double averageNumberOfDailyPlansByTrip();


	@Query("select stddev(t.dailyPlans.size) from Trip t")
	Double standardDeviationOfDailyPlansByTrip();
} 
