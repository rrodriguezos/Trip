package repositories;
import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Folder;
@Repository
public interface FolderRepository extends JpaRepository<Folder, Integer> {
	
	@Query("select f from Folder f where f.actor.id = ?1")
	Collection<Folder> foldersOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Trash folder' and f.actor.id = ?1)")
	Folder foldersTrashOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'In folder' and f.actor.id = ?1)")
	Folder foldersInFolderOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Out folder' and f.actor.id = ?1)")
	Folder foldersOutFolderOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Spam folder' and f.actor.id = ?1)")
	Folder foldersSpamFolderOfActor(int actorId);
	
	@Query("select f from Folder f where (f.name= 'Starred folder' and f.actor.id = ?1)")
	Folder foldersStarredFolderOfActor(int actorId);
	
	@Query("select f from Folder f where f.systemFolder = false and f.actor.userAccount.id = ?1")
	Collection<Folder> findFoldersAndMoveByPrincipal(int userAccountId);
	
	@Query("select f from Folder f where f.name = ?1 and f.actor.id = ?2 and f.systemFolder is true")
	Folder findFolderByString(String nameFolder, int actorId);

}
