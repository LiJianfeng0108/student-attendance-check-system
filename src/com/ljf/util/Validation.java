package com.ljf.util;

/*ui提交的信息验证*/
public class Validation {
	
	private static String REGEXNAME = "[\u4e00-\u9fa5]{2,5}";//姓名：长度3到5，且必须是汉字
	private static String REGEXPHONE = "\\d{11}";//手机号码：长度11，且必须是数字
	private static String REGEXEMAIL = "\\w+@\\w+.\\w+.\\w+";//电子邮箱：格式是xxx@xx.xx.xx
	private static String REGEXCLAZZ = "\\d{1,3}";//班级：长度1到3，且必须是数字
	//教师基本信息验证
	public static String getTeacherErrorMess(String name, String phone ,String email, String course, String clazz){
		StringBuffer mess = new StringBuffer();//stringbuffer每次在原字符串基础上添加新内容
		
		if(!name.matches(REGEXNAME)){
			mess.append("姓名：长度2到5，且必须是汉字");
		}
		if(!phone.matches(REGEXPHONE)){
			mess.append("\n手机号码：长度11，且必须是数字");
		}
		if((!email.matches(REGEXEMAIL))||(email.length()>50)){
			mess.append("\n电子邮箱：长度不能超过50，且格式必须是xxx@xx.xx.xx");
		}
		if((course==null)||("".equals(course))||(course.length()>30)){
			mess.append("\n课程：长度不能超过50，且不能为空");
		}
		if(!clazz.matches(REGEXCLAZZ)){
			mess.append("\n班级：长度1到3，且必须是数字");
		}
		return mess.toString();
	}
	//辅导员 管理员
	public static String getAdvisorOrAdminErrorMess(String name, String phone ,String email){
		StringBuffer mess = new StringBuffer();//stringbuffer每次在原字符串基础上添加新内容
		
		if(!name.matches(REGEXNAME)){
			mess.append("姓名：长度2到5，且必须是汉字");
		}
		if(!phone.matches(REGEXPHONE)){
			mess.append("\n手机号码：长度11，且必须是数字");
		}
		if((!email.matches(REGEXEMAIL))||(email.length()>50)){
			mess.append("\n电子邮箱：长度不能超过50，且格式必须是xxx@xx.xx.xx");
		}
		return mess.toString();
	}
	//学生
	public static String getStudentErrorMess(String name, String phone ,String email, String clazz){
		StringBuffer mess = new StringBuffer();//stringbuffer每次在原字符串基础上添加新内容
		
		if(!name.matches(REGEXNAME)){
			mess.append("姓名：长度2到5，且必须是汉字");
		}
		if(!phone.matches(REGEXPHONE)){
			mess.append("\n手机号码：长度11，且必须是数字");
		}
		if((!email.matches(REGEXEMAIL))||(email.length()>50)){
			mess.append("\n电子邮箱：长度不能超过50，且格式必须是xxx@xx.xx.xx");
		}
		if(!clazz.matches(REGEXCLAZZ)){
			mess.append("\n班级：长度1到3，且必须是数字");
		}
		return mess.toString();
	}
}
