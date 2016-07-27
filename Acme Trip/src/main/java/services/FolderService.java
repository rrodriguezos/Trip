package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FolderRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.FolderForm;

@org.springframework.stereotype.Service
@Transactional
public class FolderService {

	// Managed Repository ------------------------------
	@Autowired
	private FolderRepository folderRepository;

	// Supporting Services -----------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;

	// Constructors ------------------------------------
	public FolderService() {
		super();
	}

	// Simple CRUD Methods -----------------------------

	public Folder create() {
		Folder result = new Folder();
		result.setMessages(new ArrayList<Message>());
		result.setActor(actorService.findByPrincipal());
		return result;
	}

	public Folder findOne(int folderId) {
		Assert.isTrue(folderId != 0);
		Folder result = folderRepository.findOne(folderId);
		Assert.notNull(result);
		return result;
	}

	public void save(Folder folder) {
		Assert.notNull(folder);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);	
		folderRepository.saveAndFlush(folder);
	}

	public void delete(Folder folder) {
		Assert.notNull(folder);
		Actor actor = actorService.findByPrincipal();
		Assert.isTrue(folderRepository.exists(folder.getId()));
		Assert.isTrue(!folder.getSystemFolder());
		Assert.isTrue(folder.getActor().getId()==actor.getId());
		for (Message m: folder.getMessages()) {
			messageService.delete(m);
		}
		folderRepository.delete(folder);
		folderRepository.flush();
	}

	// Other Business Methods ---------------------------

	public Collection<Folder> findFoldersOfActor(int actorId) {
		return folderRepository.foldersOfActor(actorId);
	}

	public Folder findTrashFolderOfActor(int actorId) {
		return folderRepository.foldersTrashOfActor(actorId);
	}

	public Folder findInFolderOfActor(int actorId) {
		Folder folder = folderRepository.foldersInFolderOfActor(actorId);
		Assert.notNull(folder);
		return folder;
	}

	public Folder findOutFolderOfActor(int actorId) {
		Folder folder = folderRepository.foldersOutFolderOfActor(actorId);
		Assert.notNull(folder);
		return folder;
	}

	public void generateSystemFolders(Actor actor) {
		Collection<Folder> folders = new ArrayList<Folder>();
		Folder inFolder = new Folder();
		Folder outFolder = new Folder();
		Folder trashFolder = new Folder();
		Folder spamFolder = new Folder();
		Folder starredFolder = new Folder();
		inFolder.setActor(actor);
		outFolder.setActor(actor);
		trashFolder.setActor(actor);
		spamFolder.setActor(actor);
		starredFolder.setActor(actor);
		inFolder.setMessages(new ArrayList<Message>());
		outFolder.setMessages(new ArrayList<Message>());
		trashFolder.setMessages(new ArrayList<Message>());
		spamFolder.setMessages(new ArrayList<Message>());
		starredFolder.setMessages(new ArrayList<Message>());
		inFolder.setName("In folder");
		outFolder.setName("Out folder");
		trashFolder.setName("Trash folder");
		spamFolder.setName("Spam folder");
		starredFolder.setName("Starred folder");
		inFolder.setSystemFolder(true);
		outFolder.setSystemFolder(true);
		trashFolder.setSystemFolder(true);
		spamFolder.setSystemFolder(true);
		starredFolder.setSystemFolder(true);
		folders.add(inFolder);
		folders.add(outFolder);
		folders.add(trashFolder);
		folders.add(spamFolder);
		folders.add(starredFolder);
		actor.setFolders(folders);
	}

	public Folder findSpamFolferOfActor(int actorId) {
		return folderRepository.foldersSpamFolderOfActor(actorId);
	}

	public Folder foldersStarredFolderOfActor(int actorId) {
		return folderRepository.foldersStarredFolderOfActor(actorId);
	}

	public Folder reconstruct(FolderForm folderForm, int folderId) {
		Folder result;
		if (folderId == 0) {
			result = create();
			result.setSystemFolder(false);
		} else {
			result = findOne(folderId);
			Assert.isTrue(!result.getSystemFolder());
		}
		Assert.notNull(result);
		result.setName(folderForm.getName());
		return result;
	}

}
