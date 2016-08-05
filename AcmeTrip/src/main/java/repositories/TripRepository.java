package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository; 

import domain.DailyPlan;
import domain.Trip; 

@Repository 
public interface TripRepository extends JpaRepository<Trip, Integer>{ 

	//Buscar un Trip poor una cadena
	@Query("select DISTINCT t from Trip t join t.dailyPlans r join r.slots s where (t.title like concat('%', concat(?1,'%')) or t.description like concat('%', concat(?1,'%')) or r.title like concat('%', concat(?1,'%')) or r.description like concat('%', concat(?1,'%')) or s.title like concat('%', concat(?1,'%'))or s.description like concat('%', concat(?1,'%'))or s.activity.title like concat('%', concat(?1,'%'))or s.activity.description like concat('%', concat(?1,'%')))")
	Collection<Trip> findTripByString(String key);
	
	@Query("select t from Trip t where t.user.id = ?1")
	Collection<Trip> findTripByUser(int userId);
	
	@Query("select DISTINCT t from Trip t join t.dailyPlans r join r.slots s where s.activity.id = ?1")
	Collection<Trip> findTripByActivity(int activityId);
	
	
	@Query("select count(t) from Trip t where t.user.id = ?1 and" +
			"(?2 between t.startDate and t.endDate or ?3 between t.startDate and t.endDate)")
	public int findOverlappedByUser(int userId, Date startTrip, Date endTrip);
	
	@Query("select d from DailyPlan d where d.trip.id = ?1 and d.id not in" +
			"(select d2.id from DailyPlan d2 where d2.trip.id = ?1 and d2.weekDay between ?2 and ?3)")
	public Collection<DailyPlan> getDailyPlansOutDates(int tripId, Date startTrip, Date endTrip);
	
	// Dashboard C2
	@Query("select count(t) from Trip t")
	Integer getNumberTripsRegistered();
	
	// Dashboard C4
	@Query("select avg(t.dailyPlans.size), stddev(t.dailyPlans.size) from Trip t")
	Double[] getAverageNumberDailyPlansPerTrip();

	@Query("select u.tripSubscribes from User u where u.id=?1")
	Collection<Trip> findAllTripsSubscrito(int userId);

	@Query("select u.trips from User u where u.id=?1")
	Collection<Trip> findAllTripsCreatedByUserId(int userId);
} 
