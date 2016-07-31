package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import services.ActorService;
import services.FolderService;
import services.MessageService;
import domain.Actor;
import domain.Folder;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {

	// Services --------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private FolderService folderService;

	// Constructor -----------------------------------
	public MessageController() {
		super();
	}

	// Listing ---------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam int folderId) {
		ModelAndView result;
		Folder folder = folderService.findOne(folderId);

		Assert.isTrue(actorService.findByPrincipal().equals(folder.getActor()));

		result = new ModelAndView("message/list");
		result.addObject("flagged", folder.getName().equals("Spam folder")
				&& folder.getSystemFolder());
		result.addObject("starred", folder.getName().equals("Starred folder")
				&& folder.getSystemFolder());
		result.addObject("messages", folder.getMessages());
		result.addObject("requestURI", "message/list.do");
		return result;
	}

	// Send message ----------------------------------
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		MessageForm messageForm = new MessageForm();
		return createEditModelAndView(messageForm, null);
	}

	// Flag message ----------------------------------
	@RequestMapping(value = "/flag", method = RequestMethod.GET)
	public ModelAndView flag(@RequestParam int messageId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);

		if (message.getFolder().equals("Spam folder")
				&& message.getFolder().getSystemFolder()) {
			result = new ModelAndView("redirect:list.do?folderId="
					+ message.getFolder().getId());
			redirectAttrs.addFlashAttribute("message",
					"message.already.flagged");
		} else {
			try {
				messageService.flagAsSpamSave(message);
				redirectAttrs.addFlashAttribute("message", "message.commit.ok");
				result = new ModelAndView("redirect:list.do?folderId="
						+ message.getFolder().getId());
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/folder/list.do");
				redirectAttrs.addFlashAttribute("message",
						"message.commit.error");
			}
		}

		return result;
	}

	// Starred message ----------------------------------
	@RequestMapping(value = "/starred", method = RequestMethod.GET)
	public ModelAndView starred(@RequestParam int messageId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);

		if (message.getFolder().equals("Starred folder")
				&& message.getFolder().getSystemFolder()) {
			result = new ModelAndView("redirect:list.do?folderId="
					+ message.getFolder().getId());
			redirectAttrs.addFlashAttribute("message",
					"message.already.starred");
		} else {
			try {
				messageService.flagAsStarredSave(message);
				redirectAttrs.addFlashAttribute("message", "message.commit.ok");
				result = new ModelAndView("redirect:list.do?folderId="
						+ message.getFolder().getId());
			} catch (Throwable oops) {
				result = new ModelAndView("redirect:/folder/list.do");
				redirectAttrs.addFlashAttribute("message",
						"message.commit.error");
			}
		}

		return result;
	}
	// Starred message ----------------------------------
//		@RequestMapping(value = "/starredBack", method = RequestMethod.GET)
//		public ModelAndView starredBack(@RequestParam int messageId,
//				RedirectAttributes redirectAttrs) {
//			ModelAndView result;
//			Message message = messageService.findOne(messageId);
//
//			if (message.getFolder().equals("Starred folder")
//					&& message.getFolder().getSystemFolder()) {
//				result = new ModelAndView("redirect:list.do?folderId="
//						+ message.getFolder().getId());
//				redirectAttrs.addFlashAttribute("message",
//						"message.already.starred");
//			} else {
//				try {
//					messageService.flagAsBackStarredSave(message);
//					redirectAttrs.addFlashAttribute("message", "message.commit.ok");
//					result = new ModelAndView("redirect:list.do?folderId="
//							+ message.getFolder().getId());
//				} catch (Throwable oops) {
//					result = new ModelAndView("redirect:/folder/list.do");
//					redirectAttrs.addFlashAttribute("message",
//							"message.commit.error");
//				}
//			}
//
//			return result;
//		}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageForm messageForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.print(binding.getFieldError());
			System.out.print(binding.getGlobalError());
			result = createEditModelAndView(messageForm, null);
		} else {
			try 
			{	
				System.out.print(binding.getFieldError());
				System.out.print(binding.getGlobalError());
				Message message = messageService.reconstruct(messageForm);
				messageService.save(message);
				result = new ModelAndView("redirect:/folder/list.do");
			} catch(ObjectOptimisticLockingFailureException exc) {
				result = createEditModelAndView(messageForm, "common.concurrencyError");
			} catch (Throwable oops) {	
				System.out.print(binding.getFieldError());
				System.out.print(binding.getGlobalError());
				System.out.println(oops.getMessage());
				result = createEditModelAndView(messageForm, "common.error");				
			}
		}
		return result;
	}

	// Delete ---------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteMessage(@RequestParam int messageId,
			RedirectAttributes redirectAttrs) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);

		try {
			messageService.delete(message);
			result = new ModelAndView("redirect:list.do?folderId="
					+ message.getFolder().getId());
			redirectAttrs.addFlashAttribute("message", "message.commit.ok");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/folder/list.do");
			redirectAttrs.addFlashAttribute("message", "message.delete.error");
		}

		return result;
	}

	// Ancillary methods --------------------------------------------

	protected ModelAndView createEditModelAndView(MessageForm messageForm,
			String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();

		result = new ModelAndView("message/send");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);
		return result;

	}

}
