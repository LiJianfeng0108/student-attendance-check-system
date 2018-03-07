package com.ljf.util;

/*ui�ύ����Ϣ��֤*/
public class Validation {
	
	private static String REGEXNAME = "[\u4e00-\u9fa5]{2,5}";//����������3��5���ұ����Ǻ���
	private static String REGEXPHONE = "\\d{11}";//�ֻ����룺����11���ұ���������
	private static String REGEXEMAIL = "\\w+@\\w+.\\w+.\\w+";//�������䣺��ʽ��xxx@xx.xx.xx
	private static String REGEXCLAZZ = "\\d{1,3}";//�༶������1��3���ұ���������
	//��ʦ������Ϣ��֤
	public static String getTeacherErrorMess(String name, String phone ,String email, String course, String clazz){
		StringBuffer mess = new StringBuffer();//stringbufferÿ����ԭ�ַ������������������
		
		if(!name.matches(REGEXNAME)){
			mess.append("����������2��5���ұ����Ǻ���");
		}
		if(!phone.matches(REGEXPHONE)){
			mess.append("\n�ֻ����룺����11���ұ���������");
		}
		if((!email.matches(REGEXEMAIL))||(email.length()>50)){
			mess.append("\n�������䣺���Ȳ��ܳ���50���Ҹ�ʽ������xxx@xx.xx.xx");
		}
		if((course==null)||("".equals(course))||(course.length()>30)){
			mess.append("\n�γ̣����Ȳ��ܳ���50���Ҳ���Ϊ��");
		}
		if(!clazz.matches(REGEXCLAZZ)){
			mess.append("\n�༶������1��3���ұ���������");
		}
		return mess.toString();
	}
	//����Ա ����Ա
	public static String getAdvisorOrAdminErrorMess(String name, String phone ,String email){
		StringBuffer mess = new StringBuffer();//stringbufferÿ����ԭ�ַ������������������
		
		if(!name.matches(REGEXNAME)){
			mess.append("����������2��5���ұ����Ǻ���");
		}
		if(!phone.matches(REGEXPHONE)){
			mess.append("\n�ֻ����룺����11���ұ���������");
		}
		if((!email.matches(REGEXEMAIL))||(email.length()>50)){
			mess.append("\n�������䣺���Ȳ��ܳ���50���Ҹ�ʽ������xxx@xx.xx.xx");
		}
		return mess.toString();
	}
	//ѧ��
	public static String getStudentErrorMess(String name, String phone ,String email, String clazz){
		StringBuffer mess = new StringBuffer();//stringbufferÿ����ԭ�ַ������������������
		
		if(!name.matches(REGEXNAME)){
			mess.append("����������2��5���ұ����Ǻ���");
		}
		if(!phone.matches(REGEXPHONE)){
			mess.append("\n�ֻ����룺����11���ұ���������");
		}
		if((!email.matches(REGEXEMAIL))||(email.length()>50)){
			mess.append("\n�������䣺���Ȳ��ܳ���50���Ҹ�ʽ������xxx@xx.xx.xx");
		}
		if(!clazz.matches(REGEXCLAZZ)){
			mess.append("\n�༶������1��3���ұ���������");
		}
		return mess.toString();
	}
}
