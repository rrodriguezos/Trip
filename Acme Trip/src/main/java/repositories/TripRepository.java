package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DailyPlan;
import domain.Trip;

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
	

	
	
	@Query("select avg(u.trips.size) from User u")
	Double averageNumberOfTripsByUser();

	// It was not easy, but population standard deviation of trips by users
	@Query("select stddev(u.trips.size) from User u")
	Double standardDeviationOfTripsByUser();
	
	@Query("select a from Trip a where a.user.userAccount.id = ?1")
	Trip findByUserAccountID(int UserAccountID);
	
	

}
