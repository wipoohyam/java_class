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
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PnlChatterInList extends JPanel {
	private JPopupMenu pMenu;
	private JMenuItem lederPass;
	
	private User user;
	
	private RoundedButton btnUser;
	private JPanel pnlIcon;
	private JPanel pnlName;
	private JLabel lblUserListIcon;
	private JLabel lblUserListName;
	
	public PnlChatterInList(User user) {
		this.user = user;
		init();
		setDisplay();
	}
	
	private void init() {
//		Dimension iconSize = new Dimension(30,30);
//		Dimension nameSize = new Dimension(100,30);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int iIcon = user.getIcon();
		lblUserListIcon = new JLabel();
		ImageIcon userListIcon = null;
		if(iIcon == 1) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0101.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 2) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0202.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 3) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0303.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 4) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0404.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 5) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0505.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 6) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0606.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 7) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0707.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 8) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0808.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 9) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0909.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 10) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/1010.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}else if(iIcon == 11) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/1111.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}
		if(user.isRoomMaster()) {
			userListIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/crown.png")
					.getScaledInstance(22, 22, Image.SCALE_SMOOTH));
		}
		lblUserListIcon.setIcon(userListIcon);
		lblUserListName = new JLabel(user.getNick(), JLabel.LEFT);
		lblUserListName.setFont(new Font(Font.DIALOG, Font.PLAIN, 15));
		
		pMenu = new JPopupMenu();
	    lederPass = new JMenuItem("방장권한위임");
	    
	    pMenu.add(lederPass);
	}
	private void setDisplay() {
		// boxlayout > pnlChatterInlist > btn > icon, name
		Dimension d = new Dimension(160,40);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(3,0,3,0));
		btnUser = new RoundedButton(new Color(0xEEEEEE), new Color(0xCCCCCC));
		JPanel pnlInner = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		pnlIcon = new JPanel(new BorderLayout());
		pnlIcon.add(lblUserListIcon, BorderLayout.CENTER);
//		pnlIcon.setBackground(new Color(0xEEEEEE));
		pnlIcon.setOpaque(false);
		
		pnlIcon.setBorder(new EmptyBorder(0,5, 0, 5));
		
		pnlName = new JPanel(new BorderLayout());
		pnlName.add(lblUserListName, BorderLayout.CENTER);
//		pnlName.setBackground(new Color(0xEEEEEE));
		pnlName.setOpaque(false);
		
		pnlInner.add(pnlIcon);
		pnlInner.add(pnlName);
		
		setOpaque(true);
		setBackground(Color.WHITE);
		
		btnUser.add(pnlIcon, BorderLayout.WEST);
		btnUser.add(pnlName, BorderLayout.CENTER);
		add(btnUser, BorderLayout.CENTER);
	}
	public void addListeners() {
		
		btnUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnUser.setBackground(new Color(0xDDDDDD));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				btnUser.setBackground(new Color(0xEEEEEE));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				btnUser.setBackground(new Color(0xCCCCCC));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				btnUser.setBackground(new Color(0xDDDDDD));
			}
		});
		
	}
	public void addInviteListener() {
		btnUser.addActionListener(new ActionListener() {
	         @Override
	         public void actionPerformed(ActionEvent e) {
	            Object o = e.getSource();
	            if(btnUser.getActionCommand() == "") {
	               btnUser.setActionCommand("selected");
	               btnUser.setBackground(Color.RED);
	               btnUser.setBorder(new LineBorder(Color.RED, 5));
//	               System.out.println(btnUser.getActionCommand());
	            } else if(btnUser.getActionCommand().equals("selected")) {
	               btnUser.setActionCommand("");
//	               btnUser.setBorder(new LineBorder());
//	               System.out.println(btnUser.getActionCommand());
	            }
	         }
	      });
	}
	
	public RoundedButton getBtnUser() {
		return btnUser;
	}
	public void setBtnUser(RoundedButton btnUser) {
		this.btnUser = btnUser;
	}
	public User getUser() {
		return user;
	}
	public void addWhisperListener(JTextField tf) {
		btnUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String nick = user.getNick();
				tf.setText("/w "+nick+" ");
				tf.requestFocus();
			}
		});
	}
}

