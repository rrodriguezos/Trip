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

	@Query("select t from Trip t where t.title like CONCAT(?1, '%') or t.description like CONCAT(?1, '%')")
	Collection<Trip> findTripByKeyword(String key);
	
	@Query("select d.trip from DailyPlan d where d.title like CONCAT(?1, '%') or d.description like CONCAT(?1, '%')")
	Collection<Trip> findTripByDailyKeyword(String key);
	
	@Query("select s.dailyPlan.trip from Slot s where s.title like CONCAT(?1, '%') or s.description like CONCAT(?1, '%')")
	Collection<Trip> findTripBySlotKeyword(String key);
	
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


	@Query("select u.tripSubscribes from User u where u.id=?1")
	Collection<Trip> findAllTripsSubscrito(int userId);

	@Query("select u.trips from User u where u.id=?1")
	Collection<Trip> findAllTripsCreatedByUserId(int userId);

	
	@Query("select avg(u.trips.size) from User u")
	Double averageNumberOfTripsByUser();


	@Query("select stddev(u.trips.size) from User u")
	Double standardDeviationOfTripsByUser();


} 
