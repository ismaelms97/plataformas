package com.plataformas.Db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plataformas.model.Daily;
import com.plataformas.model.Tarea;
import com.plataformas.model.User;
import com.plataformas.recursos.DbResources;

@Service
public class DailyService {

	@Autowired
	DbResources  dbResources;

	public List<Daily> findDailyById(int idEstrategia){

		List<Daily> dailyList = new ArrayList<Daily>();		

		try {

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT D.fecha,DT.tarea_id,DT.estadoActual,DT.subEstadoActual FROM daily D,daily_tarea DT where D.estrategia_id = "+idEstrategia+"");
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

	public List<String> findDateDaily(int idEstrategia){
		
		List<String> dates = new ArrayList<String>();

		try {

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("SELECT D.fechaFROM daily D where D.estrategia_id = "+idEstrategia+"");		
			
			while (rs.next()) {
				
				dates.add(rs.getString("fecha"));
			}
			
			return dates ;

		} catch (SQLException e) {

			System.err.println("SQL Exeption  findDateDaily:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());

			return dates;

		}catch (Exception e) {

			System.out.println("Error en findDailyById ");
			return dates;
		}

	}

	@Transactional
	public void saveDaily(List<Daily> listDailty) throws  SQLException{

		Connection con = null;

		try{

			con = dbResources.getConection();
			con.setAutoCommit(false);

			String sql = "INSERT INTO daily (fecha,estrategia_id) values "
					+ "('"+listDailty.get(0).getFecha()+"',"+listDailty.get(0).getEstrategiaId()+")";

			PreparedStatement  stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int lastIndex = rs.getInt(1);

			stmt = con.prepareStatement("INSERT INTO daily_tarea (daily_id,tarea_id,estadoActual,subEstadoActual) values (?,?,?,?)");

			for (Daily daily : listDailty) {

				stmt.setInt(1, lastIndex);
				stmt.setInt(2, daily.getTareaId());
				stmt.setString(3, daily.getEstadoActual());
				stmt.setString(4, daily.getSubEstadoActual());

				try {
					stmt.executeUpdate();

				}catch  (Exception e) {

					System.out.println("Esta intermedia-daily ID ya existe");
				}

				System.out.println("intermedia guardada ");
			}

			con.commit();

		}catch (SQLException e) {

			System.out.println("SQL Exeption  saveEstrategiaAndTarea:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());
			con.rollback();

		}
	}
}

