package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class PnlBook extends JPanel implements ActionListener{
	private JButton btnReview;	//후기 버튼
	private JLabel lblBookTitle;
	private Book book;
	public PnlBook(Book book) {
		this.book = book;
		init();
		setDisplay();
		addListeners();
	}
	
	public void init() {
		lblBookTitle = new JLabel(book.getTitle()+"("+book.getAuthor()+")");
		btnReview = new JButton("후기");
	}
	
	public void setDisplay() {
		lblBookTitle.setVerticalAlignment(JLabel.CENTER);
		setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		setLayout(new BorderLayout());
		
		JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		setOpaque(true);
		pnlCenter.setOpaque(false);
		pnlCenter.add(lblBookTitle);
		lblBookTitle.setPreferredSize(new Dimension(280,50));
		lblBookTitle.setHorizontalAlignment(JLabel.CENTER);
		lblBookTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		lblBookTitle.setForeground(new Color(0x333333));
		pnlCenter.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnReview.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		btnReview.setPreferredSize(new Dimension(70, 26));
		MyUtils.setMyButton(btnReview, MyUtils.DEFAULTBTN);
		pnlCenter.add(btnReview);
		
		add(pnlCenter, BorderLayout.CENTER);
	}
	private void addListeners() {
		btnReview.addActionListener(this);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				new ReviewViewer(book);
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == btnReview) {
			new Review(book);
		}
	}

}
