package kr.ac.green;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
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
	public Counter(CounterState state) {
		init(state.getNum());
		setDisplay();
		addListeners();
		showFrame(state.getLocation(), state.getSize());
	}
	private void init() {
		init("0");
	}
	private void init(String strNum) {
		lblNum = new JLabel(strNum, JLabel.CENTER);
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
		showFrame(null, new Dimension(200,400));
	}

	private void showFrame(Point location, Dimension size) {
		setTitle("Counter");
		setSize(size);
		if(location != null) {
			setLocation(location);
		}else {
			setLocationRelativeTo(null);
		}
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		lblNum.setText(String.valueOf(Integer.parseInt(lblNum.getText()) + 1));
	}
	
	public CounterState getCounterState() {
		CounterState state = new CounterState();
		state.setNum(lblNum.getText());
		state.setLocation(getLocation());
		state.setSize(getSize());
		return state;
	}
	public static void main(String[] args) {
		new Counter();
	}
}
