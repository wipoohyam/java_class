package kr.ac.green.dto;

public class Student {
	//field
	private int sid;
	private String sname;
	private String stel;
	private int sgrade;
	private String sclass;
	
	//constructor
	public Student() {}
	//insert
	public Student(String sname, String stel, int sgrade, String sclass) {
		super();
		this.sname = sname;
		this.stel = stel;
		this.sgrade = sgrade;
		this.sclass = sclass;
	}
	//update
	//id로 수정할 row 정하고, (tel,grade,class만 수정 가능)
	public Student(int sid, String stel, int sgrade, String sclass) {
		super();
		this.sid = sid;
		this.stel = stel;
		this.sgrade = sgrade;
		this.sclass = sclass;
	}
	//select
	public Student(int sid, String sname, String stel, int sgrade, String sclass) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.stel = stel;
		this.sgrade = sgrade;
		this.sclass = sclass;
	}
	
	//getter & setter
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getStel() {
		return stel;
	}
	public void setStel(String stel) {
		this.stel = stel;
	}
	public int getSgrade() {
		return sgrade;
	}
	public void setSgrade(int sgrade) {
		this.sgrade = sgrade;
	}
	public String getSclass() {
		return sclass;
	}
	public void setSclass(String sclass) {
		this.sclass = sclass;
	}
}
