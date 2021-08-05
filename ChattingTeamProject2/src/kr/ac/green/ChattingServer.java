package kr.ac.green;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class ChattingServer{
	private Vector<User> userList;
	private HashMap<Integer, Room> roomList;
	public ChattingServer() {
		userList =  new Vector<>();
		roomList = new HashMap<>();
		Vector<User> chatters = new Vector<>();
		//���� : ����  �ִ��ο� = 30
		Room waitingRoom = new Room(0, "����", 0, 30, chatters);
		roomList.put(0, waitingRoom);
		
		try {
			ServerSocket server = new ServerSocket(10001);
			System.out.println("���Ӵ����");
			HashMap<String, ObjectOutputStream> hm = new HashMap<>();
			while(true) {
				Socket sock = server.accept();
				System.out.println("accepted");
				ChattingThread chThread = new ChattingThread(sock, hm, userList, roomList);
				chThread.start();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new ChattingServer();
	}
}
