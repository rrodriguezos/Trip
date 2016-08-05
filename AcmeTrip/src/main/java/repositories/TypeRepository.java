package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.ActivityType;

public interface TypeRepository extends JpaRepository<ActivityType, Integer>{

}
