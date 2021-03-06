package com.plataformas.recursos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

			System.err.println("Class driver not found");

		}catch (Exception e) {

			System.err.println("Unknow error with driver");
		}

	}

	public  Connection getConection() {

		Connection con = null;

		try {

			initializeDriver();
			con = DriverManager.getConnection(url,dbUsername,dbPassword);

		}catch (ClassNotFoundException e) {

			System.err.println("Class driver not found");

		}catch (SQLException e) {

			System.err.println("Connection with DB failed -> Message: "+e.getMessage());

		}

		return con;
	}	

	public String currentDateForDaily() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();

		return dtf.format(localDate);
	}
}