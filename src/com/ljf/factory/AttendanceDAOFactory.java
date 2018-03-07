package com.ljf.factory;

import com.ljf.dao.AccountDAOProxy;
import com.ljf.dao.AttendanceDAOProxy;
import com.ljf.dao.IAccountDAO;
import com.ljf.dao.IAttendanceDAO;

public class AttendanceDAOFactory {
	public static IAttendanceDAO getAttendanceProxy(){
		return new AttendanceDAOProxy();
	}
}
