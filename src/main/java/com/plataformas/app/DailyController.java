package com.plataformas.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.plataformas.Db2.DailyService;
import com.plataformas.Db2.EstrategiaService;
import com.plataformas.Db2.UserService;
import com.plataformas.model.User;
@Controller
@RequestMapping(value = "/plataforma")
public class DailyController {

	private static  List<User> USessions = new ArrayList<User>();

	@Autowired
	UserService userService;
	@Autowired
	EstrategiaService estrategiaService;
	@Autowired
	DailyService dailyService;

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