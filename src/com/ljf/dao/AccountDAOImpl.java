package com.ljf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ljf.vo.Account;

public class AccountDAOImpl implements IAccountDAO{
	private Connection conn = null;
	
	public AccountDAOImpl(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public boolean doUpdate(Account ac) throws Exception {
		boolean flag = false;
		PreparedStatement pstmt = null;
		String sql = "update tb_account set password=? where id=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, ac.getPassword());
			pstmt.setInt(2, ac.getId());
			int x = pstmt.executeUpdate();
			if(x>0){
				flag = true;
			}
			
			
		} catch (SQLException e) {
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public int doLogin(Account ac) throws Exception {
		int flag = -1;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select id from tb_account where id=? and password=?";
		
		try{
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, ac.getId());
			pstmt.setString(2, ac.getPassword());
			rs = pstmt.executeQuery();
			if(rs.next()){
				String personType = rs.getInt(1)+"";
				if(personType.startsWith("1")){//教师
					flag = 1;
				}
				if(personType.startsWith("2")){//辅导员
					flag = 2;
				}
				if(personType.startsWith("4")){//管理员
					flag = 4;
				}
				
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		
		return flag;
	}

	
}
