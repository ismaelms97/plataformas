package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class User {
	private int Id;
	private String username;
	private String password;
	private int equipoId;
	private String NombreEquipo;
	
	public User() {
		super();
	}

	public User(int id, String username, String password ,int equipoId) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
		this.equipoId = equipoId;
	}

	
	public User(int id, String username, String password ,int equipoId,String NombreEquipo) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
		this.equipoId = equipoId;
		this.NombreEquipo = NombreEquipo;
	}


	public int getEquipoId() {
		return equipoId;
	}


	public void setEquipoId(int equipoId) {
		this.equipoId = equipoId;
	}


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getNombreEquipo() {
		return NombreEquipo;
	}


	public void setNombreEquipo(String nombreEquipo) {
		NombreEquipo = nombreEquipo;
	}

	public static User converFromDataBase(ResultSet rs) throws SQLException {
		User user = null;
		if(rs.next()) {
			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			int equipo_id = rs.getInt("equipo_id");
			String nombreEquipo = rs.getString("name");
			user  = new User(id,username,password,equipo_id,nombreEquipo);

			
		}
		return user;
	}

}
