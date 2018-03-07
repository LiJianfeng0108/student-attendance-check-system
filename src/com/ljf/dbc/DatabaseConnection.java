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
			dbcResource = ResourceBundle.getBundle("dbc");//��ȡ��Դ�ļ�
			Class.forName(dbcResource.getString("DBDRIVER"));//����mysql����
			conn = DriverManager.getConnection(dbcResource.getString("DBURL"), dbcResource.getString("DBUSER"), dbcResource.getString("DBPASS"));//�������ݿ�
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
