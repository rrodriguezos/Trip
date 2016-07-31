package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{
	
	@Query("select m from Message m where m.folder.id = ?1")
	public Collection<Message> messagesOfAFolder(int folderId);
	
	@Query("select count(*)/(select count(*)*1.0 from Actor a) from Message m")
	Double averageNumberMessagesPerActor();
	
		@Query("select m from Message m where m.folder.actor.id=?1 and m.star is true")
		Collection<Message> findStarsByActor(int actorId);

}
