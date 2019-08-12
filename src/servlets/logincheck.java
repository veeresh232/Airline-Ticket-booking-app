package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Dao.CreateConnnection;

//import oracle.jdbc.OracleTypes;
/**
 * Servlet implementation class logincheck
 */
@WebServlet("/logincheck")
public class logincheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final String url = "jdbc:oracle:thin:@localhost:1521:orcl";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public logincheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PreparedStatement pst;
		PreparedStatement pst1;
		CallableStatement cstm;
	   
		
			
			Connection con=CreateConnnection.createcon();
			String userId=request.getParameter("id");
			String password=request.getParameter("password");
			
			try {
				
				
				pst=con.prepareStatement("select password from users where userid=?");
				pst.setString(1, userId);
				
				
				ResultSet rs=(ResultSet)pst.executeQuery();
				boolean isValidUser=false;
				while(rs.next()){
					if(password.equals(rs.getString(1)))
					{
						
							HttpSession hp=request.getSession();
							hp.setAttribute("userId",userId);
							hp.setMaxInactiveInterval(0);
							response.sendRedirect("home.jsp");
						
							isValidUser=true;
							break;
						
					}
				}
				
				
				if(!isValidUser){
					HttpSession hp=request.getSession();
					
					hp.setAttribute("error","Invalid UserID/Password");
					hp.setMaxInactiveInterval(1);
					//pw.println("Invalid UserID/Password");
					response.sendRedirect("login.jsp");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
