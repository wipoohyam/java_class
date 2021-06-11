package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class PnlReviewList extends JPanel {
	private JLabel lblPhoto;		//책 이미지
	private JLabel lblBookTitle;	//책 제목
	private JTextArea lblReview;		//후기
	private JLabel lblReadDay;		//읽은 기간
	private JLabel lblReviewPoint;	//별점
	
	private JButton btnModify;	//수정
	private JButton btnDelete;	//삭제
	
	public PnlReviewList() {
		init();
		setDisplay();
	}
	
	public void init() {
		//사용자가 사진 넣더라도 사이즈는 통일해두는 게 좋을 것 같아서 사이즈 잡아뒀어요!
		lblPhoto = new JLabel();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("아몬드.png");
		img = img.getScaledInstance(85, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(img);
		lblPhoto.setIcon(icon);
		
		lblBookTitle = new JLabel();
		lblBookTitle.setText("책 제목");
		lblReadDay = new JLabel();
		lblReadDay.setText("2021.06.11 ~ 2021.07.11");
		lblReview = new JTextArea(3, 30);
		//별점 들어갈 부분
		lblReviewPoint = new JLabel();
		
		btnModify = new JButton("수정");
		btnDelete = new JButton("삭제");
	}
	
	public void setDisplay() {
		setPreferredSize(new Dimension(300,40));
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(new LineBorder(Color.GRAY, 2));
		
		JPanel pnlWest = new JPanel();
		pnlWest.add(lblPhoto);
		
		JPanel pnlEast = new JPanel(new BorderLayout());
		
		JPanel pnlENorth = new JPanel(new BorderLayout());
		JPanel pnlENCenter = new JPanel();
		JPanel pnlENEast = new JPanel(new GridLayout(0,1));
		//별점
		pnlENEast.add(lblReviewPoint);
		pnlENEast.add(lblReadDay);
		
		pnlENorth.add(lblBookTitle, BorderLayout.WEST);
		pnlENorth.add(pnlENCenter, BorderLayout.CENTER);
		pnlENorth.add(pnlENEast, BorderLayout.EAST);
		
		JPanel pnlECenter = new JPanel();
		pnlECenter.add(lblReview);
		
		JPanel pnlESouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlESouth.add(btnModify);
		pnlESouth.add(btnDelete);
		
		pnlEast.add(pnlENorth, BorderLayout.NORTH);
		pnlEast.add(pnlECenter, BorderLayout.CENTER);
		pnlEast.add(pnlESouth, BorderLayout.SOUTH);
		
		pnlMain.add(pnlWest, BorderLayout.WEST);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		
		add(pnlMain);
	}
}
