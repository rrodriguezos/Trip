package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
	//Messages by folder
	@Query("select m from Message m where m.folder.id=?1 and m.favorite is false order by m.priority desc")
	Collection<Message> findMessagesByFolderId(int folderId);
	
	//Messages by actor
	@Query("select m from Message m where m.folder.actor.id=?1 and m.favorite is true order by m.priority desc")
	Collection<Message> findMessagesFavoritesByActor(int actorId);
}