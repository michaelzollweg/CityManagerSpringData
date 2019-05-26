package at.fh.swenga.jpa.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import at.fh.swenga.jpa.dao.ContinentRepository;
import at.fh.swenga.jpa.dao.CityRepository;
import at.fh.swenga.jpa.model.ContinentModel;
import at.fh.swenga.jpa.model.CityModel;

@Controller
public class CityController {

	@Autowired
	CityRepository cityRepository;
	
	@Autowired
	ContinentRepository continentRepository;

	@RequestMapping(value = { "/", "list" })
	public String index(Model model) {
		List<CityModel> cities = cityRepository.findAll();
		model.addAttribute("cities", cities);
		model.addAttribute("count", cities.size());
		return "index";
	}
	
	@RequestMapping(value = { "/getPage" })
	public String getPage(Pageable page,Model model) {
		Page<CityModel> citiesPage = cityRepository.findAll(page);
		model.addAttribute("citiesPage", citiesPage);
		model.addAttribute("cities", citiesPage.getContent());
		model.addAttribute("count", citiesPage.getTotalElements());
		return "index";
	}

	@RequestMapping(value = { "/find" })
	public String find(Model model, @RequestParam String searchString, @RequestParam String searchType) {
		List<CityModel> cities = null;
		int count=0;

		switch (searchType) {
		case "query1":
			cities = cityRepository.findAll();
			break;
		case "query2":
			cities = cityRepository.findByCityName(searchString);
			break;
		case "query3":
			cities = cityRepository.findByCountryName(searchString);
			break;
		case "query4":
			cities = cityRepository.findByCityNameOrByCountryName(searchString);
			break;
		case "query5":
			cities = cityRepository.findByNamedQuery(searchString);
			break;
		case "query6":
			count = cityRepository.countByCityName(searchString);
			break;
		case "query7":
			cities = cityRepository.deleteByCityName(searchString);
			break;
		case "query8":
			cities = cityRepository.deleteByContinentName(searchString);
			break;
		case "query9":
			cities = cityRepository.findByCityNameContainingOrCountryNameContainingAllIgnoreCase(searchString,searchString);
			break;
		case "query10":
			//order list by city name
			cities = cityRepository.findByOrderByCityName();
			break;
		case "query11":
			//Sort all cites by city name ascending (top 10)
			cities = cityRepository.findTop10ByOrderByCityName();
			break;
		case "query12":
			//sort cities by city name @ continent
			cities = cityRepository.findByContinentNameOrderByCityNameAsc(searchString);
			break;
		case "query13":
			//not more than 100 000 inhabitants 
			//cities = cityRepository.findByPopulationLessThanEqual(searchString);
			break;
		case "query14":
			//between 100000 and 1000000 inhabitants
			//cities = cityRepository.;
			break;
		case "query15":
			//Filter cities by continent
			//cities = cityRepository.;
			break;

		default:
			cities = cityRepository.findAll();
		}
		
		model.addAttribute("cities", cities);

		if (cities!=null) {
			model.addAttribute("count", cities.size());			
		}
		else {
			model.addAttribute("count", count);				
		}
		return "index";
	}

	@RequestMapping(value = { "/findById" })
	public String findById(@RequestParam("id") CityModel e, Model model) {
		if (e!=null) {
			List<CityModel> cities = new ArrayList<CityModel>();
			cities.add(e);
			model.addAttribute("cities", cities);
		}
		return "index";
	}



	
	@RequestMapping("/fillCityList")
	@Transactional
	public String fillData(Model model) {

		DataFactory df = new DataFactory();
		
		ContinentModel continent = null;
		
		for(int i=0;i<100;i++) {
			if (i%10==0) {
				String continentName=df.getBusinessName();
				continent=continentRepository.findFirstByName(continentName);
				if (continent==null) {
					continent = new ContinentModel(continentName);
				}
			}
			

			CityModel cityModel = new CityModel(df.getCity(),df.getStreetName(),df.getLastName(),df.getNumberBetween(10, 1000000));
			//CityModel cityModel = new CityModel(df.getCity(),df.getStreetName(),df.getLastName(),df.getNumber());
			cityModel.setContinent(continent);
			cityRepository.save(cityModel);
		}
	
		return "forward:list";
	}

	@RequestMapping("/delete")
	public String deleteData(Model model, @RequestParam int id) {
		cityRepository.deleteById(id);

		return "forward:list";
	}

	@ExceptionHandler(Exception.class)
	public String handleAllException(Exception ex) {

		return "error";

	}
}
