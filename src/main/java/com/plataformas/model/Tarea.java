package com.plataformas.model;

public class Tarea {
	private int Id;
	private String rtc;
	private String tipo;
	private String estadoInicio;
	private String estadoFinal;
	
	
	
	
	
	
	public Tarea() {
		super();
	}
	public Tarea(String rtc, String tipo, String estadoInicio, String estadoFinal) {
		super();
		this.rtc = rtc;
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
	public String getRtc() {
		return rtc;
	}
	public void setRtc(String rtc) {
		this.rtc = rtc;
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
	
	
}
