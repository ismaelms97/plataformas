package com.plataformas.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="equipo")
public class Equipo {
	
	private int Id;
	private String name;
	
	public Equipo() {
		super();
	}
	
	
	public Equipo(int id, String name) {
		super();
		Id = id;
		this.name = name;
	}

	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public static  List<Equipo> converFromDatabase(ResultSet rs,List<Equipo> teamList ) throws SQLException {
		
		if(rs.next()) {
			
			int id = rs.getInt("id");
			String name = rs.getString("name");
			Equipo equipo  = new Equipo(id,name);
			teamList.add(equipo);			
		}
		
		return teamList;
	}
	

}
