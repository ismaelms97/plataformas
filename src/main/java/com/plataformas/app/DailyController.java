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
import com.plataformas.Db2.StrategyService;
import com.plataformas.model.Daily;
import com.plataformas.model.Tarea;
import com.plataformas.recursos.DbResources;
import com.plataformas.recursos.SessionResources;
@Controller
@RequestMapping(value = "/daily")
public class DailyController {

	@Autowired
	DailyService dailyService;
	@Autowired
	SessionResources sessionResources;
	@Autowired
	StrategyService strategyService;
	@Autowired
	DbResources dbResources;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	@GetMapping(value = "/findDailys/{id}")
	public  String findDailyById(@PathVariable String id,Model model,HttpSession session) {	

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {

				try {

					List<Daily> listaDaily = dailyService.findDailyById(Integer.parseInt(id));
					List<Tarea> tareas = strategyService.findTasksByStrategy(Integer.parseInt(id));
					model.addAttribute("listaDaily", listaDaily);
					model.addAttribute("listaTareas",tareas);
				
				}catch (Exception e) {

					System.out.println("Error en showDaily : no se ha encontrado daily con ese ID");
				}

			}
		}
		return "plataforma";
	}

	@PostMapping(value = "/saveDaily")
	public @ResponseBody String  saveDaily ( String stratDaily,Model model,HttpSession session) {
		synchronized (session) {
			System.out.println("Daily " + stratDaily);
			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {

				try {

					int idEstrategia  = (Integer) session.getAttribute("estrategiaID");
					List<Daily> listDaily =  Daily.stringToObject(stratDaily,dbResources.currentDateForDaily(),idEstrategia);
					dailyService.saveDaily(listDaily);
					System.out.println("Daily   Guardada");

				}catch (Exception e) {

					System.out.println("error al guardar");
				}

			}
			return "mainPanel";
		}
	}

	@PostMapping(value = "/date")
	public @ResponseBody String getDatesOfDaily(String id ,Model model,HttpSession session) {	

		synchronized (session) {

			String date = "";

			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {

				try {

					date = dailyService.findDateDaily(Integer.parseInt(id));

				}catch (Exception e) {

					System.out.println("Error al recoger fechas");
				}
			}

			return date;
		}
	}
}