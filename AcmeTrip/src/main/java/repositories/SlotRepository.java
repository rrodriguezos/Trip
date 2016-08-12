package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.data.jpa.repository.Query; 
import org.springframework.stereotype.Repository; 

import domain.Slot;
import domain.Trip; 

@Repository 
public interface SlotRepository extends JpaRepository<Slot, Integer>{

	@Query("select s from Slot s where s.dailyPlan.id = ?1")
	Collection<Slot> slotsByDailyPlan(int dailyPlanId); 

	@Query("select count(s) from Slot s where s.dailyPlan.id = ?1 and (?2 between s.startTime and s.endTime or ?3 between s.startTime and s.endTime)")
	int checkOverlapping(int slotId, Date date1, Date date2); 
	
	@Query("select a.slots from Activity a where a.title like CONCAT(?1, '%') or a.description like CONCAT(?1, '%')")
	Collection<Slot> findTripByActivityKeyword(String key);
}