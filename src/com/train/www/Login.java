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

public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter pw = res.getWriter();
        res.setContentType("text/html");

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        final String QUERY = "SELECT * FROM register WHERE email = ? AND password = ?";

        try (Connection connection = DBsrv.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(QUERY)) {

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                pw.println("<h1><center>Login Successful!</center></h1>");
                pw.println("<center><a href='form.html'>go to booking page</a></center>");
            } else {
                pw.println("<h1><center>Invalid Email or Password</center></h1>");
                pw.println("<center><a href='login.html'>Try Again</a></center>");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
