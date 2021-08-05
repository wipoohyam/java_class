package kr.ac.green;
import java.io.Serializable;
import java.util.Vector;

public class Room implements Serializable {
	private static final long serialVersionUID = 1;
	/*
		-roomId : int
		-roomName : String
		-Vector<User> chatters
		-roomMaster : User
		-isPrivateRoom : boolean
		-roomPw : String
		-roomCapacity : int
		-roomCurrentPeople : int
		
		<<<< roomId >>>>
		99 : 로그인 화면
		0 : 대기실
		1~30 : 채팅방
	*/
	private int roomId;
	private String roomName;
	private User roomMaster;
	private String roomPw;
	private int roomCurrentPeople;
	private int roomCapacity;
	private Vector<User> chatters;
	
	//생성자가 새로운 채팅방 생성밖에없을경우 currentPeople = 1, Chatters에 roomMaster만 넣는 방법 고려
	public Room(int roomId, String roomName, int currentPeople, int roomCapacity, Vector<User> chatters) {
		//대기실 생성 생성자
		setRoomId(roomId);
		setRoomName(roomName);
		setRoomMaster(null);
		setRoomPw("");
		setRoomCapacity(roomCapacity);
		this.chatters = chatters;
	}
	//roomMaster를 지정하고 chatter에 넣는다.
	public Room(int roomId, String roomName, User roomMaster, String roomPw, int currentPeople, int capacity, Vector<User> chatters) {
		//서버에서 쓸 생성자
		setRoomId(roomId);
		setRoomName(roomName);
		setRoomMaster(roomMaster);
		setRoomPw(roomPw);
		chatters = new Vector<>();
		chatters.add(roomMaster);
		setRoomCurrentPeople(chatters.size());
		setRoomCapacity(capacity);
	}
	public Room(String roomName, User roomMaster, String roomPw, int capacity) {
		//클라이언트가 쓸 생성자
		setRoomName(roomName);
		setRoomMaster(roomMaster);
		setRoomPw(roomPw);
		chatters = new Vector<>();
		setRoomCurrentPeople(chatters.size());
		setRoomCapacity(capacity);
	}
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public User getRoomMaster() {
		return roomMaster;
	}

	public void setRoomMaster(User roomMaster) {
		this.roomMaster = roomMaster;
	}


	public String getRoomPw() {
		return roomPw;
	}

	public void setRoomPw(String roomPw) {
		this.roomPw = roomPw;
	}

	public int getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(int roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public int getRoomCurrentPeople() {
		return chatters.size();
	}

	public void setRoomCurrentPeople(int roomCurrentPeople) {
		this.roomCurrentPeople = roomCurrentPeople;
	}

	public Vector<User> getChatters() {
		return chatters;
	}

	public void setChatters(Vector<User> chatters) {
		this.chatters = chatters;
	}
	public void addChatter(User user) {
		if(roomCurrentPeople < roomCapacity) {
			chatters.add(user);
			roomCurrentPeople = chatters.size();
			System.out.println("roomId("+roomId+") : "+user.getNick()+" is added");
		}
	}
	public void removeChatter(User user) {
		int idx = -1;
		System.out.println("idx="+chatters.indexOf(user.getNick()));
		if ((idx = chatters.indexOf(user)) != -1) {
			chatters.remove(idx);
			roomCurrentPeople = chatters.size();
			System.out.println("roomId("+roomId+") : "+user.getNick()+" is removed");
		}
	}
	@Override
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Room)) {
			return false;
		}
		Room r = (Room)o;
		return (roomId == r.getRoomId());
	}
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomName=" + roomName + ", roomMaster=" + roomMaster +  ", roomPw=" + roomPw + ", roomCapacity=" + roomCapacity + ", roomCurrentPeople="
				+ roomCurrentPeople + ", chatters=" + chatters + "]";
	}
	
}
