package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.MessageService;
import domain.Actor;
import domain.Message;
import forms.MessageForm;

@Controller
@RequestMapping("/message")
public class MessageController extends AbstractController {
	
	//Services --------------------------------------
	@Autowired
	private MessageService messageService;
	@Autowired
	private ActorService actorService;
	
	
	//Constructor -----------------------------------
	public MessageController() {
		super();
	}
	
	//Listing ---------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMessages(@RequestParam int folderId) {
		ModelAndView result;
		Collection<Message> messages = messageService.findMessagesOfAFolder(folderId);
		
		result = new ModelAndView("message/list");
		
		result.addObject("messages", messages);
		result.addObject("requestURI", "message/list.do");
		return result;
	}
	
	//Send message ----------------------------------
	@RequestMapping(value = "/send", method = RequestMethod.GET)
	public ModelAndView send() {
		MessageForm messageForm = new MessageForm();
		return createEditModelAndView(messageForm, null);
	}
	
	//Edit -----------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageForm messageForm, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(messageForm, null);
		} else {
			try {				
				Message message = messageService.reconstruct(messageForm);
				messageService.save(message);
				result = new ModelAndView("redirect:/folder/list.do");
			} catch(ObjectOptimisticLockingFailureException exc) {
				result = createEditModelAndView(messageForm, "common.concurrencyError");
			} catch (Throwable oops) {	
				System.out.println(oops.getMessage());
				result = createEditModelAndView(messageForm, "common.error");				
			}
		}

		return result;
	}
	
	//Delete ---------------------------------------
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView deleteMessage(@RequestParam int messageId) {
		ModelAndView result;
		Message message = messageService.findOne(messageId);
		
		try {
			messageService.delete(message);
			result = new ModelAndView("redirect:/folder/list.do");
		} catch (Throwable oops) {
			System.out.println(oops.getMessage());
			result = new ModelAndView("redirect:list.do");
			result.addObject("message", "message.delete.error");
		}
		
		return result;
	}
	
	//Ancillary methods -----------------------------
		
	protected ModelAndView createEditModelAndView(MessageForm messageForm, String message) {
		ModelAndView result;
		Collection<Actor> actors = actorService.findAll();
			
		result = new ModelAndView("message/send");
		result.addObject("messageForm", messageForm);
		result.addObject("actors", actors);
		result.addObject("message", message);
		return result;

	}

}
