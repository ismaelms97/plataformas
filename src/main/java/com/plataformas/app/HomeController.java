package com.plataformas.app;

import java.sql.SQLException;
import java.util.Locale;

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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, Model model) throws SQLException, ClassNotFoundException {		

		try {

			String password = db2Service.findByPassword(user.getUsername());
			if(password.equals(user.getPassword())) {

				System.out.println("Encontrado");
				model.addAttribute("greeting","Hola "+ user.getUsername());
				return "plataforma";
			}else {
				System.out.println("No Encontrado");
				return "home";
			}
		} catch (NullPointerException e) { 

			System.out.println("No Encontrado");
			return "home";

		}catch (Exception e) {
			System.out.println("Error desconocido");
			return "home";
		}




	}

	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public String login(Model model) {		

		/* AQUI va el codigo para comprobar user en la base de datos. */

		model.addAttribute("greeting","Hola ");
		return "excelreader";
	}

}
