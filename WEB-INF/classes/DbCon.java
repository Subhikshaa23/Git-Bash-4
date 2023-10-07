// package dbconnection;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.annotation.WebServlet;

//jdbc packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.PreparedStatement;

@WebServlet("/DbCon")

public class DbCon extends HttpServlet {

    private static final long serialVersionUID = 1L;


    public void doPost(HttpServletRequest request, HttpServletResponse response) 
        throws IOException, ServletException
    {
        //db connection
        final String URL = "jdbc:mysql://localhost:3306/";
        final String DB = "servlet_conn"; 
        final String DRIVER = "com.mysql.cj.jdbc.Driver";
        final String USER = "root";
        final String PASSWORD = "Subhi#23";
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // out.print("Success");
        // out.print("Success2");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Connection conn = null;
        ResultSet rs = null;
        // out.print("Success6");
        Statement stmt = null;
        try {
            // out.print("Success5");
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL+DB, USER, PASSWORD);
            stmt = conn.createStatement();
            // out.print("S1");

            // Execute a query to check if the provided username and password match
            String sql = "SELECT * FROM login WHERE Username='" + username + "' AND Password='" + password + "'";
            // out.print("S3");
            rs = stmt.executeQuery(sql);
            // out.print("S2");

            // If a matching record is found, print "Hello"
            if (rs.next()) {
                // out.print("<h4>Hello</h4>");
                String sql1 = "SELECT * FROM books";
                PreparedStatement pstmt = conn.prepareStatement(sql1);
                ResultSet rs1 = pstmt.executeQuery();

                out.println("<style>");
                out.println("td, th{padding: 10px 15px;}");
                out.println("table{margin: auto auto;}");
                // out.println("#add{align-items}");
                out.println(".container{ width: 50%; margin: 0 auto;}");
                out.println("</style>");

                out.println("<div class=\"container\">");
                out.println("<h1 style=\"text-align:center\"; class=\"head\">BOOKS</h1>");

                out.println("<form method=\"post\" action=\"insert.html\">");
                out.println("<input type=\"submit\" id=\"add\" value=\"Add Book\">");
                out.println("</form>");
                out.println("</div>");

                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>ID</th>");
                out.println("<th>TITLE</th>");
                out.println("<th>AUTHOR</th>");
                out.println("<th>PUBLISHER</th>");
                out.println("<th>EDITION</th>");
                out.println("<th>PRICE</th>");
                out.println("</tr>");

                    // Display book information in the HTML table
                    while (rs1.next()) {
                        out.println("<tr>");
                        out.println("<td>" + rs1.getInt("AccNo") + "</td>");
                        out.println("<td>" + rs1.getString("Title") + "</td>");
                        out.println("<td>" + rs1.getString("Author") + "</td>");
                        out.println("<td>" + rs1.getString("Publisher") + "</td>");
                        out.println("<td>" + rs1.getString("Edition") + "</td>");
                        out.println("<td>" + rs1.getString("Price") + "</td>");
                        out.println("</tr>");
                    }

                out.print("</table>");
            } else {
                out.print("<h4>Invalid username or password</h4>");
                response.sendRedirect("index.html");
            }
        } 

        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // out.print("Success3");
        }

        finally {
            // Close the resources
            // out.print("Success4");
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
