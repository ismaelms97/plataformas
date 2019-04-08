package com.plataformas.Db2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public List<User> findAll(){

		List<User> userList = new ArrayList<User>();		

		try{

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM USER"); 

			while (rs.next()) {

				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				int equipo_id = rs.getInt("equipo_id");
				User user = new User( id, username,password ,equipo_id );
				userList.add(user);
			}

			return userList;

		}catch (SQLException e) {
			
			System.out.println("SQL Exeption  findEstrategiaById:  code -> "+e.getErrorCode());
			return userList;

		}catch (Exception e) {
			
			System.out.println("Error en findEstrategiaById ");
			return userList;
		}
	}


	public User findByUsername(String username) {
		
		User user = null;
		
		try {
			
			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT  U.*, E.name FROM user U join equipo E  on (U.equipo_id = E.id) where username = '"+username+"'"); 			

			return User.converFromDataBase(rs);

		}catch (Exception e) {
			
			System.err.println("FIND() more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			return user;
		}
	}
	
	public String findRolebyUserId(int id) {		
				
		try {
			
			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT R.role FROM role R  WHERE R.id = (SELECT UER.id_role FROM user_equipo_role UER where UER.id_user = "+id+")"); 			

			return rs.getString("role");

		}catch (Exception e) {
			
			System.err.println("findRolebyUserId() more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			
			return null;
		}
	}
}
