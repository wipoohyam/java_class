package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Counter extends JFrame implements ActionListener {
	
	private JLabel lblNum;
	private JButton btnPlus;
	
	public Counter() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		lblNum = new JLabel("0", JLabel.CENTER);
		lblNum.setFont(new Font(Font.DIALOG, Font.BOLD, 60));
		
		btnPlus = new JButton("Plus");
	}

	private void setDisplay() {
		add(lblNum, BorderLayout.CENTER);
		add(btnPlus, BorderLayout.SOUTH);
	}

	private void addListeners() {
		btnPlus.addActionListener(this);
	}
	
	private void showFrame() {
		setTitle("Counter");
		setSize(300, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		lblNum.setText(String.valueOf(Integer.parseInt(lblNum.getText()) + 1));
	}

	public static void main(String[] args) {
		new Counter();
	}
}
