package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class RoomInfoInputFrame extends JFrame {
	private User myUser;

	private JLabel lblRoomTitle;
	private JLabel lblPassword;
	private JLabel lblCapacity;

	private RoundedTf tfRoomTitle;
	private RoundedPf pfPw;
	private RoundedTf tfCapacity;

	private RoundedButton btnConfirm;
	private RoundedButton btnExit;

	private ObjectOutputStream oos;
//	private ObjectInputStream ois;
	
	private Room newRoom;
	private Room editRoom;
//	private WaitingRoomUI owner;
	
	//방수정,새방만들기 모두 쓸때 리스너를 분리하기 위해 type을 따로 준다
	private int confirmType;
	private static final int NEW = 0;
	private static final int EDIT = 1;
	

	public RoomInfoInputFrame(User myUser, ObjectOutputStream oos) {
		this.oos = oos;
		this.myUser = myUser;
		
		
		init();
		setDisplay();
		addListeners();
		showFrame();

//		Runnable r =  new ClientListen(this, oos, ois, myUser);
//		Thread t = new Thread(r);
//		t.start();
	}

	private void init() {
		try {
     		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
     	} catch (Exception e) {
     		e.printStackTrace();
     	}
		
		lblRoomTitle = new JLabel("방 제목", JLabel.LEFT);
		lblPassword = new JLabel("암호", JLabel.LEFT);
		lblCapacity = new JLabel("최대인원", JLabel.LEFT);
		tfRoomTitle = new RoundedTf();
		pfPw = new RoundedPf();
		String[] strCap = new String[10];
		for (int i = 1; i <= 10; i++) {
			strCap[i - 1] = String.valueOf(i);
		}
		tfCapacity = new RoundedTf();
		tfRoomTitle.setHorizontalAlignment(JTextField.CENTER);
		pfPw.setHorizontalAlignment(JTextField.CENTER);
		tfCapacity.setHorizontalAlignment(JTextField.CENTER);
		btnConfirm = new RoundedButton("확인", new Color(0x12A2FF), new Color(0x006CA8));
		RoomInfoInputFrame.this.getRootPane().setDefaultButton(btnConfirm);
		btnExit = new RoundedButton("취소", new Color(0xBFBFBF), new Color(0x939393));
	}

	private void setDisplay() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		setLayout(new BorderLayout());
		JPanel pnlBackground = new JPanel(new BorderLayout());
		RoundedPanel rp = new RoundedPanel(Color.WHITE, 30);
		JPanel pnlContents = new JPanel(new BorderLayout());
		JPanel pnlInputs = new JPanel(new GridLayout(0, 1));
		EmptyBorder eb = new EmptyBorder(0, 0, 10, 0);
		Dimension dLabel = new Dimension(50, 30);
		Dimension dBtn = new Dimension(75, 30);

		JPanel pnlRoomTitle = new JPanel(new BorderLayout());
		JPanel pnlPassword = new JPanel(new BorderLayout());
		JPanel pnlCapacity = new JPanel(new BorderLayout());

		pnlRoomTitle.add(lblRoomTitle, BorderLayout.NORTH);
		lblRoomTitle.setBorder(new EmptyBorder(0, 0, 5, 0));
		JPanel pnlTitleInput = new JPanel(new BorderLayout());
		pnlTitleInput.add(tfRoomTitle, BorderLayout.CENTER);
		pnlRoomTitle.add(pnlTitleInput, BorderLayout.CENTER);
		pnlInputs.add(pnlRoomTitle);
		pnlRoomTitle.setBorder(eb);

		pnlPassword.add(lblPassword, BorderLayout.NORTH);
		lblPassword.setBorder(new EmptyBorder(0, 0, 5, 0));
		JPanel pnlPasswordInput = new JPanel(new BorderLayout());
		pnlPasswordInput.add(pfPw, BorderLayout.CENTER);
		pnlPassword.add(pnlPasswordInput, BorderLayout.CENTER);
		pnlPassword.setBorder(eb);
		pnlInputs.add(pnlPassword);

		pnlCapacity.add(lblCapacity, BorderLayout.NORTH);
		lblCapacity.setBorder(new EmptyBorder(0, 0, 5, 0));
		JPanel pnlCapacityInput = new JPanel(new BorderLayout());
		pnlCapacityInput.add(tfCapacity, BorderLayout.CENTER);
		pnlCapacity.add(pnlCapacityInput, BorderLayout.CENTER);
		pnlCapacity.setBorder(eb);
		pnlInputs.add(pnlCapacity);

		pnlContents.add(pnlInputs, BorderLayout.CENTER);
		pnlInputs.setBorder(new EmptyBorder(0, 0, 15, 0));

		JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel pnlBtnC = new JPanel(new BorderLayout());
		JPanel pnlBtnE = new JPanel(new BorderLayout());
		pnlBtnC.add(btnConfirm, BorderLayout.CENTER);
		pnlBtnE.add(btnExit, BorderLayout.CENTER);
		pnlBtnC.setPreferredSize(dBtn);
		pnlBtnE.setPreferredSize(dBtn);
		pnlButtons.add(pnlBtnC);
		pnlButtons.add(pnlBtnE);

		pnlContents.add(pnlButtons, BorderLayout.SOUTH);
		pnlContents.setPreferredSize(new Dimension(270, 300));
		pnlContents.setBorder(new EmptyBorder(15, 15, 15, 15));

		rp.add(pnlContents);

		pnlBackground.add(rp, BorderLayout.CENTER);
		pnlBackground.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(pnlBackground, BorderLayout.CENTER);

		ChattingUtils.setBgWhite(pnlContents, lblCapacity, lblPassword, lblRoomTitle, pnlRoomTitle, pnlPassword,
				pnlCapacity, pnlPasswordInput, pnlButtons, pnlBtnC, pnlBtnE, pnlInputs);
	}

	private void addListeners() {
		btnConfirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String title = tfRoomTitle.getText();
				String pw = new String(pfPw.getPassword());
				int cap = 0;
				boolean flag = true;
				try {
					if ((cap = Integer.parseInt(tfCapacity.getText())) < 2) {
						JOptionPane.showMessageDialog(RoomInfoInputFrame.this, "방 최대인원은 2이상의 숫자로 입력해주세요.", "최대인원 입력오류",
								JOptionPane.INFORMATION_MESSAGE);
						flag = false;
					} else if (title.length() == 0) {
						JOptionPane.showMessageDialog(RoomInfoInputFrame.this, "방 제목을 입력해주세요.", "방 제목 입력오류",
								JOptionPane.INFORMATION_MESSAGE);
						flag = false;
					}
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(RoomInfoInputFrame.this, "최대인원은 숫자로만 입력해주세요.", "최대인원 입력오류",
							JOptionPane.INFORMATION_MESSAGE);
					flag = false;
				}
				
				if (flag) {
					if(confirmType == NEW) {
						newRoom = new Room(tfRoomTitle.getText(), myUser, pw, cap);
						newRoom.addChatter(myUser);
						try {
							oos.writeObject(new PData(Protocol.NEW_ROOM, newRoom));
							oos.flush();
							oos.reset();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}else if(confirmType == EDIT) {
						editRoom.setRoomName(title);
						editRoom.setRoomPw(pw);
						if(editRoom.getRoomCurrentPeople() <= cap) {
							editRoom.setRoomCapacity(cap);
							try {
								oos.writeObject(new PData(Protocol.EDIT_ROOM, editRoom));
								oos.flush();
								oos.reset();
							} catch(Exception e3) {
								e3.printStackTrace();
							}
							
						}else {
							JOptionPane.showMessageDialog(RoomInfoInputFrame.this, "최대인원은 현재 인원보다 커야합니다.", "최대인원 입력오류",
									JOptionPane.INFORMATION_MESSAGE);
						}
					}
				}
			}
		});
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				setTfBlank();
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				setTfBlank();
			}
		});
	}
	
	private void showFrame() {
		setTitle("방만들기");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setVisible(false);
	}
	public Room getNewRoom() {
		return newRoom;
	}
	public void setTfBlank() {
		tfRoomTitle.setText("");
		pfPw.setText("");
		tfCapacity.setText("");
	}
	public void setConfirmType(int type) {
		this.confirmType = type;
	}
	public void setTfs(Room room) {
		tfRoomTitle.setText(room.getRoomName());
		pfPw.setText(room.getRoomPw());
		tfCapacity.setText(String.valueOf(room.getRoomCapacity()));;
	}
	public void setEditRoom(Room room) {
		this.editRoom = room;
	}
	public void setMyUser(User u) {
		this.myUser = u;
	}
}
