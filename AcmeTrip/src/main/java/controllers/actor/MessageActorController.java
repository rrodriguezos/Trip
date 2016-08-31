package controllers.actor;

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
@RequestMapping("/message/actor")
public class MessageActorController extends AbstractController {

	//Constructor-----------------------------------------------------------------
	public MessageActorController() {
		super();
	}

	//Supporting services---------------------------------------------------------
	@Autowired
	private ActorService actorService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private FolderService folderService;

	//List-----------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId) {
		ModelAndView result;
		Collection<Message> messages;
		Folder folder;

		folder = folderService.findOne(folderId);
		if (folder.getSystemFolder()
				&& folder.getName().equals("Starredfolder")) {
			messages = messageService.findMessagesStarsByActor();
		} else {
			messages = messageService.findMessagesByFolder(folderId);
		}

		result = new ModelAndView("message/list");
		result.addObject("messages", messages);
		result.addObject("folder", folder);
		result.addObject("requestUri", "message/actor/list.do");

		return result;
	}

	//Display-----------------------------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int messageId) {
		ModelAndView result;
		Message message;

		message = messageService.findOneDisplay(messageId);

		result = new ModelAndView("message/display");
		result.addObject("message", message);

		return result;
	}

	//Create--------------------------------------------------------------------------
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

	//Edit ------------------------------------------------------------------------------
	@RequestMapping(value = "/move", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int messageId) {
		ModelAndView result;
		Message message;
		Collection<Folder> folders;

		message = messageService.findOne(messageId);
		folders = folderService.findFoldersToMoveByPrincipal();

		result = new ModelAndView("message/move");
		result.addObject("message", message);
		result.addObject("folders", folders);
		return result;
	}

	//Save----------------------------------------------------------------------------------
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
				messageService.saveAndSend(message);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("message/create");
				result.addObject("message", message);
				result.addObject("message2", "message.commit.error");
				result.addObject("actors", actors);
			}
		}
		return result;
	}

	//Move----------------------------------------------------------------------------------
	@RequestMapping(value = "/move", method = RequestMethod.POST, params = "save")
	public ModelAndView move(@Valid Message message, BindingResult bindingResult) {
		ModelAndView result;
		Collection<Folder> folders;

		folders = folderService.findFoldersToMoveByPrincipal();
		if (bindingResult.hasErrors()) {
			result = new ModelAndView("message/move");
			result.addObject("message", message);
			result.addObject("folders", folders);
		} else {
			try {
				message = messageService.save(message);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("message/move");
				result.addObject("message", message);
				result.addObject("message2", "message.commit.error");
				result.addObject("folders", folders);
			}
		}
		return result;
	}

	//Delete---------------------------------------------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView sendToTrashbox(@RequestParam int messageId) {
		ModelAndView result;
		Message message;
		int folderId;

		message = messageService.findOne(messageId);
		folderId = message.getFolder().getId();

		messageService.delete(messageId);

		result = new ModelAndView("redirect:/message/actor/list.do?folderId="
				+ folderId);

		return result;
	}

	//Flag--------------------------------------------------------------------------
	@RequestMapping(value = "/favorite", method = RequestMethod.GET)
	public ModelAndView favorite(@RequestParam int messageId) {
		ModelAndView result;
		Message message;
		int folderId;

		message = messageService.findOne(messageId);
		folderId = message.getFolder().getId();

		messageService.changeStar(messageId);

		result = new ModelAndView("redirect:/message/actor/list.do?folderId="
				+ folderId);

		return result;
	}

	// Flag Spam------------------------------------------------------------------------------
	@RequestMapping(value = "/tospam", method = RequestMethod.GET)
	public ModelAndView sendToSpam(@RequestParam int messageId) {
		ModelAndView result;
		Folder folder;

		folder = folderService.findFolder("Spamfolder");

		messageService.move(messageId, folder.getId());

		result = new ModelAndView("redirect:/message/actor/list.do?folderId="
				+ folder.getId());

		return result;
	}

	//Ancillary methods------------------------------------------------------------------
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
