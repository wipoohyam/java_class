package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PnlBook extends JPanel {
	private JButton btnReview;	//후기 버튼
	
	public PnlBook() {
		init();
		setDisplay();
	}
	
	public void init() {
		btnReview = new JButton("후기");
	}
	
	public void setDisplay() {
		setBorder(new LineBorder(Color.BLACK));
		setPreferredSize(new Dimension(300,50));
		setLayout(new BorderLayout());
		
		JPanel pnlCenter = new JPanel();
		JLabel pnlBook = new JLabel("책 제목(저자)");
		pnlBook.setBorder(new EmptyBorder(14, 0, 0, 0));
		pnlCenter.setBackground(new Color(82, 108, 235));
		pnlCenter.add(pnlBook);
		
		JPanel pnlEast = new JPanel();
		pnlEast.setBackground(new Color(82, 108, 235));
		pnlEast.setBorder(new EmptyBorder(8, 0, 0, 10));
		pnlEast.add(btnReview);	
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlEast, BorderLayout.EAST);
	}

}
