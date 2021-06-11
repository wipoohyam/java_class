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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class ReviewViewer extends JFrame implements ActionListener {
	//West
	private Dimension dImg;
	private JLabel lblBookImg;
	private File fDefault;
	private File fImg;

	private JLabel lblBookTitle;
	private JLabel lblBookTitleText;
	private JLabel lblBookAuthor;
	private JLabel lblBookAuthorText;
	private JLabel lblBookCompany;
	private JLabel lblBookCompanyText;
	private JLabel lblBookPages;
	private JLabel lblBookPagesText;
	public final static String NOTEXT = "미입력";
	
	private JLabel lblPeriod;
	private JLabel lblPeriodFrom;
	private JLabel lblWave;
	private JLabel lblPeriodTo;
	
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
	private JButton bCloseWindow;
	private JButton bDeleteAll;
	
	public ReviewViewer(){
		init();
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
		File fTest = new File("img/kids.jpg");
//		lblBookImg.setIcon(new ImageIcon(MyUtils.setImgSize(fTest.getAbsolutePath(), dImg)));
		MyUtils.setImgSize(lblBookImg, fTest, dImg);
		Dimension dText = new Dimension(100, 20);
		lblBookTitle = new JLabel("제목");
		lblBookTitleText = new JLabel(NOTEXT);
		lblBookTitleText.setPreferredSize(dText);
		lblBookAuthor = new JLabel("저자");
		lblBookAuthorText = new JLabel(NOTEXT);
		lblBookAuthorText.setPreferredSize(dText);
		lblBookCompany = new JLabel("출판사");
		lblBookCompanyText = new JLabel(NOTEXT);
		lblBookCompanyText.setPreferredSize(dText);
		lblBookPages = new JLabel("페이지");
		lblBookPagesText = new JLabel(NOTEXT);
		lblBookPagesText.setPreferredSize(dText);
		
		lblPeriod = new JLabel("읽은 기간", JLabel.CENTER);
		lblPeriod.setVerticalAlignment(JLabel.BOTTOM);
		lblPeriodFrom = new JLabel(NOTEXT);
		lblWave = new JLabel("~");
		lblPeriodTo = new JLabel(NOTEXT);
		
		lblRate = new JLabel("내 평점", JLabel.CENTER);
		lblRate.setVerticalAlignment(JLabel.BOTTOM);
		dStars = new Dimension(18,18);
		rate = 0;
		lblStars = new JLabel[5];
		for(int i=0;i<5;i++) {
			lblStars[i] = new JLabel();
		}
		MyUtils.setStarIcon(lblStars, 3.5, dStars);
		
		bEditBook = new JButton("수정");
		bEditBook.setVerticalAlignment(JButton.BOTTOM);
		
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
		bCloseWindow = new JButton("닫기");
		bDeleteAll = new JButton("전체삭제");
		
		Component comps[] = {
				lblBookTitle, lblBookTitleText, lblBookAuthor, lblBookAuthorText, lblBookCompany, lblBookCompanyText,
				lblBookPages, lblBookPagesText, lblPeriod, lblPeriodFrom, lblWave, lblPeriodTo,lblRate
		};
		MyUtils.setDefaultFont(comps);
	}
	private void setDisplay() {
		JPanel pnlWest = new JPanel(new BorderLayout());
		pnlWest.setBorder(new EmptyBorder(0,0,0,15));
		
		
		
		JPanel pnlBookInfo = new JPanel(new BorderLayout());
		JPanel pnlBookInfoLabel = new JPanel(new GridLayout(4,1));
		pnlBookInfoLabel.setPreferredSize(new Dimension(60, 100));
		JPanel pnlBookInfoText = new JPanel(new GridLayout(4,1));
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
		pnlBookInfoSouth.setBorder(new EmptyBorder(20,0,0,0));
		JPanel pnlBookInfoPeriod = new JPanel(new GridLayout(2,1));
		JPanel pnlBookInfoRate = new JPanel(new GridLayout(2,1));
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
		pnlEditBookBtn.add(bEditBook);
		pnlEditBookBtn.setBorder(new EmptyBorder(20,0,0,0));
		
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
		pnlReviewBtns.add(bEditReview);
		pnlReviewBtns.add(bDelReview);
		pnlEast.add(pnlReviewBtns, BorderLayout.SOUTH);
		
		//south
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlSouth.add(bCloseWindow);
		pnlSouth.add(bDeleteAll);
		
		JPanel pnlWrap = new JPanel(new BorderLayout());
		pnlWrap.add(pnlWest, BorderLayout.WEST);
		pnlWrap.add(pnlEast, BorderLayout.EAST);
		pnlWrap.add(pnlSouth, BorderLayout.SOUTH);
		pnlWrap.setBorder(new EmptyBorder(15,15,15,15));
		
		add(pnlWrap, BorderLayout.CENTER);
	}
	private void addListeners() {
		bEditBook.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == bEditBook) {
			new BookSaver();
		}
	}
	private void showFrame() {
		setTitle("책 제목"+" "+"후기");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		ReviewViewer r = new ReviewViewer();
		System.out.println(r.getWidth()+","+r.getHeight());
	}
}
