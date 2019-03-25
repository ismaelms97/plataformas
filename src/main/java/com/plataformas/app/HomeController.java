package com.plataformas.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plataformas.Db2.DailyService;
import com.plataformas.Db2.EstrategiaService;
import com.plataformas.Db2.UserService;
import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;
import com.plataformas.model.User;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private List<User> USessions = new ArrayList<User>();

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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws ClassNotFoundException, SQLException  {

		model.addAttribute("hidde", true);
		model.addAttribute("user", new User());
		return "home";
	}

	@RequestMapping(value = "/mainPanel", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, Model model,HttpSession session){

		User newUser = null;
		boolean userExist = false;
		String mensaje = "";
		try{
			newUser = userService.findByUsername(user.getUsername());

			if(newUser.getPassword().equals(user.getPassword())) {
				session.setAttribute(user.getUsername(), newUser);
				USessions.add(newUser);	
				model.addAttribute("greeting","Hola "+ user.getUsername());
				model.addAttribute("user",user);
				model.addAttribute("hidde", false);
				userExist = true;
			}else {
				mensaje = "Contraseña incorrecta";
			}

		}catch (NullPointerException e) { 			
			mensaje = "El usuario no existe";

		}catch (Exception e) {			
			mensaje = "No hay conexion";
		}

		if(userExist) {

			try {
				List<Estrategia> listaEstrategias = estrategiaService.findEstrategiaById(newUser.getEquipoId());	
				model.addAttribute("listaEstrategia",listaEstrategias);

			}catch (Exception e) {
				System.out.println("listaEstrategia , no se ha encontrado...");
			}			

			return "mainPanel";
		}else {		

			model.addAttribute("errorMsg",mensaje);
			return  "home";
		}

	}
	@RequestMapping(value = "/newEstrategia", method = RequestMethod.GET)
	public String nuevaEstrategia(@ModelAttribute("user") User user, @RequestBody String id ,  Model model,HttpSession session){	
		
		return "plataforma";


	}

	@RequestMapping(value = "/estrategia", method = RequestMethod.GET)
	public String mostrarTareasEstrategia(@ModelAttribute("estrategia") Estrategia estrategia,  Model model,HttpSession session){		

		try {
			List<Tarea> tareas = estrategiaService.findTareasByEstrategia(estrategia.getId());
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


	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/closeSession", method = RequestMethod.POST)
	public String SessionDestroy(@ModelAttribute("user") User user, Model model,HttpSession session) {	

		try {
			session.removeAttribute(user.getUsername());	
			session.removeAttribute(session.getId());

			for(User u : USessions) {
				if(u.getUsername().equals(user.getUsername())) {

					USessions.remove(u);
					System.err.println("Sessiones Actuales "+USessions.size());
				}else {
					System.err.println("No encontrado");
					System.err.println("Sessiones error delete "+USessions.size());
				}
			}

		}catch (ConcurrentModificationException e) {
			return "redirect:/";
		}catch (Exception e) {
			System.out.println("No borrado en remove()");
		}

		// este try es un test para comprobar que el usuario ha sido borrado y no se encuentra
		try {
			User u = (User) session.getAttribute(user.getUsername());
			System.out.println(u.getPassword());
		}catch (Exception e) {
			System.err.println("USUARIO no encontrado despues de borrarlo");
		}


		return "redirect:/";

	}
	
	@RequestMapping(value = "/saveStrategy", method = RequestMethod.GET)
	public @ResponseBody String saveStrategy(Model model, String name, String startDate, String endDate, String team, String stratTasks) {		
		System.out.println("Stategy name: " + startDate);
		String[][] tasks = new String[stratTasks.split("qwer").length][4];
		
		for(int i = 0; i < tasks.length; i++) {
			String[] task = stratTasks.split("qwer");
			tasks[i] = task[i].split(",");
			System.out.println(tasks[i][0] + " - " + tasks[i][1]);
		}
		return "home";
	}
	
	
	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public String login(Model model) {		

		/* AQUI va el codigo para comprobar user en la base de datos. */

		model.addAttribute("greeting","Hola ");
		return "excelreader";
	}

}
