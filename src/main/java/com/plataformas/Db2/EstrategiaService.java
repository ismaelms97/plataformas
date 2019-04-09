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
import com.plataformas.model.Estrategia;
import com.plataformas.model.Tarea;
import com.plataformas.recursos.DbResources;

@Service
public class EstrategiaService {

	@Autowired
	DbResources  dbResources;
	
	public List<Estrategia> findEstrategiaById(List<Integer> listId){
		
		List<Estrategia> estrategiaList = new ArrayList<Estrategia>();			
		
		try {

			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
			String query = "SELECT * FROM estrategia Es where ";
			int i = 1;
			for (Integer id : listId) {
				
				query += "Es.equipo_id = "+id;
				if(i<listId.size()) {
					query += " AND ";
				}
				
				i++;
			
			}
			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
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

	public List<Tarea> findTareasByEstrategia(int idEstrategia){
		
		List<Tarea> tareaList = new ArrayList<Tarea>();		
				
		try {
			
			Connection con = dbResources.getConection();
			con.setAutoCommit(false);
			Statement  stmt = con.createStatement(); 
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

	public List<Estrategia> findEstrategiaByTeam(int idUser){

		List<Estrategia> estrategiaList = new ArrayList<Estrategia>();		
		
		try {

			Connection con = dbResources.getConection();
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

	@Transactional
	public void saveEstrategiaAndTarea(List<Tarea> tareas,Estrategia estrategia) throws SQLException {

		Connection con = null;

		try{

			con = dbResources.getConection();
			con.setAutoCommit(false);

			String sql = "INSERT INTO estrategia (nombre,estado,fechaInicio,fechaFin,equipo_id) values "
					+ "('"+estrategia.getNombre()+"','"+estrategia.getEstado()+"','"+estrategia.getFechaInicio()+"','"+estrategia.getFechaFin()+"',"+estrategia.getEquipoId()+")";

			PreparedStatement  stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS); 
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			int lastIndex = rs.getInt(1);

			stmt = con.prepareStatement("INSERT INTO tarea (id,tipo,resumen,tamaño,complejidad,propiedad,peticionario,relevante,urgente,planificado)"
					+ " values (?,?,?,?,?,?,?,?,?,?)");

			for (Tarea tarea : tareas) {

				stmt.setInt(1, tarea.getId());
				stmt.setString(2, tarea.getTipo());
				stmt.setString(3, tarea.getPrioridad());
				stmt.setString(4, tarea.getResumen());
				stmt.setString(5, tarea.getTamaño());
				stmt.setString(6, tarea.getComplejidad());
				stmt.setString(7, tarea.getPeticionario());
				stmt.setBoolean(8, tarea.isRelevante());
				stmt.setBoolean(9, tarea.isUrgente());
				stmt.setString(10, tarea.getPlanificado());

				try {

					stmt.executeUpdate();

				}catch  (Exception e) {

					System.out.println("Esta tarea ya existe");
				}
			}

			stmt = con.prepareStatement("INSERT INTO estrategia_tarea (estadoInicio,estadoFinal,tarea_id,estrategia_id) values (?,?,?,?)");

			for (Tarea tarea : tareas) {

				stmt.setString(1, tarea.getEstadoInicio());
				stmt.setString(2, tarea.getEstadoFinal());
				stmt.setInt(3, tarea.getId());
				stmt.setInt(4, lastIndex);
				stmt.executeUpdate();
				System.out.println(" intermedia guardada ");
			}
			
			stmt = con.prepareStatement("INSERT INTO daily (estrategia_id) values ("+lastIndex+")");
			stmt.executeUpdate();
			rs = stmt.getGeneratedKeys();
			rs.next();
			lastIndex = rs.getInt(1);			
			
			stmt = con.prepareStatement("INSERT INTO daily_tarea (daily_id,tarea_id,propiedad) values (?,?,?)");

			for (Tarea tarea : tareas) {

				stmt.setInt(1, lastIndex);
				stmt.setInt(2, tarea.getId());
				stmt.setString(3, tarea.getPropiedad());

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

	@Transactional
	public void deleteEstrategia(int IDestrategia){

		try{

			Connection con = dbResources.getConection();
			con.setAutoCommit(false); 
			Statement  stmt  = con.createStatement(); 
			stmt.execute("DELETE FROM estrategia where id ="+IDestrategia);
			con.commit();

		}catch (SQLException e) {

			System.out.println("SQL Exeption  deleteEstrategia:  code -> "+e.getErrorCode()+" more inf : "+e.getMessage());

		}catch (Exception e) {

			System.out.println("Error en savaTarea ");
		}
	}
}



