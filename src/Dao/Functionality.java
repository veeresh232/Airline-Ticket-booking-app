package Dao;

import java.awt.List;
import java.sql.CallableStatement;
//import com.quinnox.mom.Beans.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//import com.quinnox.mom.Beans.emp;
import com.sun.javafx.fxml.BeanAdapter;

import Beans.BookingBean;
import Beans.FlightsBean;
import Beans.TicketBean;
import jdk.nashorn.internal.ir.RuntimeNode.Request;
import oracle.jdbc.OracleTypes;
import servlets.bookingServlet;

public class Functionality {
	
	//Register new user
		public int CreateUser(String name,String userid,String password) {
			Connection connection=CreateConnnection.createcon();
			PreparedStatement pst;
			
			try {
				pst=connection.prepareStatement("insert into users values(?,?,?)");
				pst.setString(1, userid);
				pst.setString(2, name);
				pst.setString(3, password);
				int result=pst.executeUpdate();
				
				
				return result;
				
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
				
			}
		}
	
	
	
	//This method returns Name of the current userId
	public static String getUserName(String userId){
		Connection connection=CreateConnnection.createcon();
		PreparedStatement pst=null;
		try {
			pst=connection.prepareStatement("select name from users where userid=?");
			pst.setString(1,userId);
			ResultSet rs=pst.executeQuery();
			String userName="";
			while(rs.next())
				userName=rs.getString(1);
			return userName;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	
	
	//this method returns the search results of the flights
	public ArrayList<FlightsBean> getFlightResults(String from,String to,String clas){
		ArrayList<FlightsBean> flightData=new ArrayList<FlightsBean>();
		Connection con=CreateConnnection.createcon();
		PreparedStatement pst;
		try {
			
			pst=con.prepareStatement("select * from flights where source=? and dest=? and class=?");
			pst.setString(1,from);
			pst.setString(2, to);
			pst.setString(3, clas);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
				FlightsBean fb=new FlightsBean();
				fb.setFid(rs.getString(1));
				fb.setName(rs.getString(2));
				fb.setSource(rs.getString(3));
				fb.setDest(rs.getString(4));
				fb.setPrice(rs.getInt(5));
				fb.setDepart(rs.getString(6));
				fb.setClas(rs.getString(7));
				fb.setDuration(rs.getString(8));
				flightData.add(fb);
			}
			
			return flightData;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	//this method confirms the booking by inserting into database
	public int insertbooking(String fid,String date,String userid,String name,int nofseats,String seats) {
		Connection connection=CreateConnnection.createcon();
		PreparedStatement pst;
		PreparedStatement pst2;
		PreparedStatement pst3;
		int price=0;
		int tripId=0;
		
try {
			
			pst=connection.prepareStatement("select price from flights where fid=?");
			pst.setString(1,fid);
			ResultSet rs=pst.executeQuery();
			while(rs.next()){
			 price=rs.getInt(1);
			}
			price=nofseats*price;
			pst2=connection.prepareStatement("insert into booking values(TRIPID.nextval,?,?,?,?,?,?,?)");
			pst2.setString(1, fid);
			pst2.setString(2,date);
			pst2.setString(3,userid);
			pst2.setString(4, name);
			pst2.setInt(5,nofseats);
			pst2.setString(6,seats);
			pst2.setInt(7,price);
			pst2.executeUpdate();
			pst3=connection.prepareStatement("select tripid.currval from dual");
			ResultSet rs2=pst3.executeQuery();
			while(rs2.next()) {
				tripId=rs2.getInt(1);
				System.out.println(tripId);	
			}
			
			return tripId;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	//returns ticket object to print the details
	public TicketBean getTicketDetails(int tripId) {
		Connection connection=CreateConnnection.createcon();
		TicketBean bean=new TicketBean();
		PreparedStatement pst;
		try {
			pst=connection.prepareStatement("select b.tripid,b.fid,b.ddate,b.name,b.seats,b.price,f.name,f.source,f.dest,f.class,f.depart from flights f,booking b where b.fid=f.fid and b.tripid=?");
			pst.setInt(1,tripId);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
			bean.setTripId(rs.getInt(1));
			bean.setFlightId(rs.getString(2));
			bean.setDate(rs.getString(3));
			bean.setPassengerName(rs.getString(4));
		    bean.setSeats(rs.getString(5));
		    bean.setPrice(rs.getInt(6));
		    bean.setFlightName(rs.getString(7));
		    bean.setFrom(rs.getString(8));
		    bean.setTo(rs.getString(9));
		    bean.setKlass(rs.getString(10));
		    bean.setDepart(rs.getString(11));
			}
			return bean;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	//this returns a list of all bookings of current user
	public ArrayList<BookingBean> getBookingList(String userId){
		Connection connection=CreateConnnection.createcon();
		ArrayList<BookingBean> list=new ArrayList<BookingBean>();
		PreparedStatement pst;
		try {
			pst=connection.prepareStatement("select b.tripid,f.name,f.source,f.dest,b.ddate,f.depart from booking b,flights f where b.fid=f.fid and b.userid=?");
			pst.setString(1, userId);
			ResultSet rs=pst.executeQuery();
			
			while(rs.next()) {
				BookingBean bean=new BookingBean();
			bean.setTripId(rs.getInt(1));
			bean.setFlightName(rs.getString(2));
			bean.setFrom(rs.getString(3));
			bean.setTo(rs.getString(4));
			bean.setDate(rs.getString(5));
			bean.setDeparture(rs.getString(6));
			list.add(bean);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	//this method cancels the ticket i.e deletes the entry from the booking table
	public void cancelTicket(int tripId) {
		Connection connection=CreateConnnection.createcon();
		ArrayList<BookingBean> list=new ArrayList<BookingBean>();
		PreparedStatement pst;
		try {
			pst=connection.prepareStatement("delete from booking where tripid=?");
			pst.setInt(1,tripId);
			pst.executeUpdate();
			
			return;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
	
	
	
	


}