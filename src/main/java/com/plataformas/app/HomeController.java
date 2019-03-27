package com.plataformas.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.plataformas.Db2.DailyService;
import com.plataformas.Db2.EstrategiaService;
import com.plataformas.Db2.UserService;
import com.plataformas.model.Estrategia;
import com.plataformas.model.User;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value = "/")
public class HomeController {

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
	
	@GetMapping(value = "/")
	public String home(Locale locale, Model model) throws ClassNotFoundException, SQLException  {

		model.addAttribute("user", new User());
		return "home";
	}

	@PostMapping(value = "/mainPanel")
	public String login(@ModelAttribute("user") User user, Model model,HttpSession session){

		User newUser = null;
		boolean userExist = false;
		String mensaje = "";
		try{
			newUser = userService.findByUsername(user.getUsername());

			if(newUser.getPassword().equals(user.getPassword())) {
				session.setAttribute("userSession", newUser);
				USessions.add(newUser);	
				model.addAttribute("greeting","Hola "+ user.getUsername());
				model.addAttribute("user",newUser);
				System.out.println(newUser.getNombreEquipo());
				model.addAttribute("nombreEquipo", " Nombre de equipo : "+newUser.getNombreEquipo());
				model.addAttribute("estrategia", new Estrategia());
				model.addAttribute("equipoId", newUser.getEquipoId());
				
				
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

	@PostMapping(value = "/closeSession")
	public String SessionDestroy(HttpSession session) {	
		synchronized (session) {
		session.invalidate();
		}
		return "redirect:/";

	}
	
	@GetMapping(value = "/saveStrategy")
	public @ResponseBody String saveStrategy(Model model, String name, String startDate, String endDate, String team, String stratTasks ) {	
		
	
		
		String[][] tasks = new String[stratTasks.split("qwer").length][4];
		
		for(int i = 0; i < tasks.length; i++) {
			String[] task = stratTasks.split("qwer");
			tasks[i] = task[i].split(",");
			System.out.println(tasks[i][0] + " - " + tasks[i][1]);
		}
		return "mainPanel";
		
	}
	
	
	@GetMapping(value = "/excel")
	public String login(Model model) {		

		/* AQUI va el codigo para comprobar user en la base de datos. */

		model.addAttribute("greeting","Hola ");
		return "excelreader";
	}

}