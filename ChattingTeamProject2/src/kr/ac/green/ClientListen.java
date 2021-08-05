package kr.ac.green;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClientListen implements Runnable {
	private final static int WAITING_ROOM = 0;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket sock;
	private PData pData;
	private int pCode;
	private User myUser;
	private HashMap<Integer, Room> roomList;
	private Vector<User> chatters;

	private WaitingRoomUI waitingroomUI;
	private ChatFrame chatframeUI;
	private RoomInfoInputFrame roominfoUI;
	private PwInputFrame pwinputframeUI;
	private InviteFrame inviteframeUI;
	private InviteConfirm inviteconfirmUI;

	public ClientListen(WaitingRoomUI wru,ChatFrame cf,RoomInfoInputFrame nr,PwInputFrame pif, InviteFrame inviteframeUI,InviteConfirm ic, Socket sock, ObjectOutputStream oos, ObjectInputStream ois,User myUser) {
		this.waitingroomUI = wru;
		this.chatframeUI = cf;
		this.roominfoUI = nr;
		this.pwinputframeUI = pif;
		this.inviteframeUI = inviteframeUI;
		this.inviteconfirmUI = ic;
		this.oos = oos;
		this.ois = ois;
		this.sock = sock;
		this.myUser = myUser;
		setIcon();
	}

	private void setMyUser(User u) {
		myUser = u;
		waitingroomUI.setMyUser(u);
		chatframeUI.setMyUser(u);
		waitingroomUI.setMyUser(u);
		chatframeUI.setMyUser(u);
		roominfoUI.setMyUser(u);
		pwinputframeUI.setMyUser(u);
		inviteframeUI.setMyUser(u);
		inviteconfirmUI.setMyUser(u);
	}
	private void setIcon() {
		Image icon = Toolkit.getDefaultToolkit().getImage("img/icon.png");
		
		waitingroomUI.setIconImage(icon);
		chatframeUI.setIconImage(icon);
		waitingroomUI.setIconImage(icon);
		chatframeUI.setIconImage(icon);
		roominfoUI.setIconImage(icon);
		pwinputframeUI.setIconImage(icon);
		inviteframeUI.setIconImage(icon);
		inviteconfirmUI.setIconImage(icon);
	}
	@Override
	public void run() {
		try {
			Object o = null;
			while ((o = ois.readObject()) != null) {
				pData = (PData) o;
				pCode = pData.getPCode();
				if (pCode == Protocol.BROADCAST_MSG) {
					Message message = (Message) pData.getPObject();
//					wru.pnlChatDisplay.setAlignmentY(TOP_ALIGNMENT);
					if (message.getMsgTalker().equals(myUser.getNick())) {
						PnlMyMsg myMsg = new PnlMyMsg(message);
//						myMsg.setAlignmentY(TOP_ALIGNMENT);
						if (message.getRoomId() == 0) {
							waitingroomUI.pnlChatDisplay.add(myMsg);
							waitingroomUI.pnlChatDisplay.add(waitingroomUI.filler);
							
						} else {
							chatframeUI.pnlChatDisplay.add(myMsg);
							chatframeUI.pnlChatDisplay.add(chatframeUI.filler);
						}

					} else {
						if (message.getRoomId() == 0) {
							waitingroomUI.pnlChatDisplay.add(new PnlOthersMsg(message));
						} else {
							chatframeUI.pnlChatDisplay.add(new PnlOthersMsg(message));
						}
					}
					if (message.getRoomId() == 0) {
						waitingroomUI.pnlChatDisplay.add(waitingroomUI.filler);
						waitingroomUI.scrollToBottom();
						waitingroomUI.pnlChatDisplay.updateUI();
						waitingroomUI.scrollToBottom();
					} else {
						chatframeUI.pnlChatDisplay.add(chatframeUI.filler);
						chatframeUI.scrollToBottom();
						chatframeUI.pnlChatDisplay.updateUI();
						chatframeUI.scrollToBottom();
					}

				} else if (pCode == Protocol.WHISPER_MSG) {
					Message message = (Message) pData.getPObject();
					if (message.getMsgTalker().equals(myUser.getNick())) {
						PnlMyMsg myMsg = new PnlMyMsg(message);
						if (message.getRoomId() == 0) {
							waitingroomUI.pnlChatDisplay.add(myMsg);
							waitingroomUI.pnlChatDisplay.add(waitingroomUI.filler);
						} else {
							chatframeUI.pnlChatDisplay.add(myMsg);
							chatframeUI.pnlChatDisplay.add(chatframeUI.filler);
						}
					}else {
						PnlOthersMsg othersMsg = new PnlOthersMsg(message);
						if (message.getRoomId() == 0) {
							waitingroomUI.pnlChatDisplay.add(othersMsg);
							waitingroomUI.pnlChatDisplay.add(waitingroomUI.filler);
						} else {
							chatframeUI.pnlChatDisplay.add(othersMsg);
							chatframeUI.pnlChatDisplay.add(chatframeUI.filler);
						}
					}
					if (message.getRoomId() == 0) {
						waitingroomUI.pnlChatDisplay.add(waitingroomUI.filler);
						waitingroomUI.pnlChatDisplay.updateUI();
					} else {
						chatframeUI.pnlChatDisplay.add(chatframeUI.filler);
						chatframeUI.pnlChatDisplay.updateUI();
					}
				}  else if (pCode == Protocol.UPDATE_WAITING_AREA) {
					// 대기실 방+유저 업데이트
					roomList = (HashMap<Integer, Room>) pData.getPObject();
					chatters = roomList.get(WAITING_ROOM).getChatters();
					waitingroomUI.setChatters(chatters);
					waitingroomUI.setRoomList(roomList);
					waitingroomUI.roomListUpdate();
					waitingroomUI.userListUpdate();
				} else if (pCode == Protocol.UPDATE_WAITING_USER) {
					// 대기실 유저리스트 업데이트
					waitingroomUI.setChatters((Vector<User>) pData.getPObject());
					waitingroomUI.userListUpdate();
				} else if(pCode == Protocol.UPDATE_ROOM_LIST) {
					roomList = (HashMap<Integer, Room>) pData.getPObject();
					waitingroomUI.setRoomList(roomList);
					waitingroomUI.roomListUpdate();
				} else if (pCode == Protocol.ENTER_ROOM_ACCEPTED) {
					// 방 입장 허가
					waitingroomUI.setVisible(false);
					setMyUser((User) pData.getPObjects(1));
					chatframeUI.setRoomInfo((Room) pData.getPObjects(0));
					chatframeUI.setVisible(true);

				} else if (pCode == Protocol.ENTER_ROOM_UNACCEPTED) {
					ChattingUtils.showDialog(waitingroomUI, "해당 방으로 입장할 수 없습니다.", "방 인원초과");
				} else if (pCode == Protocol.NEED_PASSWORD) {
					
				} else if (pCode == Protocol.NEW_ROOM_AVAILABLE) {
					Room newRoom = (Room) pData.getPObjects(0);
					setMyUser((User) pData.getPObjects(1));
					chatframeUI.setRoomInfo(newRoom);
					roominfoUI.setVisible(false);
					roominfoUI.setTfBlank();
					waitingroomUI.setVisible(false);
					chatframeUI.setVisible(true);
				} else if (pCode == Protocol.NEW_ROOM_UNAVAILABLE) {
					ChattingUtils.showDialog(roominfoUI, "만들 수 있는 방 최대 개수를 초과했습니다.", "방 개수 제한");
				} else if (pCode == Protocol.UPDATE_CHATTING_ROOM) {
					Room cRoom = (Room) pData.getPObject();
					System.out.println("myuserbefore:"+myUser);
					setMyUser(cRoom.getChatters().get(cRoom.getChatters().indexOf(myUser)));
					System.out.println("myuserafter:"+myUser);
					chatframeUI.setRoomInfo(cRoom);
					
				} else if (pCode == Protocol.SHOW_WAITING_ROOM_USER_LIST) {
					Room roomInviteFrom = (Room) pData.getPObjects(0);
					Vector<User> user = (Vector<User>) pData.getPObjects(1);
					inviteframeUI.setUserList(user);
					inviteframeUI.setRoom(roomInviteFrom);
					inviteframeUI.addListeners();
					inviteframeUI.setVisible(true);
				} else if (pCode == Protocol.REQUEST_INVITE_USER) {
					Room roomInviteFrom = (Room) pData.getPObjects(0);
					User userInviteFrom = (User) pData.getPObjects(1);
					inviteconfirmUI.setFrameInfo(userInviteFrom, roomInviteFrom);
					inviteconfirmUI.setVisible(true);
				} else if (pCode == Protocol.INVITATION_AGREED) {
					waitingroomUI.setVisible(false);
					setMyUser((User) pData.getPObjects(1));
					chatframeUI.setRoomInfo((Room) pData.getPObjects(0));
					chatframeUI.setVisible(true);
				} else if (pCode == Protocol.INVITATION_DISAGREED) {
					User u = (User) pData.getPObject();
					ChattingUtils.showDialog(chatframeUI, u.getNick()+"님이 초대를 거절했습니다.", "초대 거절");
				} else if (pCode == Protocol.EDIT_ROOM_ACCEPTED) {
					chatframeUI.setRoomInfo((Room)pData.getPObject());
					roominfoUI.setVisible(false);
					roominfoUI.setTfBlank();
				} else if (pCode == Protocol.EDIT_ROOM_UNACCEPTED) {
					JOptionPane.showMessageDialog(roominfoUI, "방을 수정할 수 없습니다.", "방 수정 불가",
							JOptionPane.INFORMATION_MESSAGE);
				} else if (pCode == Protocol.YOU_ARE_BANNED) {
					roomList = (HashMap<Integer, Room>)pData.getPObjects(1);
					chatters = roomList.get(WAITING_ROOM).getChatters();
					setMyUser(chatters.get(chatters.indexOf(myUser)));
					waitingroomUI.setChatters(chatters);
					waitingroomUI.setRoomList(roomList);
					
					waitingroomUI.roomListUpdate();
					waitingroomUI.userListUpdate();
					
					chatframeUI.setVisible(false);
					waitingroomUI.setVisible(true);
					
					Room bannedRoom = (Room) pData.getPObjects(0);
					String msg = bannedRoom.getRoomName() + " 방에서 강퇴되었습니다.";
					notice(msg, WAITING_ROOM);
				} else if (pCode == Protocol.USER_BANNED) {
					Room cRoom = (Room) pData.getPObjects(1);
					User bannedUser = (User) pData.getPObjects(0);
					
					setMyUser(cRoom.getChatters().get(cRoom.getChatters().indexOf(myUser)));
					chatframeUI.setRoomInfo(cRoom);
					String msg = bannedUser.getNick() + " 님이 강제 퇴장되었습니다.";
					notice(msg, cRoom.getRoomId());
				} else if (pCode == Protocol.EXIT_ROOM_ACCEPTED) {
					roomList = (HashMap<Integer, Room>)pData.getPObject();
					chatters = roomList.get(WAITING_ROOM).getChatters();
					setMyUser(chatters.get(chatters.indexOf(myUser)));
					waitingroomUI.setChatters(chatters);
					waitingroomUI.setRoomList(roomList);
					
					waitingroomUI.roomListUpdate();
					waitingroomUI.userListUpdate();
					
					chatframeUI.setVisible(false);
					waitingroomUI.setVisible(true);
					
				} else if (pCode == Protocol.EXIT_ACCEPTED) {
					exit();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			exit();
		}
	}
	private void disposeAll(JFrame...frames) {
		for(JFrame frame : frames) {
			frame.dispose();
		}
	}
	private void notice(String msg, int roomId) {
		Message notice = new Message(11, "알림", msg, roomId);
		if(roomId == 0) {
			waitingroomUI.pnlChatDisplay.add(new PnlOthersMsg(notice));
			waitingroomUI.pnlChatDisplay.add(chatframeUI.filler);
			waitingroomUI.pnlChatDisplay.updateUI();
		}else {
			chatframeUI.pnlChatDisplay.add(new PnlOthersMsg(notice));
			chatframeUI.pnlChatDisplay.add(chatframeUI.filler);
			chatframeUI.pnlChatDisplay.updateUI();
		}
	}
	private void exit() {
		try {
			if(oos != null) {
				oos.close();
			}
		} catch(Exception e) {}
		try {
			if(ois!=null) {
				ois.close();
			}
		} catch(Exception e) {}
		try {
			if(sock!=null) {
				sock.close();
			}
		} catch(Exception e) {}
		System.out.println("<<< "+myUser.getNick() + " : 접속 종료 >>>");
		System.exit(0);
	}
}
