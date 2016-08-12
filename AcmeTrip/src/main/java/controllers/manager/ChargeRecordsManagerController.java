package controllers.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.ChargeRecord;
import services.ChargeRecordService;

@Controller
@RequestMapping("/chargerecord/manager")
public class ChargeRecordsManagerController extends AbstractController {

	// Supporting services -------------------------------

	@Autowired
	private ChargeRecordService chargeService;


	// Constructors --------------------------------------

	public ChargeRecordsManagerController() {
		super();
	}

	// Create --------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<ChargeRecord> chargerecords;

		chargerecords = chargeService.findAllFromManagerPrincipal();

		result = new ModelAndView();
		result.addObject("chargerecords", chargerecords);

		return result;
	}

	

}
