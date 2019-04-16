package com.plataformas.recursos;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.plataformas.model.Estrategia;

@Component
public class SessionResources {

	public boolean checkUserStrategy(HttpSession session, int id) {

		@SuppressWarnings("unchecked")
		List<Estrategia> listaEstrategias  = (List<Estrategia>) session.getAttribute("userStrategy");

		for (Estrategia estrategia : listaEstrategias) {

			System.out.println(estrategia.getId()+" == "+id);

			if(estrategia.getId() == id) {

				return true;
			}
		}

		return false;
	}

	public boolean checkUserSession(HttpSession session) {		

		if(session.getAttribute("userSession") == null) {

			return false;
		}

		return true;
	}	
}