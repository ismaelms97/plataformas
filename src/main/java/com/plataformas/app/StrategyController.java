package com.plataformas.app;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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


	public static final String REDIRECT_HOME = "redirect:/";
	public static final String REDIRECT_PANEL_CONTROL = REDIRECT_HOME+"estrategia/panelControl";
	public static final String MAIN_PANEL = "mainPanel";
	public static final String PLATAFORMA = "plataforma";


	@GetMapping(value = "/panelControl")
	public  String showEstrategia(Model model,HttpSession session) {	

		synchronized (session) {

			try {

				if (!sessionResources.checkUserSession(session)){

					return REDIRECT_HOME;

				}else {

					User actualUser = (User) session.getAttribute("userSession");
					HashMap<Integer, String> equipos = User.createTeamsIdNames(actualUser);	
					List<Estrategia> listaEstrategias = strategyService.findStrategyById(actualUser.getEquipoId());	
					session.setAttribute("userStrategy", listaEstrategias);
					
					if( session.getAttribute("newEstrategia") != null) {

						session.removeAttribute("newEstrategia");
					}
					
					model.addAttribute("equipos", equipos);	
					model.addAttribute("greeting","Usuario: "+ actualUser.getUsername());	
					model.addAttribute("roles", actualUser.getRole());					
					model.addAttribute("listaEstrategia",listaEstrategias);					
					model.addAttribute("estrategia", new Estrategia());
					
					return MAIN_PANEL;
				}

			}catch (Exception e) {

				System.out.println("Panel  control Error with session or strategy");
				return REDIRECT_HOME;
			}
		}
	}

	@PostMapping(value = "/findEstrategia")
	public  String findEstrategia(HttpServletRequest request,Model model,HttpSession session) {	

		synchronized (session) {

			try {
				
				int id = Integer.parseInt(request.getParameter("id"));
				System.out.println(id);
				
				if (!sessionResources.checkUserSession(session)){

					return REDIRECT_HOME;

				}else if (!sessionResources.checkUserStrategy(session,id)){

					return REDIRECT_PANEL_CONTROL;
				}	
				
				
				List<Tarea> tareas = strategyService.findTasksByStrategy(id);
				session.setAttribute("estrategiaID", id);
				model.addAttribute("listaTareas",tareas);
				for (Tarea tarea : tareas) {
					System.out.println(tarea.getId());
					System.out.println("recuperando..estado"+tarea.getEstadoInicio());
				}
				System.out.println("TAREAS COMPLETE");

				return PLATAFORMA;


			}catch (NumberFormatException e) {

				System.out.println("Incorrect format en mostrarTareasEstrategia Controller");
				return MAIN_PANEL;

			}catch (Exception e) {

				System.out.println("other error in strategy Controller");
				return MAIN_PANEL;
			}		
		}
	}
	
	@GetMapping(value = "/findEstrategia/{id}")
	public  String findDaily(HttpSession session) {	

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				return REDIRECT_PANEL_CONTROL;
			}
		}		
	}

	@PostMapping(value = "/pushEstrategia")
	public  String pushEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {	

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				session.setAttribute("newEstrategia", estrategia);
				model.addAttribute("tarea", new Tarea());
			}

			return PLATAFORMA;
		}
	}

	@PostMapping(value = "/saveEstrategia")
	public @ResponseBody String saveEstrategia(String stratTasks ,Model model,HttpSession session) {	
		System.out.println("Tasks " + stratTasks);
		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				Estrategia newEstrategia = (Estrategia) session.getAttribute("newEstrategia");

				try {

					List<Tarea> listaTareas = Tarea.stringToObject(stratTasks);
					strategyService.saveStrategyAndTask(listaTareas,newEstrategia);

				}catch (Exception e) {

					System.out.println("error al guardar");
				}
			}

			return REDIRECT_PANEL_CONTROL;
		}
	}

	@PostMapping(value = "/updateEstrategia")
	public String updateEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				try {

					strategyService.updateStrategy(estrategia.getId());

				} catch (Exception e) {

					System.out.println("Error update");
				}
			}

			return MAIN_PANEL;
		}
	}

	@PostMapping(value = "/deleteEstrategia")
	public String deleteEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {

		synchronized (session) {

			if (!sessionResources.checkUserSession(session)){

				return REDIRECT_HOME;

			}else {

				try {

					strategyService.deleteStrategy(estrategia.getId());

				} catch (Exception e) {

					System.out.println("Error delete");
				}
			}

			return MAIN_PANEL;
		}
	}
}