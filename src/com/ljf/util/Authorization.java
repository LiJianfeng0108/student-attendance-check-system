package com.ljf.util;

/*�����½��Ա�����ͣ���ʦ������Ա�����Ȩ�޹���Ա��һ��Ȩ�޹���Ա*/
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
