package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;

public class BookSaver extends JFrame implements ActionListener{
	
	//책이미지 크기 
	private Dimension dImg;
	private JLabel lblBookImg;
	private File fDefault;
	private File fImg;
	
	//책이미지 넣고 빼는버튼 
	JFileChooser chooser;
	private JButton bAddImg;
	private JButton bDelImg;
	
	//텍스트필드위에 있을 레이블 
	private JLabel lblBookTitle;
	private JLabel lblBookAuthor;
	private JLabel lblBookCompany;
	private JLabel lblBookPages;
	
	//책정보입력을 위한 텍스트필드 
	private JTextField tfBookTitle;
	private JTextField tfBookAuthor;
	private JTextField tfBookCompany;
	private JTextField tfBookPages;
	
	//읽은기간 
	private Dimension dRead;
	private JButton bRead;
	private JButton bReading;
	private Color selectedBg;
	private Color unselectedBg;
	
	//읽은기간을 달력으로 선택 
	private Calendar cal;
	public SimpleDateFormat sdf;
	public Point p;
	private JTextField tfDateFrom;
	private JLabel lblWave;
	private JTextField tfDateTo;
	public int pick;
	public static final int DATEFROM = 0;
	public static final int DATETO = 1;
	
	//평점을 별점으로 매기고 rate에 double로 저장
	private JLabel lblRate;
	private Dimension dStars;
	private JButton[] bStars;
	private double rate;
	
	private JButton bConfirm;
	private JButton bCancel;
	
	public BookSaver() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	public BookSaver(Book book) {
		init();
		loadBook(book);
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
		fDefault = new File("img/needBookImg.jpg");
		dImg = new Dimension(310, 450);
		lblBookImg = new JLabel("");
		lblBookImg.setPreferredSize(dImg);
		lblBookImg.setOpaque(true);
		MyUtils.setImgSize(lblBookImg, fDefault, dImg);
		
		chooser = new JFileChooser(".");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isFile()) {
					String fileName = f.getName();
					int idx = fileName.lastIndexOf(".");
					String ext = fileName.substring(idx);
					//질문 : 파일필터 복수로는 안되는지?
					return ext.equalsIgnoreCase(".jpg");
				} else {
					return true;
				}
			}

			@Override
			public String getDescription() {
				return "이미지 파일";
			}
		});
		bAddImg = new JButton("등록");
		bDelImg = new JButton("삭제");
		
		lblBookTitle = makeTfLabel("제목");
		lblBookAuthor = makeTfLabel("저자");
		lblBookCompany = makeTfLabel("출판사");
		lblBookPages = makeTfLabel("페이지수");
		
		tfBookTitle = makeTf(15);
		tfBookAuthor = makeTf(15);
		tfBookCompany = makeTf(15);
		tfBookPages = makeTf(15);

		dRead = new Dimension(103,28);
		selectedBg = new Color(255,193,7);
		unselectedBg = new Color(170,170,170);
		bRead = new JButton("읽은책");
		bRead.setActionCommand("selected");
		bRead.setPreferredSize(dRead);
		bRead.setBorderPainted(false);
		bRead.setOpaque(true);
		MyUtils.setDefaultFont(bRead);
		setCursorHand(bRead);
		bReading = new JButton("읽고있는책");
		bReading.setActionCommand("unselected");
		bReading.setPreferredSize(dRead);
		bReading.setBorderPainted(false);
		bReading.setOpaque(true);
		MyUtils.setDefaultFont(bReading);
		setCursorHand(bReading);
		setTabButton();
		
		cal = Calendar.getInstance();
		sdf = new SimpleDateFormat("yyyy.MM.dd");
		tfDateFrom = makeTf(8);
		tfDateFrom.setHorizontalAlignment(JTextField.CENTER);
		tfDateFrom.setText(sdf.format(cal.getTime()));
		tfDateFrom.setEditable(false);
		setCursorHand(tfDateFrom);
		lblWave = new JLabel("~");
		tfDateTo = makeTf(8);
		tfDateTo.setHorizontalAlignment(JTextField.CENTER);
		tfDateTo.setText(sdf.format(cal.getTime()));
		tfDateTo.setEditable(false);
		setCursorHand(tfDateTo);
		
		lblRate = new JLabel("내 평점", JLabel.CENTER);
		lblRate.setVerticalAlignment(JLabel.BOTTOM);
		dStars = new Dimension(24,24);
		bStars = new JButton[5];
		for(int i=0;i<5;i++) {
			bStars[i] = new JButton();
			bStars[i].setPreferredSize(dStars);
			bStars[i].setActionCommand("unchosen");
			bStars[i].setBorderPainted(false);
			bStars[i].setOpaque(false);
			bStars[i].setContentAreaFilled(false);
			bStars[i].setActionCommand(String.valueOf(i));
			setCursorHand(bStars[i]);
		}
		rate = -1;
		MyUtils.setStarIcon(bStars, rate, dStars);
		
		
		bConfirm = new JButton("저장");
		bCancel = new JButton("취소");
		
	}
	private void loadBook(Book book) {
		if(book.getCover() != null) {
			MyUtils.setImgSize(lblBookImg, book.getCover(), dImg);
		}
		tfBookTitle.setText(book.getTitle());
		tfBookAuthor.setText(book.getAuthor());
		tfBookCompany.setText(book.getCompany());
		tfBookPages.setText(book.getPages());
		tfDateFrom.setText(book.getDateFrom());
		tfDateTo.setText(book.getDateTo());
		rate = book.getRate();
		MyUtils.setStarIcon(bStars, rate, dStars);
		
	}
	private JLabel makeTfLabel(String txt) {
		JLabel lbl = new JLabel(txt);
		MyUtils.setDefaultFont(lbl);
		lbl.setBorder(new EmptyBorder(0,5,5,0));
		return lbl;
	}
	private JTextField makeTf(int length) {
		JTextField tf = new JTextField(length);
		tf.setBorder(new EmptyBorder(6,4,6,4));
		tf.setOpaque(true);
		tf.setBackground(Color.WHITE);
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
	
	private void setDisplay() {
		//책이미지 영역 
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.add(lblBookImg, BorderLayout.CENTER);
		pnlCenter.setBorder(new EmptyBorder(0,0,10,10));
		JPanel pnlImgBtns = new JPanel(new FlowLayout(FlowLayout.LEFT));
		MyUtils.setMyButton(bAddImg, MyUtils.DEFAULTBTN);
		MyUtils.setMyButton(bDelImg, MyUtils.DEFAULTBTN);
		pnlImgBtns.add(bAddImg);
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
		MyUtils.setMyButton(bConfirm, MyUtils.CONFIRMBTN);
		MyUtils.setMyButton(bCancel, MyUtils.CONFIRMBTN);
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
	public void setTfDateFrom(Calendar cal) {
		tfDateFrom.setText(sdf.format(cal.getTime()));
	}
	public void setTfDateTo(Calendar cal) {
		tfDateTo.setText(sdf.format(cal.getTime()));
	}
	private void addListeners() {
		//책이미지 추가삭제 이벤트리스너 
		bAddImg.addActionListener(this);
		bDelImg.addActionListener(this);
		//읽은기간 버튼 이벤트리스너 
		bRead.addActionListener(this);
		bReading.addActionListener(this);
		//저장,취소 버튼 
		bConfirm.addActionListener(this);
		bCancel.addActionListener(this);
		//평점 입력 버튼 이벤트리스너 
		for(int i=0;i<5;i++) {
			bStars[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					JButton selected = (JButton) me.getSource();
					rate = Integer.parseInt(selected.getActionCommand());
					Dimension d = dStars;
					double w = d.getWidth();
					double clickedX = me.getX();
					if ((clickedX < (w / 2))) {
						rate+=0.5;
						MyUtils.setStarIcon(bStars, rate, dStars);
					} else {
						rate+=1;
						MyUtils.setStarIcon(bStars, rate, dStars);
					}
				}
			});
		}
//		날짜입력버튼 이벤트 리스너 
		tfDateFrom.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pick = DATEFROM;
				p = new Point(me.getXOnScreen(),me.getYOnScreen()+10);
				new DatePicker(BookSaver.this, cal,true);
			}
		});
		tfDateTo.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				pick = DATETO;
				p = new Point(me.getXOnScreen(),me.getYOnScreen()+10);
				new DatePicker(BookSaver.this, cal,true);
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(BookSaver.this, "작성을 취소하시겠습니까?", "작성 취소",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					dispose();
				}
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		//이미지 추가버튼 
		if (obj == bAddImg) {
			int result = chooser.showOpenDialog(this);
			if(result == JFileChooser.APPROVE_OPTION) {
				fImg = chooser.getSelectedFile();
				MyUtils.setImgSize(lblBookImg, fImg, dImg);
			}
		}
		if (obj == bDelImg) {
			MyUtils.setImgSize(lblBookImg, fDefault, dImg);
//			fImg.delete();
		}
		//읽은책, 읽고있는책 탭처리 
		if(obj == bRead || obj == bReading) {
			bRead.setActionCommand("");
			bReading.setActionCommand("");
			JButton selected = (JButton) obj;
			selected.setActionCommand("selected");
			setTabButton();
		}
		//닫기버튼
		if(obj == bCancel) {
			int result = JOptionPane.showConfirmDialog(this, "작성을 취소하시겠습니까?","작성 취소",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				dispose();
				new BookList();
			}
		}
		//저장버튼 
		if(obj == bConfirm) {
			int result = JOptionPane.showConfirmDialog(BookSaver.this, "입력한 내용을 저장하시겠습니까?", "책 저장",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				saveBook();
				dispose();
				new BookList();
			}
		}
	}
	private void saveBook() {
		Book newBook = new Book(
			fImg,
			tfBookTitle.getText(),
			tfBookAuthor.getText(),
			tfBookCompany.getText(),
			tfBookPages.getText(),
			tfDateFrom.getText(),
			tfDateTo.getText(),
			rate
		);
		//벡터에 추가 
		Vector<Book> temp = BookList.loadBooks();
		temp.add(newBook);
		//파일로 저장 
		BookList.saveBooks(temp);
	}
	private void setCursorHand(Component c) {
		c.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}
	private void showFrame() {
		setTitle("도서 관리");
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BookSaver();
	}
}
