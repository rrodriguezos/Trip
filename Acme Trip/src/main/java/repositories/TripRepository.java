package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DailyPlan;
import domain.Trip;

public interface TripRepository extends JpaRepository<Trip, Integer> {
	
	@Query("select u.trips from User u where (u.title like CONCAT('%',?1,'%') or u.description like CONCAT('%',?1,'%'))")
	public Collection<Trip> searchByKeyword(String keyword);
	
	
	@Query("select u.trips from User u where u.id = ?1")
	public Collection<Trip> tripsByUser(int userId);
	
	
	@Query("select avg(u.trips.size) from User u")
	Double averageNumberOfTripsByUser();

	// It was not easy, but population standard deviation of trips by users
	@Query("select stddev(u.trips.size) from User u")
	Double standardDeviationOfTripsByUser();

}
