package com.plataformas.app;

import java.sql.SQLException;
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

import com.plataformas.Db2.EstrategiaService;
import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;
import com.plataformas.model.User;
@Controller
@RequestMapping(value = "/estrategia")
public class EstrategiaController {


	@Autowired
	EstrategiaService estrategiaService;

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */


	@GetMapping(value = "/panelControl")
	public  String showEstrategia(Model model,HttpSession session) {	

		synchronized (session) {
			try {

				User actualUser = (User) session.getAttribute("userSession");
				List<Estrategia> listaEstrategias = estrategiaService.findEstrategiaById(actualUser.getEquipoId());	
				model.addAttribute("listaEstrategia",listaEstrategias);
				model.addAttribute("estrategia", new Estrategia());
				
				model.addAttribute("nombreEquipo", " Nombre de equipo : "+actualUser.getNombreEquipo());

				model.addAttribute("greeting","Hola "+ actualUser.getUsername());
				return "mainPanel";

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
				List<Tarea> tareas = estrategiaService.findTareasByEstrategia(Integer.parseInt(id));
				model.addAttribute("listaTareas",tareas);

				System.out.println("TAREAS COMPLETE");
				return "plataforma";
			}catch (NumberFormatException e) {
				System.out.println("formato incorrecto en mostrarTareasEstrategia Controller");
				return "mainPanel";
			}catch (Exception e) {
				System.out.println("otra error en mostrarTareasEstrategia Controller");
				return "mainPanel";
			}		
		}

	}

	@PostMapping(value = "/saveEstrategia")
	public  String saveEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,Model model,HttpSession session) {	
		
		synchronized (session) {
			User us = (User) session.getAttribute("userSession");
			estrategia.setEquipoId(us.getEquipoId());
			try {
				estrategiaService.saveEstrategia(estrategia);
			

			}catch (Exception e) {
				System.out.println("error al guardar");
			}

			return "plataforma";
		}

	}

	@PostMapping(value = "/updateEstrategia")
	public String deleteEstrategia(Model model) {	


		return "mainPanel";

	}

}
