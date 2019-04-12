package com.plataformas.app;

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

	@Autowired
	UserService userService;
	@Autowired
	StrategyService strategyService;
	
	
	public static final String HOME = "home";
	public static final String REDIRECT_HOME = "redirect:/";
	public static final String REDIRECT_MAIN_CONTROL = REDIRECT_HOME+"estrategia/panelControl";
	public static final String MAIN_PANEL = "mainPanel";


	@GetMapping(value = "/")
	public String home(Locale locale, Model model)  {

		model.addAttribute("user", new User());
		return HOME;
	}
	
	@GetMapping(value = "/mainPanel")
	public String logGet(Locale locale, Model model)  {
		
		return "redirect:/estrategia/panelControl";
	}

	@PostMapping(value = "/mainPanel")
	public String login(@ModelAttribute("user") User user, Model model,HttpServletRequest request,HttpSession session){

		
		boolean userExist = false;
		String mensaje = "";
		User newUser = null;

		try{

			newUser = userService.findByUsername(user.getUsername());

			if(newUser.getPassword().equals(User.encrypt(user.getPassword()))) {		

				userExist = true;

			}else {

				mensaje = "Incorrect Password";
			}

		}catch (NullPointerException e) { 	

			mensaje = "User does not exist ";

		}catch (Exception e) {		

			mensaje = "not connection";
		}

		if(!userExist) {
			
			model.addAttribute("errorMsg",mensaje);
			return  HOME;			

		}else {	
			
			User userTeamAndRoles = userService.findRolebyUserId(newUser);
			session = request.getSession();
			session.setAttribute("userSession", userTeamAndRoles);

			return REDIRECT_MAIN_CONTROL;
		
		}
	}

	@PostMapping(value = "/closeSession")
	public String SessionDestroy(HttpSession session) {	

		synchronized (session) {

			session.invalidate();
			System.out.println("Session closed");
		}

		return REDIRECT_HOME;
	}
}