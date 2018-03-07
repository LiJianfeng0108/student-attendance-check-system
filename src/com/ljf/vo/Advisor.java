package com.ljf.vo;

public class Advisor extends Person{
	private String departp;
	
	public Advisor() {
		super();
	}

	public Advisor(String name, String sex, String phone, String email, String departp) {
		super(name, sex, phone, email);
		this.departp = departp;
	}
	public Advisor(int advid, String name, String sex, String phone, String email, String departp) {
		super(advid, name, sex, phone, email);
		this.departp = departp;
	}

	public String getDepartp() {
		return departp;
	}

	public void setDepartp(String departp) {
		this.departp = departp;
	}
	
	@Override
	public String toString() {
		return "����Ա��ţ�" + super.getId() + "��������" + super.getName() +"���Ա�"+super.getSex() + "���绰���룺" + super.getPhone() + "�����䣺" + super.getEmail()
				+ "�� ѧԺ��" + this.getDepartp();
	}
}
