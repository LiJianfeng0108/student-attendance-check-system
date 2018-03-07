package com.ljf.factory;

import com.ljf.dao.AdminDAOProxy;
import com.ljf.dao.AdvisorDAOProxy;
import com.ljf.dao.IPersonDAO;
import com.ljf.dao.StudentDAOProxy;
import com.ljf.dao.TeacherDAOProxy;

/*工厂类负责返回不同的代理*/
public class PersonDAOFactory {
	public static IPersonDAO getTeacherProxy(){
		return new TeacherDAOProxy();
	}
	public static IPersonDAO getAdvisorProxy(){
		return new AdvisorDAOProxy();
	}
	public static StudentDAOProxy getStudentProxy(){//学生修改数据可能插入照片，也可能不修改照片
		return new StudentDAOProxy();
	}
	public static AdminDAOProxy getAdminProxy(){//管理员有级别划分，有单独的数据库查询方法
		return new AdminDAOProxy();
	}
}
