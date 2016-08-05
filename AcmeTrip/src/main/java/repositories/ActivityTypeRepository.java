package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.ActivityType;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Integer>{

}
