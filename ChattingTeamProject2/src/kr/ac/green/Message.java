package kr.ac.green;
import java.io.Serializable;

public class Message implements Serializable{
	private static final long serialVersionUID = 1;
	/*
	 * 	-msgTalker : String
		-msg : String
		-roomId : int
	 * */
	
	private int icon;
	private String msgTalker;
	private String msgListener;
	private String msg;
	private int roomId;
	
	//broadcast
	public Message(int icon, String msgTalker, String msg, int roomId) {
		setIcon(icon);
		setMsgTalker(msgTalker);
		setMsg(msg);
		setRoomId(roomId);
	}
	//whisper
	public Message(int icon, String msgTalker, String msgListener, String msg, int roomId) {
		setIcon(icon);
		setMsgTalker(msgTalker);
		setMsgListener(msgListener);
		setMsg(msg);
		setRoomId(roomId);
	}
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getMsgTalker() {
		return msgTalker;
	}

	public void setMsgTalker(String msgTalker) {
		this.msgTalker = msgTalker;
	}
	
	public String getMsgListener() {
		return msgListener;
	}
	public void setMsgListener(String msgListener) {
		this.msgListener = msgListener;
	}
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	
	@Override
	public String toString() {
		return "Message [msgTalker=" + msgTalker + ", msgListener=" + msgListener + ", msg=" + msg + ", roomId="
				+ roomId + "]";
	}
	
	
}
