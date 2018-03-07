package com.ljf.dao;

import java.util.List;

import com.ljf.dbc.DatabaseConnection;
import com.ljf.vo.Person;
import com.ljf.vo.Advisor;

public class AdvisorDAOProxy implements IPersonDAO{
	private DatabaseConnection dbc = null;
	private AdvisorDAOImpl advisorDAOImpl = null;
	
	public AdvisorDAOProxy() {
		this.dbc = new DatabaseConnection();
		advisorDAOImpl = new AdvisorDAOImpl(this.dbc.getConnection());
	}

	@Override
	public boolean doCreate(Person p) throws Exception {
		boolean flag = false;
		try{
			flag = this.advisorDAOImpl.doCreate(p);
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
			tl = this.advisorDAOImpl.doRetrieve();
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
			flag = this.advisorDAOImpl.doUpdate(p);
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
			flag = this.advisorDAOImpl.doDelete(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}

	@Override
	public Person findById(int id) throws Exception {
		Advisor adv = null;
		try{
			adv = (Advisor) this.advisorDAOImpl.findById(id);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return adv;
	}

}