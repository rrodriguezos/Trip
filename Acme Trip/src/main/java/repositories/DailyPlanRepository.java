package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DailyPlan;

@Repository
public interface DailyPlanRepository extends JpaRepository<DailyPlan, Integer> {
	
	@Query("select t.dailyplans from Trip t where t.id = ?1")
	public Collection<DailyPlan> dailyPlansByTrip(int tripId);
	
	
	@Query("select avg(t.dailyplans.size) from Trip t")
	Double averageNumberOfDailyPlansByTrip();

	// It was not easy, but population standard deviation of dailyPlans by trips
	@Query("select stddev(t.dailyplans.size) from Trip t")
	Double standardDeviationOfDailyPlansByTrip();
	
	@Query("select a from DailyPlan a where a.trip.user.userAccount.id = ?1")
	DailyPlan findByUserAccountID(int UserAccountID);

}
