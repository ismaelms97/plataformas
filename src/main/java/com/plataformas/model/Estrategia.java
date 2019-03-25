package com.plataformas.model;

public class Estrategia {
	private int Id;
	private String nombre;
	private String estado;
	private String fechaInicio;
	private String fechaFin;
	private int equipoId;

	public Estrategia() {
		super();
	}
	
	

	public Estrategia(int id,String nombre, String estado, String fechaInicio, String fechaFin, int equipoId) {
		super();
		Id = id;
		this.nombre = nombre;
		this.estado = estado;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.equipoId = equipoId;
	}



	public Estrategia(String nombre,String estado, String fechaInicio, String fechaFin, int equipoId) {
		super();
		this.nombre = nombre;
		this.estado = estado;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.equipoId = equipoId;
	}

	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public int getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(int equipoId) {
		this.equipoId = equipoId;
	}
	
	
	
}
