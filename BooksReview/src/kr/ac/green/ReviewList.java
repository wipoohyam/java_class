package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ReviewList extends JFrame{
	private PnlReviewList[] pnlReviewList;	//후기 패널
	private JButton btnBack;				//종료 버튼
	
	public ReviewList() {
		init();
		setDisplay();
		showFrame();
	}
	
	public void init() {
		pnlReviewList = new PnlReviewList[5];
		for(int i=0; i<pnlReviewList.length; i++) {
			pnlReviewList[i] = new PnlReviewList();
//			pnlReviewList[i].setBorder(new EmptyBorder(0, 0, 5, 0));
		}
		
		btnBack = new JButton("뒤로가기");
	}
	
	public void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(0,120,0,120));
		JPanel pnlCenterMain = new JPanel(new GridLayout(0, 1));
		for(PnlReviewList list : pnlReviewList) {
			pnlCenterMain.add(list);
		}
		/*
		 * 그리드 때문에 패널 갯수 적어졌을 때 크기 변하지 않도록 여백
		 */
//		if(pnlReviewList.length < 7){
//			JPanel pnlEx1 = new JPanel();
//			pnlEx1.setBorder(new EmptyBorder(0,0,0,0));
//			JPanel pnlEx2 = new JPanel();
//			pnlEx2.setBorder(new EmptyBorder(0,0,0,0));
//			JPanel pnlEx3 = new JPanel();
//			pnlEx3.setBorder(new EmptyBorder(0,0,0,0));
//			JPanel pnlEx4 = new JPanel();
//			pnlEx4.setBorder(new EmptyBorder(0,0,0,0));
//			JPanel pnlEx5 = new JPanel();
//			pnlEx5.setBorder(new EmptyBorder(0,0,0,0));
			
//			pnlCenterMain.add(pnlEx1);
//			pnlCenter.add(pnlEx2);
//			pnlCenter.add(pnlEx3);
//			pnlCenter.add(pnlEx4);
//			pnlCenter.add(pnlEx5);
//		}
		
		
		JScrollPane scroll = new JScrollPane(pnlCenterMain);
        
        if(pnlReviewList.length < 4){
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        };

		int count = pnlReviewList.length/ 9 + 4;	//스크롤바 속도
		scroll.getVerticalScrollBar().setUnitIncrement(count);
		
		pnlCenter.add(scroll);
        
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlSouth.add(btnBack);
		
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlMain);
	}
	
	public void showFrame() {
		setTitle("Review List");
		setSize(700,700);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ReviewList();
	}
}
