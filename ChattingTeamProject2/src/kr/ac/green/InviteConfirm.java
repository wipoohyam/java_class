package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class InviteConfirm extends JFrame {
	
	private Color gray;
	
	private JLabel lblInvitationIcon;
	private JLabel lblInviteUser;
	private JLabel lblInviteChat;
	private JLabel lblBlank;
	private JLabel lblInviteText;
	
	private RoundedButton btCancel;
	private RoundedButton btOk;
	private boolean decision;
	
	private User myUser;
	private User userInviteFrom;
	private Room roomInviteFrom;
	
	
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
		
	public InviteConfirm(User myUser, ObjectOutputStream oos) {
		this.myUser = myUser;
		this.oos = oos;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		gray = new Color(240, 240, 240);
		
		lblInvitationIcon = new JLabel();
		lblInvitationIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/invitation.png")
				.getScaledInstance(50,50,Image.SCALE_DEFAULT)));
		
		lblInviteUser = new JLabel("",JLabel.CENTER);
		lblInviteChat = new JLabel("",JLabel.CENTER);
		lblBlank = new JLabel("");
		lblInviteText = new JLabel("수락하시겠습니까?",JLabel.CENTER);
		
		btOk = new RoundedButton("수락", new Color(0x12A2FF), new Color(0x006CA8));
		btCancel = new RoundedButton("거절", new Color(0xEB0000), new Color(0xCB0000));
		
	}
	public void setFrameInfo(User user, Room room) {
		this.userInviteFrom = user;
		this.roomInviteFrom = room;
		lblInviteUser.setText(userInviteFrom.getNick() + " 님이 " + myUser.getNick() + " 님을");
		lblInviteChat.setText(" [" + roomInviteFrom.getRoomName() + "]" + " 으로 초대하였습니다.");
	}
	private void setDisplay() {
		
		try {
     		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     	} catch (Exception e) {
     		e.printStackTrace();
     	}
		
		JPanel pnlInviteIcon = new JPanel();
		pnlInviteIcon.setBorder(new EmptyBorder(10,0,10,0));
		pnlInviteIcon.add(lblInvitationIcon);
		
		JPanel pnlInviteText = new JPanel(new GridLayout(0,1));
		pnlInviteText.add(lblInviteUser);
		pnlInviteText.add(lblInviteChat);
		pnlInviteText.add(lblBlank);
		pnlInviteText.add(lblInviteText);
		pnlInviteText.setBorder(new EmptyBorder(0,0,10,0));
		
		JPanel pnlInvite = new JPanel(new BorderLayout());
		pnlInvite.add(pnlInviteIcon, BorderLayout.NORTH);
		pnlInvite.add(pnlInviteText, BorderLayout.SOUTH);
		
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel pnlbtCancel = new JPanel(new BorderLayout());
		JPanel pnlbtOk = new JPanel(new BorderLayout());

		pnlbtOk.add(btOk);
		pnlbtCancel.add(btCancel);
		
		Dimension btnDimension = new Dimension(75, 30);
		pnlbtCancel.setPreferredSize(btnDimension);
		pnlbtOk.setPreferredSize(btnDimension);
		
		pnlButton.setBorder(new EmptyBorder(10,0,0,0));
		pnlButton.add(pnlbtCancel);
		pnlButton.add(pnlbtOk);
		
		JPanel pnlBackground = new JPanel(new BorderLayout());
		RoundedPanel pnlRb = new RoundedPanel(Color.WHITE, 30);
		pnlRb.setBorder(new EmptyBorder(10,10,10,10));
		
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlInvite, BorderLayout.NORTH);
		pnlMain.add(pnlButton, BorderLayout.SOUTH);
		pnlMain.setOpaque(false);
		
		pnlBackground.setBorder(new EmptyBorder(10,10,10,10));
		pnlBackground.setOpaque(true);
		pnlBackground.setBackground(gray);

		pnlRb.add(pnlMain);
		pnlBackground.add(pnlRb, BorderLayout.CENTER);
		add(pnlBackground, BorderLayout.CENTER);
		
		ChattingUtils.setBgWhite(pnlInviteIcon,pnlInviteText,pnlButton,pnlbtCancel,pnlbtOk);
	}
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				decision = false;
				thisIsMyDecision(decision);
				setVisible(false);
			}
		});
		btOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				decision = true;
				thisIsMyDecision(decision);
				setVisible(false);
			}
		});
		btCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				decision = false;
				thisIsMyDecision(decision);
				setVisible(false);
			}
		});
	}
	private void thisIsMyDecision(boolean decision) {
		try {
			Object[] o = new Object[3];
			o[0] = decision;
			o[1] = userInviteFrom;
			o[2] = roomInviteFrom;
			oos.writeObject(new PData(Protocol.INVITATION_DECISION, o));
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void showFrame() {
		setTitle("알림");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
	    setVisible(false);	
	}
	
	public void setMyUser(User u) {
		this.myUser = u;
	}
}
