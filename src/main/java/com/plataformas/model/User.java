package com.plataformas.model;

public class User {
	private int Id;
	private String username;
	private String password;
	private int equipoId;
	
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
	

}
