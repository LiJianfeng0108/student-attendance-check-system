package com.ljf.dao;

import com.ljf.dbc.DatabaseConnection;
import com.ljf.vo.Account;

public class AccountDAOProxy implements IAccountDAO{
	DatabaseConnection dbc = null;
	AccountDAOImpl accountDAOImpl = null;
	public AccountDAOProxy() {
		this.dbc = new DatabaseConnection();
		this.accountDAOImpl = new AccountDAOImpl(this.dbc.getConnection());
	}
	@Override
	public boolean doUpdate(Account ac) throws Exception {
		boolean flag = false;
		try{
			flag = this.accountDAOImpl.doUpdate(ac);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}
	@Override
	public int doLogin(Account ac) throws Exception {
		int flag = -1;
		try{
			flag = this.accountDAOImpl.doLogin(ac);
		}catch(Exception e){
			throw e;
		}finally{
			this.dbc.closeConnection();
		}
		
		return flag;
	}
	
	
}
