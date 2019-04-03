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
	private int tareaId;
	
	
	
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

	

	public int getTareaId() {
		return tareaId;
	}

	public void setTareaId(int tareaId) {
		this.tareaId = tareaId;
	}

	public static  List<Daily> converFromDatabase(ResultSet rs,List<Daily> dailyList ) throws SQLException {
		
		while (rs.next()) {
			
			String fecha = rs.getString("fecha");
			int tareaId = rs.getInt("tarea_id");
			String estadoActual = rs.getString("estadoActual");
			String subEstadoActual = rs.getString("subEstadoActual");
			Daily daily = new Daily();
			daily.setFecha(fecha);
			daily.setTareaId(tareaId);
			daily.setEstadoActual(estadoActual);
			daily.setSubEstadoActual(subEstadoActual);
			dailyList.add(daily);
		}
		
		return dailyList;
	}

	public static  List<Daily> stringToObject(String stratDaily, String date, int idEstrategia){

		List<Daily> listaDaily =  new ArrayList<Daily>();

		String[][] dailys = new String[stratDaily.split("qwer").length][4];
		
		for(int i = 0; i < dailys.length; i++) {
			
			String[] dailystr = stratDaily.split("qwer");
			dailys[i] = dailystr[i].split("--");	
			String idTarea = (dailys[i][0].split(":"))[1];		
			String estadoActual = (dailys[i][1].split(":"))[1];
			String subEstadoActual = (dailys[i][2].split(":"))[1];	
			Daily daily = new Daily ();
			daily.setEstadoActual(estadoActual);
			daily.setSubEstadoActual(subEstadoActual);
			daily.setTareaId(Integer.parseInt(idTarea));
			daily.setFecha(date);
			daily.setEstrategiaId(idEstrategia);
			listaDaily.add(daily);
		}
		
		return listaDaily;

	}
}
