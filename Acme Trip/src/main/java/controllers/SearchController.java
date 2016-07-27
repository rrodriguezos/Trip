package controllers;


import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
import controllers.AbstractController;
import domain.Trip;
import forms.SearchForm;

@Controller
@RequestMapping("/search")
public class SearchController extends AbstractController  {
	
	@Autowired
	private TripService tripService;

	@RequestMapping(value = "/buscar", method = RequestMethod.GET)
	public ModelAndView buscar() {
		ModelAndView result;
		SearchForm searchForm = new SearchForm();
		result = new ModelAndView("search/buscar");
		result.addObject("searchForm", searchForm);

		return result;
	}

	@RequestMapping(value = "/buscar", method = RequestMethod.POST, params = "search")
	public ModelAndView buscar(@Valid SearchForm searchForm,
			BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors()) {

			result = new ModelAndView("search/administrator/buscar");
			result.addObject("searchForm", searchForm);

		} else {
			try {
				Assert.notNull(searchForm);
				String text = searchForm.getText();
				Collection<Trip> keyword = new HashSet<Trip>();
				keyword = tripService.searchByKeyword(text);

				result = new ModelAndView("search/buscar");
				result.addObject("keyword", keyword);
				result.addObject("searchForm", searchForm);

			} catch (Throwable oops) {

				result = new ModelAndView("search/buscar");
				result.addObject("searchForm", searchForm);

			}
		}
		return result;
	}

}
