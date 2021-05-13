package com.demo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
  private String jdbcURL = "jdbc:postgresql://localhost:5433/vehicle_showroom";
  private String jdbcUsername = "postgres";
  private String jdbcPassword = "satya123";
  private Connection jdbcConnection;
  
  
  public DBConnection() {
    connect();
  }

  public Connection getConnection() {
    return jdbcConnection;
  }

  public void connect()  {
    try {
      if(jdbcConnection == null || jdbcConnection.isClosed()) {
    	 //Class.forName("com.mysql.cj.jdbc.Driver");
    	  Class.forName("org.postgresql.Driver");
    	  jdbcConnection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);
          System.out.println("Opened database successfully");
      }
      createTableIfNotExists();
    } catch ( Exception e ) {
     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
     System.exit(0);
   }
 }

 private void createTableIfNotExists() {
   try {
       DatabaseMetaData meta = jdbcConnection.getMetaData();
       Statement stmt = jdbcConnection.createStatement();
      
       	// Create table

           String sql = "CREATE TABLE IF NOT EXISTS vehicle " +
                          "(id SERIAL PRIMARY KEY NOT NULL," +
                          " brand TEXT NOT NULL, " +
                          " model TEXT NOT NULL, " +
                          " price REAL)";
           stmt.executeUpdate(sql);

           stmt.close();
       
    } catch ( Exception e ) {
       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
       System.exit(0);
    }
 }


  public void disconnect() {
    try {
    	if (jdbcConnection != null && !jdbcConnection.isClosed()) {
    	    jdbcConnection.close();
    	}
    } catch (SQLException e) {
    	e.printStackTrace();
    }
  }
}
