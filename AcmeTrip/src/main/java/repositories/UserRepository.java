package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select c from User c where c.userAccount.id=?1")
	User findByUserAccountId(int userAccountId);


	@Query("select t.users from Trip t where t.id=?1") 
	Collection<User> usersSusTrip(int tripId);

	// The users who have registered at least 80% the maximum number of trips that a user has registered.
			@Query("select u from User u where u.trips.size >= 0.8*(select max(u2.trips.size) from User u2)")
			Collection<User> usersWhoRegisteredAtLeast80Maximum();
}
