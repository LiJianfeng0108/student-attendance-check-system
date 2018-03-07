package com.ljf.dao;

import java.util.List;

import com.ljf.dbc.DatabaseConnection;
import com.ljf.vo.Person;
import com.ljf.vo.Teacher;

/*代理类负责连接数据库，调用真实主题等*/
public class TeacherDAOProxy implements IPersonDAO{
	private DatabaseConnection dbc = null;
	private TeacherDAOImpl teacherDAOImpl = null;
	
	public TeacherDAOProxy() {
		this.dbc = new DatabaseConnection();
		teacherDAOImpl = new TeacherDAOImpl(this.dbc.getConnection());
	}

	@Override
	public boolean doCreate(Person p) throws Exception {
		boolean flag = false;
		try{
			flag = this.teacherDAOImpl.doCreate(p);
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
			tl = this.teacherDAOImpl.doRetrieve();
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
			flag = this.teacherDAOImpl.doUpdate(p);
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
			flag = this.teacherDAOImpl.doDelete(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	@Override
	public Person findById(int id) throws Exception {
		Teacher t = null;
		try{
			t = (Teacher) this.teacherDAOImpl.findById(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return t;
	}

}
