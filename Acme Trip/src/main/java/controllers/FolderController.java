package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import security.UserAccount;
import services.ActorService;
import services.AdministratorService;
import services.ManagerService;
import services.FolderService;
import services.UserService;
import domain.Actor;
import domain.Folder;
import forms.FolderForm;

@Controller
@RequestMapping("/folder")
public class FolderController extends AbstractController {
	
	//Services --------------------------------------
	@Autowired
	private FolderService folderService;
	@Autowired
	private UserService userService;
	@Autowired
	private AdministratorService administratorService;
	@Autowired
	private ManagerService managerService;
	@Autowired
	private ActorService actorService;
	
	//Constructor -----------------------------------
	public FolderController() {
		super();
	}
	
	//Listing ---------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listFolder() {
		ModelAndView result;
		Actor actor = null;
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context==null?null:context.getAuthentication();
		Object principal = authentication==null?null:authentication.getPrincipal();
		UserAccount userAccount = principal instanceof UserAccount?(UserAccount) principal:null;
		
		if(userAccount!=null){
			actor = administratorService.findByUserAccount(userAccount);
			if(actor==null){
				actor = userService.findByUserAccount(userAccount);
				if(actor==null){
					actor = managerService.findByUserAccount(userAccount);
				}
			}				
		}		
		result = new ModelAndView("folder/list");
		result.addObject("folders", folderService.findFoldersOfActor(actor.getId()));
		return result;
	}
	
	//Edit -------------------------------------------------------------------------
		@RequestMapping(value="/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam(defaultValue="0") int folderId) {
			ModelAndView result;
			FolderForm folderForm = new FolderForm();
			result = createEditModelAndView(folderForm, null);
			
			if(folderId!=0){
				Folder folder = folderService.findOne(folderId);
				Actor actor = actorService.findByPrincipal();
				Assert.isTrue(actor.equals(folder.getActor()));
				result.addObject("folderName", folder.getName());
			}
			
			result.addObject("folderId", folderId);		
			return result;
		}

		//Save -------------------------------------------------------------------------
		@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid FolderForm folderForm, BindingResult binding,
				@ModelAttribute("folderId") int folderId, SessionStatus status, RedirectAttributes redirectAttrs) {
			ModelAndView result;				
			if (binding.hasErrors()) {
				result = createEditModelAndView(folderForm, "folder.commit.not.valid");
			} else {
				try {			
					Folder folder = folderService.reconstruct(folderForm, folderId);
					folderService.save(folder);	
					redirectAttrs.addFlashAttribute("message", "folder.commit.ok");		
					result = new ModelAndView("redirect:list.do");
					status.setComplete();	
				} catch(ObjectOptimisticLockingFailureException exc) {
					result = createEditModelAndView(folderForm, "folder.concurrencyError");
				} catch (Throwable oops) {				
					result = createEditModelAndView(folderForm, "folder.commit.error");				
				}
			}

			return result;
		}
		//Delete -------------------------------------------------------------------------
		@RequestMapping(value = "/delete", method = RequestMethod.GET)
		public ModelAndView delete(@RequestParam int folderId, RedirectAttributes redirectAttrs) {
			ModelAndView result;

			try {			
				Folder folder = folderService.findOne(folderId);
				folderService.delete(folder);	
				redirectAttrs.addFlashAttribute("message", "folder.commit.ok");		
				result = new ModelAndView("redirect:list.do");
			} catch(ObjectOptimisticLockingFailureException exc) {
				redirectAttrs.addFlashAttribute("message", "folder.concurrencyError");		
				result = new ModelAndView("redirect:list.do");
			} catch (Throwable oops) {	
				redirectAttrs.addFlashAttribute("message", "folder.delete.error");		
				result = new ModelAndView("redirect:list.do");
			}

			return result;
		}
	
		// Ancillary methods -------------------------------------------------
	
		private ModelAndView createEditModelAndView(FolderForm folderForm, String message) {
			ModelAndView result = new ModelAndView("folder/edit");
			result.addObject("folderForm", folderForm);
			result.addObject("message", message);		
			return result;
		}

}

