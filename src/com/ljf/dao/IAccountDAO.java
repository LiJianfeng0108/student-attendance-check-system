package com.ljf.dao;

import com.ljf.vo.Account;

public interface IAccountDAO {
	public boolean doUpdate(Account ac) throws Exception;
	public int doLogin(Account ac) throws Exception;
}
