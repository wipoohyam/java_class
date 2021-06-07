package kr.ac.green.Login;

import java.io.Serializable;

class User implements Serializable{
	private String uid;
	private String upw;
	private String uname;
	private String unick;
	private String ugender;
	
	public User(String uid) {
		setUid(uid);
	}

	public User(
		String uid, String upw, String uname, 
		String unick, String ugender) {

		setUid(uid);
		setUpw(upw);
		setUname(uname);
		setUnick(unick);
		setUgender(ugender);
	}

	public String getUid() {
		return uid;
	}
	public String getUpw() {
		return upw;
	}
	public String getUname() {
		return uname;
	}
	public String getUnick() {
		return unick;
	}
	public String getUgender() {
		return ugender;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setUpw(String upw) {
		this.upw = upw;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public void setUnick(String unick) {
		this.unick = unick;
	}
	public void setUgender(String ugender) {
		this.ugender = ugender;
	}

	@Override
	public String toString() {
		String info = "<< " + uid + " 님의 회원정보 >>\n";
		info += "- name : " + uname + "\n";
		info += "- password : " + "????" + "\n";
		info += "- nickname : " + unick + "\n";
		info += "- gender : " + ugender + "\n";

		return info;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User)) {
			return false;
		}
		User user = (User)o;
		return uid.equals(user.getUid());
	}
}	
