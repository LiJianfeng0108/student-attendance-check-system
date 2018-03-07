package com.ljf.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ljf.vo.Person;
import com.ljf.vo.Student;

public class StudentDAOImpl implements IPersonDAO{
	Connection conn = null;
	
	public StudentDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doCreate(Person p) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;//采用与处理方式
		Student s = null;
		if(p instanceof Student){//向下转型
			s = (Student)p;
		}
		boolean flag = false;
		String sql = "insert into tb_stu_info(name, sex, phone, email, departp, departc, class, picture) values(?,?,?,?,?,?,?,?)";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, s.getName());
			pstmt.setString(2, s.getSex());
			pstmt.setString(3, s.getPhone());
			pstmt.setString(4, s.getEmail());
			pstmt.setString(5, s.getDepartp());
			pstmt.setString(6, s.getDepartc());
			pstmt.setInt(7, s.getClazz());
			pstmt.setBinaryStream(8, s.getPicture());
			int x1 = pstmt.executeUpdate();
			if(x1>0){
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
	public List doRetrieve() throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Student> tudentAllList = new ArrayList<Student>();
		Student s = null;
		String sql="select sid, name, sex, phone, email, departp, departc, class, picture from tb_stu_info";
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
				String departc = rs.getString(7);
				int clazz = rs.getInt(8);
				InputStream picture = rs.getBinaryStream(9);
				s = new Student(tid, name, sex, phone, email, departp, departc, clazz, picture);
				tudentAllList.add(s);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return tudentAllList;
	}

	@Override
	public boolean doUpdate(Person p) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		Student s = null;
		if(p instanceof Student){
			s = (Student)p;
		}
		boolean flag = false;
		String sql = "update tb_stu_info set name=?,sex=?,phone=?,email=?,departp=?,departc=?,class=?,picture=? where sid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, s.getName());
			pstmt.setString(2, s.getSex());
			pstmt.setString(3, s.getPhone());
			pstmt.setString(4, s.getEmail());
			pstmt.setString(5, s.getDepartp());
			pstmt.setString(6, s.getDepartc());
			pstmt.setInt(7, s.getClazz());
			pstmt.setBinaryStream(8,s.getPicture());
			pstmt.setInt(9, s.getId());
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
	
	public boolean doUpdateWithoutPicture(Person p) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		Student s = null;
		if(p instanceof Student){
			s = (Student)p;
		}
		boolean flag = false;
		String sql = "update tb_stu_info set name=?,sex=?,phone=?,email=?,departp=?,departc=?,class=? where sid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, s.getName());
			pstmt.setString(2, s.getSex());
			pstmt.setString(3, s.getPhone());
			pstmt.setString(4, s.getEmail());
			pstmt.setString(5, s.getDepartp());
			pstmt.setString(6, s.getDepartc());
			pstmt.setInt(7, s.getClazz());
			pstmt.setInt(8, s.getId());
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
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;//采用预处理方式
		boolean flag = false;
		String sql = "delete from tb_stu_info where sid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			boolean x1 = pstmt.execute();
			if(x1){
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
	public Person findById(int id) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Student s = null;
		String sql="select sid, name, sex, phone, email, departp, departc, class, picture from tb_stu_info where sid=?";
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
				String departp = rs.getString(6);
				String departc = rs.getString(7);
				int clazz = rs.getInt(8);
				InputStream picture = rs.getBinaryStream(9);
				s = new Student(tid, name, sex, phone, email, departp, departc, clazz, picture);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return s;
	}

}