package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="daily")
public class Daily {
	private int Id;
	private String fecha;
	private int estrategiaId;
	
	
	
	public Daily() {
		super();
	}
	
	public Daily( String fecha, int dailyId) {
		super();
		this.fecha = fecha;
		this.estrategiaId = dailyId;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getDailyId() {
		return estrategiaId;
	}

	public void setDailyId(int dailyId) {
		this.estrategiaId = dailyId;
	}


	public static  List<Daily> converFromDatabase(ResultSet rs,List<Daily> dailyList ) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("id");
			String fecha = rs.getString("nombre");
			String Id = rs.getString("estado");
			String fechaInicio = rs.getString("fechaInicio");
			String fechafin = rs.getString("fechafin");
			int equipo_id = rs.getInt("equipo_id");
			Daily daily = new Daily();
			dailyList.add(daily);
		}
		return dailyList;
	}



	
	
	
}
