package com.plataformas.Db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;

@Service
public class EstrategiaService {

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


	public List<Estrategia> findEstrategiaById(int idUser) throws ClassNotFoundException  {
		List<Estrategia> estrategiaList = new ArrayList<Estrategia>();		
		initializeDriver();	     
		try {

			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM estrategia Es where Es.equipo_id = "+idUser+"");



			return Estrategia.converFromDatabase(rs, estrategiaList);

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
			/*ResultSet rs = stmt.executeQuery("SELECT DISTINCT  T.id, T.tipo, T.estadoInicio, T.estadoFinal  FROM tarea T , estrategia E , estrategia_tarea ET"
					+ " where T.id = ET.tarea_id AND  ET.estrategia_id = "+idEstrategia+"");
					*/
			ResultSet rs = stmt.executeQuery("select distinct T.* , ET.estadoInicio,ET.estadoFinal from tarea T , estrategia E , estrategia_tarea ET where T.id = ET.tarea_id AND ET.estrategia_id = "+idEstrategia+"");


			return Tarea.converFromDatabase(rs, tareaList);

		} catch (SQLException e) {
			System.err.println("SQL Exeption  findTareasByEstrategia:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			return tareaList;

		}catch (Exception e) {
			System.out.println("Error en findEstrategiaById ");
			return tareaList;
		}
	}

	public void saveEstrategia(Estrategia estrategia) throws ClassNotFoundException, SQLException {
		initializeDriver();	    
		try{
			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false); 
			Statement  stmt = con.createStatement(); 

			int rs = stmt.executeUpdate("INSERT INTO estrategia (nombre,estado,fechaInicio,fechaFin,equipo_id) values "
					+ "('"+estrategia.getNombre()+"','"+estrategia.getEstado()+"','"+estrategia.getFechaInicio()+"','"+estrategia.getFechaFin()+"',"+estrategia.getEquipoId()+")");
			con.commit();
		}catch (SQLException e) {
			System.out.println("SQL Exeption  saveEstrategia:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());


		}catch (Exception e) {
			System.out.println("Error en saveEstrategia ");
		}
	}

	public List<Estrategia> findEstrategiaByTeam(int idUser) throws ClassNotFoundException  {
		List<Estrategia> estrategiaList = new ArrayList<Estrategia>();		
		initializeDriver();	     
		try {

			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM estrategia Es where Es.equipo_id = "+idUser+"");


			return Estrategia.converFromDatabase(rs, estrategiaList);

		} catch (SQLException e) {
			System.err.println("SQL Exeption  findEstrategiaByTeam:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			return estrategiaList;

		}catch (Exception e) {
			System.out.println("Error en findEstrategiaByTeam ");
			return estrategiaList;
		}

	}
	public void saveTarea(List<Tarea> tareas) throws ClassNotFoundException, SQLException {
		initializeDriver();	    
		try{
			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false); 


			PreparedStatement  stmt = con.prepareStatement("INSERT INTO tarea (id,tipo,prioridad,resumen,tamaño,complejidad,propiedad,peticionario,relevante,urgente,planificado)"
					+ " values (?,?,?,?,?,?,?,?,?,?,?)");
			for (Tarea tarea : tareas) {
				stmt.setInt(1, tarea.getId());
				stmt.setString(2, tarea.getTipo());
				stmt.setString(3, tarea.getPrioridad());
				stmt.setString(4, tarea.getResumen());
				stmt.setString(5, tarea.getTamaño());
				stmt.setString(6, tarea.getComplejidad());
				stmt.setString(7, tarea.getPropiedad());
				stmt.setString(8, tarea.getPeticionario());
				stmt.setBoolean(9, tarea.isRelevante());
				stmt.setBoolean(10, tarea.isUrgente());
				stmt.setString(11, tarea.getPlanificado());
				
				stmt.executeUpdate();
				}
			con.commit();

			

		}catch (SQLException e) {
			System.out.println("SQL Exeption  savaTarea:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());


		}catch (Exception e) {
			System.out.println("Error en savaTarea ");
		}
	}
	public void saveEstrategiaTarea(List<Tarea> tareas) throws ClassNotFoundException, SQLException {
		initializeDriver();	
		try{
			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false); 
			PreparedStatement  stmt = con.prepareStatement("INSERT INTO estrategia_tarea (estadoInicio,estadoFinal,tarea_id,estrategia_id) values (?,?,?,(select max(id) from estrategia))");
			for (Tarea tarea : tareas) {
				stmt.setString(1, tarea.getEstadoInicio());
				stmt.setString(2, tarea.getEstadoFinal());
				stmt.setInt(3, tarea.getId());
				stmt.executeUpdate();
				System.out.println(" intermedia guardada ");
			}

			con.commit();

		}catch (SQLException e) {
			System.out.println("SQL Exeption  saveEstrategiaTarea:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());


		}catch (Exception e) {
			System.out.println("Error en saveEstrategiaTarea ");
		}
	}
}



