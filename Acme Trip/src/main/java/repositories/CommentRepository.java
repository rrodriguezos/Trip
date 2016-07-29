package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
	

	// appropiate comments of a given commentable (activity or trip)
		@Query("select c from Comment c where c.commentable.id = ?1 and c.isAppropiate = true")
		Collection<Comment> findCommentsByCommentableId(int commentableId);

}
