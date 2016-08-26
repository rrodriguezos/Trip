package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Activity;
import domain.ActivityType;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer>{

	@Query("select a.activityType from Activity a where a.id = ?1")
	ActivityType activityTypeByActivity(int activityId);
	

}
