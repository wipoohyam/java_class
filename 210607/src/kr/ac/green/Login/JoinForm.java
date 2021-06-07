package kr.ac.green.Login;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;

public class JoinForm extends JDialog implements ActionListener{
	public static final int ID = 0;
	public static final int PW = 1;
	public static final int RE = 2;
	public static final int NAME = 3;
	public static final int NICK = 4;	
	public String[] names = 
		{"ID", "Password", "Retry", "Name", "NickName"}; 
	
	private JButton btnJoin;
	private JButton btnCancel;		
	private JTextComponent[] inputs;	
	private JRadioButton rbtnMale;
	private JRadioButton rbtnFemale;	
	private LoginForm owner;

	public JoinForm(LoginForm owner) {
		super(owner, "Join", true);
		this.owner = owner;
		init();
		setDisplay();
		addListener();
		showDlg();
	}

	private void init() {
		btnJoin = LoginUtils.getButton("Join");
		btnCancel = LoginUtils.getButton("Cancel");		
		inputs = new JTextComponent[] {
			LoginUtils.getTextComponent(LoginUtils.TEXT),
			LoginUtils.getTextComponent(LoginUtils.PASSWORD),
			LoginUtils.getTextComponent(LoginUtils.PASSWORD),
			LoginUtils.getTextComponent(LoginUtils.TEXT),
			LoginUtils.getTextComponent(LoginUtils.TEXT)
		};		
		ButtonGroup group = new ButtonGroup();		
		rbtnMale = new JRadioButton("Male", true);
		rbtnFemale = new JRadioButton("Female");		
		group.add(rbtnMale);
		group.add(rbtnFemale);		
	}

	private void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlNorth = new JPanel(new GridLayout(0, 1));
		for(int i=0; i<inputs.length; i++) {
			JPanel pnl = new JPanel();
			pnl.add(LoginUtils.getLabel(names[i]));
			pnl.add(inputs[i]);
			pnlNorth.add(pnl);
		}
		
		JPanel pnlGender = new JPanel();		
		pnlGender.add(rbtnMale);
		pnlGender.add(rbtnFemale);
		pnlGender.setBorder(new TitledBorder("Gender"));
				
		JPanel pnlBtns = new JPanel();		
		pnlBtns.add(btnJoin);
		pnlBtns.add(btnCancel);
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlGender, BorderLayout.CENTER);
		pnlMain.add(pnlBtns, BorderLayout.SOUTH);	
		
		TitledBorder tBorder = new TitledBorder(
			new EmptyBorder(5, 10, 5, 10), 
			"- Input your information"
		);
		tBorder.setTitleFont(new Font(Font.DIALOG, Font.BOLD, 13));	
		pnlMain.setBorder(tBorder);
		
		add(pnlMain, BorderLayout.CENTER);
	}

	private void addListener() {
		btnJoin.addActionListener(this);
		btnCancel.addActionListener(this);
		addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent we) {
					closeDlg();
				}
		});
	}

	private void showDlg() {
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void closeDlg() {
		// 부모창 눈에 보이게 설정
		owner.setVisible(true);
		// 자원해제
		dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnJoin) {
			
			boolean flag = true;
			String msg = "Join OK";
			// 입력 확인
			for(int idx=0; flag && idx<inputs.length; idx++) {
				if(LoginUtils.isEmpty(inputs[idx])) {
					flag = false;
					msg = "missing input : " + names[idx];
					inputs[idx].requestFocus();
				}
			}
			
			// 아이디 체크
			if(flag) {
				User user = owner.findUser(inputs[ID].getText());
				if(user != null) {
					flag = false;
					msg = "invalid ID : already existed";
					inputs[ID].requestFocus();
				}
			}

			// 비번 체크
			if(flag) {
				if(!inputs[PW].getText().equals(inputs[RE].getText())) {
					msg = "check your password";
					inputs[PW].requestFocus();
					flag = false;
				}
			}

			if(flag) {
				User user = new User(
					inputs[ID].getText(),
					inputs[PW].getText(),
					inputs[NAME].getText(),
					inputs[NICK].getText(),
					rbtnMale.isSelected() ? "male" : "female"				
				);
				owner.addUser(user);
				closeDlg();
			} 
			JOptionPane.showMessageDialog(
				this,
				msg,
				"Information",
				JOptionPane.INFORMATION_MESSAGE
			);			
		} else {
			closeDlg();
		}
	}
}