package com.train.www;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterSrv extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res)throws ServletException,IOException
	{
		PrintWriter pw=res.getWriter();
		res.setContentType("text/html");
		String name=req.getParameter("name");
		String email=req.getParameter("email");
		String password=req.getParameter("password");
		

        PreparedStatement preparedStatement = null;
       
		  
        final  String QUERY1 = "INSERT INTO register (full_name, email, password) VALUES ( ?, ? ,?)";
		//insert data into data base
		try
		{
			   Connection connection=DBsrv.getConnection();
	            preparedStatement = connection.prepareStatement(QUERY1);

	            
	          
			
	            preparedStatement.setString(1,name);
	            preparedStatement.setString(2,email);
	            preparedStatement.setString(3,password);
			
			int result=preparedStatement.executeUpdate();
			if(result==0) {
				pw.println("<h1><center>register fill the details correctly sucessfully</center></h1> ");
				}
			else
			{
				pw.println("<h1><center>register sucessfully</h1> </center>");
				pw.println("<center><a href='./login.html'>Click here to login</a></center>");

				
			}
			
			preparedStatement.close();
		
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		pw.close();
		
		
	}
}
