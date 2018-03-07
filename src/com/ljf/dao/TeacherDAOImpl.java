package com.ljf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ljf.vo.Person;
import com.ljf.vo.Teacher;


/*teacher真实主题，由其代理进行调用*/
public class TeacherDAOImpl implements IPersonDAO{
	Connection conn = null;
	
	public TeacherDAOImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public boolean doCreate(Person p) throws Exception{
		conn.setAutoCommit(false);//不自动提交，以免插入两个表中间出现错误
		PreparedStatement pstmt = null;//采用与处理方式，进行查询操作
		Statement stmt = null;//对应查找信息表表中最后一个id，以便给账号表创建帐号
		ResultSet rs = null;
		int id = 0;
		Teacher t = null;
		if(p instanceof Teacher){//向下转型
			t = (Teacher)p;
		}
		boolean flag = false;
		String sql = "insert into tb_teac_info(name, sex, phone, email, departp, departc, class, course) values(?,?,?,?,?,?,?,?)";
		String sql2 = "insert into tb_account(id, password) values(?,?)";
		try {
			
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, t.getName());
			pstmt.setString(2, t.getSex());
			pstmt.setString(3, t.getPhone());
			pstmt.setString(4, t.getEmail());
			pstmt.setString(5, t.getDepartp());
			pstmt.setString(6, t.getDepartc());
			pstmt.setInt(7, t.getClazz());
			pstmt.setString(8, t.getCourse());
			int x1 = pstmt.executeUpdate();
			
			stmt = this.conn.createStatement();
			rs = stmt.executeQuery("select tid from tb_teac_info order by tid desc limit 0,1");//降序查询，第一个id
			if(rs.next()){
				id = rs.getInt(1);
			}
			
			pstmt = this.conn.prepareStatement(sql2);
			pstmt.setString(1, id+"");
			pstmt.setString(2, t.getPhone());
			int x2 = pstmt.executeUpdate();
			
			conn.commit();//提交事务
			if((x1>0)&&(x2>0)){
				flag = true;
			}
			
			
		} catch (SQLException e) {
			conn.rollback();//事务回滚
			throw e;
		}finally{
			stmt.close();
			pstmt.close();
		}
		return flag;
	}

	@Override
	public List doRetrieve() throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Teacher> teacherAllList = new ArrayList<Teacher>();
		Teacher t = null;
		String sql="select tid, name, sex, phone, email, departp, departc, class, course from tb_teac_info";
		try {
			pstmt = this.conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()){
				int tid = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				String departp = rs.getString(6);
				String departc = rs.getString(7);
				int clazz = rs.getInt(8);
				String course = rs.getString(9);
				t = new Teacher(tid, name, sex, phone, email, departp, departc, clazz, course);
				teacherAllList.add(t);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return teacherAllList;
	}

	@Override
	public boolean doUpdate(Person p) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		Teacher t = null;
		if(p instanceof Teacher){
			t = (Teacher)p;
		}
		boolean flag = false;
		String sql = "update tb_teac_info set name=?,sex=?,phone=?,email=?,departp=?,departc=?,class=?,course=? where tid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setString(1, t.getName());
			pstmt.setString(2, t.getSex());
			pstmt.setString(3, t.getPhone());
			pstmt.setString(4, t.getEmail());
			pstmt.setString(5, t.getDepartp());
			pstmt.setString(6, t.getDepartc());
			pstmt.setInt(7, t.getClazz());
			pstmt.setString(8, t.getCourse());
			pstmt.setInt(9, t.getId());
			int x = pstmt.executeUpdate();
			if(x>0){
				flag = true;
			}
			
			
		} catch (SQLException e) {
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public boolean doDelete(int id) throws Exception{
		conn.setAutoCommit(false);
		PreparedStatement pstmt = null;//采用与处理方式
		boolean flag = false;
		String sql = "delete from tb_teac_info where tid=?";
		String sql2 = "delete from tb_account where id=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			boolean x1 = pstmt.execute();
			
			pstmt = this.conn.prepareStatement(sql2);
			pstmt.setInt(1, id);
			boolean x2 = pstmt.execute();
			conn.commit();
			if(x1&&x2){
				flag = true;
			}
			
		} catch (SQLException e) {
			conn.rollback();
			throw e;
		}finally{
			pstmt.close();
		}
		return flag;
	}

	@Override
	public Person findById(int id) throws Exception{
		conn.setAutoCommit(true);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Teacher t = null;
		String sql="select tid, name, sex, phone, email, departp, departc, class, course from tb_teac_info where tid=?";
		try {
			pstmt = this.conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()){
				int tid = rs.getInt(1);
				String name = rs.getString(2);
				String sex = rs.getString(3);
				String phone = rs.getString(4);
				String email = rs.getString(5);
				String departp = rs.getString(6);
				String departc = rs.getString(7);
				int clazz = rs.getInt(8);
				String course = rs.getString(9);
				t = new Teacher(tid, name, sex, phone, email, departp, departc, clazz, course);
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			rs.close();
			pstmt.close();
		}
		return t;
	}

}
