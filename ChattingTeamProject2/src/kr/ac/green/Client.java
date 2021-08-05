package kr.ac.green;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private Socket sock;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;;
	
	private WaitingRoomUI waitingroomUI;
	private ChatFrame chatframeUI;
	private RoomInfoInputFrame roomInfoUI;
	private PwInputFrame pwinputframeUI;
	private InviteFrame inviteframeUI;
	private InviteConfirm inviteconfirmUI;
	private User myUser;
	
	public Client(WaitingRoomUI wru,ChatFrame cf,RoomInfoInputFrame riif,PwInputFrame pif, InviteFrame ifUI,InviteConfirm ic, Socket sock, ObjectOutputStream oos, ObjectInputStream ois,User myUser) {
		this.waitingroomUI = wru;
		this.chatframeUI = cf;
		this.roomInfoUI = riif;
		this.pwinputframeUI = pif;
		this.inviteframeUI = ifUI;
		this.inviteconfirmUI = ic;
		this.sock = sock;
		this.oos = oos;
		this.ois = ois;
		this.myUser = myUser;
		
		Runnable r = new ClientListen(waitingroomUI,chatframeUI,roomInfoUI,pwinputframeUI,inviteframeUI,inviteconfirmUI, sock, oos, ois, myUser); 
		Thread t = new Thread(r);
		t.start();
	}
	
}
