package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ReviewViewer extends JDialog implements ActionListener {
	//West
	private Dimension dImg;
	private JLabel lblBookImg;
	private File fDefault;
	private File fImg;

	private JPanel pnlBookInfoText;
	private JLabel lblBookTitle;
	private JLabel lblBookTitleText;
	private JLabel lblBookAuthor;
	private JLabel lblBookAuthorText;
	private JLabel lblBookCompany;
	private JLabel lblBookCompanyText;
	private JLabel lblBookPages;
	private JLabel lblBookPagesText;
	
	private boolean read;
	private JLabel lblPeriod;
	private JLabel lblPeriodFrom;
	private JLabel lblWave;
	private JLabel lblPeriodTo;
	
	private JPanel pnlBookInfoRate;
	private JLabel lblRate;
	private Dimension dStars;
	private double rate;
	private JLabel[] lblStars;
	
	private JButton bEditBook;
	
	//East
	private Dimension dReview;
	private JTextArea taReview;
	private JButton bEditReview;
	private JButton bDelReview;
	
	//South
	private JButton bSave;
	private JButton bCloseWindow;
	private JButton bDeleteAll;
	
	//불러온책, 이 변수는 저장 후 확인 전까지 수정하지 않는다.
	private Book book;
	//수정사항들을 담을 책 변수 
	private Book tempBook;
	
	private ReviewList rlOwner;
	private BookList blOwner;
	
	//책을 불러온다(book) 불러온 책은 저장하지 않을경우를 생각해 그대로 두고 
	public ReviewViewer(BookList owner, Book book, boolean modal){
		this.blOwner = owner;
		this.book = book;
		this.tempBook = book;
		init();
		setBookInfo(book);
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public ReviewViewer(ReviewList owner, Book book, boolean modal){
		this.rlOwner = owner;
		this.book = book;
		this.tempBook = book;
		init();
		setBookInfo(book);
		setDisplay();
		addListeners();
		showFrame();
	}
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//west
		dImg = new Dimension(220, 340);
		lblBookImg = new JLabel();
		lblBookImg.setPreferredSize(dImg);
		fDefault = new File("img/needBookImg.jpg");
		pnlBookInfoText = new JPanel(new GridLayout(4,1));
		Dimension dText = new Dimension(100, 20);
		lblBookTitle = new JLabel("제목");
		lblBookTitleText = new JLabel();
		lblBookTitleText.setPreferredSize(dText);
		lblBookAuthor = new JLabel("저자");
		lblBookAuthorText = new JLabel();
		lblBookAuthorText.setPreferredSize(dText);
		lblBookCompany = new JLabel("출판사");
		lblBookCompanyText = new JLabel();
		lblBookCompanyText.setPreferredSize(dText);
		lblBookPages = new JLabel("총 페이지");
		lblBookPagesText = new JLabel();
		lblBookPagesText.setPreferredSize(dText);
		
		lblPeriod = new JLabel("", JLabel.CENTER);
		lblPeriod.setVerticalAlignment(JLabel.BOTTOM);
		lblPeriodFrom = new JLabel();
		lblWave = new JLabel("~");
		lblPeriodTo = new JLabel();
		pnlBookInfoRate = new JPanel(new GridLayout(2,1));
		lblRate = new JLabel("내 평점", JLabel.CENTER);
		lblRate.setVerticalAlignment(JLabel.BOTTOM);
		dStars = new Dimension(18,18);
		lblStars = new JLabel[5];
		for(int i=0;i<5;i++) {
			lblStars[i] = new JLabel();
		}
		MyUtils.setStarIcon(lblStars, -1, dStars);
		
		bEditBook = new JButton("수정");
		bEditBook.setVerticalAlignment(JButton.CENTER);
		
		//east
		dReview = new Dimension(440,540);
		taReview = new JTextArea();
		taReview.setPreferredSize(dReview);
		taReview.setEditable(false);
		taReview.setCaretColor(Color.WHITE);
		taReview.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		taReview.setBorder(new EmptyBorder(10,10,10,10));
		taReview.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		taReview.setLineWrap(true);
		
		
		
		
		bEditReview = new JButton("수정");
		bDelReview = new JButton("삭제");
		
		//south
		bSave = new JButton("저장");
		bCloseWindow = new JButton("닫기");
		bDeleteAll = new JButton("전체삭제");
		
		Component comps[] = {
				lblBookTitle, lblBookTitleText, lblBookAuthor, lblBookAuthorText, lblBookCompany, lblBookCompanyText,
				lblBookPages, lblBookPagesText, lblPeriod, lblPeriodFrom, lblWave, lblPeriodTo,lblRate
		};
		MyUtils.setDefaultFont(comps);
	}
	public void setBookInfo(Book tBook) {
		tempBook = tBook;
		if(tempBook.getCover() != null) {
			fImg = tempBook.getCover();
		}else {
			fImg = fDefault;
		}
		MyUtils.setImgSize(lblBookImg, fImg, dImg);
		lblBookTitleText.setText(tempBook.getTitle());
		lblBookAuthorText.setText(tempBook.getAuthor());
		lblBookCompanyText.setText(tempBook.getCompany());
		lblBookPagesText.setText(tempBook.getPages());
		read = tempBook.getRead();
		if(read) {
			lblPeriod.setText("읽은기간(읽은 책)");
			lblPeriodTo.setText(tempBook.getDateTo());
		}else {
			lblPeriod.setText("읽은기간(읽고있는 책)");
			lblPeriodTo.setText("");
		}
		lblPeriodFrom.setText(tempBook.getDateFrom());
		rate = tempBook.getRate();
		pnlBookInfoText.revalidate();
		MyUtils.setStarIcon(lblStars, tempBook.getRate(), dStars);
		pnlBookInfoRate.revalidate();
		taReview.setText(tempBook.getReview());
		validate();
	}
	public Book getBookInfo() {
		if(fImg != null) {
			tempBook.setCover(fImg);
		}else {
			tempBook.setCover(fDefault);
		}
		tempBook.setTitle(lblBookTitleText.getText());
		tempBook.setAuthor(lblBookAuthorText.getText());
		tempBook.setCompany(lblBookCompanyText.getText());
		tempBook.setPages(lblBookPagesText.getText());
		tempBook.setRead(read);
		tempBook.setDateFrom(lblPeriodFrom.getText());
		tempBook.setDateTo(lblPeriodTo.getText());
		tempBook.setRate(rate);
		return tempBook;
	}
	private void setDisplay() {
		JPanel pnlWest = new JPanel(new BorderLayout());
		pnlWest.setBorder(new EmptyBorder(0,0,0,15));
		
		
		
		JPanel pnlBookInfo = new JPanel(new BorderLayout());
		JPanel pnlBookInfoLabel = new JPanel(new GridLayout(4,1));
		pnlBookInfoLabel.setPreferredSize(new Dimension(60, 100));
		pnlBookInfoText.setPreferredSize(new Dimension(160, 100));
		pnlBookInfoLabel.add(lblBookTitle);
		pnlBookInfoLabel.add(lblBookAuthor);
		pnlBookInfoLabel.add(lblBookCompany);
		pnlBookInfoLabel.add(lblBookPages);
		pnlBookInfoLabel.setBorder(new EmptyBorder(0,10,0,10));
		pnlBookInfoText.add(lblBookTitleText);
		pnlBookInfoText.add(lblBookAuthorText);
		pnlBookInfoText.add(lblBookCompanyText);
		pnlBookInfoText.add(lblBookPagesText);
		pnlBookInfo.add(pnlBookInfoLabel, BorderLayout.WEST);
		pnlBookInfo.add(pnlBookInfoText, BorderLayout.CENTER);
		
		JPanel pnlBookInfoSouth = new JPanel(new GridLayout(4,1));
		pnlBookInfoSouth.setPreferredSize(new Dimension(220, 220));
		JPanel pnlBookInfoPeriod = new JPanel(new GridLayout(2,1));
		JPanel pnlPeriodLine = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlPeriodLine.add(lblPeriodFrom);
		pnlPeriodLine.add(lblWave);
		pnlPeriodLine.add(lblPeriodTo);
		pnlBookInfoPeriod.add(lblPeriod);
		pnlBookInfoPeriod.add(pnlPeriodLine);
		
		pnlBookInfoRate.add(lblRate);
		JPanel pnlStars = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(int i=0;i<5;i++) {
			pnlStars.add(lblStars[i]);
		}
		pnlBookInfoRate.add(pnlStars);
		
		
		JPanel pnlEditBookBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
		MyUtils.setMyButton(bEditBook, MyUtils.DEFAULTBTN);
		pnlEditBookBtn.add(bEditBook);
		pnlEditBookBtn.setBorder(new EmptyBorder(10,0,0,0));
		
		pnlBookInfoSouth.add(pnlBookInfoPeriod);
		pnlBookInfoSouth.add(pnlBookInfoRate);
		pnlBookInfoSouth.add(pnlEditBookBtn);

		pnlBookInfo.add(pnlBookInfoSouth, BorderLayout.SOUTH);
		
		pnlWest.add(lblBookImg, BorderLayout.NORTH);
		pnlWest.add(pnlBookInfo, BorderLayout.CENTER);
		
		
		//east
		JPanel pnlEast = new JPanel(new BorderLayout());
		pnlEast.add(new JScrollPane(taReview, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
		JPanel pnlReviewBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyUtils.setMyButton(bEditReview, MyUtils.DEFAULTBTN);
		MyUtils.setMyButton(bDelReview, MyUtils.DEFAULTBTN);
		pnlReviewBtns.add(bEditReview);
		pnlReviewBtns.add(bDelReview);
		pnlEast.add(pnlReviewBtns, BorderLayout.SOUTH);
		
		//south
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		MyUtils.setMyButton(bSave, MyUtils.CONFIRMBTN);
		MyUtils.setMyButton(bCloseWindow, MyUtils.CONFIRMBTN);
		MyUtils.setMyButton(bDeleteAll, MyUtils.CONFIRMBTN);
		pnlSouth.add(bSave);
		pnlSouth.add(bCloseWindow);
		pnlSouth.add(bDeleteAll);
		
		JPanel pnlWrap = new JPanel(new BorderLayout());
		pnlWrap.add(pnlWest, BorderLayout.WEST);
		pnlWrap.add(pnlEast, BorderLayout.EAST);
		pnlWrap.add(pnlSouth, BorderLayout.SOUTH);
		pnlWrap.setBorder(new EmptyBorder(15,15,15,15));
		
		add(pnlWrap, BorderLayout.CENTER);
	}
	private boolean isChanged() {
		boolean changed = false;
		if(!book.getTitle().equals(tempBook.getTitle()) ||
			!book.getAuthor().equals(tempBook.getAuthor()) ||
			!book.getCompany().equals(tempBook.getCompany()) ||
			!book.getPages().equals(tempBook.getPages()) ||
			!book.getDateFrom().equals(tempBook.getDateFrom()) ||
			!book.getDateTo().equals(tempBook.getDateTo()) ||
			book.getRate() != tempBook.getRate()
			) {
			changed= true;
			return changed;
		}
		if(!((book.getCover() == fDefault) && (tempBook.getCover() ==fDefault))) {
			if(!book.getCover().equals(tempBook.getCover())) {
				changed = true;
				return changed;
			}
		}
		if(!(book.getReview() == null && getTaReview() == null)) {
			if(!book.getReview().equals(getTaReview())) {
				changed = true;
				return changed;
			}
		}
		return changed;
	}
	private void addListeners() {
		bEditBook.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
				setOwnersEnabled();
			}
		});
		bSave.addActionListener(this);
		bCloseWindow.addActionListener(this);
		bEditReview.addActionListener(this);
		bDelReview.addActionListener(this);
		bDeleteAll.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == bEditBook) {
			new BookSaver(ReviewViewer.this, tempBook, true);
			this.setEnabled(false);
		}
		if(o == bCloseWindow) {
			if(isChanged()) {
				int result = JOptionPane.showConfirmDialog(ReviewViewer.this, "수정 중인 정보는 저장되지 않습니다.정말 닫으시겠습니까?", "창닫기",
						JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					setBookInfo(book);
					dispose();
					setOwnersEnabled();
				}else {}
			}else {
				dispose();
				setOwnersEnabled();
			}
		}
		if(o == bEditReview) {
			new Review(ReviewViewer.this, book, true);
			this.setEnabled(false);
		}
		if(o== bDelReview) {
			int result = JOptionPane.showConfirmDialog(ReviewViewer.this, "작성된 리뷰를 삭제하시겠습니까? 삭제된 리뷰는 저장 후에 반영됩니다.", "리뷰 삭제",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				taReview.setText("");
			}
		}
		if(o == bSave) {
			int result = JOptionPane.showConfirmDialog(ReviewViewer.this, "수정사항을 모두 저장하시겠습니까?", "후기 수정",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				Vector<Book> temp = BookList.loadBooks();
				int idx = temp.indexOf(book);
				
				tempBook.setReview(getTaReview());
				temp.set(idx, tempBook);
				BookList.saveBooks(temp);
				dispose();
				setOwnersEnabled();
			}else {}
		}
		if(o==bDeleteAll) {
			int result = JOptionPane.showConfirmDialog(ReviewViewer.this, "책정보와 리뷰 모두 삭제하시겠습니까?", "전체 삭제",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				Vector<Book> temp = BookList.loadBooks();
				temp.remove(book);
				BookList.saveBooks(temp);
				dispose();
				setOwnersEnabled();
			}
		}
	}
	private void setOwnersEnabled() {
		try {
			rlOwner.setEnabled(true);
		} catch(Exception e) {}
		try {
			blOwner.setEnabled(true);
			blOwner.setBooksShow();
		} catch(Exception e) {}
	}
	public void setTaReview(String review) {
		taReview.setText(review);
		tempBook.setReview(review);
	}
	public String getTaReview() {
		return taReview.getText();
	}
	public void setTempBook(Book book) {
		this.tempBook = book;
	}
	public Book getTempBook() {
		return tempBook;
	}
	private void showFrame() {
		setTitle(book.getTitle()+" "+"후기");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
	}
}
