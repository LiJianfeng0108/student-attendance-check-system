package com.ljf.dbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DatabaseConnection {
	private ResourceBundle dbcResource = null;
	private Connection conn = null;
	
	public DatabaseConnection() {
		try {
			dbcResource = ResourceBundle.getBundle("dbc");//读取资源文件
			Class.forName(dbcResource.getString("DBDRIVER"));//加载mysql驱动
			conn = DriverManager.getConnection(dbcResource.getString("DBURL"), dbcResource.getString("DBUSER"), dbcResource.getString("DBPASS"));//连接数据库
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection(){
		return conn;
	}
	public void closeConnection(){
		try {
			if(conn!=null){
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
