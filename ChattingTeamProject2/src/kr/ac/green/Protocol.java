package kr.ac.green;

public final class Protocol {
	private static final long serialVersionUID = 1;
	//client - �α���
	public static final int CHECK_NICKNAME = 100;
	
	
	//client - ����
	public static final int ENTER_ROOM = 210;
	public static final int SEND_PASSWORD = 213;
	
	//client - ������˾�
	public static final int NEW_ROOM = 230;

	
	//client - ä��
	public static final int INVITE_USER = 310;
	public static final int SELECT_INVITE_USER = 311;
	public static final int INVITATION_DECISION = 312;
	//client - ������� 
	public static final int EDIT_ROOM = 350;
	public static final int DELEGATE_ROOM_MASTER = 352;
	public static final int BAN_USER = 353;
	public static final int EXIT_ROOM = 399;
	
	
	//client - �޽���
	public static final int SEND_BROADCAST = 900;
	public static final int SEND_WHISPER = 901;
	public static final int EXIT_PROGRAM = 999;
	
	
	
	
	//server - �α���
	public static final int NICK_OK = 1100;
	public static final int NICK_NOT_OK = 1101;
	public static final int SERVER_OCCUPIED = 1102;
	
	
	//server - ����
	public static final int UPDATE_WAITING_AREA = 1200;
	public static final int UPDATE_WAITING_USER = 1201;
	public static final int UPDATE_ROOM_LIST = 1202;
	public static final int ENTER_ROOM_ACCEPTED = 1210;
	public static final int ENTER_ROOM_UNACCEPTED = 1211;
	public static final int NEED_PASSWORD = 1212;
	//server - ������˾�
	public static final int NEW_ROOM_AVAILABLE = 1230;
	public static final int NEW_ROOM_UNAVAILABLE = 1231;
	
	
	//server - ä�ù�
	public static final int UPDATE_CHATTING_ROOM = 1300;
	public static final int SHOW_WAITING_ROOM_USER_LIST = 1310;
	public static final int REQUEST_INVITE_USER = 1311;
	public static final int INVITATION_AGREED = 1312;
	public static final int INVITATION_DISAGREED = 1313;
	//server - ������� �ൿ
	public static final int EDIT_ROOM_ACCEPTED = 1350;
	public static final int EDIT_ROOM_UNACCEPTED = 1351;
	public static final int USER_BANNED = 1353;
	public static final int YOU_ARE_BANNED = 1354;
	public static final int EXIT_ROOM_ACCEPTED = 1399;
	
	
	//server - �޽���
	public static final int BROADCAST_MSG = 1900;
	public static final int WHISPER_MSG = 1901;
	
	public static final int EXIT_ACCEPTED = 1999;
}
