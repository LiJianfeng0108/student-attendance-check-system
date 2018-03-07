package com.ljf.dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ljf.vo.Person;
import com.ljf.vo.Student;

public class AttendanceDAOImpl implements IAttendanceDAO {
	private Connection conn = null;
	
	public AttendanceDAOImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public Map doRetrieveByClassAndCourse(int clazz, String course) throws Exception {
		this.conn.setAutoCommit(true);
		Map hm = new HashMap();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.id,a.sid,a.name,a.type,a.course,a.date from tb_attendance a,tb_stu_info s,tb_teac_info t where a.name=s.name and s.class=? and a.course=t.course and a.course=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, clazz);
			pstmt.setString(2, course);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				int sid = rs.getInt(2);
				String name = rs.getString(3);
				String type = rs.getString(4);
				String course2 = rs.getString(5);
				Date date = rs.getDate(6);
				hm.put(id, sid+" "+name+" "+type+" "+course2+" "+date);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		
		return hm;
	}

	@Override
	public Map doRetrieveByDepartp(String departp) throws Exception {
		this.conn.setAutoCommit(true);
		Map hm = new HashMap();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select a.id,a.sid,a.name,a.type,a.course,a.date from tb_attendance a,tb_stu_info s,tb_adv_info v where a.name=s.name and s.departp=v.departp and v.departp=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, departp);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				int sid = rs.getInt(2);
				String name = rs.getString(3);
				String type = rs.getString(4);
				String course = rs.getString(5);
				Date date = rs.getDate(6);
				hm.put(id, sid+" "+name+" "+type+" "+course+" "+date);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		
		return hm;
	}

	@Override
	public boolean doInsertForLeave(Person p, List course, Date date)
			throws Exception {
		this.conn.setAutoCommit(false);
		boolean flag = false;
		String sql = "insert into tb_attendance(sid,name,type,course,date) values(?,?,?,?,?)";
		PreparedStatement pstmt = null;
		//System.out.print(date.getTime());
		try{
			for(Object c:course){
				pstmt = this.conn.prepareStatement(sql);
				pstmt.setInt(1, p.getId());
				pstmt.setString(2, p.getName());
				pstmt.setString(3, "Çë¼Ù");
				pstmt.setString(4, (String)c);
				pstmt.setDate(5, new java.sql.Date(date.getTime()));
				pstmt.executeUpdate();
			}
			this.conn.commit();
			flag = true;
		}catch(Exception e){
			this.conn.rollback();
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public boolean doDeleteForLeave(Person p, Date date)
			throws Exception {
		this.conn.setAutoCommit(true);
		boolean flag = false;
		String sql = "delete from tb_attendance where sid=? and name=? and type=? and date=?";
		PreparedStatement pstmt = null;
		try{
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, p.getId());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, "Çë¼Ù");
			pstmt.setDate(4, new java.sql.Date(date.getTime()));
			int x  = pstmt.executeUpdate();
			if(x>0){
				flag = true;
			}
			
		}catch(Exception e){
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public List doRetrieveForCheck(int clazz, String course) throws Exception {
		List al = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select sid,name,picture from tb_stu_info s where class=? and sid not in(select sid from tb_attendance where type='Çë¼Ù' and date=? and course=?)";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, clazz);
			pstmt.setDate(2, new java.sql.Date(new Date().getTime()));
			pstmt.setString(3, course);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int id = rs.getInt(1);
				String name = rs.getString(2);
				InputStream picture = rs.getBinaryStream(3);
				Student s = new Student();
				s.setId(id);
				s.setName(name);
				s.setPicture(picture);
				al.add(s);
			}
		} catch (Exception e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		
		return al;
	}

	@Override
	public boolean doInsertForCheck(Person p, String course) throws Exception {
		this.conn.setAutoCommit(true);
		boolean flag = false;
		String sql = "insert into tb_attendance(sid,name,type,course,date) values(?,?,?,?,?)";
		PreparedStatement pstmt = null;
		try{
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, p.getId());
			pstmt.setString(2, p.getName());
			pstmt.setString(3, "¿õ¿Î");
			pstmt.setString(4, course);
			pstmt.setDate(5, new java.sql.Date(new Date().getTime()));
			pstmt.executeUpdate();
			flag = true;
			
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public boolean doUpdateFromAbToLa(int sid, String course)
			throws Exception {
		this.conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		boolean flag = false;
		String sql = "update tb_attendance set type = '³Ùµ½' where date=? and course=? and sid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setDate(1, new java.sql.Date(new Date().getTime()));
			pstmt.setString(2, course);
			pstmt.setInt(3, sid);
			int x = pstmt.executeUpdate();
			if(x>0){
				flag = true;
			}
		} catch (Exception e) {
			throw e;
		}finally{
			pstmt.close();
		}
		
		return flag;
	}

}
