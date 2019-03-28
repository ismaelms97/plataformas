package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="tarea")
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
	public static  List<Tarea> stringToObject(String stratTasks ){
		
		List<Tarea> listaTareas =  new ArrayList<Tarea>();

		String[][] tasks = new String[stratTasks.split("qwer").length][4];

		for(int i = 0; i < tasks.length; i++) {
			String[] task = stratTasks.split("qwer");
			tasks[i] = task[i].split(",");

			String[] sId = tasks[i][0].split(":");
			int id = Integer.parseInt(sId[1]);
			String tipo = (tasks[i][1].split(":"))[1];
			String estadoI = (tasks[i][2].split(":"))[1];
			String estadoF = (tasks[i][3].split(":"))[1];

			Tarea tarea = new Tarea (id,tipo,estadoI,estadoF);
			listaTareas.add(tarea);
		}
		return listaTareas;
	}

}
