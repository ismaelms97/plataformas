package com.plataformas.app;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plataformas.Db2.DailyService;
import com.plataformas.model.Daily;
@Controller
@RequestMapping(value = "/daily")
public class DailyController {

	@Autowired
	DailyService dailyService;
	//@A

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */


	@GetMapping(value = "/showDaily/{id}")
	public  String showDaily(@PathVariable String id,Model model,HttpSession session) {	
		
		synchronized (session) {
		//	if (!sessionResources.checkUserSession(session)){
			
			//dailyService.findDailyById(Integer.parseInt(id));
		}
		
		return "mainPanel";

	}
	@PostMapping(value = "/saveDaily")
	public @ResponseBody String  saveDaily ( String stratDaily,Model model,HttpSession session) {
		
		synchronized (session) {
			
			try {
				
				List<Daily> listDaily =  Daily.stringToObject(stratDaily);				
				//int idEstrategia  = (Integer) session.getAttribute("estrategiaID");
				//Daily newDaily = null;
				//newDaily.setEstrategiaId(idEstrategia);
				//dailyService.saveDaily(newDaily,idEstrategia);
				System.out.println("Daily   Guardada");

			}catch (Exception e) {

				System.out.println("error al guardar");
			}

		}
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