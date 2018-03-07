package com.ljf.util;

/*保存登陆人员的类型：教师，辅导员，最高权限管理员，一般权限管理员*/
public class Authorization {
	private static boolean ISTEAC = false;
	private static boolean ISADV = false;
	private static boolean ISSUPREMEADM = false;
	private static boolean ISNORMALADM = false;
	public static void setAuthorization(boolean isTeac, boolean isAdv, boolean isSupremeAdm, boolean isNormalAdm){
		ISTEAC = isTeac;
		ISADV = isAdv;
		ISSUPREMEADM = isSupremeAdm;
		ISNORMALADM = isNormalAdm;
	}
	
	public static boolean getTeacAuthorization(){
		return ISTEAC;
	}
	public static boolean getAdvAuthorization(){
		return ISADV;
	}
	public static boolean getSupremAdmAuthorization(){
		return ISSUPREMEADM;
	}
	public static boolean getNormalAdmAuthorization(){
		return ISNORMALADM;
	}
}
