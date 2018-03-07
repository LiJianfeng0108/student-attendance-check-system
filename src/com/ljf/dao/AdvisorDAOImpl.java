package com.ljf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ljf.vo.Person;
import com.ljf.vo.Advisor;;

public class AdvisorDAOImpl implements IPersonDAO{
	Connection conn = null;
	
	public AdvisorDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doCreate(Person p) throws Exception{
		conn.setAutoCommit(false);//不自动提交，以免插入两个表中间出现错误
		PreparedStatement pstmt = null;//采用与处理方式
		Statement stmt = null;//对应查找信息表表中最后一个id，以便给账号表创建帐号
		ResultSet rs = null;
		int id = 0;
		Advisor adv = null;
		if(p instanceof Advisor){//向下转型
			adv = (Advisor)p;
		}
		boolean flag = false;
		String sql = "insert into tb_adv_info(name, sex, phone, email, departp) values(?,?,?,?,?)";
		String sql2 = "insert into tb_account(id, password) values(?,?)";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, adv.getName());
			pstmt.setString(2, adv.getSex());
			pstmt.setString(3, adv.getPhone());
			pstmt.setString(4, adv.getEmail());
			pstmt.setString(5, adv.getDepartp());
			int x1 = pstmt.executeUpdate();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery("select advid from tb_adv_info order by advid desc limit 0,1");//降序查询，第一个id
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			pstmt = this.conn.prepareStatement(sql2);
			pstmt.setString(1, id+"");
			pstmt.setString(2, adv.getPhone());
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
		List<Advisor> advisorAllList = new ArrayList<Advisor>();
		Advisor adv = null;
		String sql="select advid, name, sex, phone, email, departp from tb_adv_info";
		try {
			pstmt = this.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int tid = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				String departp = rs.getString(6);
				adv = new Advisor(tid, name, sex, phone, email, departp);
				advisorAllList.add(adv);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return advisorAllList;
	}

	@Override
	public boolean doUpdate(Person p) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		Advisor adv = null;
		if(p instanceof Advisor){
			adv = (Advisor)p;
		}
		boolean flag = false;
		String sql = "update tb_adv_info set name=?,sex=?,phone=?,email=?,departp=? where advid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, adv.getName());
			pstmt.setString(2, adv.getSex());
			pstmt.setString(3, adv.getPhone());
			pstmt.setString(4, adv.getEmail());
			pstmt.setString(5, adv.getDepartp());
			pstmt.setInt(6, adv.getId());
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
		String sql = "delete from tb_adv_info where advid=?";
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
		Advisor adv = null;
		String sql="select advid, name, sex, phone, email, departp from tb_adv_info where advid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int advid = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				String departp = rs.getString(6);
				adv = new Advisor(advid, name, sex, phone, email, departp);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return adv;
	}

}
