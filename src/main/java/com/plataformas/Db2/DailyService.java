package com.plataformas.Db2;

import java.sql.Connection;
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
			ResultSet rs = stmt.executeQuery("SELECT D.fecha,DT.tarea_id,DT.estadoActual,DT.subEstadoActual,DT.propiedad FROM daily D,daily_tarea DT where D.estrategia_id = "+idEstrategia+"");
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

	public String findDateDaily(int idEstrategia){
		
		String date = null;
		
		try {

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery("Select D.fecha FROM daily D where D.estrategia_id = "+idEstrategia+" AND"
							+ " D.id = (Select MAX(D.id) from daily D where D.estrategia_id ="+idEstrategia+")");		
			
			while (rs.next()) {
				
				date = rs.getString("fecha");
			}
			
			return date;

		} catch (SQLException e) {

			System.err.println("SQL Exeption  findDateDaily:  code -> "+e.getErrorCode());
			System.err.println("more inf : "+e.getMessage()+" reason  -> "+e.getCause());

			return date;

		}catch (Exception e) {

			System.out.println("Error en findDateDaily ");
			return date;
		}

	}

	@Transactional
	public void saveDaily(List<Daily> listDailty) throws  SQLException{

		Connection con = null;

		try{

			con = dbResources.getConection();
			con.setAutoCommit(false);

			String sql = "INSERT INTO daily (fecha,estrategia_id) values ('"+listDailty.get(0).getFecha()+"',"+listDailty.get(0).getEstrategiaId()+")";

			PreparedStatement  stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int lastIndex = rs.getInt(1);

			stmt = con.prepareStatement("INSERT INTO daily_tarea (daily_id,tarea_id,estadoActual,subEstadoActual,propiedad) values (?,?,?,?,?)");

			for (Daily daily : listDailty) {

				stmt.setInt(1, lastIndex);
				stmt.setInt(2, daily.getTareaId());
				stmt.setString(3, daily.getEstadoActual());
				stmt.setString(4, daily.getSubEstadoActual());
				stmt.setString(5, daily.getPropiedad());

				try {
					
					stmt.executeUpdate();

				}catch  (Exception e) {

					System.out.println("Esta intermedia-daily ID ya existe");
				}

				System.out.println("intermedia guardada ");
			}

			con.commit();

		}catch (SQLException e) {

			System.out.println("SQL Exeption  daily_tarea:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());
			con.rollback();

		}
	}
}

