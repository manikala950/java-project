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

public class LoginSrv extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Capture user inputs from the form
        String source = req.getParameter("st1");
        String destination = req.getParameter("dt1");

        if (source == null || source.isEmpty() || destination == null || destination.isEmpty()) {
            out.println("<h3>Please enter both source and destination.</h3>");
            return;
        }

        
        final String QUERY1 = "SELECT * FROM train_details WHERE train_source = ? AND train_destination = ?";
        
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet1 = null;

        try {
            // Load JDBC Driver and connect to the database
            Connection connection=DBsrv.getConnection();
            preparedStatement = connection.prepareStatement(QUERY1);

            preparedStatement.setString(1, source);
            preparedStatement.setString(2, destination);
            resultSet1 = preparedStatement.executeQuery();

            // Generate HTML response to display train details
            out.println("<html><body>");
            out.println("<h2>Available Trains:</h2>");
            out.println("<table border='1' cellpadding='10'><tr><th>Train No</th><th>Train Name</th><th>Source</th><th>Destination</th><th>Departure Time</th><th>Arrival Time</th><th>Seats Available</th><th>Select Seat</th></tr>");

            boolean trainsFound = false;

            while (resultSet1.next()) {
                trainsFound = true;
                int trainNo = resultSet1.getInt("train_no");
                String trainName = resultSet1.getString("train_name");
                
                

                out.println("<tr>");
                out.println("<td>" + trainNo + "</td>");
                out.println("<td>" + trainName + "</td>");
                out.println("<td>" + resultSet1.getString("train_source") + "</td>");
                out.println("<td>" + resultSet1.getString("train_destination") + "</td>");
                out.println("<td>" + resultSet1.getTime("train_departuretime") + "</td>");
                out.println("<td>" + resultSet1.getTime("train_destinationtime") + "</td>");
                out.println("<td>" + resultSet1.getInt("seat_available") + "</td>");
                out.println("<td>");
                out.println("<form action='test4' method='POST'>");
                out.println("<input type='hidden' name='trainNo' value='" + trainNo + "'>");
                out.println("<input type='hidden' name='trainName' value='" + trainName + "'>");
                out.println("<input type='hidden' name='source' value='" + source + "'>");
                out.println("<input type='hidden' name='destination' value='" + destination + "'>");
                out.println("<input type='submit' value='Select Seat' style='background-color: #007bff; color: white; border: none; padding: 10px 20px; font-size: 16px; cursor: pointer; border-radius: 5px;'>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }

            if (!trainsFound) {
                out.println("<tr><td colspan='8'>No trains available for the selected route.</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
            
           
    		
            
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h3>Error retrieving train details. Please try again later.</h3>");
        }
    }

    
    }

