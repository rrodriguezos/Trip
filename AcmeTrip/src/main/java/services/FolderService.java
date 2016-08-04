package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;

import repositories.FolderRepository;

@Service
@Transactional
public class FolderService {

	//Managed repository -------------------------------
	@Autowired
	private FolderRepository folderRepository;
	
	//Supporting services ------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private MessageService messageService;
	
	//Constructor --------------------------------------
	public FolderService(){
		super();
	}
	// Simple CRUD methods -----------------------------
	public Folder create(){
		Folder result;
		Collection<Message> messages;
		Actor actor;
		
		messages = new ArrayList<Message>();
		actor=actorService.findByPrincipal();
		
		result = new Folder();
		result.setMessages(messages);
		result.setActor(actor);
		
		return result;
	}
	
	public Collection<Folder> findAll(){
		Collection<Folder> result;
		
		result = folderRepository.findAll();
		
		return result;
	}
	
	public Folder save(Folder folder){			
		Assert.notNull(folder);
		
		//CONCURRENCY CHECK 
		if(folder.getId()!=0){
			Folder folderCheck = folderRepository.findOne(folder.getId());
			Assert.isTrue(folder.getVersion() == folderCheck.getVersion());
		}
		if(folder.getId()!=0){
			checkPrincipalActor(folder);
		}		

		Folder result;
		
		result = folderRepository.save(folder);
		
		return result;
	}
	
	public void delete(Folder folder){
		Assert.notNull(folder);
		Assert.isTrue(!folder.getSystem());
		checkPrincipalActor(folder);
		
		Collection<Message> messages;
		
		messages = folderRepository.messagesFavoritesByFolder(folder.getId());
		
		for(Message m: messages){
			folder.getMessages().remove(m);
			m.setFolder(folderRepository.findStarredFolderByActor(actorService.findByPrincipal().getId()));
			messageService.save(m);
		}
		
		folderRepository.delete(folder);
	}
	
	public Folder findOne(int folderId){
		Assert.isTrue(folderId!=0);
		
		Folder result;
		
		result = folderRepository.findOne(folderId);
		Assert.notNull(result);
		
		return result;
	}
	
	public Folder findOneEdit(int folderId){
		Assert.isTrue(folderId!=0);
		
		Folder result;
		
		result = folderRepository.findOne(folderId);
		
		checkPrincipalActor(result);
		
		Assert.notNull(result);
		Assert.isTrue(!result.getSystem());
		
		return result;
	}
	
	// Other methods -------------------------------------
	
	// Este metodo crea los cinco folders de un actor, se le debe pasar el id del actor
	public void foldersByDefect(Actor actor){
		Folder infolder, outfolder, trashfolder, spamfolder, starredfolder;
		Collection<Message> messages1, messages2, messages3, messages4, messages5;
		
		Assert.notNull(actor);
		
		messages1 = new ArrayList<Message>();
		messages2 = new ArrayList<Message>();
		messages3 = new ArrayList<Message>();
		messages4 = new ArrayList<Message>();
		messages5 = new ArrayList<Message>();
		
		infolder = new Folder();
		Assert.notNull(infolder);
		outfolder = new Folder();
		Assert.notNull(outfolder);
		trashfolder = new Folder();
		Assert.notNull(trashfolder);
		spamfolder = new Folder();
		Assert.notNull(spamfolder);
		starredfolder = new Folder();
		Assert.notNull(starredfolder);
		
		// Se les asigna el actor y la propiedad system a true
		infolder.setActor(actor);
		infolder.setName("Infolder");
		infolder.setSystem(true);
		infolder.setMessages(messages1);
		
		outfolder.setActor(actor);
		outfolder.setName("Outfolder");
		outfolder.setSystem(true);
		outfolder.setMessages(messages2);
		
		trashfolder.setActor(actor);
		trashfolder.setName("Trashfolder");
		trashfolder.setSystem(true);
		trashfolder.setMessages(messages3);
		
		starredfolder.setActor(actor);
		starredfolder.setName("Starredfolder");
		starredfolder.setSystem(true);
		starredfolder.setMessages(messages4);
		
		spamfolder.setActor(actor);
		spamfolder.setName("Spamfolder");
		spamfolder.setSystem(true);
		spamfolder.setMessages(messages5);
		
		// Por último se hacen persistentes los objetos en la bd
		save(infolder);
		save(outfolder);
		save(trashfolder);
		save(spamfolder);
		save(starredfolder);
	}
	
	public Collection<Folder> findFoldersByPrincipal(){
		Collection<Folder> result;
		Actor actor;
		
		actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		result = folderRepository.findFoldersByUserAccount(actor.getUserAccount().getId());
		Assert.notNull(result);
		
		checkPrincipalActorFolders(result);
		
		return result;
	}
	
	public Folder findFolder(String nameFolder){
		Assert.notNull(nameFolder);
		
		Actor actor;
		Folder folder;
		
		actor = actorService.findByPrincipal();
		folder = folderRepository.findFolder(nameFolder, actor.getId());
		Assert.notNull(folder);
		return folder;
	}
	
	public Folder findSystemFolder(String nameFolder){
		Assert.notNull(nameFolder);
		
		Actor actor;
		Folder folder;
		
		actor = actorService.findByPrincipal();
		folder = folderRepository.findSystemFolder(nameFolder, actor.getId());
		Assert.notNull(folder);
		return folder;
	}
	
	public Folder findFolder(String nameFolder, int actorId){
		Assert.notNull(nameFolder);
		Folder folder;
		
		folder = folderRepository.findFolder(nameFolder, actorId);
		Assert.notNull(folder);
		return folder;
	}
	private void checkPrincipalActorFolders(Collection<Folder> folders){
		Assert.notNull(folders);
		
		for(Folder f: folders){
			checkPrincipalActor(f);
		}
	}
	
	public void checkPrincipalActor(Folder folder){
		Assert.notNull(folder);
		
		Actor actor;
		
		actor = actorService.findByPrincipal();
		
		Assert.isTrue(actor.getId()==folder.getActor().getId());
	}
		
	public Collection<Folder> findFoldersToMoveByPrincipal(){
		Collection<Folder> result;
		Actor principal;
		
		principal=actorService.findByPrincipal();
		result=folderRepository.findFoldersToMoveByUserAccount(principal.getUserAccount().getId());
		Assert.notNull(result);
		
		return result;
	}
}
