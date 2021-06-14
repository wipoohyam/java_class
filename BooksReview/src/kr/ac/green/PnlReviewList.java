package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class PnlReviewList extends JPanel implements ActionListener {
	private JLabel lblPhoto;		//å �̹���
	private Dimension dPhoto;
	private File fDefault;
	private JLabel lblBookTitle;	//å ����
	private JTextArea lblReview;		//�ı�
	private JLabel lblReadDay;		//���� �Ⱓ
	private JPanel pnlReviewPoint;	//����
	private JLabel[] lblStars;
	private Dimension dStars;
	
	private JButton btnModify;	//����
	private JButton btnDelete;	//����
	
	private Book book;
	

	public PnlReviewList(Book book) {
		this.book = book;
		init();
		setDisplay();
		addListeners();
	}
	
	public void init() {
		//����ڰ� ���� �ִ��� ������� �����صδ� �� ���� �� ���Ƽ� ������ ��Ƶ׾��!
		lblPhoto = new JLabel();
		lblPhoto.setPreferredSize(dPhoto);
		dPhoto = new Dimension(new Dimension(110, 170));
		fDefault = new File("img/needBookImg.jpg");
		if(book.getCover() != null) {
			MyUtils.setImgSize(lblPhoto, book.getCover() , dPhoto);
		}else {
			MyUtils.setImgSize(lblPhoto, fDefault , dPhoto);
		}
		
		lblBookTitle = new JLabel();
		lblBookTitle.setText(book.getTitle());
		lblBookTitle.setVerticalAlignment(JLabel.BOTTOM);
		lblBookTitle.setHorizontalAlignment(JLabel.LEFT);
		lblBookTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		lblReadDay = new JLabel();
		lblReadDay.setHorizontalAlignment(JLabel.RIGHT);
		lblReadDay.setText(book.getDateFrom()+"~"+book.getDateTo());
		MyUtils.setDefaultFont(lblReadDay);
		lblReview = new JTextArea(4,25);
		lblReview.setText(book.getReview());
		lblReview.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
		lblReview.setBorder(new EmptyBorder(0,0,0,0));
		lblReview.setEditable(false);
		lblReview.setCaretColor(Color.WHITE);
		lblReview.setLineWrap(true);
		//���� �� �κ�
		pnlReviewPoint = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		dStars = new Dimension(12,12);
		lblStars = new JLabel[5];
		for(int i = 0; i<5; i++) {
			lblStars[i] = new JLabel();
		}
		MyUtils.setStarIcon(lblStars, book.getRate(), dStars);
		
		btnModify = new JButton("����");
		btnDelete = new JButton("����");
		btnDelete.setActionCommand(book.getTitle());
	}
	
	public void setDisplay() {
		
		JPanel pnlMain = new JPanel(new BorderLayout());
//		pnlMain.setPreferredSize(new Dimension(500,160));
		pnlMain.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		
		JPanel pnlWest = new JPanel();
		pnlWest.add(lblPhoto);
		
		JPanel pnlEast = new JPanel(new BorderLayout());
		pnlEast.setBorder(new EmptyBorder(5,0,0,5));
		JPanel pnlENorth = new JPanel(new BorderLayout());
		JPanel pnlENEast = new JPanel(new GridLayout(0,1));
		//����
		for(int i = 0; i<5;i++) {
			pnlReviewPoint.add(lblStars[i]);
		}
		Dimension dLine = new Dimension(180,18);
		pnlReviewPoint.setPreferredSize(dLine);
		lblReadDay.setPreferredSize(dLine);
		pnlENEast.add(pnlReviewPoint);
		pnlENEast.add(lblReadDay);
		
		
		pnlReviewPoint.setOpaque(true);
		lblReadDay.setOpaque(true);
		
//		pnlReviewPoint.setBackground(Color.RED);
//		lblReadDay.setBackground(Color.BLUE);
		
		
		JPanel pnlENWEST = new JPanel(new BorderLayout());
		pnlENWEST.add(lblBookTitle, BorderLayout.CENTER);
		pnlENWEST.setPreferredSize(new Dimension(220,36));
		pnlENorth.add(pnlENWEST, BorderLayout.WEST);
		pnlENorth.add(pnlENEast, BorderLayout.EAST);
		pnlENorth.setBorder(new EmptyBorder(0,0,5,0));
		
		JPanel pnlECenter = new JPanel(new BorderLayout());
		JScrollPane spReview = new JScrollPane(lblReview);
		spReview.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		spReview.setBorder(new LineBorder(Color.LIGHT_GRAY, 1));
		pnlECenter.add(spReview, BorderLayout.CENTER);
		JPanel pnlESouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyUtils.setMyButton(btnModify, MyUtils.DEFAULTBTN);
		MyUtils.setMyButton(btnDelete, MyUtils.DEFAULTBTN);
		pnlESouth.add(btnModify);
		pnlESouth.add(btnDelete);
		
		pnlEast.add(pnlENorth, BorderLayout.NORTH);
		pnlEast.add(pnlECenter, BorderLayout.CENTER);
		pnlEast.add(pnlESouth, BorderLayout.SOUTH);
		
		pnlMain.add(pnlWest, BorderLayout.WEST);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		
		add(pnlMain, BorderLayout.CENTER);
	}
	private void addListeners() {
		btnModify.addActionListener(this);
		btnDelete.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == btnModify) {
			new Review(book);
		}
		if(o == btnDelete) {
			
		}
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}
	public Book getBook() {
		return book;
	}
}
