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
/*
 *  ESTA CLASE FALTA ACABARLA
 */
@Service
public class DailyService {
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


	public List<Estrategia> findAllDaily(int idUser) throws ClassNotFoundException  {
		List<Estrategia> estrategiaList = new ArrayList<Estrategia>();		
		initializeDriver();	     
		System.out.println("idUser : "+idUser);
		try {

			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM daily D where D.equipo_id = "+idUser+"");

			while (rs.next()) {
				int id = rs.getInt("id");
				String nombre = rs.getString("nombre");
				String estado = rs.getString("estado");
				String fechaInicio = rs.getString("fechaInicio");
				String fechafin = rs.getString("fechafin");
				int equipo_id = rs.getInt("equipo_id");
				Estrategia estrategia = new Estrategia( id,nombre, estado,fechaInicio ,fechafin ,equipo_id);

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
	public List<Tarea> findTareasByEstrategia(int idEstrategia) throws ClassNotFoundException  {
		List<Tarea> tareaList = new ArrayList<Tarea>();		
		initializeDriver();	     
		System.out.println("idUser : "+idEstrategia);
		try {

			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT DISTINCT  T.id, T.tipo, T.estadoInicio, T.estadoFinal  FROM tarea T , estrategia E , estrategia_tarea ET"
					+ " where T.id = ET.tarea_id AND  ET.estrategia_id = "+idEstrategia+"");

			while (rs.next()) {
				int id = rs.getInt("id");
				String tipo = rs.getString("tipo");
				String estadoInicio = rs.getString("estadoInicio");
				String estadoFinal = rs.getString("estadoFinal");
				Tarea tarea = new Tarea(id,tipo,estadoInicio,estadoFinal);
				tareaList.add(tarea);
			}

			return tareaList;

		} catch (SQLException e) {
			System.err.println("SQL Exeption  findTareasByEstrategia:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			return tareaList;

		}catch (Exception e) {
			System.out.println("Error en findEstrategiaById ");
			return tareaList;
		}
	}
	
	public void saveDaily(Daily daily) throws ClassNotFoundException {
		initializeDriver();	    
		try{
			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("INSERT INTO estrategia (fecha,daily_id) values ('"+daily.getFecha()+"',"+daily.getDailyId()+")"); 

		}catch (SQLException e) {
			System.out.println("SQL Exeption saveDaily:  code -> "+e.getErrorCode() +" more inf : "+e.getMessage());

		}catch (Exception e) {
			System.out.println("Error en saveDaily ");
		}

	}

}
