package kr.ac.green.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import kr.ac.green.dto.Student;

public class StudentDao implements IDaoConstants {
	//field
	private static StudentDao instance = new StudentDao();
	
	//constructor
	private StudentDao() {
		try {
			Class.forName(DRIVER);
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//getInstance
	public static StudentDao getInstance() {
		return instance;
	}
	
	//connect
	public Connection connect() {
		Connection con = null;
		try {
			con = DriverManager.getConnection(DBURL, UID, PW);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//disconnect
	public void disconnect(Connection con) {
		try {
			con.close();
		} catch(Exception e) {}
	}
	
	//insert
	public int insert(Connection con, Student student) {
		int result = 0;
		PreparedStatement pStmt = null;
		
		try {
			pStmt = con.prepareStatement(SQLS[INSERT]);
			pStmt.setString(1, toEn(student.getSname()));
			pStmt.setString(2, toEn(student.getStel()));
			pStmt.setInt(3, student.getSgrade());
			pStmt.setString(4, toEn(student.getSclass()));
			result = pStmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		
		return result;
	}
	
	//getAll
	public Student[] getAll(Connection con) {
		Student[] list = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			pStmt = con.prepareStatement(SQLS[GETALL]);
			rs = pStmt.executeQuery();
			
			rs.last();
			list = new Student[rs.getRow()];
			rs.beforeFirst();
			
			int idx = 0;
			while(rs.next()) {
				list[idx] = rowMapping(rs);
				idx++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}
		for(Student s : list) {
			System.out.println(s.getSname());
		}
		return list;
	}
	
	//getById
	public Student getById(Connection con, int sid) {
		Student student = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			pStmt = con.prepareStatement(SQLS[GET_BY_ID]);
			pStmt.setInt(1, sid);
			rs = pStmt.executeQuery();
			
			if(rs.next()) {
				student = rowMapping(rs);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}
		return student;
	}
	
	//getByName
	public Student[] getByName(Connection con, String sname) {
		Student[] list = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		sname = "%"+sname+"%";
		try {
			pStmt = con.prepareStatement(SQLS[GET_BY_NAME]);
			pStmt.setString(1, toEn(sname));
			rs = pStmt.executeQuery();
			
			rs.last();
			list = new Student[rs.getRow()];
			rs.beforeFirst();
			
			int idx = 0;
			while(rs.next()) {
				list[idx] = rowMapping(rs);
				idx++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}
		return list;
	}
	
	//getByClass
	public Student[] getByClass(Connection con, String sclass) {
		Student[] list = null;
		PreparedStatement pStmt = null;
		ResultSet rs = null;
		
		try {
			pStmt = con.prepareStatement(SQLS[GET_BY_CLASS]);
			pStmt.setString(1, toEn(sclass));
			rs = pStmt.executeQuery();
			
			rs.last();
			list = new Student[rs.getRow()];
			rs.beforeFirst();
			
			int idx = 0;
			while(rs.next()) {
				list[idx] = rowMapping(rs);
				idx++;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs);
			close(pStmt);
		}
		return list;
	}
	
	//delete
	public int delete(Connection con, int sid) {
		int result = 0;
		PreparedStatement pStmt = null;
		
		try {
			pStmt = con.prepareStatement(SQLS[DELETE]);
			pStmt.setInt(1, sid);
			result = pStmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		return result;
	}
	
	//update
	public int update(Connection con, Student student) {
		int result = 0;
		PreparedStatement pStmt = null;
		
		try {
			pStmt = con.prepareStatement(SQLS[UPDATE]);
			pStmt.setString(1, toEn(student.getStel()));
			pStmt.setInt(2, student.getSgrade());
			pStmt.setString(3, toEn(student.getSclass()));
			pStmt.setInt(4, student.getSid());
			result = pStmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			close(pStmt);
		}
		
		return result;
	}
	
	//rowMapping
	private Student rowMapping(ResultSet rs) throws SQLException {
		int sid = rs.getInt("sid");
		String sname = toKor(rs.getString("sname"));
		String stel = toKor(rs.getString("stel"));
		int sgrade = rs.getInt("sgrade");
		String sclass = toKor(rs.getString("sclass"));
		
		return new Student(sid, sname, stel, sgrade, sclass);
	}
	
	//close
	private void close(Statement stmt) {
		try {
			stmt.close();
		} catch(Exception e) {}
	}
	private void close(ResultSet rs) {
		try {
			rs.close();
		} catch(Exception e) {}
	}
	
	//toEn
	private String toEn(String str) {
		String en = null;
		try {
			en = new String(str.getBytes("utf-8"),"8859_1");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return en;
	}
	//toKor
	private String toKor(String str) {
		String kor = null;
		try {
			kor = new String(str.getBytes("8859_1"),"utf-8");
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return kor;
	}
}
