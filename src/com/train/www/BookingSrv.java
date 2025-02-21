package com.train.www;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BookingSrv extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Parse trainNo, seatNo, and seatCost from the request
            int trainNo = Integer.parseInt(req.getParameter("trainNo"));
            int seatNo = Integer.parseInt(req.getParameter("seatNo"));
            String source=req.getParameter("source");
            String destination=req.getParameter("destination");
            String booked="Booked";
            double seatCost = Double.parseDouble(req.getParameter("seatCost"));

            // JDBC connection details
           
            
            // SQL queries to update seat status and insert booking details
            final String UPDATE_SEAT_STATUS = "UPDATE seat_details SET is_booked = 1 WHERE train_no = ? AND seat_no = ?";
            final String INSERT_BOOKING = "INSERT INTO booking_details (train_no, seat_no, seat_cost,source,destination) VALUES ( ?,?, ?, ? ,?)";

            // Load JDBC Driver and connect to database
            Connection connection=DBsrv.getConnection();
            

            // Start a transaction
            connection.setAutoCommit(false);

            // Update the seat status to 'booked' in the seat_details table
            PreparedStatement updateSeatStmt = connection.prepareStatement(UPDATE_SEAT_STATUS);
            updateSeatStmt.setInt(1, trainNo);
            updateSeatStmt.setInt(2, seatNo);
            updateSeatStmt.executeUpdate();

            // Insert booking details into booking_details table
            PreparedStatement insertBookingStmt = connection.prepareStatement(INSERT_BOOKING);
            insertBookingStmt.setInt(1, trainNo);
            insertBookingStmt.setInt(2, seatNo);
          insertBookingStmt.setDouble(3, seatCost);
          insertBookingStmt.setString(4, source);
          insertBookingStmt.setString(5, destination);
           // insertBookingStmt.setString(3, booked);
            insertBookingStmt.executeUpdate();

            // Commit the transaction
            connection.commit();

            // Close the connections
            updateSeatStmt.close();
            insertBookingStmt.close();
            connection.close();

            // Provide feedback to the user
            out.println("<html><body>");
            out.println("<h3>Seat booked successfully!</h3>");
            out.println("<p>Train No: " + trainNo + ", Seat No: " + seatNo + ", Cost: " + seatCost + "</p>");
            out.println("<a href='home.html'>Go to Home</a>");
            out.println("</body></html>");

        } catch (Exception e) {
            // Handle errors and rollback in case of failure
            out.println("<h3>Booking failed. Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();}
        }
}