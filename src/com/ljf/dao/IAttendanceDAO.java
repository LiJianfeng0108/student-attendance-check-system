package com.ljf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ljf.vo.Person;

public interface IAttendanceDAO {
	public Map doRetrieveByClassAndCourse(int clazz, String course) throws Exception;//���ؽ�ʦ�ܿ�����ѧ��������Ϣ
	public Map doRetrieveByDepartp(String departp) throws Exception;//���ظ���Ա�ܿ�����ѧ��������Ϣ
	public boolean doInsertForLeave(Person p, List course, Date date) throws Exception;//�������
	public boolean doDeleteForLeave(Person p, Date date) throws Exception;//ɾ�����
	public List doRetrieveForCheck(int clazz, String course) throws Exception;//���ش�����ѧ��
	public boolean doInsertForCheck(Person p, String course) throws Exception;//�������
	public boolean doUpdateFromAbToLa(int sid, String course) throws Exception;//���θ�Ϊ�ٵ�
}
