package com.plataformas.app;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plataformas.Db2.DailyService;
import com.plataformas.Db2.StrategyService;
import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;
import com.plataformas.model.User;
import com.plataformas.recursos.SessionResources;


@Controller
@RequestMapping(value = "/estrategia")
public class StrategyController {


	@Autowired
	StrategyService strategyService;
	@Autowired
	SessionResources sessionResources;
	@Autowired
	DailyService dailyService;
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */


	@GetMapping(value = "/panelControl")
	public  String showEstrategia(Model model,HttpSession session) {	

		synchronized (session) {

			try {

				if (!sessionResources.checkUserSession(session)){

					model.addAttribute("mensajeAcceso", "Inactive Session");
					return "accessDenied";

				}else {

					User actualUser = (User) session.getAttribute("userSession");
					List<Estrategia> listaEstrategias = strategyService.findStrategyById(actualUser.getEquipoId());	
					model.addAttribute("listaEstrategia",listaEstrategias);
					session.setAttribute("userStrategy", listaEstrategias);
					model.addAttribute("estrategia", new Estrategia());

					if( session.getAttribute("newEstrategia") != null) {

						session.removeAttribute("newEstrategia");
					}

					HashMap<Integer, String> equipos = User.createTeamsIdNames(actualUser);	
					model.addAttribute("equipos", equipos);	
					model.addAttribute("greeting","Usuario: "+ actualUser.getUsername());	
					model.addAttribute("roles", actualUser.getRole());
					
					return "mainPanel";
				}

			}catch (Exception e) {

				System.out.println("Panel  control Error with session or strategy");
				return "redirect:/";
			}
		}
	}

	@GetMapping(value = "/findEstrategia/{id}")
	public  String findEstrategia(@PathVariable String id,Model model,HttpSession session) {	

		synchronized (session) {

			try {

				if (!sessionResources.checkUserSession(session) || !sessionResources.checkUserStrategy(session,id)) {

					model.addAttribute("mensajeAcceso", "You have not acces with this strategy");
					return "accessDenied";

				}else {

					List<Tarea> tareas = strategyService.findTasksByStrategy(Integer.parseInt(id));
					session.setAttribute("estrategiaID", Integer.parseInt(id.trim()));
					model.addAttribute("listaTareas",tareas);
					System.out.println("TAREAS COMPLETE");

					return "plataforma";
				}

			}catch (NumberFormatException e) {

				System.out.println("Incorrect format en mostrarTareasEstrategia Controller");
				return "mainPanel";

			}catch (Exception e) {

				System.out.println("other error in strategy Controller");
				return "mainPanel";
			}		
		}
	}

	@PostMapping(value = "/pushEstrategia")
	public  String pushEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {	

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {

				session.setAttribute("newEstrategia", estrategia);
				model.addAttribute("tarea", new Tarea());
			}

			return "plataforma";
		}
	}

	@PostMapping(value = "/saveEstrategia")
	public @ResponseBody String saveEstrategia(String stratTasks ,Model model,HttpSession session) {	
		System.out.println("Tasks " + stratTasks);
		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {

				Estrategia newEstrategia = (Estrategia) session.getAttribute("newEstrategia");

				try {

					List<Tarea> listaTareas = Tarea.stringToObject(stratTasks);
					strategyService.saveStrategyAndTask(listaTareas,newEstrategia);

				}catch (Exception e) {

					System.out.println("error al guardar");
				}
			}

			return "redirect:/estrategia/panelControl";
		}
	}
	
	@PostMapping(value = "/updateEstrategia")
	public String updateEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {
				
				try {

					strategyService.updateStrategy(estrategia.getId());

				} catch (Exception e) {

					System.out.println("Error update");
				}
			}

			return "mainPanel";
		}
	}

	@PostMapping(value = "/deleteEstrategia")
	public String deleteEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				model.addAttribute("mensajeAcceso", "Inactive Session");
				return "accessDenied";

			}else {
				
				try {

					strategyService.deleteStrategy(estrategia.getId());

				} catch (Exception e) {

					System.out.println("Error delete");
				}
			}

			return "mainPanel";
		}
	}
}