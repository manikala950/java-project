package com.train.www;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBsrv {
	
	
	 static Connection connection=null;
	 
	 private DBsrv() {
	 
	 }

	public static Connection getConnection()
	{
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
        final String URL = "jdbc:mysql://localhost:3306/trainticketbookingsystem";
        final String USER = "root";
        final String PASS = "root";
        if(connection==null) 
        {
        	connection=DriverManager.getConnection(URL,USER,PASS);
        	
        }
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		return connection;
	}	
		
	

}
