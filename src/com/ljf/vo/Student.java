package com.ljf.vo;

import java.io.InputStream;

public class Student extends Person{
	private String departp;
	private String departc;
	private int clazz;
	private InputStream picture;
	public Student() {
		super();
	}
	public Student(String name, String sex, String phone, String email, String departp, String departc, int clazz,
			InputStream picture) {
		super(name, sex, phone, email);
		this.departp = departp;
		this.departc = departc;
		this.clazz = clazz;
		this.picture = picture;
	}
	public Student(int sid, String name, String sex, String phone, String email, String departp, String departc, int clazz,
			InputStream picture) {
		super(sid, name, sex, phone, email);
		this.departp = departp;
		this.departc = departc;
		this.clazz = clazz;
		this.picture = picture;
	}
	public Student(int sid, String name, String sex, String phone, String email, String departp, String departc, int clazz) {
		super(sid, name, sex, phone, email);
		this.departp = departp;
		this.departc = departc;
		this.clazz = clazz;
	}
	public Student(String name, String sex, String phone, String email, String departp, String departc, int clazz) {
		super(name, sex, phone, email);
		this.departp = departp;
		this.departc = departc;
		this.clazz = clazz;
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
	public InputStream getPicture() {
		return picture;
	}
	public void setPicture(InputStream picture) {
		this.picture = picture;
	}
	
	@Override
	public String toString() {
		return "ѧ����ţ�" + super.getId() + "��������" + super.getName() +"���Ա�"+super.getSex() + "���绰���룺" + super.getPhone() + "�����䣺" + super.getEmail()
				+ "�� Ժϵ��" + this.getDepartp() + " " + this.getDepartc() + "�����ڰ༶��" + this.getClazz();
	}
	
}
