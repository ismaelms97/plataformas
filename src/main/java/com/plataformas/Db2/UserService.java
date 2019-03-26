package com.plataformas.Db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.plataformas.model.Daily;
import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;
import com.plataformas.model.User;
@Service
public class UserService {
	//private String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50001/BLUDB:user=rvg03272;password=0@vn6gg9jg7zqjb1;sslConnection=true;";
	private String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50000/BLUDB";
	private String dbUsername = "rvg03272";
	private String dbPassword = "0@vn6gg9jg7zqjb1";
	
	public void initializeDriver() throws ClassNotFoundException {
		try {
			Class.forName("com.ibm.db2.jcc.DB2Driver");  
		}catch (ClassNotFoundException e) {
			System.out.println("Class driver not found");
		}catch (Exception e) {
			System.out.println("Unknow error with driver");
		}
		   
	}

	public List<User> findAll() throws ClassNotFoundException{
		List<User> userList = new ArrayList<User>();
		initializeDriver();		

		try{
		Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
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
	

	public User findByUsername(String username) throws ClassNotFoundException, SQLException {
		User user = null;
		initializeDriver();    
		try {
			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT  U.*, E.name FROM user U join equipo E  on (U.equipo_id = E.id) where username = '"+username+"'"); 			
			
			return User.converFromDataBase(rs);
			
		}catch (Exception e) {
			System.err.println("FIND() more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			user.setUsername("username : "+e.getMessage()+" cause :"+e.getCause());
			return user;
		}
		

	}
	
	

}
