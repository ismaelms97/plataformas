package com.plataformas.app;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping(value = "/daily")
public class DailyController {


	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	

	@GetMapping(value = "/showDaily")
	public  String showDaily(Model model) {	
		
		
		return "mainPanel";
		
	}
	@PostMapping(value = "/saveDaily")
	public  String saveDaily(Model model) {	
		
		
		return "mainPanel";
		
	}
	@GetMapping(value = "/findDaily")
	public String findDailyById(Model model) {	
		
		
		return "mainPanel";
		
	}
	@PostMapping(value = "/updateDaily")
	public String updateDaily(Model model) {	
		
		
		return "mainPanel";
		
	}
	@PostMapping(value = "/deleteDaily")
	public String deleteDaily(Model model) {	
		
		
		return "mainPanel";
	}

}