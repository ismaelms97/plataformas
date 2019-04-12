package com.plataformas.app;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.plataformas.Db2.StrategyService;
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
	StrategyService strategyService;

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

		User userTeamAndRoles = null;
		boolean userExist = false;
		String mensaje = "";

		try{

			User newUser = userService.findByUsername(user.getUsername());

			if(newUser.getPassword().equals(User.encrypt(user.getPassword()))) {

				userTeamAndRoles = userService.findRolebyUserId(newUser);

				session = request.getSession();
				session.setAttribute("userSession", userTeamAndRoles);
				USessions.add(newUser);	

				model.addAttribute("greeting","Usuario: "+ user.getUsername());				
				model.addAttribute("roles", userTeamAndRoles.getRole());

				HashMap<Integer, String> equipos = User.createTeamsIdNames(userTeamAndRoles);	
				model.addAttribute("equipos", equipos);	
				model.addAttribute("estrategia", new Estrategia());
				userExist = true;

			}else {

				mensaje = "Incorrect Password";
			}

		}catch (NullPointerException e) { 	

			mensaje = "User does not exist ";

		}catch (Exception e) {		

			mensaje = "not connection";
		}

		if(userExist) {

			try {

				List<Estrategia> listaEstrategias = strategyService.findStrategyById(userTeamAndRoles.getEquipoId());				
				model.addAttribute("listaEstrategia",listaEstrategias);
				session.setAttribute("userStrategy", listaEstrategias);	


			}catch (Exception e) {

				System.out.println("listaEstrategia , not found...");
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