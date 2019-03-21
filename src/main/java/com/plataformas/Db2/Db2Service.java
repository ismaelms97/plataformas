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
import com.plataformas.model.User;
@Service
public class Db2Service {
	private String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50001/BLUDB:user=rvg03272;password=0@vn6gg9jg7zqjb1;sslConnection=true;";
	
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
		Connection con = DriverManager.getConnection(url);
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

	public User findByUsername(String Uusername) throws ClassNotFoundException, SQLException {

		initializeDriver();    
		Connection con = DriverManager.getConnection(url);
		con.setAutoCommit(false);
		Statement  stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT * FROM USER where username = '"+Uusername+"'"); 
		User user = null;
		if(rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			int equipo_id = rs.getInt("equipo_id");
			user  = new User(id,username,password,equipo_id);

			
		}
		return user;

	}
	public List<Estrategia> findEstrategiaById(int idUser) throws ClassNotFoundException  {
		List<Estrategia> estrategiaList = new ArrayList<Estrategia>();		
		initializeDriver();	     
		System.out.println("idUser : "+idUser);
		try {

			Connection con = DriverManager.getConnection(url);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM estrategia Es where Es.equipo_id = "+idUser+"");
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String estado = rs.getString("estado");
				String fechaInicio = rs.getString("fechaInicio");
				String fechafin = rs.getString("fechafin");
				int equipo_id = rs.getInt("equipo_id");
				Estrategia estrategia = new Estrategia( id, estado,fechaInicio ,fechafin ,equipo_id);

				estrategiaList.add(estrategia);
			}

			return estrategiaList;

		} catch (SQLException e) {
			System.err.println("SQL Exeption  findEstrategiaById:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			return estrategiaList;

		}catch (Exception e) {
			System.out.println("Error en findEstrategiaById ");
			return estrategiaList;
		}





	}
	public void saveEstrategia(Estrategia estrategia) throws ClassNotFoundException, SQLException {
		initializeDriver();	    
		try{
			Connection con = DriverManager.getConnection(url);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("INSERT INTO estrategia (estado,fechaInicio,fechaFin,equipo_id) values "
					+ "('"+estrategia.getEstado()+"','"+estrategia.getFechaInicio()+"','"+estrategia.getFechaFin()+"',"+estrategia.getEquipoId()+");"); 
		}catch (SQLException e) {
			System.out.println("SQL Exeption  saveEstrategia:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());


		}catch (Exception e) {
			System.out.println("Error en saveEstrategia ");
		}




	}
	public void saveDaily(Daily daily) throws ClassNotFoundException {
		initializeDriver();	    
		try{
			Connection con = DriverManager.getConnection(url);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("INSERT INTO estrategia (fecha,daily_id) values ('"+daily.getFecha()+"',"+daily.getDailyId()+");"); 

		}catch (SQLException e) {
			System.out.println("SQL Exeption saveDaily:  code -> "+e.getErrorCode() +" more inf : "+e.getMessage());

		}catch (Exception e) {
			System.out.println("Error en saveDaily ");
		}

	}

}
