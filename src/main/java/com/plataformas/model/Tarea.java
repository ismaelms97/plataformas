package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Tarea {
	private int Id;
	private String tipo;
	private String estadoInicio;
	private String estadoFinal;
	
	
	
	

	public Tarea() {
		super();
	}
	
	public Tarea(int id, String tipo, String estadoInicio, String estadoFinal) {
		super();
		Id = id;
		this.tipo = tipo;
		this.estadoInicio = estadoInicio;
		this.estadoFinal = estadoFinal;
	}
	public Tarea(String rtc, String tipo, String estadoInicio, String estadoFinal) {
		super();
		this.tipo = tipo;
		this.estadoInicio = estadoInicio;
		this.estadoFinal = estadoFinal;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstadoInicio() {
		return estadoInicio;
	}
	public void setEstadoInicio(String estadoInicio) {
		this.estadoInicio = estadoInicio;
	}
	public String getEstadoFinal() {
		return estadoFinal;
	}
	public void setEstadoFinal(String estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	public static  List<Tarea> converFromDatabase(ResultSet rs,List<Tarea> tareaList ) throws SQLException {
		while (rs.next()) {
			int id = rs.getInt("id");
			String tipo = rs.getString("tipo");
			String estadoInicio = rs.getString("estadoInicio");
			String estadoFinal = rs.getString("estadoFinal");
			Tarea tarea = new Tarea(id,tipo,estadoInicio,estadoFinal);
			tareaList.add(tarea);
		}
		return tareaList;
	}
	
}
