package kr.ac.green;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ReviewViewer extends JFrame {
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
	
	public ReviewViewer() {
		init();
		setDisplay();
		addListeners();
		showFrame();
		
	}
	private void init() {
		//west
		dImg = new Dimension(110, 170);
		lblBookImg = new JLabel();
		fDefault = new File("img/needBookImg.jpg");
		
		lblBookTitle = new JLabel("제목");
		lblBookTitleText = new JLabel(NOTEXT);
		lblBookAuthor = new JLabel("저자");
		lblBookAuthorText = new JLabel(NOTEXT);
		lblBookCompany = new JLabel("출판사");
		lblBookCompanyText = new JLabel(NOTEXT);
		lblBookPages = new JLabel("페이지");
		lblBookPagesText = new JLabel(NOTEXT);
		
		lblPeriod = new JLabel("읽은 기간");
		lblPeriodFrom = new JLabel(NOTEXT);
		lblWave = new JLabel("~");
		lblPeriodTo = new JLabel(NOTEXT);
		
		lblRate = new JLabel("내 평점");
		dStars = new Dimension(18,18);
		rate = 0;
		lblStars = new JLabel[5];
		for(int i=0;i<5;i++) {
			lblStars[i] = new JLabel();
		}
		
		bEditBook = new JButton("수정");
		
		//east
		dReview = new Dimension(200,240);
		taReview = new JTextArea();
		bEditReview = new JButton("수정");
		bDelReview = new JButton("삭제");
		
		//south
		bCloseWindow = new JButton("닫기");
		bDeleteAll = new JButton("전체삭제");
		
	}
	private void setDisplay() {
		JPanel pnlWest = new JPanel();
		
	}
	private void addListeners() {
		
	}
	private void showFrame() {
		
	}
}
