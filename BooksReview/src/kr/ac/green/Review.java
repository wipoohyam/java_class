package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Review extends JFrame {
	private JTextArea taReview;
	
	private JButton btnSave;
	private JButton btnCancel;
	
	public Review() {
		init();
		setDisplay();
		showFrame();
	}
	
	public void init() {
		taReview = new JTextArea(20, 34);
		taReview.setLineWrap(true);
		
		btnSave = new JButton("저장");
		btnCancel = new JButton("취소");
	}
	
	public void setDisplay() {
		setLayout(new BorderLayout());
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.add(taReview);
		JScrollPane scroll = new JScrollPane(taReview);
		scroll.setBorder(new EmptyBorder(10,10,10,10));
		add(scroll, BorderLayout.CENTER);
		
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlSouth.add(btnSave);
		pnlSouth.add(btnCancel);
		
		add(pnlSouth, BorderLayout.SOUTH);
	}
	
	public void showFrame() {
		setTitle("Review");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new Review();
	}
}
