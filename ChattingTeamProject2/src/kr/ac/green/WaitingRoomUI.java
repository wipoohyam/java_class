package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class WaitingRoomUI extends JFrame implements ActionListener {
	private Vector<User> chatters;
	private HashMap<Integer, Room> roomList;
	private User myUser;
	private RoundedButton btnIn; // 입장
	private RoundedButton btnNewRoom; // 방 만들기
	private RoundedButton btnClose; // 종료

	private JPanel pnlUserList;
	private JPanel pnlCCenter;
	private JScrollPane scrollRoom;
	private Vector<PnlRoom> roomsInList;

	public JPanel pnlChatDisplay;
	JScrollPane scrollChatDisplay;
	JScrollBar vScroll;
	JScrollBar vertical;
	private PnlOthersMsg othersMsg;
	private PnlMyMsg myMsg;
	public Box.Filler filler;
//	private Vector<PnlChatterInList> usersInList;

	private RoundedTf tfInput; // 채팅 입력창
	private JButton btnSend; // 전송
	private RoomInfoInputFrame newroomUI;
	private PwInputFrame pwinputUI;
	private Socket sock;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public WaitingRoomUI(Vector<User> chatters, HashMap<Integer, Room> roomList, User myUser,PwInputFrame pwinputUI, RoomInfoInputFrame newroomUI,
			ChatFrame chatframeUI, Socket sock, ObjectOutputStream oos, ObjectInputStream ois) {
		this.chatters = chatters;
		this.roomList = roomList;
		this.myUser = myUser;
		this.newroomUI = newroomUI;
		this.pwinputUI = pwinputUI;
		this.sock = sock;
		this.oos = oos;
		this.ois = ois;

		init();
		setDisplay();
		roomListUpdate();
		addListeners();
		showFrame();
		printHelp();
	}

	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		btnIn = new RoundedButton("입장", new Color(0x12A2FF), new Color(0x006CA8));
		btnNewRoom = new RoundedButton("방만들기", new Color(0xBFBFBF), new Color(0x939393));
		btnClose = new RoundedButton("종료", new Color(0xBFBFBF), new Color(0x939393));
		roomsInList = new Vector<>();
		Collection<Room> collection = roomList.values();
		Iterator<Room> iter = collection.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			if (r.getRoomId() != 0) {
				roomsInList.add(new PnlRoom(r));
			}
		}

		pnlChatDisplay = new JPanel();
		Dimension minSize = new Dimension(300, 0);
		Dimension prefSize = new Dimension(300, 0);
		Dimension maxSize = new Dimension(300, pnlChatDisplay.getHeight());
		filler = new Box.Filler(minSize, prefSize, maxSize);
		tfInput = new RoundedTf();

		// 버튼에 아이콘 넣기
		btnSend = new RoundedButton(new Color(0x107aff), new Color(0x0b6cd6));
		btnSend.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().getImage("img/send.png").getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		btnSend.setBorderPainted(false);
		WaitingRoomUI.this.getRootPane().setDefaultButton(btnSend);
		
	}

	private void setDisplay() {

		Dimension btnDimension = new Dimension(85, 35);
		Dimension tfDimension = new Dimension(400, 30);
		Font fBtn = new Font(Font.DIALOG, Font.PLAIN, 16);

		RoundedPanel pnlCenterRounded = new RoundedPanel(Color.WHITE, 30);
		JPanel pnlNWest = new JPanel(new FlowLayout());
		JPanel pnlBtnIn = new JPanel(new BorderLayout());
		JPanel pnlBtnNewRoom = new JPanel(new BorderLayout());
		pnlBtnIn.setPreferredSize(btnDimension);
		pnlBtnNewRoom.setPreferredSize(btnDimension);
		pnlBtnIn.add(btnIn);
		btnIn.setFont(fBtn);
		pnlBtnNewRoom.add(btnNewRoom);
		btnNewRoom.setFont(fBtn);
		pnlNWest.add(pnlBtnIn);
		pnlNWest.add(pnlBtnNewRoom);

		JPanel pnlNEast = new JPanel(new FlowLayout());
		JPanel pnlBtnClose = new JPanel(new BorderLayout());
		pnlBtnClose.setPreferredSize(btnDimension);
		pnlBtnClose.add(btnClose);
		btnClose.setFont(fBtn);
		pnlNEast.add(pnlBtnClose);

		JPanel pnlNorth = new JPanel(new BorderLayout());
		pnlNorth.add(pnlNWest, BorderLayout.WEST);
		pnlNorth.add(pnlNEast, BorderLayout.EAST);
		pnlNorth.setBorder(new EmptyBorder(5, 0, 5, 0));

		// 방 제목 + 인원 수
		pnlCCenter = new JPanel(new GridLayout(0, 1));
		pnlCCenter.setOpaque(true);
		pnlCCenter.setBackground(Color.WHITE);

		scrollRoom = new JScrollPane(pnlCCenter);
		scrollRoom.getVerticalScrollBar();
		scrollRoom.setOpaque(true);
		scrollRoom.setBorder(new EmptyBorder(1, 1, 1, 1));
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setPreferredSize(new Dimension(580, 330));
		pnlCenter.add(pnlNorth, BorderLayout.NORTH);
		pnlCenter.add(scrollRoom, BorderLayout.CENTER);
		pnlCenterRounded.add(pnlCenter);

		// 아래쪽
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBorder(new EmptyBorder(10, 0, 0, 0));
		// 아래 왼쪽
		JPanel pnlSCenterRounded = new RoundedPanel(Color.WHITE, 30);
		JPanel pnlSCenter = new JPanel(new BorderLayout());
		// 채팅창
		JPanel pnlSCCenter = new JPanel(new BorderLayout());
		// 채팅입력창
		JPanel pnlSCSouth = new JPanel(new BorderLayout());

		pnlChatDisplay.setLayout(new BoxLayout(pnlChatDisplay, BoxLayout.Y_AXIS));

		pnlChatDisplay.add(Box.createVerticalGlue());
		
		pnlChatDisplay.updateUI();

		scrollChatDisplay = new JScrollPane(pnlChatDisplay);

		scrollChatDisplay.setBorder(new EmptyBorder(0, 0, 0, 0));
		scrollChatDisplay.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollChatDisplay.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		vScroll = scrollChatDisplay.getVerticalScrollBar();
		
		pnlSCCenter.add(scrollChatDisplay, BorderLayout.CENTER);

		JPanel pnlTfInput = new JPanel(new BorderLayout());
		JPanel pnlBtnSend = new JPanel(new BorderLayout());
		pnlTfInput.add(tfInput, BorderLayout.CENTER);
		pnlBtnSend.add(btnSend, BorderLayout.CENTER);
		pnlBtnSend.setPreferredSize(new Dimension(30, 30));
		pnlTfInput.setPreferredSize(tfDimension);
		pnlTfInput.setBorder(new EmptyBorder(0, 0, 0, 5));
		pnlSCSouth.add(pnlTfInput, BorderLayout.CENTER);
		pnlSCSouth.add(pnlBtnSend, BorderLayout.EAST);
		pnlSCSouth.setBorder(new EmptyBorder(10, 0, 0, 0));

		pnlSCenter.setPreferredSize(new Dimension(360, 380));
		pnlSCenter.setBorder(new EmptyBorder(10, 0, 10, 0));
		pnlSCenter.add(pnlSCCenter, BorderLayout.CENTER);
		pnlSCenter.add(pnlSCSouth, BorderLayout.SOUTH);
		pnlSCenterRounded.add(pnlSCenter);
		pnlSouth.add(pnlSCenterRounded, BorderLayout.CENTER);

		pnlSCenterRounded.add(pnlSCenter);

		// 아래 오른쪽
		// pnlSEastRounded > pnlSEast > pnlUserList
		JPanel pnlSEastWrap = new JPanel(new BorderLayout());
		JPanel pnlSEastRounded = new RoundedPanel(Color.WHITE, 30);
		JPanel pnlSEast = new JPanel(new BorderLayout());
		pnlUserList = new JPanel();

		pnlUserList.setLayout(new BoxLayout(pnlUserList, BoxLayout.Y_AXIS));

		userListUpdate();

		JScrollPane scrollUser = new JScrollPane(pnlUserList);
		scrollUser.setBorder(null);
		scrollUser.getVerticalScrollBar();

		pnlSEast.setPreferredSize(new Dimension(200, 380));
		pnlSEast.setBorder(new EmptyBorder(5, 5, 5, 5));

		pnlSEast.add(scrollUser);
		pnlSEastRounded.add(pnlSEast);

		pnlSEastWrap.add(pnlSEastRounded, BorderLayout.CENTER);
		pnlSEastWrap.setBorder(new EmptyBorder(0, 10, 0, 0));

		pnlSouth.add(pnlSEastWrap, BorderLayout.EAST);

		ChattingUtils.setBgWhite(tfInput, pnlTfInput, pnlBtnSend, pnlCenter, pnlNorth, pnlNWest, pnlNEast, pnlBtnIn,
				pnlBtnNewRoom, pnlBtnClose, pnlChatDisplay, scrollRoom, pnlSCSouth, pnlSCenter, pnlSEast, pnlUserList);

		JPanel pnlBg = new JPanel(new BorderLayout());
		pnlBg.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlBg.setBackground(new Color(240, 240, 240));
		pnlBg.add(pnlCenterRounded, BorderLayout.CENTER);
		pnlBg.add(pnlSouth, BorderLayout.SOUTH);
		add(pnlBg, BorderLayout.CENTER);
	}

	private void addListeners() {
		btnSend.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				requestExit();
			}
		});
		btnClose.addActionListener(this);
		btnNewRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newroomUI.setVisible(true);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o == btnSend) {
			String msg = tfInput.getText();
			if(msg.length() >0) {
				if (msg.indexOf("/w") == 0) {
					int start = msg.indexOf(" ") + 1;
					int end = msg.indexOf(" ", start);
					if (end != -1) {
						String to = msg.substring(start, end);
						String msg2 = msg.substring(end + 1);
						Message message = new Message(myUser.getIcon(), myUser.getNick(), to, msg2,
								myUser.getCurrentPlace());
						System.out.println(message);
						PData pData = new PData(Protocol.SEND_WHISPER, message);
						tfInput.setText("");
						try {
							oos.writeObject(pData);
							oos.flush();
							oos.reset();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				} else {
					Message message = new Message(myUser.getIcon(), myUser.getNick(), msg, myUser.getCurrentPlace());
					PData pData = new PData(Protocol.SEND_BROADCAST, message);
					tfInput.setText("");
					try {
						oos.writeObject(pData);
						oos.flush();
						oos.reset();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
			// 방 제목을 눌러서 입장
		} else if (o == btnClose) {
			int result = JOptionPane.showConfirmDialog(WaitingRoomUI.this, "종료하시겠습니까?", "종료", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				requestExit();
			}else {}
		}

	}

	private void showFrame() {
		setTitle("대기실");
		setSize(630, 800);
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	public void roomListUpdate() {
		pnlCCenter.removeAll();
		roomsInList.clear();
		Collection<Room> collection = roomList.values();
		Iterator<Room> iter = collection.iterator();
		while (iter.hasNext()) {
			Room r = iter.next();
			if (r.getRoomId() != 0) {
				PnlRoom pnlRoom = new PnlRoom(r);
				roomsInList.add(pnlRoom);
			}
		}
		for (PnlRoom r : roomsInList) {
			r.getBtnRoom().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int roomId = r.getRoom().getRoomId();
					try {
						if(r.getRoom().getRoomPw().length() > 0) {
							pwinputUI.setRoomId(roomId);
							pwinputUI.setVisible(true);
						}else {
							oos.writeObject(new PData(Protocol.ENTER_ROOM, roomId));
							oos.flush();
							oos.reset();
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			pnlCCenter.add(r);
		}
		PnlRoom pnlNewRoomBtn = new PnlRoom();
		pnlNewRoomBtn.getBtnRoom().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				newroomUI.setConfirmType(0);
				newroomUI.setVisible(true);
			}
		});
		pnlCCenter.add(pnlNewRoomBtn);
		if (roomsInList.size() < 3) {
			JPanel[] pnlBlank = new JPanel[4];
			for (int i = 0; i < 3 - roomsInList.size(); i++) {
				pnlBlank[i] = new JPanel();
				pnlBlank[i].setPreferredSize(new Dimension(380, 50));
				pnlBlank[i].setBackground(Color.WHITE);
				pnlCCenter.add(pnlBlank[i]);
			}
			scrollRoom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		}
		scrollRoom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		pnlChatDisplay.removeAll();
		pnlChatDisplay.add(Box.createVerticalGlue());
		
		pnlChatDisplay.updateUI();

		pnlCCenter.updateUI();
	}

	public void userListUpdate() {
		pnlUserList.removeAll();
		for (int i = 0; i < chatters.size(); i++) {
			PnlChatterInList pnlChatter = new PnlChatterInList(chatters.get(i));
			pnlChatter.addListeners();
			pnlChatter.addWhisperListener(tfInput);
			pnlUserList.add(pnlChatter);
		}
		pnlUserList.add(Box.createVerticalGlue());
		pnlUserList.updateUI();
		pack();
	}

	public void requestExit() {
		try {
			oos.writeObject(new PData(Protocol.EXIT_PROGRAM, myUser));
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void exit() {
		try {
			ois.close();
		} catch(Exception e) {}
		try {
			oos.close();
		} catch(Exception e) {}
		try {
			sock.close();
		} catch(Exception e) {}
		System.out.println("클라이언트의 접속을 종료합니다.");
		System.exit(0);
	}

	public Vector<User> getChatters() {
		return chatters;
	}

	public void setChatters(Vector<User> chatters) {
		this.chatters = chatters;
	}

	public void setRoomList(HashMap<Integer, Room> roomList) {
		this.roomList = roomList;
	}
	public void setMyUser(User u) {
		this.myUser = u;
	}
	public void scrollToBottom() {
		vScroll.setValue( vScroll.getMaximum() );
	}
	private void notice(String talker, String msg) {
		Message notice = new Message(11, talker, msg, 0);
		pnlChatDisplay.add(new PnlOthersMsg(notice));
		pnlChatDisplay.add(filler);
		pnlChatDisplay.updateUI();
	}
	public void printHelp() {
		notice("도움말","채팅이 시작되었습니다!\n자유롭게 방을 만들고 채팅해주세요.");
		notice("도움말", "귓속말 : \"/w ID 메세지\" 를 입력.\n사용자를 선택해 입력.");
	}
}
