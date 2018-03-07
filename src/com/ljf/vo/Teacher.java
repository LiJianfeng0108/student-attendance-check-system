package com.ljf.vo;

public class Teacher extends Person{
	private String departp;
	private String departc;
	private int clazz;
	private String course;
	public Teacher() {
		super();
	}
	public Teacher(String name, String sex, String phone , String email, String departp, String departc, int clazz, String course) {
		super(name, sex, phone, email);
		this.departp = departp;
		this.departc = departc;
		this.clazz = clazz;
		this.course = course;
	}
	public Teacher(int tid, String name, String sex, String phone , String email, String departp, String departc, int clazz, String course) {
		super(tid, name, sex, phone, email);
		this.departp = departp;
		this.departc = departc;
		this.clazz = clazz;
		this.course = course;
	}
	
	public String getDepartp() {
		return departp;
	}
	public void setDepartp(String departp) {
		this.departp = departp;
	}
	public String getDepartc() {
		return departc;
	}
	public void setDepartc(String departc) {
		this.departc = departc;
	}
	public int getClazz() {
		return clazz;
	}
	public void setClazz(int clazz) {
		this.clazz = clazz;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	@Override
	public String toString() {
		return "教师编号：" + super.getId() + "，姓名：" + super.getName() +"，性别："+super.getSex() + "，电话号码：" + super.getPhone() + "，邮箱：" + super.getEmail()
				+ "， 院系：" + this.getDepartp() + " " + this.getDepartc() + "，授课班级：" + this.getClazz() + "，教授科目" + this.getCourse();
	}
	
	
}
