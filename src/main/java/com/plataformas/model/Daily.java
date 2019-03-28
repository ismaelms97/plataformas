package com.plataformas.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="daily")
public class Daily {
	private int Id;
	private String fecha;
	private int dailyId;
	
	
	
	public Daily() {
		super();
	}
	
	public Daily( String fecha, int dailyId) {
		super();
		this.fecha = fecha;
		this.dailyId = dailyId;
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
		return dailyId;
	}

	public void setDailyId(int dailyId) {
		this.dailyId = dailyId;
	}






	
	
	
}
