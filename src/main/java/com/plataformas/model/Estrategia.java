package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="estrategia")
public class Estrategia {

	@Id
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

	public static  List<Estrategia> converFromDatabase(ResultSet rs,List<Estrategia> estrategiaList ) throws SQLException {

		while (rs.next()) {

			int id = rs.getInt("id");
			String nombre = rs.getString("nombre");
			String estado = rs.getString("estado");
			String fechaInicio = rs.getString("fechaInicio");
			String fechafin = rs.getString("fechafin");
			int equipo_id = rs.getInt("equipo_id");
			Estrategia estrategia = new Estrategia( id,nombre, estado,fechaInicio ,fechafin ,equipo_id);
			estrategiaList.add(estrategia);
		}
		return estrategiaList;
	}	
}
