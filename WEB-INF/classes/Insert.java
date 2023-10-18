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

@WebServlet("/Insert")

public class Insert extends HttpServlet {

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

        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String publisher = request.getParameter("publisher");
        String edition = request.getParameter("edition");
        String price = request.getParameter("price");


        Connection conn = null;

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL+DB, USER, PASSWORD);
        

            String sql = "INSERT INTO books (Title, Author, Publisher, Edition, Price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, title);
                pstmt.setString(2, author);
                pstmt.setString(3, publisher);
                pstmt.setString(4, edition);
                pstmt.setString(5, price);

    
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) 
            {
                response.sendRedirect("insert.html#successMessage");

            } else {
                // out.print("Error inserting record");
                response.sendRedirect("insert.html");
            }
    
        } 

        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        finally {
            try {
                // if (rs != null) rs.close();
                // if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
