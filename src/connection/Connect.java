package connection;

import java.util.*;
import java.sql.*;

public class Connect {
	static Properties prop;
	static final String CONFIGNAME = "../database.properties";
	Connection conn;
	String tableName;
	HashMap<String, String> tableStructure = new HashMap<>();

	public Connect() throws Exception {
		tableStructure = new HashMap<String, String>();
		prop = new Properties();
		System.out.println(CONFIGNAME);
		prop.load(this.getClass().getResourceAsStream(CONFIGNAME));
		try {
			Class.forName(prop.getProperty("DRIVER_CLASS"));
			System.out.print("Sucess loading Driver");
		} catch (Exception e) {
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(prop.getProperty("CONNECTION_URL"),
					prop.getProperty("CONNECTION_USERNAME"), prop.getProperty("CONNECTION_PASSWORD"));
			tableName = "sales";
			tableStructure = setTableStructure("sales");
		} catch (SQLException e) {
			System.out.println("Connection error!");
			e.printStackTrace();
		}
	}

	public HashMap<String, String> setTableStructure(String tableName) {
		tableStructure = new HashMap<String, String>();
		try {
			Statement stmt = conn.createStatement();
			String sql = "select columnselect column_name,data_type" + " from information_schema.columns"
					+ " where table_name='" + tableName + "'";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String dataType = "";
				String dataName = rs.getString("column_name");
				if (rs.getString("data_type").equals("character varying")
						|| rs.getString("data_type").equals("character")) {
					dataType = "String";
				} else {
					dataType = "int";
				}
				tableStructure.put(dataName, dataType);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("fail");
		}
		return tableStructure;
	}
	
	public HashMap<String, String> getStructure() {
		return tableStructure;
	}
}
