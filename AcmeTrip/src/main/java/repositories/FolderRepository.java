package repositories;


import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;
import domain.Message;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
	//Folders by user account
	@Query("select f from Folder f where f.actor.userAccount.id=?1")
	Collection<Folder> findFoldersByUserAccount(int userAccountId);
	
	@Query("select f from Folder f where f.name = ?1 and f.actor.id = ?2 and f.systemFolder is true")
	Folder findFolder(String nameFolder, int actorId);
	
	@Query("select f from Folder f where f.name = ?1 and f.actor.id = ?2 and f.systemFolder = true")
	Folder findSystemFolder(String nameFolder, int actorId);
	
	@Query("select f from Folder f where f.systemFolder = false and f.actor.userAccount.id = ?1")
	Collection<Folder> findFoldersToMoveByUserAccount(int userAccountId);
	
	@Query("select m from Folder f join f.messages m where f.id = ?1 and m.star is true")
	Collection<Message> messagesFavoritesByFolder(int folderId);
	
	@Query("select f from Folder f where f.actor.id = ?1 and f.name = 'Starredfolder'")
	Folder findStarredFolderByActor(int actorId);
}
