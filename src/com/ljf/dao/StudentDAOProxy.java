package com.ljf.dao;

import java.util.List;

import com.ljf.dbc.DatabaseConnection;
import com.ljf.vo.Student;
import com.ljf.vo.Person;

public class StudentDAOProxy implements IPersonDAO{
	private DatabaseConnection dbc = null;
	private StudentDAOImpl studentDAOImpl = null;
	
	public StudentDAOProxy() {
		this.dbc = new DatabaseConnection();
		studentDAOImpl = new StudentDAOImpl(this.dbc.getConnection());
	}

	@Override
	public boolean doCreate(Person p) throws Exception {
		boolean flag = false;
		try{
			flag = this.studentDAOImpl.doCreate(p);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	@Override
	public List doRetrieve() throws Exception {
		List tl = null;
		try{
			tl = this.studentDAOImpl.doRetrieve();
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return tl;
	}

	@Override
	public boolean doUpdate(Person p) throws Exception {
		boolean flag = false;
		try{
			flag = this.studentDAOImpl.doUpdate(p);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}
	public boolean doUpdateWithoutPicture(Person p) throws Exception {
		boolean flag = false;
		try{
			flag = this.studentDAOImpl.doUpdateWithoutPicture(p);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	@Override
	public boolean doDelete(int id) throws Exception {
		boolean flag = false;
		try{
			flag = this.studentDAOImpl.doDelete(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	@Override
	public Person findById(int id) throws Exception {
		Student s = null;
		try{
			s = (Student) this.studentDAOImpl.findById(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return s;
	}

}
