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
		return "辅导员编号：" + super.getId() + "，姓名：" + super.getName() +"，性别："+super.getSex() + "，电话号码：" + super.getPhone() + "，邮箱：" + super.getEmail()
				+ "， 学院：" + this.getDepartp();
	}
}
