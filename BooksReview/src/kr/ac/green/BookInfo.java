package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BookInfo extends JFrame implements ActionListener{
	private Font defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
	private Dimension dImg;
	private JLabel lblBookImg;
	private ImageIcon icon;
	private Image img;
	private JButton bAddImg;
	private JButton bEditImg;
	private JButton bDelImg;
	
	private JLabel lblBookTitle;
	private JLabel lblBookAuthor;
	private JLabel lblBookCompany;
	private JLabel lblBookPages;

	private JTextField tfBookTitle;
	private JTextField tfBookAuthor;
	private JTextField tfBookCompany;
	private JTextField tfBookPages;
	
	private Dimension dReadOrNot;
	private JButton bRead;
	private JButton bReading;
	private Color selectedBg;
	private Color unselectedBg;
	
	private JTextField tfDateFrom;
	private JLabel lblWave;
	private JTextField tfDateTo;
	
	private JLabel lblRate;
	private Dimension dStars;
	private JButton[] bStars;
	public static final int EMPTYSTAR = 0;
	public static final int HALFSTAR = 1;
	public static final int FILLEDSTAR = 2;
	
	private JButton bConfirm;
	private JButton bCancel;
	
	public BookInfo() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	private void init() {
		dImg = new Dimension(310, 450);
		lblBookImg = new JLabel("");
		lblBookImg.setPreferredSize(dImg);
		lblBookImg.setOpaque(true);
		lblBookImg.setBackground(Color.RED);
		
		bAddImg = new JButton("추가");
		bEditImg = new JButton("수정");
		bDelImg = new JButton("삭제");
		
		lblBookTitle = makeTfLabel("제목");
		lblBookAuthor = makeTfLabel("저자");
		lblBookCompany = makeTfLabel("출판사");
		lblBookPages = makeTfLabel("페이지수");
		
		tfBookTitle = makeTf(15);
		tfBookAuthor = makeTf(15);
		tfBookCompany = makeTf(15);
		tfBookPages = makeTf(15);

		dReadOrNot = new Dimension(95,28);
		selectedBg = new Color(255,193,7);
		unselectedBg = new Color(170,170,170);
		bRead = new JButton("읽은책");
		bRead.setActionCommand("selected");
		bRead.setPreferredSize(dReadOrNot);
		bRead.setBorderPainted(false);
		bRead.setOpaque(true);
		bRead.setFont(defaultFont);
		
		bReading = new JButton("읽고있는책");
		bReading.setActionCommand("unselected");
		bReading.setPreferredSize(dReadOrNot);
		bReading.setBorderPainted(false);
		bReading.setOpaque(true);
		bReading.setFont(defaultFont);
		setTabButton();
		
		
		tfDateFrom = makeTf(6);
		lblWave = new JLabel("~");
		tfDateTo = makeTf(6);
		
		lblRate = new JLabel("내 평점", JLabel.CENTER);
		dStars = new Dimension(24,24);
		bStars = new JButton[5];
		for(int i=0;i<5;i++) {
			bStars[i] = new JButton();
			bStars[i].setPreferredSize(dStars);
			bStars[i].setActionCommand("unchosen");
			bStars[i].setBorderPainted(false);
			bStars[i].setActionCommand(String.valueOf(i));
		}
		setStarIcon(0, EMPTYSTAR);
		bConfirm = new JButton("저장");
		bCancel = new JButton("취소");
		
	}
	private JLabel makeTfLabel(String txt) {
		JLabel lbl = new JLabel(txt);
		lbl.setFont(defaultFont);
		lbl.setBorder(new EmptyBorder(0,5,10,0));
		return lbl;
	}
	private JTextField makeTf(int length) {
		JTextField tf = new JTextField(length);
		tf.setBorder(new EmptyBorder(6,4,6,4));
		return tf;
	}
	private void setTabButton() {
		if(bRead.getActionCommand().equals("selected")) {
			bRead.setBackground(selectedBg);
			bRead.setForeground(Color.BLACK);
			bReading.setBackground(unselectedBg);
			bReading.setForeground(Color.WHITE);
		}else {
			bRead.setBackground(unselectedBg);
			bRead.setForeground(Color.WHITE);
			bReading.setBackground(selectedBg);
			bReading.setForeground(Color.BLACK);
		}
	}
	private void setStarIcon(int idx, int which) {
		for(int i = 0; i<idx; i++) {
			bStars[i].setIcon(new ImageIcon(setImgSize("img/star_filled.png", dStars)));
		}
		for(int j=4;j>idx;j--) {
			bStars[j].setIcon(new ImageIcon(setImgSize("img/star_empty.png", dStars)));
		}
		if(which == EMPTYSTAR) {
			bStars[idx].setIcon(new ImageIcon(setImgSize("img/star_empty.png", dStars)));
		}else if(which == HALFSTAR) {
			bStars[idx].setIcon(new ImageIcon(setImgSize("img/star_half.png", dStars)));
		}else if(which == FILLEDSTAR) {
			bStars[idx].setIcon(new ImageIcon(setImgSize("img/star_filled.png", dStars)));
		}
	}
	private Image setImgSize(String path, Dimension d) {
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		Image newImg = img.getScaledInstance((int) d.getWidth(), (int) d.getHeight(), 0);
		return newImg;
	}
	private void setDisplay() {
		//책이미지 영역 
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(lblBookImg, BorderLayout.CENTER);
		pnlCenter.setBorder(new EmptyBorder(0,0,10,10));
		JPanel pnlImgBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlImgBtns.add(bAddImg);
		pnlImgBtns.add(bEditImg);
		pnlImgBtns.add(bDelImg);
		pnlCenter.add(pnlImgBtns, BorderLayout.SOUTH);
		
		
		//책정보 영역 
		JPanel pnlEast = new JPanel(new BorderLayout());
		
		//정보입력창 
		JPanel pnlEastNorth = new JPanel(new GridLayout(4,1));
		
		EmptyBorder eb10b = new EmptyBorder(0,0,10,0);
		JPanel pnlTitle = new JPanel(new BorderLayout());
		pnlTitle.add(lblBookTitle, BorderLayout.CENTER);
		pnlTitle.add(tfBookTitle, BorderLayout.SOUTH);
		pnlTitle.setBorder(eb10b);
		
		JPanel pnlAuthor = new JPanel(new BorderLayout());
		pnlAuthor.add(lblBookAuthor, BorderLayout.CENTER);
		pnlAuthor.add(tfBookAuthor, BorderLayout.SOUTH);
		pnlAuthor.setBorder(eb10b);
		
		JPanel pnlCompany = new JPanel(new BorderLayout());
		pnlCompany.add(lblBookCompany, BorderLayout.CENTER);
		pnlCompany.add(tfBookCompany, BorderLayout.SOUTH);
		pnlCompany.setBorder(eb10b);
		
		JPanel pnlPages = new JPanel(new BorderLayout());
		pnlPages.add(lblBookPages, BorderLayout.CENTER);
		pnlPages.add(tfBookPages, BorderLayout.SOUTH);
		pnlPages.setBorder(eb10b);

		pnlEastNorth.add(pnlTitle);
		pnlEastNorth.add(pnlAuthor);
		pnlEastNorth.add(pnlCompany);
		pnlEastNorth.add(pnlPages);
		
		JPanel pnlEastSouth = new JPanel(new BorderLayout());
		JPanel pnlEastBtns = new JPanel(new GridLayout(2,1));
		//기간입력 
		JPanel pnlPeriod = new JPanel(new GridLayout(2,1));
		JPanel pnlPeriodBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlPeriodBtns.add(bRead);
		pnlPeriodBtns.add(bReading);
		pnlPeriod.add(pnlPeriodBtns);
		JPanel pnlPeriodDates = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlPeriodDates.add(tfDateFrom);
		pnlPeriodDates.add(lblWave);
		pnlPeriodDates.add(tfDateTo);
		pnlPeriod.add(pnlPeriodDates);
		pnlPeriod.setBorder(new EmptyBorder(0,0,5,0));

		
		
		//평점 
		JPanel pnlRates = new JPanel(new GridLayout(2,1));
		pnlRates.add(lblRate);
		JPanel pnlStars = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(int i=0; i<5; i++) {
			pnlStars.add(bStars[i]);
		}
		pnlRates.add(pnlStars);
		
		pnlEastBtns.add(pnlPeriod);
		pnlEastBtns.add(pnlRates);
		pnlEastSouth.setBorder(new EmptyBorder(20,0,0,0));
		pnlEastSouth.add(pnlEastBtns, BorderLayout.NORTH);
		
		pnlEast.add(pnlEastNorth,BorderLayout.NORTH);
		pnlEast.add(pnlEastSouth, BorderLayout.CENTER);
		
		//저장,취소 버튼 
		JPanel pnlBtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlBtns.add(bConfirm);
		pnlBtns.add(bCancel);
		
		//감싸기 
		JPanel pnlWrap = new JPanel(new BorderLayout());
		pnlWrap.add(pnlCenter, BorderLayout.CENTER);
		pnlWrap.add(pnlEast, BorderLayout.EAST);
		pnlWrap.add(pnlBtns, BorderLayout.SOUTH);
		pnlWrap.setBorder(new EmptyBorder(15,15,15,15));
		
		add(pnlWrap, BorderLayout.CENTER);
		
		
	}
	private void addListeners() {
//		for(int i=0;i<5;i++) {
//			bStars[i].addActionListener(this);
//		}
		bRead.addActionListener(this);
		bReading.addActionListener(this);
		for(int i=0;i<5;i++) {
			bStars[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					JButton selected = (JButton) me.getSource();
					int idx = Integer.parseInt(selected.getActionCommand());
					Dimension d = dStars;
					double w = d.getWidth();
					double clickedX = me.getX();
					System.out.println(idx+":"+w+","+clickedX);
					if ((clickedX < (w / 2))) {
						setStarIcon(idx, HALFSTAR);
					} else {
						setStarIcon(idx, FILLEDSTAR);
					}
				}
			});
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		//질문할것: obj ==bStars는 왜 안되는가?? 안되면 어떻게 해야하는가,, 노가다를 해야하는가..
//		if(obj == bStars[0] || obj == bStars[1] || obj == bStars[2] || obj == bStars[3] || obj == bStars[4]){
//			JButton selected = (JButton) obj;
//			int idx = Integer.parseInt(selected.getActionCommand());
//			setStarIcon(idx, FILLEDSTAR);
//		}
		if(obj == bRead || obj == bReading) {
			bRead.setActionCommand("");
			bReading.setActionCommand("");
			JButton selected = (JButton) obj;
			selected.setActionCommand("selected");
			setTabButton();
		}
	}
	private void showFrame() {
		setTitle("도서 관리");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BookInfo();
	}
}
