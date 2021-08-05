package kr.ac.green;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class PnlRoom extends JPanel {
	private RoundedButton btnRoom;
	private JLabel lblRoomMasterIcon;
	private JLabel lblRoomMasterName;
	private JLabel lblLockIcon;
	private JLabel lblRoomName;
	private JLabel lblPeoples;
	
//	private JPanel pnlWrap;
	private JPanel pnlNew;
	private JPanel pnlCenter;
	private JPanel pnlLock;
	private JPanel pnlRoomMaster;
	private JPanel pnlRoomMasterIcon;
	private JPanel pnlRoomName;
	private JPanel pnlPeoples;
	private Room room;
	//Room(int roomId, String roomName, User roomMaster, boolean privateRoom, String roomPw, int currentPeople, int capacity, Vector<User> chatters)
	public PnlRoom(Room room) {
		this.room = room;
		init();
		editPnlRoom(room);
		setBtnDisplay();
		setEnterRoom();
	}
	public PnlRoom() {
		setBtnDisplay();
		setNewRoom();
	}
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		lblLockIcon = new JLabel();
		lblRoomName = new JLabel("",JLabel.LEFT);
		lblPeoples = new JLabel();
		lblRoomMasterIcon = new JLabel();
		lblRoomMasterName = new JLabel("", JLabel.LEFT);

		pnlCenter = new JPanel(new BorderLayout());
		pnlLock = new JPanel(new BorderLayout());
	}
	private void setBtnDisplay() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(560, 70));
		setOpaque(true);
		setBackground(Color.WHITE);
		

		pnlCenter = new JPanel(new BorderLayout());
		btnRoom = new RoundedButton(new Color(0xEEEEEE), new Color(0xBFBFBF));
		btnRoom.setLayout(new FlowLayout(FlowLayout.CENTER));
		pnlCenter.setPreferredSize(new Dimension(400,47));
		
		btnRoom.add(pnlCenter);
	}
	private void setEnterRoom() {
		Font fRoom = new Font(Font.DIALOG, Font.PLAIN, 18);
		
		
		pnlRoomName = new JPanel(new BorderLayout());
		pnlLock.setBorder(new EmptyBorder(0,0,0,5));

		ImageIcon imgIcon = null;
		imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/lock.png").getScaledInstance(18, 18, Image.SCALE_SMOOTH));
		
		lblLockIcon.setIcon(imgIcon);
		
		pnlRoomName.add(pnlLock, BorderLayout.WEST);
		lblRoomName.setFont(fRoom);
		pnlRoomName.add(lblRoomName, BorderLayout.CENTER);
		pnlCenter.add(pnlRoomName, BorderLayout.CENTER);
		
		pnlRoomMaster = new JPanel(new BorderLayout());
		pnlRoomMasterIcon = new JPanel(new BorderLayout());
		lblRoomMasterName.setFont(new Font(Font.DIALOG, Font.PLAIN, 13));
		lblRoomMasterIcon.setPreferredSize(new Dimension(13,13));
		pnlRoomMasterIcon.add(lblRoomMasterIcon, BorderLayout.CENTER);
		pnlRoomMasterIcon.setBorder(new EmptyBorder(0,5,0,3));
		pnlRoomMaster.add(pnlRoomMasterIcon, BorderLayout.WEST);
		pnlRoomMaster.add(lblRoomMasterName, BorderLayout.CENTER);
		pnlRoomName.add(pnlRoomMaster, BorderLayout.NORTH);
		
		lblPeoples.setForeground(new Color(0x107aff));
		lblPeoples.setFont(fRoom);
		pnlPeoples = new JPanel(new BorderLayout());
		pnlPeoples.add(lblPeoples, BorderLayout.CENTER);
		pnlCenter.add(pnlPeoples, BorderLayout.EAST);
		pnlCenter.setAlignmentY(CENTER_ALIGNMENT);
		pnlCenter.setPreferredSize(new Dimension(500,47));
		
		setColor(new Color(0xEEEEEE), pnlCenter, pnlRoomMaster,pnlRoomMasterIcon, pnlLock, pnlRoomName, pnlPeoples);
		
		btnRoom.add(pnlCenter);
		
		add(btnRoom, BorderLayout.CENTER);
		JPanel pnlMargin = new JPanel();
		pnlMargin.setOpaque(false);
		pnlMargin.setPreferredSize(new Dimension(500,5));
		add(pnlMargin, BorderLayout.SOUTH);
		
		btnRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRoom.setBackground(new Color(0xDDDDDD));
//				setColor(new Color(0xDDDDDD), pnlCenter, pnlRoomMasterIcon, pnlRoomMaster, pnlLock, pnlRoomName, pnlPeoples);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRoom.setBackground(new Color(0xEEEEEE));
//				setColor(new Color(0xEEEEEE),  pnlCenter, pnlRoomMasterIcon, pnlRoomMaster, pnlLock, pnlRoomName, pnlPeoples);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnRoom.setBackground(new Color(0xCCCCCC));
//				setColor(new Color(0xCCCCCC),  pnlCenter, pnlRoomMasterIcon, pnlRoomMaster, pnlLock, pnlRoomName, pnlPeoples);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnRoom.setBackground(new Color(0xDDDDDD));
//				setColor(new Color(0xDDDDDD),  pnlCenter,pnlRoomMasterIcon, pnlRoomMaster, pnlLock, pnlRoomName, pnlPeoples);
			}
		});
	}
	private void setNewRoom() {
		//Room(int roomId, String roomName, int currentPeople, int roomCapacity, Vector<User> chatters) {
		room = new Room(-99, "방추가", 0, 0, null);
		pnlNew = new JPanel(new BorderLayout());
		JLabel lblNew = new JLabel("방 만 들 기", JLabel.CENTER);
		lblNew.setOpaque(false);
		lblNew.setFont(new Font(Font.DIALOG, Font.PLAIN, 18));
		pnlNew.add(lblNew);
		pnlCenter.add(pnlNew);
		btnRoom.add(pnlCenter);
		add(btnRoom);
		
		btnRoom.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnRoom.setBackground(new Color(0xDDDDDD));
				setColor(new Color(0xDDDDDD), pnlCenter, pnlNew);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnRoom.setBackground(new Color(0xEEEEEE));
				setColor(new Color(0xEEEEEE),  pnlCenter, pnlNew);
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnRoom.setBackground(new Color(0xCCCCCC));
				setColor(new Color(0xCCCCCC),  pnlCenter, pnlNew);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnRoom.setBackground(new Color(0xDDDDDD));
				setColor(new Color(0xDDDDDD),  pnlCenter, pnlNew);
			}
		});
		btnRoom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			}
		});
	}
	private void setColor(Color color,JPanel...pnl) {
		for(JPanel panel : pnl) {
			panel.setOpaque(false);
//			panel.setBackground(color);
		}
	}
	public void editPnlRoom(Room room) {
		lblRoomMasterName.setText(room.getRoomMaster().getNick());
		int iIcon = room.getRoomMaster().getIcon();
		ImageIcon imgIcon = null;
		if(iIcon == 1) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0101.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 2) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0202.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 3) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0303.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 4) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0404.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 5) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0505.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 6) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0606.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 7) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0707.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 8) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0808.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 9) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0909.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 10) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/1010.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}else if(iIcon == 11) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/1111.png")
					.getScaledInstance(13, 13, Image.SCALE_SMOOTH));
		}
		lblRoomMasterIcon.setIcon(imgIcon);
		lblRoomName.setText(room.getRoomName());
		lblRoomMasterName.setText(room.getRoomMaster().getNick()+"의 채팅방");
		lblPeoples.setText("( " + room.getRoomCurrentPeople() + " / " + room.getRoomCapacity() + " )");
		if(room.getRoomCurrentPeople()==room.getRoomCapacity()) {
			lblPeoples.setForeground(new Color(0xfe124c));
		}
		if(room.getRoomPw().length() > 0) {
			pnlLock.add(lblLockIcon, BorderLayout.CENTER);
		}
		updateUI();
		
	}
	public RoundedButton getBtnRoom() {
		return btnRoom;
	}
	public Room getRoom() {
		return room;
	}
	
}
