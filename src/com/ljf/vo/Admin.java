package com.ljf.vo;

public class Admin extends Person{
	private int authorization;
	public Admin() {
		super();
	}
	public Admin(String name, String sex, String phone, String email, int authorization) {
		super(name, sex, phone, email);
		this.authorization = authorization;
	}
	public Admin(int admid, String name, String sex, String phone, String email, int authorization) {
		super(admid, name, sex, phone, email);
		this.authorization = authorization;
	}
	
	public int getAuthorization() {
		return authorization;
	}
	public void setAuthorization(int authorization) {
		this.authorization = authorization;
	}
	
	@Override
	public String toString() {
		return "管理员编号：" + super.getId() + "，姓名：" + super.getName() +"，性别："+super.getSex() + "，电话号码：" + super.getPhone() + "，邮箱：" + super.getEmail()
				+ "， 权限：" + this.getAuthorization();
	}
}
