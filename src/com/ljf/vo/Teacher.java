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
		return "��ʦ��ţ�" + super.getId() + "��������" + super.getName() +"���Ա�"+super.getSex() + "���绰���룺" + super.getPhone() + "�����䣺" + super.getEmail()
				+ "�� Ժϵ��" + this.getDepartp() + " " + this.getDepartc() + "���ڿΰ༶��" + this.getClazz() + "�����ڿ�Ŀ" + this.getCourse();
	}
	
	
}
