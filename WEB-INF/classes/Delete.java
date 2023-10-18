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

@WebServlet("/Delete")

public class Delete extends HttpServlet {

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
            
            Integer recordIdToDelete = Integer.parseInt(request.getParameter("deleteVal"));
    
            Connection conn = null;

            try {
                    Class.forName(DRIVER);
                    conn = DriverManager.getConnection(URL+DB, USER, PASSWORD);
                
                    String sql = "DELETE FROM books WHERE AccNo = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, recordIdToDelete);

                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) 
                    {
                        out.print("Record has been deleted successfully");
                        
                    } else {
                        out.print("Enter valid ID");

                    }
            
            }
            catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                out.print(e);
            }
    
            finally {
                try {
                    // if (rs != null) rs.close();
                    // if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
    
        
    }
}
