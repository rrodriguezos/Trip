package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DailyPlan;

@Repository
public interface DailyPlanRepository extends JpaRepository<DailyPlan, Integer> {
	
	@Query("select t.dailyPlans from Trip t where t.id = ?1")
	public Collection<DailyPlan> dailyPlansByTrip(int tripId);
	
	
	@Query("select avg(t.dailyPlans.size) from Trip t")
	Double averageNumberOfDailyPlansByTrip();

	// It was not easy, but population standard deviation of dailyPlans by trips
	@Query("select stddev(t.dailyPlans.size) from Trip t")
	Double standardDeviationOfDailyPlansByTrip();

}