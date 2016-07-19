package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	
	
	@Query("select u.activities from User u where u.id = ?1")
	public Collection<Activity> activitiesByUser(int userId);

}
