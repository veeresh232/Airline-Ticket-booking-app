package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.FlightsBean;
import Dao.Functionality;

/**
 * Servlet implementation class searchServlet
 */
@WebServlet("/searchServlet")
public class searchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public searchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String from=request.getParameter("from").toLowerCase();
		String to=request.getParameter("to").toLowerCase();
		String date=request.getParameter("date");
		String[] format=date.split("-");
		String[] month= {"Jan","Feb","March","April","May","June","July","Aug","Sep","Oct","Nov","Dec"};
		
		date=format[2]+"-"+month[Integer.parseInt(format[1])-1]+"-"+format[0];
		String clas=request.getParameter("class");
		Functionality functionality=new Functionality();
		ArrayList<FlightsBean> flightData=new ArrayList<FlightsBean>();
		flightData=functionality.getFlightResults(from, to, clas);
		HttpSession session=request.getSession();
		session.setMaxInactiveInterval(0);
		session.setAttribute("flightData", flightData);
		session.setAttribute("date", date);
		response.sendRedirect("searchResult.jsp");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
