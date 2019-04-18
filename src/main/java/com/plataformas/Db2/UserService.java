package com.plataformas.Db2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.plataformas.model.User;
import com.plataformas.recursos.DbResources;

@Service
public class UserService {

	@Autowired
	DbResources  dbResources;

//	public User findAllUsers() { // ESTO RECOGE TOOODOS LOS USUARIOS CON SUS RESPECTIVOS EQUIPOS
//
//		User user = null;
//
//		try {
//
//			Connection con = dbResources.getConection();
//			con.setAutoCommit(false);
//			Statement  stmt = con.createStatement(); 
//			ResultSet rs = stmt.executeQuery("SELECT U.username,E.name FROM user U JOIN user_equipo_role UER ON (U.id = UER.id_user) JOIN equipo E ON (E.id = UER.id_equipo)"); 			
//
//			return User.converFromDataBase(rs);
//
//		}catch (Exception e) {
//
//			System.err.println("findAllUsers() more inf : "+e.getMessage()+" reason  -> "+e.getCause());
//			return user;
//		}
//	}
	
	public User findByUsername(String username) {

		User user = null;

		try {

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT U.id, U.username,U.password FROM user U  where username = '"+username+"'"); 			

			return User.converFromDataBase(rs);

		}catch (Exception e) {

			System.err.println("FIND() more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			return user;
		}
	}

	public User findRolebyUserId(User newUser) {		

		try {

			List<String> listaEquipos = new ArrayList<String>();
			List<String> listaRoles = new ArrayList<String>();
			List<Integer> listaIdTeams = new ArrayList<Integer>();

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT name, role, UER.id_equipo FROM equipo E INNER JOIN user_equipo_role UER ON (E.id = UER.id_equipo) "
					+ "INNER JOIN user U ON (UER.id_user = U.id) "
					+ "INNER JOIN role R ON (UER.id_role = R.id) "
					+ "WHERE U.id = "+newUser.getId()+""); 

			while (rs.next()) {

				listaEquipos.add(rs.getString("name"));
				listaRoles.add(rs.getString("role"));
				listaIdTeams.add(rs.getInt("id_equipo"));
			}

			newUser.setNombreEquipo(listaEquipos);
			newUser.setRole(listaRoles);
			newUser.setEquipoId(listaIdTeams);

			return newUser;

		}catch (Exception e) {

			System.err.println("findRolebyUserId() more inf : "+e.getMessage()+" reason  -> "+e.getCause());

			return newUser;
		}
	}
}