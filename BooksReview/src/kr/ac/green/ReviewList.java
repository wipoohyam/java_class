package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.border.EmptyBorder;

public class ReviewList extends JFrame implements ActionListener{
	private PnlReviewList[] pnlReviewList;	//후기 패널
	private JPanel pnlCenterMain;
	private JButton btnBack;				//종료 버튼
	private Vector<Book> books;
	public ReviewList() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		books = new BookList().getBooks();
		pnlReviewList = new PnlReviewList[books.size()];
		for(int i=0; i<pnlReviewList.length; i++) {
			pnlReviewList[i] = new PnlReviewList(books.get(i));
		}
		
		btnBack = new JButton("닫기");
	}
	public void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(10,10,0,10));
		pnlCenterMain = new JPanel(new GridLayout(0, 1));
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
		MyUtils.setMyButton(btnBack, MyUtils.DEFAULTBTN);
		pnlSouth.setBorder(new EmptyBorder(10,0,10,15));
		pnlSouth.add(btnBack);
		
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		add(pnlMain);
	}
	public void refreshReviewList() {
		books = new BookList().getBooks();
		pnlReviewList = new PnlReviewList[books.size()];
		for(int i=0; i<pnlReviewList.length; i++) {
			pnlReviewList[i] = new PnlReviewList(books.get(i));
		}
		pnlCenterMain.removeAll();
		for(PnlReviewList list : pnlReviewList) {
			pnlCenterMain.add(list);
		}
	}
	private void addListeners() {
		btnBack.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
				new BookList();
			}
		});

		for(int i = 0; i<pnlReviewList.length;i++) {
			pnlReviewList[i].getBtnDelete().addActionListener(this);
		}
		
	}
	public void refresh() {
		pnlCenterMain.updateUI();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == btnBack) {
			dispose();
			new BookList();
		}
		if(o instanceof JButton && o != null) {
			JButton btn = (JButton) o;
			for(int i=0; i<pnlReviewList.length; i++) {
				if(btn == pnlReviewList[i].getBtnDelete()) {
					int result = JOptionPane.showConfirmDialog(this, "선택한 도서와 후기를 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION) {
						pnlCenterMain.remove(pnlReviewList[i]);
						books.remove(pnlReviewList[i].getBook());
						BookList.saveBooks(books);
						pnlCenterMain.updateUI();
					}
				}
			}
		}
	}
	public void showFrame() {
		setTitle("Review List");
		setSize(580,600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
	}
	
}
