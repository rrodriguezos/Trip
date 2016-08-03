package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Folder;
import domain.Message;
import domain.Message.MessagePriority;
import domain.Trip;
import domain.User;
import forms.MessageForm;

@Service
@Transactional
public class MessageService {

	// Managed Repository ------------------------------
	@Autowired
	private MessageRepository messageRepository;

	// Supporting Services -----------------------------
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;
	@Autowired
	private UserService userService;

	// Constructors ------------------------------------
	public MessageService() {
		super();
	}

	// Simple CRUD Methods -----------------------------
	public Message create() {
		Message result;
		Actor sender;
		Folder folder;
		
		sender = actorService.findByPrincipal();
		folder=folderService.findOutFolderOfActor(sender.getId());
		
		result = new Message();
		result.setSender(sender);
		result.setMoment(new Date(System.currentTimeMillis()-1000));
		result.setFolder(folder);
		
		return result;
	}

	public Message findOne(int messageId) {
		Assert.isTrue(messageId != 0);
		Message result = messageRepository.findOne(messageId);
		Assert.notNull(result);
		return result;
	}

	public Message save(Message message) {
		Assert.notNull(message);
		Message result;
		if (message.getId() == 0) {
			Message message2 = new Message();

			message2.setBody(message.getBody());
			message2.setMoment(message.getMoment());
			message2.setRecipient(message.getRecipient());
			message2.setMessagePriority(message.getMessagePriority());
			message2.setSender(message.getSender());
			message2.setSubject(message.getSubject());
			message2.setFolder(folderService.findInFolderOfActor(message
					.getRecipient().getId()));

			message2.getFolder().getMessages().add(message2);
			folderService.save(message2.getFolder());
			result = messageRepository.saveAndFlush(message2);

			message.getFolder().getMessages().add(message);
			folderService.save(message.getFolder());
		}
		result = messageRepository.saveAndFlush(message);
		return result;
	}

	public Message saveToSendCopy(Message message) {
		Assert.notNull(message);
		Message result;

		result = save(message);
		Message clon;
		Actor recipient;
		Folder folder;

		clon = message.clone();
		recipient = message.getRecipient();
		folder = folderService.findInFolderOfActor(recipient.getId());

		clon.setFolder(folder);
		save(clon);

		return result;
	}

	public void delete(Message message) {
		Assert.notNull(message);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		Assert.isTrue(actor.equals(message.getFolder().getActor()));
		if (!message.getFolder().getName().equals("Trash folder")) {

			message.getFolder().getMessages().remove(message);
			folderService.save(message.getFolder());

			Folder folder = folderService.findTrashFolderOfActor(actor.getId());

			folder.getMessages().add(message);

			message.setFolder(folder);

			folderService.save(folder);
			messageRepository.saveAndFlush(message);
		} else {
			message.getFolder().getMessages().remove(message);
			folderService.save(message.getFolder());
			messageRepository.delete(message);
		}
	}

	// Other Business Methods ---------------------------

	public void move(int messageId, int folderId) {

		Folder f = folderService.findOne(folderId);
		folderService.checkPrincipal(f);
		Message message = findOne(messageId);

		message.setFolder(f);

		save(message);
	}

	public Collection<Message> findMessagesOfAFolder(int folderId) {
		return messageRepository.messagesOfAFolder(folderId);
	}

//	public void flagAsSpamSave(Message message) {
//		Assert.notNull(message);
//		Actor actor = actorService.findByPrincipal();
//		Assert.notNull(actor);
//		Assert.isTrue(actor.equals(message.getFolder().getActor()));
//
//		message.getFolder().getMessages().remove(message);
//		Folder folder = folderService.findSpamFolferOfActor(actor.getId());
//		folder.getMessages().add(message);
//		message.setFolder(folder);
//		messageRepository.saveAndFlush(message);
//	}

	public void flagAsStarredSave(Message message) {
		
		Assert.notNull(message.getId());		
		
		if(!message.getFolder().getName().equals("Starred folder")){
			message.setStar(!message.getStar());
			
			save(message);
		}
	}

	public Collection<Message> findStarsByActor() {

		Collection<Message> result = messageRepository
				.findStarsByActor(actorService.findByPrincipal().getId());
		Assert.notNull(result);

		return result;
	}
	

	public Message reconstruct(MessageForm messageForm) {
		Assert.notNull(messageForm);
		Actor actor = actorService.findByPrincipal();
		Assert.notNull(actor);
		Message result = create();
		Actor recipient = actorService.findOne(messageForm.getRecipient());
		Assert.notNull(recipient);
		result.setBody(messageForm.getBody());
		result.setStar(messageForm.getStar());
		result.setRecipient(recipient);
		if (messageForm.getMessagePriority().toString() == "LOW") {
			result.setMessagePriority(MessagePriority.LOW);
		} else if (messageForm.getMessagePriority().toString() == "NEUTRAL") {
			result.setMessagePriority(MessagePriority.NEUTRAL);
		} else if (messageForm.getMessagePriority().toString() == "HIGH") {
			result.setMessagePriority(MessagePriority.HIGH);
		}
		result.setSubject(messageForm.getSubject());
		return result;
	}

	public void broadcastAlertTripMessage(Trip trip, String subject, String body) {
		Collection<User> users;
		Message message;

		users = userService.usersSusTrip(trip.getId());
		message = create();

		message.setSubject(subject);
		message.setBody(body);
		message.setSender(trip.getUser());
		message.setMessagePriority(MessagePriority.NEUTRAL);

		for (User user : users) {
			message.setRecipient(user);
			message.setFolder(folderService.findInFolderOfActor(user.getId()));

			save(message);
		}

	}

}
