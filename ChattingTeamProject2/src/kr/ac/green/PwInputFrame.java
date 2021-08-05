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
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

public class PwInputFrame extends JFrame {
	private User myUser;
	
	private int roomId;
	
	private JLabel lblPwTitle;

	private RoundedPf pfPassword;

	private RoundedButton btnMake;
	private RoundedButton btnExit;
	
	private ObjectOutputStream oos;
	public PwInputFrame(User myUser, ObjectOutputStream oos) {
		this.myUser = myUser;
		this.oos = oos;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		lblPwTitle = new JLabel("비밀번호를 입력하세요", JLabel.CENTER);

		pfPassword = new RoundedPf();

		btnMake = new RoundedButton("확인", new Color(0x12A2FF), new Color(0x006CA8));
		btnExit = new RoundedButton("취소", new Color(0xBFBFBF), new Color(0x939393));
	}

	private void setDisplay() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Dimension nameSize = new Dimension(150, 30);
		Dimension fieldSize = new Dimension(150, 30);
		Dimension btnSize = new Dimension(80, 30);

		JPanel pnlTitle = new JPanel(new FlowLayout());
		JPanel pnlRoomTitle = new JPanel(new BorderLayout());
		pnlRoomTitle.add(lblPwTitle);
		pnlRoomTitle.setPreferredSize(nameSize);
		pnlRoomTitle.setBackground(Color.WHITE);
		JPanel pnlRoomField = new JPanel(new BorderLayout());

		pnlRoomField.setPreferredSize(fieldSize);
		pnlRoomField.setBackground(Color.WHITE);
		pnlTitle.add(pnlRoomTitle);
		pnlTitle.add(pnlRoomField);
		pnlTitle.setBackground(Color.WHITE);

		JPanel pnlPassword = new JPanel(new FlowLayout());
		JPanel pnlPwTf = new JPanel(new BorderLayout());
		pfPassword.setHorizontalAlignment(JTextField.CENTER);
		pnlPwTf.add(pfPassword);
		pnlPwTf.setPreferredSize(fieldSize);
		pnlPwTf.setBackground(Color.WHITE);
		pnlPassword.add(pnlPwTf);

		pnlPassword.setBackground(Color.WHITE);

		JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel pnlBtnMake = new JPanel(new BorderLayout());
		pnlBtnMake.add(btnMake);
		pnlBtnMake.setPreferredSize(btnSize);
		pnlBtnMake.setBackground(Color.WHITE);
		JPanel pnlBtnExit = new JPanel(new BorderLayout());
		pnlBtnExit.add(btnExit);
		pnlBtnExit.setPreferredSize(btnSize);
		pnlBtnExit.setBackground(Color.WHITE);
		pnlButton.add(pnlBtnMake);
		pnlButton.add(pnlBtnExit);
		pnlButton.setBackground(Color.WHITE);
		pnlButton.setBorder(new EmptyBorder(10, 1, 10, 1));

		AbstractBorder pnlBorder = new RoundedBorder(Color.LIGHT_GRAY, 0, 30, 0);
		JPanel pnlCenter = new JPanel(new GridLayout(0, 1));
		pnlCenter.add(pnlRoomTitle);
		pnlCenter.add(pnlPassword);

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlButton, BorderLayout.SOUTH);
		pnlMain.setBackground(Color.WHITE);
		pnlMain.setBorder(pnlBorder);
		JPanel pnlC = new JPanel(new BorderLayout());
		pnlC.add(pnlMain, BorderLayout.CENTER);
		pnlC.setBorder(new EmptyBorder(10, 10, 10, 10));

		add(pnlC, BorderLayout.CENTER);
	}
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
			}
		});
		btnMake.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pw = new String(pfPassword.getPassword());
				Object[] o = {roomId, pw};
				try {
					oos.writeObject(new PData(Protocol.SEND_PASSWORD, o));
					oos.flush();
					oos.reset();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	private void showFrame() {
		setTitle("암호구현");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(false);
	}
	public void setMyUser(User u) {
		this.myUser = u;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
}
