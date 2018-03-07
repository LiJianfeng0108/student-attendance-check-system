package com.ljf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ljf.vo.Admin;
import com.ljf.vo.Person;

public class AdminDAOImpl implements IPersonDAO{
	Connection conn = null;
	
	public AdminDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doCreate(Person p) throws Exception{
		conn.setAutoCommit(false);//不自动提交，以免插入两个表中间出现错误
		PreparedStatement pstmt = null;//采用与处理方式
		Statement stmt = null;//对应查找信息表表中最后一个id，以便给账号表创建帐号
		ResultSet rs = null;
		int id = 0;
		Admin adm = null;
		if(p instanceof Admin){//向下转型
			adm = (Admin)p;
		}
		boolean flag = false;
		String sql = "insert into tb_adm_info(name, sex, phone, email, authorization) values(?,?,?,?,?)";
		String sql2 = "insert into tb_account(id, password) values(?,?)";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, adm.getName());
			pstmt.setString(2, adm.getSex());
			pstmt.setString(3, adm.getPhone());
			pstmt.setString(4, adm.getEmail());
			pstmt.setInt(5, adm.getAuthorization());
			int x1 = pstmt.executeUpdate();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery("select admid from tb_adm_info order by admid desc limit 0,1");//降序查询，第一个id
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			pstmt = this.conn.prepareStatement(sql2);
			pstmt.setString(1, id+"");
			pstmt.setString(2, adm.getPhone());
			int x2 = pstmt.executeUpdate();
			
			conn.commit();//提交事务
			if((x1>0)&&(x2>0)){
				flag = true;
			}
			
			
		} catch (SQLException e) {
			conn.rollback();//事务回滚
			throw e;
		}finally{
			stmt.close();
			pstmt.close();
		}
		return flag;
	}

	@Override
	public List doRetrieve() throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Admin> teacherAllList = new ArrayList<Admin>();
		Admin adm = null;
		String sql="select admid, name, sex, phone, email, authorization from tb_adm_info";
		try {
			pstmt = this.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int tid = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				int authorization = rs.getInt(6);
				adm = new Admin(tid, name, sex, phone, email, authorization);
				teacherAllList.add(adm);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return teacherAllList;
	}

	@Override
	public boolean doUpdate(Person p) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		Admin adm = null;
		if(p instanceof Admin){
			adm = (Admin)p;
		}
		boolean flag = false;
		String sql = "update tb_adm_info set name=?,sex=?,phone=?,email=?,authorization=? where admid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, adm.getName());
			pstmt.setString(2, adm.getSex());
			pstmt.setString(3, adm.getPhone());
			pstmt.setString(4, adm.getEmail());
			pstmt.setInt(5, adm.getAuthorization());
			pstmt.setInt(6, adm.getId());
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
	public boolean doDelete(int id) throws Exception{
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;//采用与处理方式
		boolean flag = false;
		String sql = "delete from tb_adm_info where admid=?";
		String sql2 = "delete from tb_account where id=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			boolean x1 = pstmt.execute();
			
			pstmt = this.conn.prepareStatement(sql2);
			pstmt.setInt(1, id);
			boolean x2 = pstmt.execute();
			conn.commit();
			if(x1&&x2){
				flag = true;
			}
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public Person findById(int id) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Admin adm = null;
		String sql="select admid, name, sex, phone, email, authorization from tb_adm_info where admid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int tid = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				int authorization = rs.getInt(6);
				adm = new Admin(tid, name, sex, phone, email, authorization);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return adm;
	}

	public boolean isSupremeAdmin(String admid) throws Exception{
		boolean flag = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select authorization from tb_adm_info where admid=?";
		try {
			conn.setAutoCommit(true);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, admid);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int type = rs.getInt(1);
				if(type==1){
					flag = true;
				}
			}
			
		} catch (SQLException e) {
			throw e;
		}
		
		return flag;
	}
}
