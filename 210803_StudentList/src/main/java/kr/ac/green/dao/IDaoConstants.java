package kr.ac.green.dao;

public interface IDaoConstants {
	String DRIVER = "com.mysql.jdbc.Driver";
	String DBURL = "jdbc:mysql://localhost:3306/test?&useSSL=false";
	String UID = "root";
	String PW = "1234";
	
	String[] SQLS = {
		"INSERT INTO students(sname, stel, sgrade, sclass) VALUES (?,?,?,?)",
		"SELECT * FROM students ORDER BY sid DESC",
		"SELECT * FROM students WHERE sid=?",
		"SELECT * FROM students WHERE sname LIKE ?",
		"SELECT * FROM students WHERE sclass=?",
		"DELETE FROM students WHERE sid=?",
		"UPDATE students SET stel=?, sgrade=?, sclass=? WHERE sid=?"
	};
	
	int INSERT = 0;
	int GETALL = 1;
	int GET_BY_ID = 2;
	int GET_BY_NAME = 3;
	int GET_BY_CLASS = 4;
	int DELETE = 5;
	int UPDATE = 6;
}
