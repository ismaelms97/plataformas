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
	private String prioridad ;
	private String resumen;
	private String tamaño;
	private String complejidad;
	private String propiedad;
	private String peticionario;
	private boolean relevante;
	private boolean urgente;
	private String planificado ;
	private String estadoInicio;
	private String estadoFinal;



	public Tarea() {
		super();
	}


	public Tarea(int id, String tipo, String estadoInicio, String estadoFinal, String prioridad, String resumen, String tamaño, String complejidad,
			String propiedad, String peticionario, boolean relevante, boolean urgente, String planificado) {
		super();
		Id = id;
		this.tipo = tipo;
		this.prioridad = prioridad;
		this.resumen = resumen;
		this.tamaño = tamaño;
		this.complejidad = complejidad;
		this.propiedad = propiedad;
		this.peticionario = peticionario;
		this.relevante = relevante;
		this.urgente = urgente;
		this.planificado = planificado;
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

	public String getPrioridad() {
		return prioridad;
	}


	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}


	public String getResumen() {
		return resumen;
	}


	public void setResumen(String resumen) {
		this.resumen = resumen;
	}


	public String getTamaño() {
		return tamaño;
	}


	public void setTamaño(String tamaño) {
		this.tamaño = tamaño;
	}


	public String getComplejidad() {
		return complejidad;
	}


	public void setComplejidad(String complejidad) {
		this.complejidad = complejidad;
	}


	public String getPropiedad() {
		return propiedad;
	}


	public void setPropiedad(String propiedad) {
		this.propiedad = propiedad;
	}


	public String getPeticionario() {
		return peticionario;
	}


	public void setPeticionario(String peticionario) {
		this.peticionario = peticionario;
	}


	public boolean isRelevante() {
		return relevante;
	}


	public void setRelevante(boolean relevante) {
		this.relevante = relevante;
	}


	public boolean isUrgente() {
		return urgente;
	}


	public void setUrgente(boolean urgente) {
		this.urgente = urgente;
	}


	public String getPlanificado() {
		return planificado;
	}


	public void setPlanificado(String planificado) {
		this.planificado = planificado;
	}


	public static  List<Tarea> converFromDatabase(ResultSet rs,List<Tarea> tareaList ) throws SQLException {
		
		while (rs.next()) {
			
			int id = rs.getInt("id");
			String tipo = rs.getString("tipo");
			String estadoInicio = rs.getString("estadoInicio");
			String estadoFinal = rs.getString("estadoFinal");
			String prioridad = rs.getString("prioridad");
			String resumen = rs.getString("resumen");
			String tamaño = rs.getString("tamaño");
			String complejidad = rs.getString("complejidad");
			String propiedad = rs.getString("propiedad");
			String peticionario = rs.getString("peticionario");
			boolean relevante = rs.getBoolean("relevante");
			boolean urgente = rs.getBoolean("urgente");
			String planificado = rs.getString("planificado");
			Tarea tarea = new Tarea(id,tipo,estadoInicio,estadoFinal,prioridad, resumen,tamaño, complejidad,propiedad, peticionario,relevante,urgente, planificado);
			tareaList.add(tarea);
		}
		
		return tareaList;
	}
	
	public static  List<Tarea> stringToObject(String stratTasks ){

		List<Tarea> listaTareas =  new ArrayList<Tarea>();

		String[][] tasks = new String[stratTasks.split("qwer").length][12];

		for(int i = 0; i < tasks.length; i++) {
			
			String[] task = stratTasks.split("qwer");
			tasks[i] = task[i].split("--");
			int id = Integer.parseInt((tasks[i][0].split(":"))[1]);
			String tipo = (tasks[i][1].split(":"))[1];
			
			String estadoI = (tasks[i][2].split(":"))[1];
			String estadoF = (tasks[i][3].split(":"))[1];
			String prioridad = (tasks[i][4].split(":"))[1];
			String resumen = (tasks[i][5].split(":"))[1];
			
			String tamaño = (tasks[i][6].split(":"))[1];
			String complejidad = (tasks[i][7].split(":"))[1];
			String propiedad = (tasks[i][8].split(":"))[1];
			String peticionario = (tasks[i][9].split(":"))[1];
			boolean relevante = Boolean.parseBoolean((tasks[i][10].split(":"))[1]);
			boolean urgente = Boolean.parseBoolean((tasks[i][11].split(":"))[1]);
			String planificado = (tasks[i][12].split(":"))[1];
			Tarea tarea = new Tarea (id,tipo,estadoI,estadoF,prioridad, resumen,tamaño, complejidad,propiedad, peticionario,relevante,urgente, planificado);			
			listaTareas.add(tarea);
			
			
		}
		
		return listaTareas;
	}

}
