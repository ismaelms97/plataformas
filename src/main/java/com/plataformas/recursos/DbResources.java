package com.plataformas.recursos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

@Component
public class DbResources {

	private String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50000/BLUDB";
	private String dbUsername = "rvg03272";
	private String dbPassword = "0@vn6gg9jg7zqjb1";

	public void initializeDriver() throws ClassNotFoundException {

		try {

			Class.forName("com.ibm.db2.jcc.DB2Driver"); 

		}catch (ClassNotFoundException e) {

			System.out.println("Class driver not found");

		}catch (Exception e) {

			System.out.println("Unknow error with driver");
		}

	}

	public  Connection getConection() {

		Connection con = null;

		try {

			initializeDriver();
			con = DriverManager.getConnection(url,dbUsername,dbPassword);

		}catch (ClassNotFoundException e) {

			System.out.println("Class driver not found");

		}catch (SQLException e) {

			System.out.println("Connection with DB failed -> Message: "+e.getMessage());

		}

		return con;
	}	
}
