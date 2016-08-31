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

import services.FolderService;
import controllers.AbstractController;
import domain.Folder;

@Controller
@RequestMapping("/folder/actor")
public class FolderActorController extends AbstractController {

	// Constructor-----------------------------------------------------------------
	public FolderActorController() {
		super();
	}

	// Supporting services---------------------------------------------------------

	@Autowired
	private FolderService folderService;

	//List-----------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Folder> folders;

		folders = folderService.findFoldersByPrincipal();

		result = new ModelAndView("folder/list");
		result.addObject("folders", folders);
		result.addObject("requestUri", "folder/actor/list.do");

		return result;
	}

	//Create--------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Folder folder;

		folder = folderService.create();

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);

		return result;
	}

	//Edit ------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int folderId) {
		ModelAndView result;
		Folder folder;
		folder = folderService.findOneEdit(folderId);

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		return result;
	}

	//Save----------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Folder folder, BindingResult bindingResult) {
		ModelAndView result;

		if (bindingResult.hasErrors()) {
			result = createEditModelAndView(folder);
		} else {
			try {
				folder = folderService.save(folder);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(folder, "folder.commit.error");
			}
		}
		return result;
	}

	//Delete---------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Folder folder, BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = createEditModelAndView(folder);
		} else {
			try {
				folderService.delete(folder);
				result = new ModelAndView("redirect:/folder/actor/list.do");
			} catch (Throwable oops) {
				result = createEditModelAndView(folder, "folder.commit.error");
			}
		}
		return result;
	}

	//Ancillary methods------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(Folder folder) {
		ModelAndView result;

		result = createEditModelAndView(folder, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Folder folder, String message) {
		ModelAndView result;

		result = new ModelAndView("folder/edit");
		result.addObject("folder", folder);
		result.addObject("message2", message);

		return result;
	}
}