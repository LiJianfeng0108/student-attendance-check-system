package com.ljf.vo;

public abstract class Person {
	private int id;
	private String name;
	private String sex;
	private String phone;//mysql数据库int长度最多11位，最高位为符号位，而手机号是11位，为保持一致用string<——>varchar
	private String email;
	public Person() {
		
	}
	public Person(String name, String sex, String phone, String email) {
		this.name = name;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
	}
	public Person(int id, String name, String sex, String phone, String email) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.phone = phone;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
