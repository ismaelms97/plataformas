package com.plataformas.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="user")
public class User {

	private int Id;
	private String username;
	private String password;
	private List<Integer> equipoId;
	private List<String> NombreEquipo;
	private List<String> role;

	public User() {
		super();
	}
		
	public User(List<String> nombreEquipo, List<String> role) {
		super();
		NombreEquipo = nombreEquipo;
		this.role = role;
	}

	public User(int id, String username, String password ) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
	}

	public User(int id, String username, String password ,List<Integer> equipoId,List<String> NombreEquipo) {
		super();
		Id = id;
		this.username = username;
		this.password = password;
		this.equipoId = equipoId;
		this.NombreEquipo = NombreEquipo;
	}

	public List<Integer> getEquipoId() {
		return equipoId;
	}

	public void setEquipoId(List<Integer> equipoId) {
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

	public List<String> getNombreEquipo() {
		return NombreEquipo;
	}

	public void setNombreEquipo(List<String> nombreEquipo) {
		NombreEquipo = nombreEquipo;
	}

	public List<String> getRole() {
		return role;
	}



	public void setRole(List<String> role) {
		this.role = role;
	}



	public static User converFromDataBase(ResultSet rs) throws SQLException {

		User user = null;

		if(rs.next()) {

			int id = rs.getInt("id");
			String username = rs.getString("username");
			String password = rs.getString("password");
			user  = new User(id,username,password);			
		}

		return user;
	}

	@SuppressWarnings("restriction")
	public static String encrypt(String pass) {

		try {

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(pass.getBytes());
			return new sun.misc.BASE64Encoder().encode(md.digest());

		} catch (NoSuchAlgorithmException e) {

			System.err.println("User -> encrypt() Failed");

		}

		return "";

	}
	
	public static HashMap<Integer, String> createTeamsIdNames (User userTeamAndRoles) {
		
		HashMap<Integer, String> equipos = new HashMap<Integer, String>();
		int index = 0;
		
		for (String nombre : userTeamAndRoles.getNombreEquipo()) {
			
			equipos.put(userTeamAndRoles.getEquipoId().get(index), nombre);
			index++;
		} 
		
		return equipos;
	}
}