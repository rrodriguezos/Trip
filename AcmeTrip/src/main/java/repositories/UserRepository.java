package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select c from User c where c.userAccount.id=?1")
	User findByUserAccountId(int userAccountId);

	// Dashboard C1
	@Query("select count(u) from User u")
	Integer getNumberOfUsers();

	// Dashboard C3
	@Query("select avg(u.trips.size), stddev(u.trips.size) from User u")
	Double[] getAverageNumberTripsPerUser();

	// Dashboard C5
	@Query("select u,(select max(u2.trips.size) from User u2) from User u where u.trips.size >= (select max(u2.trips.size) from User u2)*0.8")
	Collection<Object[]> getUsersCreated80MaximunNumbersOfTrips();

	// Dashboard C6
	@Query("select u from User u where u.id not in (select t.user.id from Trip t "
			+ "where (t.startDate between ?1 and ?2) and (t.endDate between ?1 and ?2)) and "
			+ "u.id not in (select c.actor.id from Comment c where c.moment between ?1 and ?2)")
	Collection<User> getUsersInactiveInLastYear(Date date1, Date date2);

	@Query("select t.users from Trip t where t.id=?1") 
	Collection<User> usersSusTrip(int tripId);
}
