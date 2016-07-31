package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Activity;
import domain.Slot;


@Repository
public interface ActivityRepository extends JpaRepository<Activity, Integer> {
	
	
	@Query("select u.activities from User u where u.id = ?1")
	public Collection<Activity> activitiesByUser(int userId);
	
	@Query("select s.activity from Slot s where s.id = ?1")
	public Activity activityBySlot(int slotId);
	
	@Query("select a.activities from ActivityType a where a.id = ?1")
	public Collection<Activity> activitiesByActivityType(int activitytypeId);
	
	@Query("select a from Activity a where a.isAppropiate is true")
	Collection<Activity> findIsAppropriated();

}
