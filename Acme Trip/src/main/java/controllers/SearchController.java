package controllers;


import java.util.Collection;
import java.util.HashSet;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.TripService;
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
	public ModelAndView list(@Valid SearchForm searchForm) {
		ModelAndView result;

		Assert.notNull(searchForm);
		String text = searchForm.getText();
		Collection<Trip> trips = new HashSet<Trip>();
		trips = tripService.searchByKeyword(text);

		result = new ModelAndView("search/buscar");
		result.addObject("trips", trips);
		result.addObject("searchForm", searchForm);

		return result;
	}




}
