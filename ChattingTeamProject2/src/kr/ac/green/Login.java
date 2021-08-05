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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame {
	private JLabel lblIp;
	private JLabel lblNick;
	private RoundedTf tfIp;
	private RoundedTf tfNick;

	private RoundedButton btnLogin;
	private RoundedButton btnExit;

	private Socket sock;
	private ObjectOutputStream oos = null;
	private ObjectInputStream ois = null;

	private PData pData;
	private int pCode;

	public Login() {
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
		lblIp = new JLabel("IP");
		lblNick = new JLabel("닉네임");
		tfIp = new RoundedTf();
		tfIp.setText("127.0.0.1");
		tfIp.setForeground(Color.LIGHT_GRAY);
		tfIp.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (tfIp.getText().equals("127.0.0.1")) {
					tfIp.setText("");
					tfIp.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (tfIp.getText().isEmpty()) {
					tfIp.setForeground(Color.LIGHT_GRAY);
					tfIp.setText("127.0.0.1");
				}
			}
		});

		tfNick = new RoundedTf();

		btnLogin = new RoundedButton("접속", new Color(0x12A2FF), new Color(0x006CA8));
		btnExit = new RoundedButton("종료", new Color(0xBFBFBF), new Color(0x939393));

		this.getRootPane().setDefaultButton(btnLogin);
	}

	private void setDisplay() {
		JPanel pnlBackground = new JPanel(new BorderLayout());
		RoundedPanel rb = new RoundedPanel(Color.WHITE, 30);
		JPanel pnlContents = new JPanel(new BorderLayout());
		pnlContents.setOpaque(false);
		pnlContents.setPreferredSize(new Dimension(220, 220));
		pnlContents.setBorder(new EmptyBorder(15, 15, 15, 15));

		JPanel pnlCenter = new JPanel(new GridLayout(2, 1));
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel pnlIp = new JPanel(new BorderLayout());
		JPanel pnlIpTf = new JPanel(new BorderLayout());
		JPanel pnlNick = new JPanel(new BorderLayout());
		JPanel pnlNickTf = new JPanel(new BorderLayout());

		lblIp.setBorder(new EmptyBorder(0, 5, 5, 0));
		lblNick.setBorder(new EmptyBorder(0, 5, 5, 0));

		pnlIp.add(lblIp, BorderLayout.NORTH);
		pnlNick.add(lblNick, BorderLayout.NORTH);

		pnlIpTf.setOpaque(false);
		pnlNickTf.setOpaque(false);
		pnlIpTf.setBorder(new EmptyBorder(0, 0, 10, 0));
		pnlNickTf.setBorder(new EmptyBorder(0, 0, 10, 0));

		pnlIpTf.add(tfIp);
		pnlNickTf.add(tfNick);

		pnlIp.add(pnlIpTf, BorderLayout.CENTER);
		pnlNick.add(pnlNickTf, BorderLayout.CENTER);

		pnlCenter.add(pnlIp);
		pnlCenter.add(pnlNick);

		pnlSouth.setBorder(new EmptyBorder(10, 0, 0, 0));
		JPanel pnlBtnLogin = new JPanel(new BorderLayout());
		JPanel pnlBtnExit = new JPanel(new BorderLayout());
		Dimension btnDimension = new Dimension(75, 30);
		pnlBtnLogin.setPreferredSize(btnDimension);
		pnlBtnExit.setPreferredSize(btnDimension);
		pnlBtnLogin.add(btnLogin);
		pnlBtnExit.add(btnExit);
		pnlSouth.add(pnlBtnLogin);
		pnlSouth.add(pnlBtnExit);

		pnlContents.add(pnlCenter, BorderLayout.CENTER);
		pnlContents.add(pnlSouth, BorderLayout.SOUTH);

		pnlBackground.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlBackground.setOpaque(true);
		pnlBackground.setBackground(MyUtils.GRAY);

		rb.add(pnlContents);
		pnlBackground.add(rb, BorderLayout.CENTER);
		add(pnlBackground, BorderLayout.CENTER);

		ChattingUtils.setBgWhite(pnlIp, pnlNick, tfIp, tfNick, pnlCenter, pnlBtnLogin, pnlBtnExit, pnlSouth);
	}

	private void addListeners() {
		btnLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doLogin();
			}
		});
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(tfNick.getHeight());
				exit();
			}
		});
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				exit();
			}
		});
	}

	private void exit() {
		int result = JOptionPane.showConfirmDialog(Login.this, "종료하시겠습니까?", "프로그램 종료", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.YES_OPTION) {
			System.exit(0);
		} else {
		}
	}

	private void showFrame() {
		Image icon = Toolkit.getDefaultToolkit().getImage("img/icon.png");
		this.setIconImage(icon);
		setTitle("로그인");
		setSize(280, 280);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		// tfNick에 처음 커서가 가게 한다.
		tfNick.requestFocus();
		tfNick.setCaretPosition(0);
	}

	private void doLogin() {
		String ip = tfIp.getText();
		String nick = tfNick.getText();
		try {
			sock = new Socket(ip, 10001);
			oos = new ObjectOutputStream(sock.getOutputStream());
			pData = new PData(Protocol.CHECK_NICKNAME, nick);
			oos.writeObject(pData);
			oos.flush();
			oos.reset();

			ois = new ObjectInputStream(sock.getInputStream());
			Object data = null;
			data = ois.readObject();
			pData = (PData) data;
			pCode = pData.getPCode();
			if (pCode == Protocol.NICK_OK) {
				// 닉네임 사용가능, 접속 진행
				Vector<User> users = (Vector<User>) pData.getPObjects(0);
				HashMap<Integer, Room> rooms = (HashMap<Integer, Room>) pData.getPObjects(1);
				User myUser = (User) pData.getPObjects(2);
				InviteConfirm inviteconfirmUI = new InviteConfirm(myUser, oos);
				InviteFrame inviteframeUI = new InviteFrame(myUser, oos);
				PwInputFrame pwinputframeUI = new PwInputFrame(myUser, oos);
				RoomInfoInputFrame roomInfoUI = new RoomInfoInputFrame(myUser, oos);
				ChatFrame chatframeUI = new ChatFrame(myUser,roomInfoUI, oos);
				Vector<User> waitingChatters = rooms.get(0).getChatters();
				WaitingRoomUI waitingroomUI = new WaitingRoomUI(waitingChatters, rooms, myUser, pwinputframeUI, roomInfoUI, chatframeUI, sock, oos, ois);
				waitingroomUI.setVisible(true);
				dispose();
				new Client(waitingroomUI,chatframeUI,roomInfoUI,pwinputframeUI,inviteframeUI,inviteconfirmUI, sock, oos, ois, myUser);
			} else if (pCode == Protocol.SERVER_OCCUPIED) {
				// 대기실 인원 초과
				JOptionPane.showMessageDialog(Login.this, "현재 서버 인원초과로 접속할 수 없습니다. 잠시 후에 시도해주세요.", "서버 인원초과",
						JOptionPane.INFORMATION_MESSAGE);
			} else if (pCode == Protocol.NICK_NOT_OK) {
				JOptionPane.showMessageDialog(Login.this, "닉네임 중복으로 인한 사용불가. 다른 닉네임을 입력해 주세요.", "닉네임 사용불가",
						JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Login();
			}
		});
	}
}




