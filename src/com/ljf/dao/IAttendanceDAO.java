package com.ljf.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ljf.vo.Person;

public interface IAttendanceDAO {
	public Map doRetrieveByClassAndCourse(int clazz, String course) throws Exception;//加载教师能看到的学生考勤信息
	public Map doRetrieveByDepartp(String departp) throws Exception;//加载辅导员能看到的学生考勤信息
	public boolean doInsertForLeave(Person p, List course, Date date) throws Exception;//插入请假
	public boolean doDeleteForLeave(Person p, Date date) throws Exception;//删除请假
	public List doRetrieveForCheck(int clazz, String course) throws Exception;//加载待点名学生
	public boolean doInsertForCheck(Person p, String course) throws Exception;//插入旷课
	public boolean doUpdateFromAbToLa(int sid, String course) throws Exception;//旷课改为迟到
}
