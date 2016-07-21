package controllers;

import java.util.ArrayList;
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

import security.Authority;
import services.ActorService;
import services.AdministratorService;
import services.ManagerService;
import services.UserService;
import services.FolderService;
import utilities.DPUtils;
import domain.Folder;

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
		Collection <Folder> folders	= new ArrayList<Folder>();	
		int actorId = 0;
			
		if(DPUtils.hasRole(Authority.ADMIN)){
			actorId = administratorService.findByPrincipal().getId();
		}else if(DPUtils.hasRole(Authority.USER)){
			actorId = userService.findByPrincipal().getId();
		}else if(DPUtils.hasRole(Authority.MANAGER)){
			actorId = managerService.findByPrincipal().getId();
		}
			
		folders = folderService.findFoldersOfActor(actorId);;
		
		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);

		return result;
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(defaultValue="0") Integer folderId) {
		Folder folder = folderId != 0 ? folderService.findOne(folderId) : new Folder();
		Assert.notNull(folder);
		folder.setActor(actorService.findByPrincipal());
		
		return createEditModelAndView(folder);
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Folder folder, BindingResult binding) {
		ModelAndView result;		
		Assert.isTrue(!folder.getSystemFolder());
		
		if (binding.hasErrors()) {
			result = createEditModelAndView(folder);
		} else {
			try {				
				folderService.save(folder);
				result = new ModelAndView("redirect:list.do");
			} catch(ObjectOptimisticLockingFailureException exc) {
				result = createEditModelAndView(folder, "common.concurrencyError");
			} catch (Throwable oops) {				
				result = createEditModelAndView(folder, "common.error");				
			}
		}

		return result;
	}
			
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Folder folder, BindingResult binding) {
		ModelAndView result;

		try {			
			folderService.delete(folder);			
			result = new ModelAndView("redirect:list.do");		
		} catch(ObjectOptimisticLockingFailureException exc) {
			result = createEditModelAndView(folder, "common.concurrencyError");
		} catch (Throwable oops) {	
			result = createEditModelAndView(folder, "folder.delete.error");
		}

		return result;
	}
	
	//////////////////////
	
	private ModelAndView createEditModelAndView(Folder folder) {
		return createEditModelAndView(folder, null);
	}	
	
	private ModelAndView createEditModelAndView(Folder folder, String message) {
		ModelAndView result;
				
		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("message", message);
		
		return result;
	}

}

