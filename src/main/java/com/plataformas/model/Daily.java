package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="daily")
public class Daily {
	private int Id;
	private String fecha;
	private int estrategiaId;
	private String estadoActual;
	private String subEstadoActual;
	
	
	
	public Daily() {
		super();
	}
	
	

	

	public Daily(int id, String fecha, int estrategiaId, String estadoActual, String subEstadoActual) {
		super();
		Id = id;
		this.fecha = fecha;
		this.estrategiaId = estrategiaId;
		this.estadoActual = estadoActual;
		this.subEstadoActual = subEstadoActual;
	}





	public Daily(String fecha, int estrategiaId, String estadoActual, String subEstadoActual) {
		super();
		this.fecha = fecha;
		this.estrategiaId = estrategiaId;
		this.estadoActual = estadoActual;
		this.subEstadoActual = subEstadoActual;
	}





	public Daily(String fecha, String estadoActual, String subEstadoActual) {
		super();
		this.fecha = fecha;
		this.estadoActual = estadoActual;
		this.subEstadoActual = subEstadoActual;
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

	public int getEstrategiaId() {
		return estrategiaId;
	}

	public void setEstrategiaId(int dailyId) {
		this.estrategiaId = dailyId;
	}


	public String getEstadoActual() {
		return estadoActual;
	}





	public void setEstadoActual(String estadoActual) {
		this.estadoActual = estadoActual;
	}





	public String getSubEstadoActual() {
		return subEstadoActual;
	}





	public void setSubEstadoActual(String subEstadoActual) {
		this.subEstadoActual = subEstadoActual;
	}





	public static  List<Daily> converFromDatabase(ResultSet rs,List<Daily> dailyList ) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("id");
			String fecha = rs.getString("fecha");
			int idEstrategia = rs.getInt("estrategia_id");
			String estadoActual = rs.getString("estadoActual");
			String subEstadoActual = rs.getString("subEstadoActual");
			Daily daily = new Daily(id,fecha,idEstrategia,estadoActual,subEstadoActual);
			dailyList.add(daily);
		}
		return dailyList;
	}

	public static  List<Daily> stringToObject(String stratDaily){

		List<Daily> listaDaily =  new ArrayList<Daily>();

		String[][] dailys = new String[stratDaily.split("qwer").length][12];

		for(int i = 0; i < dailys.length; i++) {
			String[] dailystr = stratDaily.split("qwer");
			dailys[i] = dailystr[i].split("--");
			
			String fecha = (dailys[i][0].split(":"))[1];			
			String estadoActual = (dailys[i][2].split(":"))[1];
			String subEstadoActual = (dailys[i][3].split(":"))[1];
			
			
			Daily daily = new Daily (fecha,estadoActual,subEstadoActual);
			
			listaDaily.add(daily);
			
			
		}
		return listaDaily;

	
	}
}
