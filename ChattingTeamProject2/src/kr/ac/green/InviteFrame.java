package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class InviteFrame extends JFrame {
	
	private Color gray;
	private JLabel lblInviteIcon;
	private JLabel lblInviteText;
	
	private User myUser;
	private Room room;
	
	private RoundedButton btCancel;
	private RoundedButton btOk;
	
	private User[] user;
	
	private Vector<User> waitingUsers;
	private Vector<PnlChatterInList> pnlWaitingUsers;
	private JPanel pnlUserList;
	private ObjectOutputStream oos;
	
	public InviteFrame(User myUser, ObjectOutputStream oos) {
//		this.waitingUsers = waitingUsers;
		this.myUser = myUser;
		this.oos = oos;
		init();
		setDisplay();
		showFrame();
	}
	
	public void init() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		gray = new Color(240, 240, 240);
		
		lblInviteIcon = new JLabel();
		lblInviteIcon.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/invite.png")
				.getScaledInstance(70,70,Image.SCALE_DEFAULT)));
		
		lblInviteText = new JLabel("초대하고 싶은 사람의 아이디를 클릭하세요.");
		
		btCancel = new RoundedButton("취소", new Color(0xBFBFBF), new Color(0x939393));
		btOk = new RoundedButton("확인", new Color(0x12A2FF), new Color(0x006CA8));

	}
	
	public void setDisplay() {
		
		try {
     		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
     	} catch (Exception e) {
     		e.printStackTrace();
     	}
		
		JPanel pnlInviteIcon = new JPanel();
		pnlInviteIcon.setBorder(new EmptyBorder(10,0,10,0));
		pnlInviteIcon.add(lblInviteIcon);
		
		JPanel pnlInviteText = new JPanel();
		pnlInviteText.add(lblInviteText);
		pnlInviteText.setBorder(new EmptyBorder(0,0,10,0));
		
		JPanel pnlInvite = new JPanel(new BorderLayout());
		pnlInvite.add(pnlInviteIcon, BorderLayout.NORTH);
		pnlInvite.add(pnlInviteText, BorderLayout.SOUTH);
		
		pnlUserList = new JPanel();
		pnlUserList.setLayout(new BoxLayout(pnlUserList, BoxLayout.Y_AXIS));
		pnlUserList.setOpaque(true);
		pnlUserList.setBackground(Color.WHITE);
//		for(int i=0;i<waitingUsers.size();i++) {
//			JPanel pnl = new PnlChatterInList(waitingUsers.get(i));
//			pnl.setMaximumSize(new Dimension(200,50));
//			pnlUserList.add(pnl);
//		}
		pnlUserList.add(Box.createVerticalGlue());
		
		JScrollPane UserListScroll = new JScrollPane(pnlUserList);
		Dimension pnlUserListSize = new Dimension(150,200);
		UserListScroll.setPreferredSize(pnlUserListSize);
		UserListScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		UserListScroll.setBorder(new EmptyBorder(10,10,10,10));
		UserListScroll.setOpaque(true);
		UserListScroll.setBackground(Color.WHITE);
		
		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JPanel pnlbtCancel = new JPanel(new BorderLayout());
		JPanel pnlbtOk = new JPanel(new BorderLayout());

		pnlbtOk.add(btOk);
		pnlbtCancel.add(btCancel);
		
		Dimension btnDimension = new Dimension(75, 30);
		pnlbtCancel.setPreferredSize(btnDimension);
		pnlbtOk.setPreferredSize(btnDimension);
		
		pnlButton.setBorder(new EmptyBorder(10,0,0,0));
		pnlButton.add(pnlbtOk);
		pnlButton.add(pnlbtCancel);
		
		JPanel pnlBackground = new JPanel(new BorderLayout());
		RoundedPanel pnlRb = new RoundedPanel(Color.WHITE, 30);
		
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlInvite, BorderLayout.NORTH);
		pnlMain.add(UserListScroll, BorderLayout.CENTER);
		pnlMain.add(pnlButton, BorderLayout.SOUTH);
		pnlMain.setBorder(new EmptyBorder(10,10,10,10));
		pnlMain.setOpaque(false);
		
		pnlBackground.setBorder(new EmptyBorder(10,10,10,10));
		pnlBackground.setOpaque(true);
		pnlBackground.setBackground(gray);

		pnlRb.add(pnlMain);
		pnlBackground.add(pnlRb, BorderLayout.CENTER);
		add(pnlBackground, BorderLayout.CENTER);
		
		ChattingUtils.setBgWhite(pnlInviteIcon,pnlInviteText,pnlUserList,pnlButton,pnlbtCancel,pnlbtOk);
	}
	
	public void addListeners() {
		
		btCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
		btOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<User> selectedUsers = new Vector<>();
				for(PnlChatterInList pnlUser : pnlWaitingUsers){
					String ac = pnlUser.getBtnUser().getActionCommand();
					if(ac.equals("selected")){
						selectedUsers.add(pnlUser.getUser());
					}
				}
				Object[] o3 = new Object[3];
				o3[0] = myUser;
				o3[1] = selectedUsers;
				o3[2] = room;
				try {
					oos.writeObject(new PData(Protocol.SELECT_INVITE_USER, o3));
					oos.flush();
					oos.reset();
				} catch(Exception e2) {
					e2.printStackTrace();
				}
				setVisible(false);
			}
		});
	}
	public void setUserList(Vector<User> waitingUsers) {
		pnlUserList.removeAll();
		pnlWaitingUsers = new Vector<>();
		for(int i=0;i<waitingUsers.size();i++) {
			PnlChatterInList pnl = new PnlChatterInList(waitingUsers.get(i));
			pnl.addInviteListener();
			pnlWaitingUsers.add(pnl);
			
			pnl.setMaximumSize(new Dimension(200,50));
			pnlUserList.add(pnl);
		}
		pnlUserList.updateUI();
	}
	
	
	
	public void showFrame() {
		setTitle("초대장");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
	    setVisible(false);	
	}

	public Vector<User> getWaitingUsers() {
		return waitingUsers;
	}
	public void setMyUser(User u) {
		this.myUser = u;
	}
	public void setRoom(Room room) {
		this.room = room;
	}
}