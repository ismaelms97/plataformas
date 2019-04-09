package com.plataformas.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */

	@GetMapping(value = "/")
	public String home(Locale locale, Model model)  {

		model.addAttribute("user", new User());
		return "home";
	}

	@PostMapping(value = "/mainPanel")
	public String login(@ModelAttribute("user") User user, Model model,HttpServletRequest request,HttpSession session){

		User newUser = null;
		boolean userExist = false;
		String mensaje = "";

		try{
			
			newUser = userService.findByUsername(user.getUsername());
			System.out.println(newUser.getUsername()+" "+newUser.getPassword());
			
			if(newUser.getPassword().equals(User.encrypt(user.getPassword()))) {

				
				
				
				User UserTeamAndRoles = userService.findRolebyUserId(newUser);
				System.out.println(UserTeamAndRoles.getUsername()+" "+UserTeamAndRoles.getPassword()+" "+UserTeamAndRoles.getRole());
				

				session = request.getSession();
				session.setAttribute("userSession", newUser);
				USessions.add(newUser);	
				model.addAttribute("greeting","Usuario: "+ user.getUsername());
				model.addAttribute("teams", UserTeamAndRoles.getNombreEquipo());
				model.addAttribute("roles", UserTeamAndRoles.getRole());
				model.addAttribute("teamsID", UserTeamAndRoles.getEquipoId());
				
				model.addAttribute("estrategia", new Estrategia());
				model.addAttribute("equipoId", newUser.getEquipoId());
				userExist = true;

			}else {

				mensaje = "Contraseņa incorrecta";
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
				session.setAttribute("userStrategy", listaEstrategias);	


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
			System.out.println("Session closed");
		}

		return "redirect:/";
	}
}