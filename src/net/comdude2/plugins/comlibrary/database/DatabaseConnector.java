/*
ComLibrary - A library plugin for Minecraft
Copyright (C) 2015  comdude2 (Matt Armer)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Contact: admin@mcviral.net
*/

package net.comdude2.plugins.comlibrary.database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DatabaseConnector {
	
	private String URL = null;
	private String username = null;
	private String password = null;
	private Connection connection = null;
	
	//jdbc:mysql://localhost:3306/   <-- Url needs to be like that
	public DatabaseConnector(String URL){
		this.URL = URL;
	}
	
	public void setupConnection(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void connect() throws ConnectionException, SQLException, IllegalStateException, Exception{
		if ((this.URL != null) && (this.username != null) && (this.password != null)){
			//Connect
			try {
				connection = (Connection) DriverManager.getConnection(this.URL, username, password);
			    System.out.println("Database connected!");
			} catch (SQLException e) {
				throw new IllegalStateException("Cannot connect the database!", e);
			}
		}else{
			throw new ConnectionException("One of the connection fields were null!");
		}
	}
	
	public Connection getConnection(){
		return connection;
	}
	
	public void disconnect(){
		if (connection != null){
			try {
				if (!connection.isClosed()){
					System.out.println("Disconnecting database: " + URL);
					connection.close();
					System.out.println("Database Disconnected.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadJdbcDriver(){
		System.out.println("Loading driver...");
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
		    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		}
	}
	
}
