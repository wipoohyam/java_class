package kr.ac.green;
import java.io.Serializable;
import java.util.Random;

public class User implements Serializable{
	private static final long serialVersionUID = 1;
	/*
		-myId : String
		-myIp : String
		-currentPlace : int
		-isRoomMaster : boolean
	*/
	
	//icon = 0 이면 강아지, icon = 1 이면 사자.. 이런식
	private int icon;
	private String nick;
	private int currentPlace;
	private boolean roomMaster;
	public User(String nick) {
		this.nick = nick;
	}
	public User(String nick, int currentPlace, boolean roomMaster) {
		Random r = new Random();
		setIcon(r.nextInt(10)+1);
		setNick(nick);
		setCurrentPlace(currentPlace);
		setRoomMaster(roomMaster);
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public int getCurrentPlace() {
		return currentPlace;
	}

	public void setCurrentPlace(int currentPlace) {
		this.currentPlace = currentPlace;
	}

	public boolean isRoomMaster() {
		return roomMaster;
	}

	public void setRoomMaster(boolean roomMaster) {
		this.roomMaster = roomMaster;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof User)) {
			return false;
		}
		User u = (User)o;
		return nick.equals(u.getNick());
	}
	@Override
	public String toString() {
		return "User [icon=" + icon + ", nick=" + nick + ", currentPlace=" + currentPlace + ", roomMaster=" + roomMaster
				+ "]";
	}
	
}
