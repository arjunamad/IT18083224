package hc.suport;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DbContext {
	private static Connection mConnection = null;

	private DbContext() {

	}

	public static Connection getConnection() throws Exception {
		if (mConnection == null) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			mConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/helthcare?useTimezone=true&serverTimezone=UTC", "root", "");
			return mConnection;
		}

		return mConnection;
	}
	
	
}
