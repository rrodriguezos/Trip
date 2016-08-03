package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import controllers.AbstractController;
import domain.Actor;
import domain.Folder;
import domain.Message;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Constructor-----------------------------------------------------------------
	public MessageController() {
		super();
	}

	// Supporting
	// services---------------------------------------------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private FolderService folderService;

	// List---------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId) {
		ModelAndView result;
		Collection<Message> messages;
		Folder folder;

		folder = folderService.findOne(folderId);
		if(folder.getSystemFolder() && folder.getName().equals("Starred folder")){
			messages = messageService.findStarsByActor();
		}else{
			messages = messageService.findMessagesOfAFolder(folderId);
		}

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("folder", folder);
		result.addObject("requestUri", "message/list.do");

		return result;
	}

	// Display----------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageId) {
		ModelAndView result;
		Message message;

		message = messageService.findOne(messageId);

		result = new ModelAndView("message/display");
		result.addObject("message", message);

		return result;
	}

	// Creating--------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Message message;
		Collection<Actor> actors;
		Actor actor;

		message = messageService.create();
		actors = actorService.findAll();
		actor = actorService.findByPrincipal();
		actors.remove(actor);

		result = new ModelAndView("message/create");
		result.addObject("message", message);
		result.addObject("actors", actors);
		return result;
	}

	// Editing ------------------------------------------------
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int messageId) {
		ModelAndView result;
		Message message;
		Collection<Folder> folders;
	
		message = messageService.findOne(messageId);
		folders = folderService.findFoldersAndMoveByPrincipal();
		
		result = new ModelAndView("message/move");
		result.addObject("message", message);
		result.addObject("folders", folders);
		return result;
	}

	// Save----------------------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Message message, BindingResult bindingResult) {
		ModelAndView result;
		Collection<Actor> actors;
		Actor actor;

		actors = actorService.findAll();
		actor = actorService.findByPrincipal();
		actors.remove(actor);

		if (bindingResult.hasErrors()) {
			result = new ModelAndView("message/create");
			result.addObject("message", message);
			result.addObject("actors", actors);
		} else {
			try {
				messageService.saveToSendCopy(message);
				result = new ModelAndView("redirect:/folder/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("message/create");
				result.addObject("message", message);
				result.addObject("message2", "message.commit.error");
				result.addObject("actors", actors);
			}
		}
		return result;
	}

	// SaveToMove-------------------------------------------------------------------------------------------
	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(@Valid Message message, BindingResult bindingResult) {
		ModelAndView result;
		Collection<Folder> folders;

		folders = folderService.findFoldersAndMoveByPrincipal();
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("message/move");
			result.addObject("message", message);
			result.addObject("folders", folders);
		} else {
			try {
				message = messageService.save(message);
				result = new ModelAndView("redirect:/folder/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("message/move");
				result.addObject("message", message);
				result.addObject("message2", "message.commit.error");
				result.addObject("folders", folders);
			}
		}
		return result;
	}

	// Delete------------------------------------------------------------------------------------
	@RequestMapping(value="/delete", method=RequestMethod.GET)
	public ModelAndView sendToTrashbox(@RequestParam int messageId){
		ModelAndView result;
		Message message;
		int folderId;
		
		message = messageService.findOne(messageId);
		folderId = message.getFolder().getId();
		
		messageService.delete(message);
		
		result = new ModelAndView("redirect:/message/list.do?folderId="+folderId);
		
		return result;
	}
	
	//Star -----------------------------------------------------------------------------------------
	@RequestMapping(value="/starred",method = RequestMethod.GET)
	public ModelAndView favorite(@RequestParam int messageId){
		ModelAndView result;
		Message message;
		int folderId;
		
		message = messageService.findOne(messageId);
		folderId = message.getFolder().getId();
		
		messageService.flagAsStarredSave(message);
		
		result = new ModelAndView("redirect:/message/list.do?folderId="+folderId);
		
		return result;
	}
	
	// Flag Spam--------------------------------------------------------------------------------------
	@RequestMapping(value="/flag", method=RequestMethod.GET)
	public ModelAndView sendToSpam(@RequestParam int messageId){
		ModelAndView result;
		Folder folder;

		folder = folderService.findFolderByString("Spam folder");
		
		messageService.move(messageId, folder.getId());
		
		result = new ModelAndView("redirect:/message/list.do?folderId="+folder.getId());
		
		return result;
	}

	// Ancillary methods-----------------------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Message message) {
		ModelAndView result;

		result = createEditModelAndView(message, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Message mesage, String message) {
		ModelAndView result;

		result = new ModelAndView("message/edit");
		result.addObject("message", mesage);
		result.addObject("message2", message);

		return result;
	}

}
