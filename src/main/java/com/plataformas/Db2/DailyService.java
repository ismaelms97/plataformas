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
import org.springframework.transaction.annotation.Transactional;

import com.plataformas.model.Daily;
import com.plataformas.model.Tarea;

@Service
public class DailyService {

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

	public List<Daily> findDailyById(int idEstrategia) throws ClassNotFoundException  {
		
		List<Daily> dailyList = new ArrayList<Daily>();		
		initializeDriver();	  
		
		try {

			Connection con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT * FROM daily D where D.estrategia_id = "+idEstrategia+"");
			return Daily.converFromDatabase(rs, dailyList);

		} catch (SQLException e) {
			
			System.err.println("SQL Exeption  findDailyById:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());
			
			return dailyList;

		}catch (Exception e) {
			
			System.out.println("Error en findDailyById ");
			return dailyList;
		}

	}

	@Transactional
	public void saveDaily(Daily daily,int estrategiaID ,List<Tarea> tareas) throws ClassNotFoundException, SQLException{

		initializeDriver();	
		Connection con = null;

		try{

			con = DriverManager.getConnection(url,dbUsername,dbPassword);
			con.setAutoCommit(false);

			String sql = "INSERT INTO daily (fecha,estrategia_id) values "
					+ "('"+daily.getFecha()+"',"+daily.getEstrategiaId()+")";

			PreparedStatement  stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int lastIndex = rs.getInt(1);


			stmt = con.prepareStatement("INSERT INTO daily_tarea (daily_id,tarea_id,estadoActual,subEstadoActual) values (?,?,?,?)");

			for (Tarea tarea : tareas) {
				stmt.setInt(1, lastIndex);
				stmt.setInt(2, tarea.getId());
				stmt.setString(2, tarea.getEstadoInicio());
				stmt.setString(2, tarea.getEstadoFinal());

				stmt.executeUpdate();
				System.out.println(" intermedia guardada ");
			}

			con.commit();

		}catch (SQLException e) {
			
			System.out.println("SQL Exeption  saveEstrategiaAndTarea:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());
			con.rollback();

		}
	}
}

