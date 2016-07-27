package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	

	//comments of a given commentable (trip or activity)
		@Query("select c from Comment c where c.commentable.id = ?1")
		Collection<Comment> findCommentsByCommentableId(int commentableId);

}
