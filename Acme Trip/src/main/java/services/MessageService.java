package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

import repositories.MessageRepository;


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


		// Constructors ------------------------------------
		public MessageService() {
			super();
		}

		// Simple CRUD Methods -----------------------------
		public Message create() {
			Message result = new Message();
			result.setMoment(new Date(System.currentTimeMillis()-1000));
			Actor actor = actorService.findByPrincipal();
			result.setSender(actor);
			result.setFolder(folderService.findOutFolderOfActor(actor.getId()));
			return result;
		}
		
		public Message findOne(int messageId) {
			Assert.isTrue(messageId != 0);
			Message result = messageRepository.findOne(messageId);
			Assert.notNull(result);
			return result;
		}
		
		public void save(Message message) {
			Assert.notNull(message);
			if (message.getId() == 0) {
				Message message2 = new Message();			
				
				message.setBody(message.getBody());
				message.setMoment(message.getMoment());
				message.setRecipient(message.getRecipient());
				message.setSender(message.getSender());
				message.setSubject(message.getSubject());				
			
			}
			messageRepository.save(message);
		}
		
		public void delete(Message message) {
			Collection <Message> messages = new HashSet <Message>();
			if (!message.getFolder().getName().equals("Trash Folder")) {
				Collection<Message> messagesOrigin = message.getFolder().getMessages();
				messagesOrigin.remove(message);
		
				messages.addAll(messagesOrigin);
				//messageRepository.delete(message);
				
				Actor actor = actorService.findByPrincipal();
				Folder folder = folderService.findTrashFolderOfActor(actor.getId());
				
				Collection <Message> trashMessages = folder.getMessages();
				trashMessages.add(message);
				folder.setMessages(trashMessages);
				
				message.setFolder(folder);
				messageRepository.save(message);
			} else {
				messageRepository.delete(message);
			}
		}

		
		// Other Business Methods ---------------------------
		
		public Collection <Message> findMessagesOfAFolder (int folderId){
			return messageRepository.messagesOfAFolder(folderId);
		}
		
		public void flagAsSpamSave(Message message) {
			Assert.notNull(message);
			Actor actor = actorService.findByPrincipal();
			Assert.notNull(actor);
			Assert.isTrue(actor.equals(message.getFolder().getActor()));
			
			message.getFolder().getMessages().remove(message);		
			Folder folder = folderService.findSpamFolferOfActor(actor.getId());			
			folder.getMessages().add(message);
			message.setFolder(folder);
			messageRepository.saveAndFlush(message);
		}
		
		public void flagAsStarredSave(Message message) {
			Assert.notNull(message);
			Actor actor = actorService.findByPrincipal();
			Assert.notNull(actor);
			Assert.isTrue(actor.equals(message.getFolder().getActor()));
			
			message.getFolder().getMessages().remove(message);		
			Folder folder = folderService.foldersStarredFolderOfActor(actor.getId());			
			folder.getMessages().add(message);
			message.setFolder(folder);
			messageRepository.saveAndFlush(message);
		}
		public Message reconstruct(MessageForm messageForm) {
			Assert.notNull(messageForm);
			Message result = create();
			Actor recipient = actorService.findOne(messageForm.getRecipient());
			Assert.notNull(recipient);
			result.setBody(messageForm.getBody());
			result.setRecipient(recipient);
			result.setSubject(messageForm.getSubject());
			return result;
		}
		
		
		
		
//		public Double averageNumberMessagesPerActor() {
//			Assert.isTrue(DPUtils.hasRole(Authority.ADMIN), DPMessage.NO_PERMISSIONS);
//			Double result = messageRepository.averageNumberMessagesPerActor();
//			Assert.notNull(result);
//			return result;
//		}
//
}
