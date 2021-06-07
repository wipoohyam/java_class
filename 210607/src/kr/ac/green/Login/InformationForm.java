package kr.ac.green.Login;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class InformationForm extends JDialog implements ActionListener {
	private JTextArea taInfo;
	private JButton btnLogout;
	private JButton btnWithdraw;
	private User user;
	private LoginForm owner;
	public InformationForm(LoginForm owner, User user) {
		super(owner, "Information", true);		
		this.owner = owner;
		this.user = user;
		init();
		setDisplay();
		addListener();
		showDlg();
	}

	private void init() {		
		btnLogout = LoginUtils.getButton("Logout");
		btnWithdraw = LoginUtils.getButton("Withdraw");
		
		taInfo = new JTextArea(10, 40);
		taInfo.setEditable(false);
		TitledBorder tBorder = 	new TitledBorder(
				new LineBorder(Color.GRAY, 1), 
				"Check your Information"
		);		
		tBorder.setTitleFont(new Font(Font.DIALOG, Font.BOLD, 13));
		taInfo.setBorder(tBorder);
		taInfo.setText(user.toString());
	}

	private void setDisplay() {
		JPanel pnlBtns = new JPanel();
		pnlBtns.add(btnLogout);
		pnlBtns.add(btnWithdraw);
		
		add(taInfo, BorderLayout.CENTER);
		add(pnlBtns, BorderLayout.SOUTH);
	}

	private void addListener() {		
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we) {
				logout();
			}
		});

		btnLogout.addActionListener(this);
		btnWithdraw.addActionListener(this);
	}

	private void showDlg() {		
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	private void logout() {
		int result = JOptionPane.showConfirmDialog(
			this, 
			"logout : are you sure?",
			"Question",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE
		);
		if(result == JOptionPane.YES_OPTION) {
			owner.setVisible(true);
			dispose();
		}
	}
	private void withdraw() {
		int result = JOptionPane.showConfirmDialog(
			this, 
			"do you really want to withdraw?",
			"Question",
			JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE
		);
		if(result == JOptionPane.YES_OPTION) {
			String password = JOptionPane.showInputDialog(
				this,
				"input your password",
				"check password",
				JOptionPane.INFORMATION_MESSAGE			
			);
			String msg = "good bye~";
			
			boolean isCorrect = password.equals(user.getUpw());
			if(!isCorrect) {			
				msg = "wrong password";
			}
			JOptionPane.showMessageDialog(
				this,
				msg,
				"Message",
				JOptionPane.INFORMATION_MESSAGE			
			);
			if(isCorrect) {
				owner.removeUser(user);
				owner.setVisible(true);
				dispose();
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == btnLogout) {
			logout();
		} else {
			withdraw();
		}
	}
}