package czar.english.irregulars.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import czar.english.irregulars.services.VerbService;

@Controller
public class VerbController {
	
	@Autowired
	VerbService verbService;

	@RequestMapping(value = "/verbs/list", method = GET)
	public String main(Model model) {
		model.addAttribute("verbs", verbService.findAll());
		return "verbs/list";
	}

	

}
