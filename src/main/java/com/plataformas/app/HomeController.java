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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.plataformas.Db2.Db2Service;
import com.plataformas.model.Estrategia;
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
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model)  {

		/* EJEMPLO DE COMO RECOJER LA LISTA DE USUARIOS DE LA BASE DE DATOS
		@SuppressWarnings("unchecked")
		List<User> users = db2Service.findAll();

		for (User user : users) {

			System.out.println(user.getId()+" , "+user.getUsername()+" "+user.getPassword()+" ,  "+user.getEquipoId());
		}
		 */

		model.addAttribute("user", new User());
		return "home";
	}

	@RequestMapping(value = "/estrategia", method = RequestMethod.POST)
	public String login(@ModelAttribute("user") User user, Model model,HttpSession session){
		return "plataforma";
		
		/*
		 * try {

			User newUser = db2Service.findByUsername(user.getUsername());
		
				if(newUser.getPassword().equals(user.getPassword())) {
					USessions.add(newUser);				
					session.setAttribute("users", USessions);
					List<Estrategia> listaEstrategias = db2Service.findEstrategiaById(newUser.getEquipoId());
					System.out.println("Encontrado");
					model.addAttribute("greeting","Hola "+ user.getUsername());
					model.addAttribute("user",user);
					model.addAttribute("listaEstrategia",listaEstrategias);
					return "plataforma";
				}else {
					model.addAttribute("errorMsg","Contraseña incorrecta");
					System.out.println("No Encontrado");
					return  "home";
				}
			
		} catch (NullPointerException e) { 
			model.addAttribute("errorMsg","El usuario no existe");
			System.out.println("No Encontrado");
			return "home"; 

		}catch (Exception e) {
			System.out.println("Error desconocido");
			return "plataforma";
		}
		*/
	}
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/closeSession", method = RequestMethod.POST)
	public String SessionDestroy(@ModelAttribute("user") User user, Model model,HttpSession session) {	

		List<User> actualSession = (List<User>) session.getAttribute("users"); 
		for (User user2 : actualSession) {

			System.out.println(user2.getUsername()+" "+user2.getPassword()+" "+user2.getId());
		}
		try {
			for(User u : actualSession) {
				if(u.getId() == user.getId()) {

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
			return "redirect:/";
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public String login(Model model) {		

		/* AQUI va el codigo para comprobar user en la base de datos. */

		model.addAttribute("greeting","Hola ");
		return "excelreader";
	}

}
