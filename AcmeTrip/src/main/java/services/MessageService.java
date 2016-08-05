package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Message.MessagePriority;
import domain.Trip;
import domain.User;

import repositories.MessageRepository;

@Transactional
@Service
public class MessageService {
	
	//Managed Repository -----------------------------------------------------------
	@Autowired
	private MessageRepository messageRepository;
	
	//Supported Services -----------------------------------------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private UserService userService;
	@Autowired
	private FolderService folderService;
	
	//Constructor ----------------------------------------------------------------------
	public MessageService(){
		super();
	}
	
	//Simple CRUD Methods --------------------------------------------------------------
	public Message create(){
		Message result;
		Actor sender;
		Folder folder;
		
		sender = actorService.findByPrincipal();
		folder=folderService.findFolder("Outfolder", sender.getId());
		
		result = new Message();
		result.setSender(sender);
		result.setMoment(new Date(System.currentTimeMillis()));
		result.setFolder(folder);
		
		return result;
	}
	
	public Message save(Message message){
		Message result;
		Assert.notNull(message);
		
		//CONCURRENCY CHECK 
		if(message.getId()!=0){
			Message messageCheck = messageRepository.findOne(message.getId());
			Assert.isTrue(message.getVersion() == messageCheck.getVersion());
		}
		result = messageRepository.save(message);
		
		return result;
	}
	
	public Message saveToSend(Message message){
		Assert.notNull(message);
		Message result;
		
		result=save(message); 
		Message copy;
		Actor recipient;
		Folder folder;
		
		copy=message.clone();
		recipient=message.getRecipient();
		folder=folderService.findFolder("Infolder", recipient.getId());
		
		copy.setFolder(folder);
		save(copy);
	
		return result;
	}
	
	public void delete(Message message){
		Assert.notNull(message);
		
		messageRepository.delete(message);
	}

	public Message findOne(int messageId){
		Assert.isTrue(messageId!=0);
		Message result;
		
		result = messageRepository.findOne(messageId);
		
		return result;
	}
	
	//Other Business Methods -----------------------------------------------------------
	
	public void broadcastAlertTripMessage(Trip trip, String subject, String body) {
		Collection<User> users;
		Message message;

		users = userService.usersSusTrip(trip.getId());
		message = create();

		message.setSubject(subject);
		message.setBody(body);
		message.setSender(trip.getUser());
		message.setMessagePriority(MessagePriority.HIGH);

		for (User user : users) {
			message.setRecipient(user);
			message.setFolder(folderService.findInFolderOfActor(user.getId()));

			save(message);
		}

	}
	
	public Message findOneDisplay(int messageId){
		Message result;
		
		result=findOne(messageId);
		checkPrincipalActor(result);
		
		return result;
	}
	public void move(int messageId, int folderId){
		Folder folder;
		Message message;
		
		folder = folderService.findOne(folderId);
		folderService.checkPrincipalActor(folder);
		message = findOne(messageId);
		
		message.setFolder(folder);
		
		save(message);
	}
	
	public void delete(int messageId){
		Assert.isTrue(messageId!=0);
		Message message;
		message = messageRepository.findOne(messageId);
		checkPrincipalActor(message);
		String folderName = message.getFolder().getName();
		if(folderName.equals("Trashfolder") && message.getFolder().getSystemFolder()){
			delete(message);
		}else{
			Folder folder = folderService.findSystemFolder("Trashfolder");
			message.setFolder(folder);
			save(message);
		}
	}
	public Collection<Message> findMessagesFavoritesByActor(){
		Collection<Message> result;
		
		result = messageRepository.findMessagesFavoritesByActor(actorService.findByPrincipal().getId());
		Assert.notNull(result);
		
		return result;		
	}
	
	public Collection<Message> findMessagesByFolder(int folderId){
		Assert.notNull(folderId);
		Folder folder;
		
		folder = folderService.findOne(folderId);
		checkPrincipalFolderActor(folder);
		
		Collection<Message> result;
		
		result = messageRepository.findMessagesByFolderId(folderId);
		Assert.notNull(result);
		
		return result;		
	}
	
	
	
	
	
	public void changeFavorite(int messageId){
		Assert.notNull(messageId);
		Message message;
		
		message = findOne(messageId);
		
		if(!message.getFolder().getName().equals("Starredfolder")){
			message.setStar(!message.getStar());
			
			save(message);
		}
	}
	
	private void checkPrincipalActor(Message message){
		Assert.notNull(message);
		
		Actor actor;
		
		actor= actorService.findByPrincipal();
		
		Assert.isTrue(actor.getId()==message.getFolder().getActor().getId());
		
	}
	
	private void checkPrincipalFolderActor(Folder folder){
		Assert.notNull(folder);
		
		Actor actor;
		
		actor= actorService.findByPrincipal();
		
		Assert.isTrue(actor.getId()==folder.getActor().getId());
		
	}

	
}
