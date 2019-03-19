package com.plataformas.Db2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.stereotype.Service;
@Service
public class Db2Service {
	
	public ResultSet findAll() throws ClassNotFoundException, SQLException {
		
		String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50001/BLUDB:user=rvg03272;password=0@vn6gg9jg7zqjb1;sslConnection=true;";
		Class.forName("com.ibm.db2.jcc.DB2Driver");     
		Connection con = DriverManager.getConnection(url);
		con.setAutoCommit(false);
		Statement  stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT * FROM USER"); 
		return rs;
	}
	
	public String findByPassword(String password) throws ClassNotFoundException, SQLException {
		
		String url = "jdbc:db2://dashdb-txn-sbox-yp-lon02-01.services.eu-gb.bluemix.net:50001/BLUDB:user=rvg03272;password=0@vn6gg9jg7zqjb1;sslConnection=true;";
		Class.forName("com.ibm.db2.jcc.DB2Driver");     
		Connection con = DriverManager.getConnection(url);
		con.setAutoCommit(false);
		Statement  stmt = con.createStatement(); 
		ResultSet rs = stmt.executeQuery("SELECT username FROM USER where password = "+password); 
		
		if(rs.next()) {
			return rs.getString("username");
		}else {
			return null;
		}
		
		
	}

}
