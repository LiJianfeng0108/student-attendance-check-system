package com.ljf.dao;

import java.util.List;

import com.ljf.vo.Person;

public interface IPersonDAO {//接口定义CRUD标准
	public boolean doCreate(Person p) throws Exception;
	public List doRetrieve() throws Exception;
	public boolean doUpdate(Person p) throws Exception;
	public boolean doDelete(int id) throws Exception;
	public Person findById(int id) throws Exception;
}
