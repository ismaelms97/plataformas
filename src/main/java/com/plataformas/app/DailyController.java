package com.plataformas.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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


	public static final String REDIRECT_HOME = "redirect:/";
	public static final String MAIN_PANEL = "mainPanel";
	public static final String PLATAFORMA = "plataforma";
	public static final String REDIRECT_PANEL_CONTROL = REDIRECT_HOME+"estrategia/panelControl";


	@PostMapping(value = "/findDailys")
	public  String findDailyById(HttpServletRequest request,Model model,HttpSession session) {	

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				try {

					int id = Integer.parseInt(request.getParameter("id"));
					List<Daily> listaDaily = dailyService.findDailyById(id);
					List<Tarea> tareas = strategyService.findTasksByStrategy(id);
					model.addAttribute("listaDaily", listaDaily);
					model.addAttribute("listaTareas",tareas);

				}catch (Exception e) {

					System.err.println("Error en showDaily : no se ha encontrado daily con ese ID");
				}
			}
		}
		
		return PLATAFORMA;
	}

	@GetMapping(value = "/findDailys/{id}")
	public  String findDaily(HttpSession session) {	

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				return REDIRECT_PANEL_CONTROL;
			}
		}		
	}

	@PostMapping(value = "/saveDaily")
	public @ResponseBody String  saveDaily ( String stratDaily,Model model,HttpSession session) {
		
		synchronized (session) {

			String isSaved = "";

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				try {

					int idEstrategia  = (Integer) session.getAttribute("estrategiaID");
					List<Daily> listDaily =  Daily.stringToObject(stratDaily,dbResources.currentDateForDaily(),idEstrategia);
					dailyService.saveDaily(listDaily);
					System.err.println("Daily   Guardada");
					isSaved = "true";

				}catch (Exception e) {

					System.err.println("error al guardar");
					isSaved = "false";
				}

			}
			return isSaved;
		}
	}
}