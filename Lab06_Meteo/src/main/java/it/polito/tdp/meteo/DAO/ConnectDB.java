package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import com.zaxxer.hikari.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	// check user e password
	static private final String jdbcUrl = "jdbc:mysql://localhost/meteo";
	
	private static HikariDataSource ds;
	
	public static Connection getConnection() {
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(jdbcUrl);
		config.setUsername("root");
		config.setPassword("gigika123");
		config.setHealthCheckProperties(null);
		ds = new HikariDataSource(config);
		
		try {
			
				Connection connection = ds.getConnection();
				
				return connection;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException("Cannot get a connection " + jdbcUrl, e);
		}
	}

}
