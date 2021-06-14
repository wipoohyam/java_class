package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Review extends JFrame implements ActionListener {
	private JTextArea taReview;
	
	private JButton btnSave;
	private JButton btnCancel;
	
	private Book book;
	public Review(Book book) {
		this.book = book;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		taReview = new JTextArea(20, 34);
		taReview.setLineWrap(true);
		taReview.setText(book.getReview());
		
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
	private void addListeners() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(Review.this, "작성을 취소하시겠습니까?", "작성 취소",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
		btnSave.addActionListener(this);
		btnCancel.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == btnCancel) {
			int result = JOptionPane.showConfirmDialog(this, "작성을 취소하시겠습니까?","작성 취소",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				dispose();
			}else if(result == JOptionPane.NO_OPTION) {}else {}
		}
		if(o == btnSave) {
			int result = JOptionPane.showConfirmDialog(this, "저장하시겠습니까?","후기 저장",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				Vector<Book> temp = BookList.loadBooks();
				temp.remove(book);
				book.setReview(taReview.getText());
				temp.add(book);
				BookList.saveBooks(temp);
				dispose();
			}else if(result == JOptionPane.NO_OPTION) {
				dispose();
			}else {}
		}
	}
	public void showFrame() {
		setTitle("Review");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
}
