package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DailyPlan;
import domain.Trip;
import java.util.Date;

@Repository 
public interface TripRepository extends JpaRepository<Trip, Integer> {
	
	@Query("select DISTINCT t from Trip t join t.dailyplans " +
			"r join r.slots s where (t.title like concat('%', concat(?1,'%')) or " +
			"t.description like concat('%', concat(?1,'%')) or " +
			"r.title like concat('%', concat(?1,'%')) or r.description like concat(" +
			"'%', concat(?1,'%')) or s.title like concat('%', concat(?1,'%'))or " +
			"s.description like concat('%', concat(?1,'%'))or s.activity.title like concat" +
			"('%', concat(?1,'%'))or s.activity.description like concat('%', concat(?1,'%')))")
	public Collection<Trip> searchByKeyword(String keyword);
	
	
	@Query("select u.trips from User u where u.id = ?1")
	public Collection<Trip> findTripsByUser(int userId);
	
	@Query("select d.trip from DailyPlan d where d.id = ?1")
	public Trip tripByDailyplan(int dailyplanId);
	
	
	@Query("select u.trips from User u where u.id=?1")
	Collection<Trip> findAllTripsByUserId(int userId);

	
	
	@Query("select avg(u.trips.size) from User u")
	Double averageNumberOfTripsByUser();

	// It was not easy, but population standard deviation of trips by users
	@Query("select stddev(u.trips.size) from User u")
	Double standardDeviationOfTripsByUser();
	
	@Query("select a from Trip a where a.user.userAccount.id = ?1")
	Collection<Trip> findByUserAccountID(int UserAccountID);
	

	@Query("select u.trips from User u where u.id=?1")
	Collection<Trip> findAllTripsCreatedByUserId(int userId);
	
	@Query("select u.tripSubscribes from User u where u.id=?1")
	Collection<Trip> findAllTripsSubscrito(int userId);
	
	@Query("select d from DailyPlan d where d.trip.id = ?1 and d.id not in" +
			"(select d2.id from DailyPlan d2 where d2.trip.id = ?1 and d2.weekDay between ?2 and ?3)")
	public Collection<DailyPlan> getDailyplansWrongsDates(int tripId, Date startTrip, Date endTrip);
	
	@Query("select count(t) from Trip t where t.user.id = ?1 and" +
			"(?2 between t.startDate and t.endDate or ?3 between t.startDate and t.endDate)")
	public int findOverlapByUser(int userId, Date startTrip, Date endTrip);

	
	

}
