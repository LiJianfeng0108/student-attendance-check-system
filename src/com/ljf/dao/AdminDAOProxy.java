package com.ljf.dao;

import java.util.List;

import com.ljf.dbc.DatabaseConnection;
import com.ljf.vo.Admin;
import com.ljf.vo.Person;

public class AdminDAOProxy implements IPersonDAO{
	private DatabaseConnection dbc = null;
	private AdminDAOImpl adminDAOImpl = null;
	
	public AdminDAOProxy() {
		this.dbc = new DatabaseConnection();
		adminDAOImpl = new AdminDAOImpl(this.dbc.getConnection());
	}

	@Override
	public boolean doCreate(Person p) throws Exception {
		boolean flag = false;
		try{
			flag = this.adminDAOImpl.doCreate(p);
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
			tl = this.adminDAOImpl.doRetrieve();
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
			flag = this.adminDAOImpl.doUpdate(p);
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
			flag = this.adminDAOImpl.doDelete(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	@Override
	public Person findById(int id) throws Exception {
		Admin adm = null;
		try{
			adm = (Admin) this.adminDAOImpl.findById(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return adm;
	}
	
	public boolean isSupremeAdmin(String admid) throws Exception{
		boolean flag = false;
		try {
			flag = this.adminDAOImpl.isSupremeAdmin(admid);
		} catch (Exception e) {
			throw e;
		}
		return flag;
	}

}