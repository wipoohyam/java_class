package kr.ac.green.Login;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

// 프로그램 시작할때와 종료할때가 최적의 시간이다
public class LoginForm extends JFrame implements ActionListener {
	private JTextComponent tfId;
	private JTextComponent pfPw;

	private JButton btnLogin;
	private JButton btnJoin;

	private Vector<User> list;

	private final String USER_DATA = "userData.dat";

	private boolean changed = false;

	public LoginForm() {
		loadData();
		init();
		setDisplay();
		addListener();
		showFrame();
	}

	private Vector<User> loadData() {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		list = new Vector<>();
		try {
			fis = new FileInputStream("member.dat");
			ois = new ObjectInputStream(fis);
			
			list = (Vector<User>) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			
		}finally{
			MyUtils.closeAll(ois,fis);
		}
		return list;
	}

	private void init() {
		tfId = LoginUtils.getTextComponent(LoginUtils.TEXT);
		pfPw = LoginUtils.getTextComponent(LoginUtils.PASSWORD);
		btnJoin = LoginUtils.getButton("Join");
		btnLogin = LoginUtils.getButton("Login");
	}

	private void setDisplay() {
		JPanel pnlText = new JPanel(new GridLayout(0, 1));
		JPanel pnlInput = new JPanel(new GridLayout(0, 1));

		pnlText.add(LoginUtils.getLabel("ID"));
		pnlText.add(LoginUtils.getLabel("Password"));

		JPanel pnlId = new JPanel();
		pnlId.add(tfId);
		JPanel pnlPw = new JPanel();
		pnlPw.add(pfPw);
		pnlInput.add(pnlId);
		pnlInput.add(pnlPw);

		JPanel pnlSouth = new JPanel();
		pnlSouth.add(btnLogin);
		pnlSouth.add(btnJoin);

		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.add(pnlText, BorderLayout.WEST);
		pnlMain.add(pnlInput, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);

		pnlMain.setBorder(new EmptyBorder(5, 10, 5, 10));

		add(pnlMain, BorderLayout.CENTER);
	}

	private void addListener() {
		btnLogin.addActionListener(this);
		btnJoin.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(LoginForm.this, "exit?", "question",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					if (changed) {
						setVisible(false);
						saveData(list);
					}
					System.exit(0);
				}
			}
		});
	}

	private void showFrame() {
		setTitle("Login");
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	public static void saveData(Object obj) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		obj = (Object) obj;
		try {
			fos = new FileOutputStream("member.dat");
			oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.closeAll(oos, fos);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if (src == btnLogin) {
			JTextComponent input = null;
			String msg = "welcome!!";
			User user = null;
			if (LoginUtils.isEmpty(tfId)) {
				msg = "input your ID";
				input = tfId;
			} else {
				if (LoginUtils.isEmpty(pfPw)) {
					msg = "input your password";
					input = pfPw;
				} else {
					String uid = tfId.getText();
					String upw = pfPw.getText();
					user = findUser(uid);
					if (user == null) {
						msg = "check your ID";
						input = tfId;
					} else {
						if (!upw.equals(user.getUpw())) {
							msg = "check your password";
							input = pfPw;
						}
					}
				}
			}
			JOptionPane.showMessageDialog(this, msg, "Information", JOptionPane.INFORMATION_MESSAGE);
			if (input != null) {
				input.requestFocus();
			} else {
				clear();
				setVisible(false);
				new InformationForm(this, user);
			}
		} else {
			clear();
			setVisible(false);
			new JoinForm(this);
		}
	}

	private void clear() {
		tfId.setText("");
		pfPw.setText("");
	}

	public User findUser(String userId) {
		int idx = list.indexOf(new User(userId));
		if (idx >= 0) {
			return list.get(idx);
		} else {
			return null;
		}
	}

	public void addUser(User user) {
		if (findUser(user.getUid()) == null) {
			list.add(user);
			changed = true;
		}
	}

	public void removeUser(User user) {
		list.remove(user);
		changed = true;
	}

	public static void main(String[] args) {
		new LoginForm();
	}
}