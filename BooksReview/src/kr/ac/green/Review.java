package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class Review extends JDialog implements ActionListener {
	private JTextArea taReview;
	
	private JButton btnSave;
	private JButton btnCancel;
	
	private BookList blOwner;
	private ReviewList rlOwner;
	private ReviewViewer rvOwner;
	private Book book;
	public Review(BookList owner, Book book, boolean modal) {
		this.book = book;
		setModal(modal);
		blOwner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	public Review(ReviewList owner, Book book, boolean modal) {
		this.book = book;
		setModal(modal);
		rlOwner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	public Review(ReviewViewer owner, Book book, boolean modal) {
		this.book = book;
		setModal(modal);
		rvOwner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		taReview = new JTextArea(20, 34);
		taReview.setLineWrap(true);
		taReview.setText(book.getReview());
		taReview.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		btnSave = new JButton("저장");
		btnCancel = new JButton("취소");
		MyUtils.setMyButton(btnSave, 1);
		MyUtils.setMyButton(btnCancel, 1);
	}
	
	public void setDisplay() {
		setLayout(new BorderLayout());
		
		JPanel pnlCenter = new JPanel();
		taReview.setBorder(new EmptyBorder(5,5,5,5));
		pnlCenter.add(taReview);
		JScrollPane scroll = new JScrollPane(taReview);
		scroll.setBorder(new EmptyBorder(10,10,10,10));
		add(scroll, BorderLayout.CENTER);
		
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlSouth.setBorder(new EmptyBorder(0,0,10,0));
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
			@Override
			public void windowActivated(WindowEvent we) {
				toFront();
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
		if(o == btnSave && blOwner != null) {
			int result = JOptionPane.showConfirmDialog(this, "저장하시겠습니까?","후기 저장",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				saveReview();
				dispose();
			}else {}
		}
		if(o == btnSave && rlOwner != null) {
			int result = JOptionPane.showConfirmDialog(this, "저장하시겠습니까?","후기 저장",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				saveReview();
				dispose();
			}else {}
		}
		if(o == btnSave && rvOwner != null) {
			int result = JOptionPane.showConfirmDialog(this, "저장하시겠습니까?","후기 저장",JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				rvOwner.setTaReview(taReview.getText());
				dispose();
			}else {}
		}
	}
	private void saveReview() {
		Vector<Book> temp = BookList.loadBooks();
		String review = taReview.getText();
		book.setReview(review);
		temp.remove(book);
		temp.add(book);
		BookList.saveBooks(temp);
	}
	public void showFrame() {
		setTitle("Review");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
}
