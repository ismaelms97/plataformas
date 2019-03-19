package com.plataformas.app;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.plataformas.Db2.Db2Service;
import com.plataformas.model.User;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	private List<User> USessions = new ArrayList<User>();
	@Autowired
	Db2Service db2Service;
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		model.addAttribute("user", new User());
		return "home";
	}

	@RequestMapping(value = "/estrategia", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, Model model,HttpSession session){
		try {

			String password = db2Service.findByUsername(user.getUsername());
			if(password.equals(user.getPassword())) {
				USessions.add(user);
				session.setAttribute("users", USessions);
				System.out.println("Encontrado");

				@SuppressWarnings("unchecked")
				List<User> x = (List<User>) session.getAttribute("users"); // Recoge la List de users para de la session
				System.out.println(x.size());

				model.addAttribute("greeting","Hola "+ user.getUsername());
				model.addAttribute("user",user);

				return "plataforma";
			}else {
				model.addAttribute("errorMsg","Contraseña incorrecta");
				System.out.println("No Encontrado");
				return "home";
			}
		} catch (NullPointerException e) { 
			model.addAttribute("errorMsg","El usuario no existe");
			System.out.println("No Encontrado");
			return "home";

		}catch (Exception e) {
			System.out.println("Error desconocido");
			return "home";
		}

	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/closeSession", method = RequestMethod.POST)
	public String SessionDestroy(@ModelAttribute("user") User user, Model model,HttpSession session) {	

		List<User> actualSession = (List<User>) session.getAttribute("users"); 
		try {
			for(User u : actualSession) {
				if(u.getUsername().equals(user.getUsername())) {

					actualSession.remove(u);
					USessions.remove(u);
					session.setAttribute("users", actualSession);
					System.out.println("Sessiones Actuales "+actualSession.size());

				}else {
					System.out.println("No encontrado");
					System.out.println("Sessiones FAIL de borrar "+actualSession.size());
				}

			}
			
		}catch (ConcurrentModificationException e) {
			return "home";
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
