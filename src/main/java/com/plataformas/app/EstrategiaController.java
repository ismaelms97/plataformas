package com.plataformas.app;

import java.sql.SQLException;
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

import com.plataformas.Db2.EstrategiaService;
import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;
import com.plataformas.model.User;
import com.plataformas.recursos.SessionResources;
@Controller
@RequestMapping(value = "/estrategia")
public class EstrategiaController {


	@Autowired
	EstrategiaService estrategiaService;
	@Autowired
	SessionResources sessionResources;
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

					model.addAttribute("mensajeAcceso", "Acceso Denegado");

					return "accessDenied";

				}else {

					User actualUser = (User) session.getAttribute("userSession");
					List<Estrategia> listaEstrategias = estrategiaService.findEstrategiaById(actualUser.getEquipoId());	
					model.addAttribute("listaEstrategia",listaEstrategias);
					session.setAttribute("userStrategy", listaEstrategias);
					model.addAttribute("estrategia", new Estrategia());

					if( session.getAttribute("newEstrategia") != null) {

						session.removeAttribute("newEstrategia");
					}

					model.addAttribute("nombreEquipo", " Nombre de equipo : "+actualUser.getNombreEquipo());
					model.addAttribute("greeting","Hola "+ actualUser.getUsername());
					return "mainPanel";

				}
			}catch (Exception e) {

				System.out.println("Panel de control Error con session o con estrategias");
				return "redirect:/";
			}

		}

	}
	@GetMapping(value = "/findEstrategia/{id}")
	public  String findEstrategia(@PathVariable String id,Model model,HttpSession session) {	

		synchronized (session) {

			try {

				if (!sessionResources.checkUserSession(session) || !sessionResources.checkUserStrategy(session,id)) {

					model.addAttribute("mensajeAcceso", "Acceso Denegado");

					return "accessDenied";
				}else {

					List<Tarea> tareas = estrategiaService.findTareasByEstrategia(Integer.parseInt(id));
					model.addAttribute("listaTareas",tareas);
					System.out.println("TAREAS COMPLETE");
					return "plataforma";
				}

			}catch (NumberFormatException e) {

				System.out.println("formato incorrecto en mostrarTareasEstrategia Controller");
				return "mainPanel";

			}catch (Exception e) {

				System.out.println("otra error en mostrarTareasEstrategia Controller");
				return "mainPanel";
			}		
		}

	}
	@PostMapping(value = "/pushEstrategia")
	public  String pushEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {	

		synchronized (session) {

			User us = (User) session.getAttribute("userSession");
			estrategia.setEquipoId(us.getEquipoId());
			session.setAttribute("newEstrategia", estrategia);
			model.addAttribute("tarea", new Tarea());

			return "plataforma";
		}

	}

	@PostMapping(value = "/saveEstrategia")
	public @ResponseBody String saveEstrategia(String stratTasks ,Model model,HttpSession session) {	

		synchronized (session) {

			Estrategia newEstrategia = (Estrategia) session.getAttribute("newEstrategia");

			try {

				List<Tarea> listaTareas = Tarea.stringToObject(stratTasks);
				estrategiaService.saveEstrategiaAndTarea(listaTareas,newEstrategia);

			}catch (Exception e) {

				System.out.println("error al guardar");
			}
		}

		return "redirect:/estrategia/panelControl";


	}

	@PostMapping(value = "/deleteEstrategia")
	public String deleteEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model Model,HttpSession session) {
		synchronized (session) {
			try {
				estrategiaService.deleteEstrategia(estrategia.getId());

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}


		return "mainPanel";

	}

}
