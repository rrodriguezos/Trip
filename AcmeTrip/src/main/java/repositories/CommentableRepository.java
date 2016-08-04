package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Commentable;

@Repository
public interface CommentableRepository extends JpaRepository<Commentable, Integer>{
	
	@Query("select c from Commentable c where id = ?1")
	public Commentable findOne(int commentableId);
	
}
