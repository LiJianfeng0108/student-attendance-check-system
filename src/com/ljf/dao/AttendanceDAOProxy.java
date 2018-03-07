package com.ljf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ljf.dbc.DatabaseConnection;
import com.ljf.vo.Person;
import com.ljf.vo.Student;

public class AttendanceDAOProxy implements IAttendanceDAO {
	private DatabaseConnection dbc = null;
	private AttendanceDAOImpl attendanceDAOImpl = null;
	
	public AttendanceDAOProxy() {
		this.dbc = new DatabaseConnection();
		attendanceDAOImpl = new AttendanceDAOImpl(this.dbc.getConnection());
	}
	
	@Override
	public Map doRetrieveByClassAndCourse(int clazz, String course) throws Exception {
		Map hm = null;
		try{
			hm = this.attendanceDAOImpl.doRetrieveByClassAndCourse(clazz, course);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return hm;
	}
	@Override
	public Map doRetrieveByDepartp(String departp) throws Exception {
		Map hm = null;
		try{
			hm = this.attendanceDAOImpl.doRetrieveByDepartp(departp);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return hm;
	}

	@Override
	public boolean doInsertForLeave(Person p, List course, Date date)
			throws Exception {
		boolean flag = false;
		try{
			flag = this.attendanceDAOImpl.doInsertForLeave(p, course, date);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		return flag;
	}

	@Override
	public boolean doDeleteForLeave(Person p, Date date)
			throws Exception {
		boolean flag = false;
		try{
			flag = this.attendanceDAOImpl.doDeleteForLeave(p, date);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		return flag;
	}

	@Override
	public List doRetrieveForCheck(int clazz, String course)
			throws Exception {
		List al = null;
		try{
			al = this.attendanceDAOImpl.doRetrieveForCheck(clazz, course);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return al;
	}

	@Override
	public boolean doInsertForCheck(Person p, String course) throws Exception {
		boolean flag = false;
		try{
			flag = this.attendanceDAOImpl.doInsertForCheck(p, course);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		return flag;
	}

	@Override
	public boolean doUpdateFromAbToLa(int sid, String course)
			throws Exception {
		boolean flag = false;
		try{
			flag = this.attendanceDAOImpl.doUpdateFromAbToLa(sid, course);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	

}
