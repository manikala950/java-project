package com.train.www;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class SeatSrv extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        try {
            // Parse train number from request
            int trainNo = Integer.parseInt(req.getParameter("trainNo"));
            String source=req.getParameter("source");
            String destination=req.getParameter("destination");
            // JDBC connection details
            
            final String QUERY2 = "SELECT * FROM seat_details WHERE train_no = ?";

            PreparedStatement preparedStatement = null;
            ResultSet resultSet2 = null;
            // Load JDBC Driver and connect to database
            Connection connection=DBsrv.getConnection();
            preparedStatement = connection.prepareStatement(QUERY2);
            preparedStatement.setInt(1, trainNo);

            resultSet2 = preparedStatement.executeQuery();

            // Generate HTML response
            out.println("<html><body>");
            out.println("<h2>Seat Selection:</h2>");
            out.println("<table border='1' cellpadding='10'><tr><th>Seat No</th><th>Seat Type</th><th>Booked Status</th><th>Price</th><th>Action</th></tr>");

            boolean seatFound = false;

            // Iterate through the result set and display seat details
            while (resultSet2.next()) {
                seatFound = true;
                int seatNo = resultSet2.getInt("seat_no");
                String seatType = resultSet2.getString("seat_type");
                int isBooked = resultSet2.getInt("is_booked");
                double seatCost = resultSet2.getDouble("seat_cost");

                out.println("<tr>");
                out.println("<td>" + seatNo + "</td>");
                out.println("<td>" + seatType + "</td>");
                out.println("<td>" + (isBooked == 1 ? "Booked" : "Available") + "</td>");
                out.println("<td>" + seatCost + "</td>");

                // Add hyperlink for available seats
                if (isBooked == 0) {
                	out.println("<td>");
                	out.println("<form action='test5' method='POST'>");
                	out.println("<input type='hidden' name='seatNo' value='" + seatNo + "'>");
                	out.println("<input type='hidden' name='trainNo' value='" + trainNo + "'>");
                	out.println("<input type='hidden' name='seatCost' value='" + seatCost + "'>");
                	out.println("<input type='hidden' name='source' value='" + source + "'>");
                	out.println("<input type='hidden' name='destination' value='" + destination + "'>");
                	out.println("<input type='submit' value='Book Now' style='background-color: #28a745; color: white; border: none; padding: 10px 20px; font-size: 16px; cursor: pointer; border-radius: 5px;'>");
                	out.println("</form>");
                	out.println("</td>");

                } else {
                    out.println("<td>N/A</td>");
                }
                out.println("</tr>");
            }

            if (!seatFound) {
                out.println("<tr><td colspan='5'>No seats available for the selected train.</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
            
        } catch (Exception e) {
            out.println("<h3>Seat selection failed. Error: " + e.getMessage() + "</h3>");
            e.printStackTrace();
            }
        }
}