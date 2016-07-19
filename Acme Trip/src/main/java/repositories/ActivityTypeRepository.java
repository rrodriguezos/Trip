package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ActivityType;
import domain.Slot;

@Repository
public interface ActivityTypeRepository  extends JpaRepository<ActivityType, Integer>{
	
	
	@Query("select a.activityType from Activity a where a.id = ?1")
	public ActivityType activityTypeByActivity(int activityId);

}
