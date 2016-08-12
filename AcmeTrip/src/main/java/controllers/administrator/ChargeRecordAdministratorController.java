package controllers.administrator;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.ChargeRecord;
import services.CampaignService;
import services.ChargeRecordService;

@Controller
@RequestMapping("/chargerecord/administrator")
public class ChargeRecordAdministratorController extends AbstractController {

	// Services -----------------------
	@Autowired
	private CampaignService campaignService;

	@Autowired
	private ChargeRecordService chargeService;

	// Constructor --------------------
	public ChargeRecordAdministratorController() {
		super();
	}

	// List ------------------------------------------------------------------

	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public ModelAndView list() {
		campaignService.generateChargeRecords();
		ModelAndView result;
		Collection<ChargeRecord> charges = chargeService.findAll();
		Boolean fallo = true;
		Date today = new Date(System.currentTimeMillis()-1000);
		for (ChargeRecord c : charges) {
			if (c.getCreateMoment().getDay() == today.getDay() && c.getCreateMoment().getMonth() == today.getMonth()
					&& c.getCreateMoment().getYear() == today.getYear()) {
				fallo = false;
				break;
			}
		}
		result = new ModelAndView("chargerecord/administrator/generate");
		result.addObject("fallo", fallo);

		return result;
	}

}
