package kr.ac.green;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

public class ChattingThread extends Thread {
	private static final int LOGIN = 99;
	private static final int WAITING_ROOM = 0;

	private Socket sock;
	
	private User myUser;
	private Vector<User> userList;
	private HashMap<Integer, Room> roomList;
	private PData pData;
	private int pCode;
	private Object pObject;
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private HashMap<String, ObjectOutputStream> hm;

	public ChattingThread(Socket sSocket, HashMap<String, ObjectOutputStream> serverHm, Vector<User> serverUserList,
			HashMap<Integer, Room> serverRoomList) {
		this.sock = sSocket;
		this.hm = serverHm;
		this.userList = serverUserList;
		this.roomList = serverRoomList;
		try {
			ois = new ObjectInputStream(sock.getInputStream());
			oos = new ObjectOutputStream(sock.getOutputStream());
			
			Object data = null;
			data = ois.readObject();
			pData = (PData) data;
			pCode = pData.getPCode();
			pObject = pData.getPObject();
			
			if (pCode == Protocol.CHECK_NICKNAME) {
				String nickNeedCheck = (String) pObject;
				if (checkNick(nickNeedCheck)) {
					if (userList.size() > 30) {
						// 최대인원 초과
						oos.writeObject(new PData(Protocol.SERVER_OCCUPIED));
						oos.flush();
						oos.reset();
					} else {
						synchronized (userList) {
							synchronized(hm) {
								hm.put(nickNeedCheck, oos);
							}
							myUser = new User(nickNeedCheck, WAITING_ROOM, false);
							userList.add(myUser);
							Room waitingroom = roomList.get(WAITING_ROOM);
							waitingroom.addChatter(myUser);
							roomList.put(WAITING_ROOM, waitingroom);
							Object[] o = new Object[3];
							o[0] = userList;
							o[1] = roomList;
							o[2] = myUser;
							oos.writeObject(new PData(Protocol.NICK_OK, o));
							oos.flush();
							oos.reset();
							
							updateWaitingUsers();
						}
					}
				} else {
					// 1101 닉네임 중복
					oos.writeObject(new PData(Protocol.NICK_NOT_OK));
					oos.flush();
					oos.reset();
				}
			} 
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			Object data = null;
			while((data = ois.readObject()) != null) {
				pData = (PData) data;
				pCode = pData.getPCode();
				if (pCode == Protocol.ENTER_ROOM) {
					System.out.println("READ : ENTER_ROOM");
					int roomId = (Integer) pData.getPObject();
					Room room = roomList.get(roomId);
					if(room.getRoomCurrentPeople() < room.getRoomCapacity()) {
						myUser.setCurrentPlace(roomId);
						room.addChatter(myUser);
						roomList.put(roomId, room);
						Room waitingroom = roomList.get(WAITING_ROOM);
						waitingroom.removeChatter(myUser);
						roomList.put(WAITING_ROOM, waitingroom);
						
						updateWaitingArea();
						updateChattingRoom(room);
						Object[] o2 = new Object[2];
						o2[0] = room;
						o2[1] = myUser;
						System.out.println("myUser:"+myUser+",roomId:"+roomId);
						pData = new PData(Protocol.ENTER_ROOM_ACCEPTED, o2);
						sendDataToMyUser(pData);
						System.out.println("WRITE : ENTER_ROOM_ACCEPTED");
					}else {
						pData = new PData(Protocol.ENTER_ROOM_UNACCEPTED);
						sendDataToMyUser(pData);
						System.out.println("WRITE : ENTER_ROOM_UNACCEPTED");
					}
				}else if(pCode == Protocol.SEND_PASSWORD){
					int roomId = (Integer) pData.getPObjects(0);
					String pw = (String) pData.getPObjects(1);
					Room room = roomList.get(roomId);
					try {
						if(room.getRoomPw().equals(pw)) {
							if(room.getRoomCurrentPeople() < room.getRoomCapacity()) {
								myUser.setCurrentPlace(roomId);
								room.addChatter(myUser);
								roomList.put(roomId, room);
								Room waitingroom = roomList.get(WAITING_ROOM);
								waitingroom.removeChatter(myUser);
								roomList.put(WAITING_ROOM, waitingroom);
								
								updateWaitingArea();
								updateChattingRoom(room);
								Object[] o2 = new Object[2];
								o2[0] = room;
								o2[1] = myUser;
								System.out.println("myUser:"+myUser+",roomId:"+roomId);
								pData = new PData(Protocol.ENTER_ROOM_ACCEPTED, o2);
								sendDataToMyUser(pData);
								System.out.println("WRITE : ENTER_ROOM_ACCEPTED");
							}else {
								pData = new PData(Protocol.ENTER_ROOM_UNACCEPTED);
								sendDataToMyUser(pData);
								System.out.println("WRITE : ENTER_ROOM_UNACCEPTED");
							}
						}else {
							pData = new PData(Protocol.ENTER_ROOM_UNACCEPTED);
							sendDataToMyUser(pData);
							System.out.println("WRITE : ENTER_ROOM_UNACCEPTED");
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
					
					
					
				}else if (pCode == Protocol.NEW_ROOM) {
					System.out.println("READ:NEW_ROOM");
					/*
						1. 사용자와 사용자가 만드려는 방 받아서 방에 번호 부여(새 방 완성), 사용자 현재위치 새 방으로 갱신
						2. 사용자 - 대기실 chatters에서 빼고 새 방 chatters에 추가(사용자 목록 갱신 완료)
						3. 서버 roomList에 새 방 추가, (2)에서 갱신된 대기실 put해서 수정
					*/
					if(roomList.size() <30) {
						// 1. ~
						Room newRoom = (Room) pData.getPObject();
						int roomId = 0;
						//빈 방번호가 나올때까지 ++
						while(roomList.get(roomId) != null) {
							roomId++;
						}
						newRoom.setRoomId(roomId);		//방완성 
						myUser.setCurrentPlace(roomId);
						myUser.setRoomMaster(true);		//유저완
						newRoom.getChatters().set(newRoom.getChatters().indexOf(myUser), myUser);
						userList.set(userList.indexOf(myUser),myUser);
						// ~ 1.
						
						// 2. ~
						Room waitingRoom = roomList.get(WAITING_ROOM);
//						System.out.print("before remove");
//						for(User u : waitingRoom.getChatters()) {
//							System.out.print(u.getNick()+",");
//						}
//						System.out.println();
						waitingRoom.removeChatter(myUser);
						System.out.print("after remove");
						for(User u : waitingRoom.getChatters()) {
							System.out.print(u.getNick()+",");
						}
						System.out.println();
						
						//userList 갱신
						roomList.put(roomId, newRoom);
						roomList.put(WAITING_ROOM, waitingRoom);
						
						Object[] o2 = {newRoom, myUser};
						pData = new PData(Protocol.NEW_ROOM_AVAILABLE, o2);
						synchronized(hm) {
							oos = hm.get(myUser.getNick());
							oos.writeObject(pData);
							oos.flush();
							oos.reset();
						}
						updateWaitingArea();
					}else {
						pData = new PData(Protocol.NEW_ROOM_UNAVAILABLE);
						sendDataToMyUser(pData);
					}
				} else if(pCode == Protocol.INVITE_USER) {
					Vector<User> chatters = roomList.get(0).getChatters();
					Room room = roomList.get(myUser.getCurrentPlace());
					Object o[] = new Object[2];
					o[0] = room;
					o[1] = chatters;
					if(chatters != null) {
						synchronized (hm) {
							oos = hm.get(myUser.getNick());
							oos.writeObject(new PData(Protocol.SHOW_WAITING_ROOM_USER_LIST, o));
							oos.flush();
							oos.reset();
						}
					}
				} else if (pCode == Protocol.SELECT_INVITE_USER) {
					User userInviteFrom = (User)pData.getPObjects(0);
					Room roomInviteFrom = (Room)pData.getPObjects(2);
					Vector<User> usersInviteTo = (Vector<User>) pData.getPObjects(1);
					Object[] o = new Object[2];
					o[0] = roomInviteFrom;
					o[1] = userInviteFrom;
					synchronized (hm) {
						for(User u : usersInviteTo) {
							oos = hm.get(u.getNick());
							oos.writeObject(new PData(Protocol.REQUEST_INVITE_USER, o));
							oos.flush();
							oos.reset();
						}
					}
					 
				} else if (pCode == Protocol.INVITATION_DECISION) {
					//myUser의 결정이다.
					boolean decision = (boolean) pData.getPObjects(0);
					User userInviteFrom = (User) pData.getPObjects(1);
					Room roomInviteFrom = (Room) pData.getPObjects(2);
					//초대수락
					if(decision) {
						//방입장가능 확인 후 방정보 넘긴다
						if(roomInviteFrom.getRoomCurrentPeople() < roomInviteFrom.getRoomCapacity()) {
							myUser.setCurrentPlace(roomInviteFrom.getRoomId());
							roomInviteFrom.addChatter(myUser);
							roomList.put(roomInviteFrom.getRoomId(), roomInviteFrom);
							Room waitingroom = roomList.get(WAITING_ROOM);
							waitingroom.removeChatter(myUser);
							roomList.put(WAITING_ROOM, waitingroom);
							
							updateWaitingArea();
							updateChattingRoom(roomInviteFrom);
							Object[] o2 = new Object[2];
							o2[0] = roomInviteFrom;
							o2[1] = myUser;
							pData = new PData(Protocol.INVITATION_AGREED, o2);
							sendDataToMyUser(pData);
						}else {
							pData = new PData(Protocol.ENTER_ROOM_UNACCEPTED, myUser);
							sendDataToMyUser(pData);
						}
					}else {
						//거절
						synchronized(hm) {
							oos = hm.get(userInviteFrom.getNick());
							oos.writeObject(new PData(Protocol.INVITATION_DISAGREED,myUser));
							oos.flush();
							oos.reset();
						}
						
					}
				} else if (pCode == Protocol.EDIT_ROOM) {
					Room editedRoom = (Room) pData.getPObject();
					int roomId = editedRoom.getRoomId();
					Room originRoom = roomList.get(roomId);
					Vector<User> editedRoomChatters = originRoom.getChatters();
					
					try {
						synchronized(hm) {
							if(editedRoom.getRoomCapacity()<originRoom.getRoomCurrentPeople()) {
								sendDataToMyUser(new PData(Protocol.EDIT_ROOM_UNACCEPTED));
							}else {
								roomList.put(editedRoom.getRoomId(), editedRoom);
								for(User u : editedRoomChatters) {
									oos = hm.get(u.getNick());
									oos.writeObject(new PData(Protocol.EDIT_ROOM_ACCEPTED, editedRoom));
									oos.flush();
									oos.reset();
								}
								updateRoomList();
							}
						}
					} catch(Exception e) {
						e.printStackTrace();
					}
				} else if (pCode == Protocol.DELEGATE_ROOM_MASTER) {
					User delegated = (User)pData.getPObject();
					Room requestedRoom = roomList.get(delegated.getCurrentPlace());
					Vector<User> requestedRoomChatters = requestedRoom.getChatters();
					requestedRoom.setRoomMaster(delegated);
					myUser.setRoomMaster(false);
					delegated.setRoomMaster(true);
					requestedRoomChatters.set(requestedRoomChatters.indexOf(delegated), delegated);
					requestedRoomChatters.set(requestedRoomChatters.indexOf(myUser), myUser);
					requestedRoom.setChatters(requestedRoomChatters);
					
					synchronized(hm) {
						for(User u : requestedRoomChatters) {
							oos = hm.get(u.getNick());
							oos.writeObject(new PData(Protocol.UPDATE_CHATTING_ROOM, requestedRoom));
							oos.flush();
							oos.reset();
						}
					}
				} else if (pCode == Protocol.BAN_USER) {
					User bannedUser = (User) pData.getPObject();
					Room bannedRoom = roomList.get(bannedUser.getCurrentPlace());
					Room waitingRoom = roomList.get(WAITING_ROOM);
					
					bannedRoom.removeChatter(bannedUser);	
					bannedUser.setCurrentPlace(WAITING_ROOM);

					myUser = bannedUser;
					
					roomList.put(bannedRoom.getRoomId(), bannedRoom);

					myUser.setRoomMaster(false);
					userList.set(userList.indexOf(myUser), myUser);	
					waitingRoom.addChatter(myUser);
					roomList.put(WAITING_ROOM, waitingRoom);
					Object[] o1 = {bannedRoom, roomList};
					Object[] o2 = {bannedUser, bannedRoom};
					synchronized(hm) {
						sendDataToMyUser(new PData(Protocol.YOU_ARE_BANNED, o1));
						for(User u : bannedRoom.getChatters()) {
							oos = hm.get(u.getNick());
							oos.writeObject(new PData(Protocol.USER_BANNED, o2));
							oos.flush();
							oos.reset();
						}
						for(User u : waitingRoom.getChatters()) {
							if(!u.getNick().equals(myUser.getNick())) {
								oos = hm.get(u.getNick());
								oos.writeObject(new PData(Protocol.UPDATE_WAITING_AREA, roomList));
								oos.flush();
								oos.reset();
							}
						}
					}
				} else if (pCode == Protocol.EXIT_ROOM) {
					//myUser(exitUser) : 현재위치변경, 방장여부 변경
					//채팅방 : 채팅유저 변경, 방장 변경, 방정보 변경
					//대기실 : 방목록정보 변경, 채팅유저 변경
					User exitUser = (User) pData.getPObject();
					System.out.println("receivedExitUser:" + exitUser);
					Room exitRoom = roomList.get(exitUser.getCurrentPlace());
					Room waitingRoom = roomList.get(WAITING_ROOM);
					
					exitRoom.removeChatter(exitUser);	
					exitUser.setCurrentPlace(WAITING_ROOM);

					myUser = exitUser;
					
					if(exitRoom.getChatters().size() == 0) {
						roomList.remove(exitRoom.getRoomId());
					}else {
						if(myUser.isRoomMaster()) {
							exitRoom.getChatters().get(0).setRoomMaster(true);
							System.out.println("setRoomMaster:"+exitRoom.getChatters().get(0));
							exitRoom.setRoomMaster(exitRoom.getChatters().get(0));
						}
						roomList.put(exitRoom.getRoomId(), exitRoom);
						synchronized(hm) {
						}
					}

					myUser.setRoomMaster(false);
					userList.set(userList.indexOf(exitUser), myUser);	
					waitingRoom.addChatter(myUser);
					roomList.put(WAITING_ROOM, waitingRoom);
					synchronized(hm) {
						sendDataToMyUser(new PData(Protocol.EXIT_ROOM_ACCEPTED, roomList));
						for(User u : exitRoom.getChatters()) {
							oos = hm.get(u.getNick());
							oos.writeObject(new PData(Protocol.UPDATE_CHATTING_ROOM, exitRoom));
							oos.flush();
							oos.reset();
						}
						for(User u : waitingRoom.getChatters()) {
							if(!u.getNick().equals(exitUser.getNick())) {
								oos = hm.get(u.getNick());
								oos.writeObject(new PData(Protocol.UPDATE_WAITING_AREA, roomList));
								oos.flush();
								oos.reset();
							}
						}
					}
					System.out.println("--------------");
					System.out.println("myUser:"+myUser);
					System.out.println("exitRoom:"+exitRoom);
					for(User u : exitRoom.getChatters()) {
						System.out.println(u);
					}
					System.out.println("waitingRoom:"+waitingRoom);
					for(User u : waitingRoom.getChatters()) {
						System.out.println(u);
					}
					System.out.println("--------------");
				} else if (pCode == Protocol.SEND_BROADCAST) {
					Message message = (Message) pData.getPObject();
					int roomId = message.getRoomId();
					Vector<User> chatters = roomList.get(roomId).getChatters();
					for(int i=0; i<chatters.size();i++) {
						synchronized(hm) {
							oos = hm.get(chatters.get(i).getNick());
							pData = new PData(Protocol.BROADCAST_MSG, message);
							oos.writeObject(pData);
							oos.flush();
							oos.reset();
						}
					}
				// 귓속말
				} else if (pCode == Protocol.SEND_WHISPER) {
					Message message = (Message) pData.getPObject();
					synchronized(hm) {
						oos = hm.get(message.getMsgListener());
						pData = new PData(Protocol.WHISPER_MSG, message);
						oos.writeObject(pData);
						oos.flush();
						oos.reset();
					}
					synchronized(hm) {
						oos = hm.get(message.getMsgTalker());
						oos.writeObject(new PData(Protocol.WHISPER_MSG, message));
						oos.flush();
						oos.reset();
					}
				} else if (pCode == Protocol.EXIT_PROGRAM) {
					System.out.println("READ:EXIT_PROGRAM");
					/*
						유저 - hm에서 제거, 전체 유저리스트에서 제거, 있던 방 chatters에서 제거
					*/
					User user = (User) pData.getPObject();

					Room room = roomList.get(user.getCurrentPlace());
					int roomId = room.getRoomId();
					room.removeChatter(myUser);
					if(user.getCurrentPlace() == 0) {
						updateWaitingArea();
					}else {
						updateChattingRoom(roomList.get(user.getCurrentPlace()));
					}
					
					synchronized(hm) {
						oos.writeObject(new PData(Protocol.EXIT_ACCEPTED));
						oos.flush();
						oos.reset();
						synchronized(userList) {
							userList.remove(userList.indexOf(user));
						}
						
						hm.remove(user.getNick());
						
						if(roomId == 0) {
							updateWaitingUsers();
						}else {
							for(User u : roomList.get(roomId).getChatters()) {
								oos = hm.get(u.getNick());
								try {
									oos.writeObject(new PData(Protocol.UPDATE_CHATTING_ROOM, room));
									oos.flush();
									oos.reset();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							updateWaitingArea();
						}
					}
					close();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	private void close() {
		try {
			if(oos != null) {
				oos.close();
			}
		} catch(Exception e) {}
		try {
			if(ois != null) {
				ois.close();
			}
		} catch(Exception e) {}
		try {
			if(sock != null) {
				sock.close();
			}
		} catch(Exception e) {}
	}

	private boolean checkNick(String nickNeedCheck) {
		boolean isOk = false;
		if(nickNeedCheck.length() >0) {
			int idx = userList.indexOf(new User(nickNeedCheck));
			if(idx == -1) {
				isOk = true;
			}
		}
		return isOk;
	}

	private void updateWaitingUsers() {
		synchronized (hm) {
			try {
				Room waitingRoom = roomList.get(WAITING_ROOM);
				Vector<User> waitingUsers = waitingRoom.getChatters();
				for (User u : waitingUsers) {
					String nick = u.getNick();
					if(!nick.equals(myUser.getNick())) {
						oos = hm.get(nick);
						pData = new PData(Protocol.UPDATE_WAITING_USER, waitingUsers);
						oos.writeObject(pData);
						oos.flush();
						oos.reset();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void updateWaitingArea() {
		synchronized (hm) {
			try {
				Room waitingRoom = roomList.get(WAITING_ROOM);
				Vector<User> waitingUsers = waitingRoom.getChatters();
				for (User u : waitingUsers) {
					String nick = u.getNick();
					if(!nick.equals(myUser.getNick())) {
						oos = hm.get(nick);
						pData = new PData(Protocol.UPDATE_WAITING_AREA, roomList);
						oos.writeObject(pData);
						oos.flush();
						oos.reset();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void updateRoomList() {
		synchronized (hm) {
			try {
				Object[] o = new Object[2];
				Room waitingRoom = roomList.get(WAITING_ROOM);
				Vector<User> waitingUsers = waitingRoom.getChatters();
				System.out.print("waitingUsers:");
				for(User u : waitingUsers) {
					System.out.print(u.getNick()+",");
				}
				System.out.println("");
				for (User u : waitingUsers) {
					String nick = u.getNick();
					if(!nick.equals(myUser.getNick())) {
						oos = hm.get(nick);
						pData = new PData(Protocol.UPDATE_ROOM_LIST, roomList);
						oos.writeObject(pData);
						oos.flush();
						oos.reset();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void updateChattingRoom(Room room) {
		synchronized(hm) {
			try {
				Vector<User> roomChatters = room.getChatters();
				for(User u : roomChatters) {
					String nick = u.getNick();
					if(!nick.equals(myUser.getNick())) {
						oos = hm.get(nick);
						pData = new PData(Protocol.UPDATE_CHATTING_ROOM, room);
						oos.writeObject(pData);
						oos.flush();
						oos.reset();
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	// roomId로 Room 받고 그 안에서 유저목록 받아서 유저목록 nick뽑아서 hm으로 oos로 전달?
	private void broadcast(Message message) {
		synchronized (hm) {
			int roomId = message.getRoomId();
			Room destRoom = roomList.get(roomId);
			Vector<User> roomUsers = destRoom.getChatters();
			ObjectOutputStream oos = null;

			try {
				for (int i = 0; i < roomUsers.size(); i++) {
					oos = hm.get(roomUsers.get(i).getNick());
					pData = new PData(Protocol.BROADCAST_MSG, message);
					oos.writeObject(pData);
					oos.flush();
					oos.reset();
					System.out.println("WRITE : BROADCAST_MSG");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void whisper(Message message) {
		synchronized (hm) {
			String listener = message.getMsgListener();
			ObjectOutputStream oos = null;
			try {
				oos = hm.get(listener);
				pData = new PData(Protocol.WHISPER_MSG, message);
				oos.writeObject(pData);
				oos.flush();
				oos.reset();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void sendDataToMyUser(PData pData) {
		synchronized(hm) {
			try {
				oos = hm.get(myUser.getNick());
				oos.writeObject(pData);
				oos.flush();
				oos.reset();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
