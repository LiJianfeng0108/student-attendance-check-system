package com.ljf.factory;

import com.ljf.dao.AdminDAOProxy;
import com.ljf.dao.AdvisorDAOProxy;
import com.ljf.dao.IPersonDAO;
import com.ljf.dao.StudentDAOProxy;
import com.ljf.dao.TeacherDAOProxy;

/*�����ฺ�𷵻ز�ͬ�Ĵ���*/
public class PersonDAOFactory {
	public static IPersonDAO getTeacherProxy(){
		return new TeacherDAOProxy();
	}
	public static IPersonDAO getAdvisorProxy(){
		return new AdvisorDAOProxy();
	}
	public static StudentDAOProxy getStudentProxy(){//ѧ���޸����ݿ��ܲ�����Ƭ��Ҳ���ܲ��޸���Ƭ
		return new StudentDAOProxy();
	}
	public static AdminDAOProxy getAdminProxy(){//����Ա�м��𻮷֣��е��������ݿ��ѯ����
		return new AdminDAOProxy();
	}
}
