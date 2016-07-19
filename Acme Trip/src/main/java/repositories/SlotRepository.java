package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Slot;

@Repository
public interface SlotRepository extends JpaRepository<Slot, Integer> {
	
	@Query("select d.slots from DailyPlan d where d.id = ?1")
	public Collection<Slot> slotByDailyPlan(int dailyPlanId);
	
	
	@Query("select a.slots from Activity a where a.id = ?1")
	public Collection<Slot> slotByActivity(int activityId);

}
