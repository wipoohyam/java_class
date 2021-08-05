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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ChatFrame extends JFrame {

	private PnlChatterInList pnlUser;
	private Vector<RoundedButton> userBtns;
	private JPanel pnlUserList;

	private Room room;
	private User myUser;

	private JLabel lblLock;
	private JLabel lblTitle;
	private JPanel pnlLock;
	private JPanel pnlTitleInner;

	public JPanel pnlChatDisplay;
	private JScrollPane chatScroll;
	private JScrollBar vScroll;
	private JTextField tfInput;
	private JButton btnSend;
	public Box.Filler filler;

	private JPanel pnlSetting;
	private JPanel pnlMasterPass;
	private JPanel pnlOut;
	
	private RoundedButton btnRoomSetting;
	private RoundedButton btnExitRoom;
	private RoundedButton btnMasterPass;
	private RoundedButton btnOut;
	
	private JLabel lblUserCount;

	private Vector<User> chatters;

	private RoundedButton btnInvite;

	private RoomInfoInputFrame editRoomUI;
	private ObjectOutputStream oos;

	public ChatFrame(User myUser, RoomInfoInputFrame editRoomUI, ObjectOutputStream oos) {
		this.myUser = myUser;
		this.editRoomUI = editRoomUI;
		this.oos = oos;
		init();
		setDisplay();
		addListeners();
		showFrame();
		printHelp();
	}

	public void setRoomInfo(Room room) {

		this.room = room;
		pnlUserList.removeAll();
		this.chatters = room.getChatters();
		lblTitle.setText(room.getRoomName());
		pnlLock.removeAll();
		if (room.getRoomPw().length()>0) {
			pnlLock.add(lblLock, BorderLayout.CENTER);
			lblLock.setToolTipText(room.getRoomPw());
		}
		pnlLock.updateUI();
		pnlTitleInner.updateUI();
//		System.out.println("roomMaster:"+room.getRoomMaster().getNick()+",myUser:"+myUser.getNick());
		if (myUser.isRoomMaster()) {
			myUser.setRoomMaster(true);
			pnlSetting.add(btnRoomSetting);
			pnlMasterPass.add(btnMasterPass);
			pnlOut.add(btnOut);
		}else {
			pnlSetting.removeAll();
			pnlMasterPass.removeAll();
			pnlOut.removeAll();
		}
		pnlChatDisplay.removeAll();
		pnlChatDisplay.add(Box.createVerticalGlue());
		
		pnlChatDisplay.updateUI();

		pnlSetting.updateUI();
		pnlMasterPass.updateUI();
		pnlOut.updateUI();
		
		lblUserCount.setText("참가인원 " + room.getRoomCurrentPeople() + " / " + room.getRoomCapacity());

		userBtns = new Vector<>();
		for (int i = 0; i < chatters.size(); i++) {
			pnlUser = new PnlChatterInList(chatters.get(i));
			String nick = chatters.get(i).getNick();
			
			
			userBtns.add(pnlUser.getBtnUser());
			pnlUser.getBtnUser().setActionCommand("");
			if(myUser.isRoomMaster()) {
				pnlUser.getBtnUser().addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						
					}
					@Override
					public void mouseEntered(MouseEvent e) {
					}
					@Override
					public void mouseReleased(MouseEvent e) {
						RoundedButton btn = (RoundedButton) e.getSource();
						if(btn.getActionCommand().equals("")) {
							for (RoundedButton b : userBtns) {
								b.setActionCommand("");
								b.setBackground(new Color(0xEEEEEE));
							}
							btn.setBackground(new Color(0xCCCCCC));
							btnMasterPass.setBackground(new Color(0xCCCCCC));
							btnOut.setBackground(new Color(0xCCCCCC));
							btn.setActionCommand(nick);
						}else {
							btn.setActionCommand("");
							btn.setBackground(new Color(0xEEEEEE));
							btnMasterPass.setBackground(new Color(0xBFBFBF));
							btnOut.setBackground(new Color(0xBFBFBF));
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
			}else {
				pnlUser.addWhisperListener(tfInput);
			}
			pnlUser.setMaximumSize(new Dimension(200, 50));
			pnlUserList.add(pnlUser);
		}
		
		pnlUserList.updateUI();
	}

	// pnlUser를 누르면 pnlUser가 담겨있는 리스트를 초기화시킨다
	public void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		lblTitle = new JLabel();
		lblTitle.setFont(new Font(Font.DIALOG, Font.BOLD, 16));

		lblLock = new JLabel();
		lblLock.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().getImage("img/lock.png").getScaledInstance(26, 26, Image.SCALE_DEFAULT)));

		btnExitRoom = new RoundedButton("나가기", new Color(0xBFBFBF), new Color(0x939393));

		pnlChatDisplay = new JPanel();
		Dimension minSize = new Dimension(300, 0);
		Dimension prefSize = new Dimension(300, 0);
		Dimension maxSize = new Dimension(300, pnlChatDisplay.getHeight());

		filler = new Box.Filler(minSize, prefSize, maxSize);

		tfInput = new JTextField();
		tfInput.setBorder(new EmptyBorder(0, 0, 0, 0));
		tfInput.requestFocus();
		btnSend = new RoundedButton(new Color(0x107aff), new Color(0x0b6cd6));
		btnSend.setIcon(new ImageIcon(
				Toolkit.getDefaultToolkit().getImage("img/send.png").getScaledInstance(30, 30, Image.SCALE_DEFAULT)));
		btnSend.setBorderPainted(false);
		ChatFrame.this.getRootPane().setDefaultButton(btnSend);

		lblUserCount = new JLabel();
		lblUserCount.setFont(new Font(Font.DIALOG, Font.BOLD, 12));

		btnRoomSetting = new RoundedButton("채팅방설정", new Color(0xBFBFBF), new Color(0x939393));		
		btnMasterPass = new RoundedButton("방장위임", new Color(0xBFBFBF), new Color(0x939393));
		btnOut = new RoundedButton("강제퇴장", new Color(0xBFBFBF), new Color(0x939393));
		
		btnInvite = new RoundedButton("초대", new Color(0x00DB3F), new Color(0x037022));
	}

	public void setDisplay() {
		try {
     		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
     	} catch (Exception e) {
     		e.printStackTrace();
     	}

		Dimension btnSettingSize = new Dimension(110, 30);
		Dimension btnExitSize = new Dimension(80, 30);
		Dimension btnMasterPassSize = new Dimension(100, 30);
		Dimension btnOutSize = new Dimension(100, 30);
		Dimension pnlUserListSize = new Dimension(100, 10);
		Dimension btnInviteSize = new Dimension(200, 0);

		// 화면 위쪽 (타이틀 + 버튼)
		// 타이틀
		JPanel pnlTitle = new JPanel(new BorderLayout());
		pnlLock = new JPanel(new BorderLayout());
		pnlLock.setBorder(new EmptyBorder(0,0,0,5));
		RoundedPanel pnlTitleRounded = new RoundedPanel(Color.WHITE);
		pnlTitleInner = new JPanel(new BorderLayout());
		pnlTitle.add(pnlTitleRounded, BorderLayout.CENTER);
		pnlTitleRounded.add(pnlTitleInner);
		pnlTitle.setPreferredSize(new Dimension(380, 60));
		pnlTitleInner.setPreferredSize(new Dimension(340, 40));
		pnlTitle.setOpaque(false);
		pnlTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
		pnlTitleInner.add(pnlLock, BorderLayout.WEST);
		pnlTitleInner.add(lblTitle, BorderLayout.CENTER);

		// 셋팅버튼
		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		pnlSetting = new JPanel(new BorderLayout());
		pnlSetting.setOpaque(true);
		pnlSetting.setPreferredSize(btnSettingSize);
		

		JPanel pnlExit = new JPanel(new BorderLayout());
		pnlExit.add(btnExitRoom);
		pnlExit.setPreferredSize(btnExitSize);
		
		pnlButtons.add(pnlSetting);
		pnlButtons.add(pnlExit);

		// Main North
		JPanel pnlMainNorth = new JPanel(new BorderLayout());
		pnlMainNorth.add(pnlTitle, BorderLayout.WEST);
		JPanel pnlMainNorthEast = new JPanel(new BorderLayout());
		pnlMainNorthEast.add(pnlButtons, BorderLayout.SOUTH);
		pnlMainNorthEast.setBorder(new EmptyBorder(0, 0, 10, 0));
		pnlMainNorth.add(pnlMainNorthEast, BorderLayout.EAST);

		// 유저들의 채팅 + 나의 채팅
		pnlChatDisplay.setLayout(new BoxLayout(pnlChatDisplay, BoxLayout.Y_AXIS));
		pnlChatDisplay.add(Box.createVerticalGlue());
		pnlChatDisplay.updateUI();

		chatScroll = new JScrollPane(pnlChatDisplay);
		chatScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		chatScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		chatScroll.setOpaque(true);
		chatScroll.setBackground(Color.WHITE);
//		chatScroll.setBorder(null);

		JPanel pnlChatDisplayWrap = new JPanel(new BorderLayout());
		JPanel pnlChatDisplayRounded = new RoundedPanel(Color.WHITE);
		pnlChatDisplayRounded.setPreferredSize(new Dimension(380, 520));
		chatScroll.setPreferredSize(new Dimension(370, 510));
		chatScroll.setBorder(new EmptyBorder(15, 10, 15, 10));
		chatScroll.setOpaque(false);
		vScroll = chatScroll.getVerticalScrollBar();

		pnlChatDisplayWrap.setOpaque(false);
		pnlChatDisplayWrap.setBorder(new EmptyBorder(0, 0, 10, 0));
		pnlChatDisplayRounded.add(chatScroll);
		pnlChatDisplayWrap.add(pnlChatDisplayRounded, BorderLayout.CENTER);
		// pnlUserInputRounded > pnlUserInput > pnlUserTfRounded> pnlUserTf
		// pnlUserInputRounded > pnlUserInput > pnlSendBtn > btnSend
		JPanel pnlUserInput = new JPanel(new BorderLayout());
		pnlUserInput.setPreferredSize(new Dimension(350, 35));
		JPanel pnlUserTfRounded = new RoundedPanel(Color.WHITE);
		JPanel pnlUserTf = new JPanel(new BorderLayout());
		pnlUserTf.setPreferredSize(new Dimension(315, 25));
		pnlUserTf.setBorder(new EmptyBorder(0, 13, 0, 13));
		pnlUserTf.setOpaque(false);
		JPanel pnlSend = new JPanel(new BorderLayout());
		JPanel pnlSendBtn = new JPanel(new BorderLayout());
		pnlUserTfRounded.add(pnlUserTf, BorderLayout.CENTER);
		pnlUserTf.add(tfInput, BorderLayout.CENTER);
		pnlSendBtn.add(btnSend, BorderLayout.CENTER);
		pnlSendBtn.setPreferredSize(new Dimension(35, 35));
		JPanel pnlMargin = new JPanel();
		pnlMargin.setPreferredSize(new Dimension(5, 35));
		pnlSend.add(pnlSendBtn, BorderLayout.EAST);
		pnlSend.add(pnlMargin, BorderLayout.WEST);
		pnlUserInput.add(pnlUserTfRounded, BorderLayout.CENTER);
		pnlUserInput.add(pnlSend, BorderLayout.EAST);

		// Center(west)
		JPanel pnlMainCWest = new JPanel(new BorderLayout());
		pnlMainCWest.add(pnlChatDisplayWrap, BorderLayout.CENTER);
		pnlMainCWest.add(pnlUserInput, BorderLayout.SOUTH);

		// 참가인원 + 방장 및 참여유저 리스트
		JPanel pnlChatCount = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlChatCount.add(lblUserCount);

		pnlUserList = new JPanel();
		pnlUserList.setLayout(new BoxLayout(pnlUserList, BoxLayout.Y_AXIS));

		JScrollPane userListScroll = new JScrollPane(pnlUserList);
		userListScroll.setPreferredSize(pnlUserListSize);
		userListScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		userListScroll.setOpaque(true);
		userListScroll.setBorder(null);
		userListScroll.setPreferredSize(new Dimension(200, 425));

		// 방장위임, 강제퇴장, 초대
		JPanel pnlMasterMenu = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		pnlMasterPass = new JPanel(new BorderLayout());
		pnlMasterPass.setPreferredSize(btnMasterPassSize);
		pnlOut = new JPanel(new BorderLayout());
		pnlOut.setPreferredSize(btnOutSize);
		
		pnlMasterMenu.add(pnlMasterPass);
		pnlMasterMenu.add(pnlOut);
		pnlMasterMenu.setBorder(new EmptyBorder(0, 10, 10, 0));
		
		JPanel pnlInvite = new JPanel(new BorderLayout());
		pnlInvite.add(btnInvite);
		pnlInvite.setBorder(new EmptyBorder(0, 10, 20, 0));
		pnlInvite.setPreferredSize(btnInviteSize);
		
		JPanel pnlMasterInvite = new JPanel(new GridLayout(0,1));
		pnlMasterInvite.add(pnlMasterMenu);
		pnlMasterInvite.add(pnlInvite);

		// Center(east)
		JPanel mainCenterEast = new JPanel(new BorderLayout());
		JPanel pnlUserListRounded = new RoundedPanel(Color.WHITE);
		JPanel pnlUserListInner = new JPanel(new BorderLayout());
		pnlUserListInner.add(pnlChatCount, BorderLayout.NORTH);
		pnlUserListInner.add(userListScroll, BorderLayout.CENTER);
		pnlUserListInner.add(pnlMasterInvite, BorderLayout.SOUTH);
		pnlUserListRounded.add(pnlUserListInner);
		pnlUserListInner.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlUserListInner.setOpaque(false);
		mainCenterEast.add(pnlUserListRounded);
		mainCenterEast.setOpaque(false);
		mainCenterEast.setBorder(new EmptyBorder(0, 10, 0, 0));

		// Main South
		JPanel pnlMainCenter = new JPanel(new BorderLayout());
		pnlMainCenter.add(pnlMainCWest, BorderLayout.WEST);
		pnlMainCenter.add(mainCenterEast, BorderLayout.EAST);

		// Main
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setOpaque(true);
		pnlMain.setBackground(MyUtils.GRAY);
		pnlMain.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlMain.add(pnlMainNorth, BorderLayout.NORTH);
		pnlMain.add(pnlMainCenter, BorderLayout.CENTER);

		add(pnlMain);

		ChattingUtils.setBgWhite(pnlLock, pnlTitleInner, pnlChatDisplay, pnlChatCount, pnlInvite, pnlMasterPass, pnlOut, pnlMasterMenu, pnlUserList);
	}

	public void showFrame() {
		setTitle("채팅");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setVisible(false);
	}


	private void addListeners() {
		btnSend.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("myuser = " + myUser);
				String msg = tfInput.getText();
				if (msg.length() > 0) {
					if (msg.indexOf("/w") == 0) {
						int start = msg.indexOf(" ") + 1;
						int end = msg.indexOf(" ", start);
						if (end != -1) {
							String to = msg.substring(start, end);
							String msg2 = msg.substring(end + 1);
							Message message = new Message(myUser.getIcon(), myUser.getNick(), to, msg2,
									myUser.getCurrentPlace());
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
						Message message = new Message(myUser.getIcon(), myUser.getNick(), msg,
								myUser.getCurrentPlace());
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
			}
		});
		btnInvite.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 서버로부터 받은 대기실명단 들어가야 함.
				Object[] o = new Object[2];
				o[0] = room.getRoomId();
				o[1] = myUser;
				System.out.println("myUser:"+myUser);
				System.out.println("room:"+room);
				PData pData = new PData(Protocol.INVITE_USER);
				try {
					oos.writeObject(pData);
					oos.flush();
					oos.reset();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("초대");
			}    	
		});
		btnExitRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PData pData = new PData(Protocol.EXIT_ROOM, myUser);
				try {
					oos.writeObject(pData);
					oos.flush();
					oos.reset();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnMasterPass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedNick = "";
				for(RoundedButton b : userBtns) {
					if(b.getActionCommand().length()>0) {
						selectedNick = b.getActionCommand();
					}
				}
				if(!myUser.getNick().equals(selectedNick)) {
					User delegated = chatters.get(chatters.indexOf(new User(selectedNick)));
					try {
						oos.writeObject(new PData(Protocol.DELEGATE_ROOM_MASTER, delegated));
						oos.flush();
						oos.reset();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedNick = "";
				for(RoundedButton b : userBtns) {
					if(b.getActionCommand().length()>0) {
						selectedNick = b.getActionCommand();
					}
				}
				if(!myUser.getNick().equals(selectedNick)) {
					User banned = chatters.get(chatters.indexOf(new User(selectedNick)));
					try {
						oos.writeObject(new PData(Protocol.BAN_USER, banned));
						oos.flush();
						oos.reset();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnRoomSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editRoomUI.setConfirmType(1);
				editRoomUI.setTfs(room);
				editRoomUI.setEditRoom(room);
				editRoomUI.setVisible(true);
			}
		});
	}

	public void setMyUser(User u) {
		this.myUser = u;
	}
	private void notice(String talker, String msg) {
		Message notice = new Message(11, talker, msg, 0);
		pnlChatDisplay.add(new PnlOthersMsg(notice));
		pnlChatDisplay.add(filler);
		pnlChatDisplay.updateUI();
	}
	public void printHelp() {
		notice("도움말","방장은 방 설정변경/참가자 강제퇴장/방장 권한 위임을 할 수 있습니다.");
		notice("도움말","비밀번호 방에서 방제 옆 자물쇠 위에 마우스를 올리면 비밀번호를 확인할 수 있습니다.");
	}
	
	public void scrollToBottom() {
		vScroll.setValue( vScroll.getMaximum() );
	}
}
