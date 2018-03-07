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
		return "����Ա��ţ�" + super.getId() + "��������" + super.getName() +"���Ա�"+super.getSex() + "���绰���룺" + super.getPhone() + "�����䣺" + super.getEmail()
				+ "�� Ȩ�ޣ�" + this.getAuthorization();
	}
}
