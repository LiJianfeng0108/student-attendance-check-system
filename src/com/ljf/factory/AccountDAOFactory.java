package com.ljf.factory;

import com.ljf.dao.AccountDAOProxy;
import com.ljf.dao.IAccountDAO;

public class AccountDAOFactory {
	public static IAccountDAO getAccountProxy(){
		return new AccountDAOProxy();
	}
}
