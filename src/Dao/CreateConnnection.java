package Dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;


import java.sql.*;

public class CreateConnnection {
	private static final long serialVersionUID = 1L;
	static final String db_url = "jdbc:oracle:thin:@localhost:1521:orcl";
	static final String userName = "hr";
	static final String password = "hr";
	static Connection connection;

	public static Connection createcon() {

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(db_url, userName, password);
		} catch (ClassNotFoundException | SQLException exception) {
			// TODO Auto-generated catch block
			exception.printStackTrace();
		}
		
		return connection;
	}
}

