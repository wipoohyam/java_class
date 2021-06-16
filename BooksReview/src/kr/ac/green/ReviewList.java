package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

public class ReviewList extends JDialog implements ActionListener{
	private PnlReviewList[] pnlReviewList;	//후기 패널
	private JPanel pnlCenterMain;
	private JScrollPane scroll;
	private JButton btnBack;				//종료 버튼
	private Vector<Book> books;
	private BookList owner;
	public ReviewList(BookList owner, boolean modal) {
		this.owner = owner;
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	public void init() {
		books = BookList.loadBooks();
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
		
		
		scroll = new JScrollPane(pnlCenterMain);
        
		refreshList();
        
		pnlCenter.add(scroll);
        
		JPanel pnlSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		MyUtils.setMyButton(btnBack, MyUtils.DEFAULTBTN);
		pnlSouth.setBorder(new EmptyBorder(10,0,10,15));
		pnlSouth.add(btnBack);
		
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		add(pnlMain);
	}
	private void addListeners() {
		btnBack.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				dispose();
				owner.setEnabled(true);
			}
			@Override
			public void windowActivated(WindowEvent we) {
				refreshList();
			}
		});

		for(int i = 0; i<pnlReviewList.length;i++) {
			pnlReviewList[i].getBtnDelete().addActionListener(this);
			pnlReviewList[i].getBtnModify().addActionListener(this);
			pnlReviewList[i].getBtnOpen().addActionListener(this);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o == btnBack) {
			dispose();
			owner.setBooksShow();
			owner.setEnabled(true);
		}
		if(o instanceof JButton && o != null) {
			JButton btn = (JButton) o;
			for(int i=0; i<pnlReviewList.length; i++) {
				if(btn == pnlReviewList[i].getBtnModify()) {
					new Review(ReviewList.this , pnlReviewList[i].getBook(), true);
					this.setEnabled(false);
				}
				if(btn == pnlReviewList[i].getBtnOpen()) {
					new ReviewViewer(ReviewList.this, pnlReviewList[i].getBook(),true);
					this.setEnabled(false);
				}
				if(btn == pnlReviewList[i].getBtnDelete()) {
					int result = JOptionPane.showConfirmDialog(this, "선택한 도서와 후기를 삭제하시겠습니까?","삭제",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if(result == JOptionPane.YES_OPTION) {
						books.remove(pnlReviewList[i].getBook());
						BookList.saveBooks(books);
						refreshList();
					}
				}
			}
		}
	}

	public void refreshList() {
		
		books = BookList.loadBooks();
		pnlCenterMain.removeAll();
		pnlReviewList = new PnlReviewList[books.size()];
		for(int i=0;i<pnlReviewList.length;i++) {
			pnlReviewList[i] = new PnlReviewList(books.get(i));
		}
		for(int i=0;i<pnlReviewList.length;i++) {
			pnlCenterMain.add(pnlReviewList[i]);
			pnlCenterMain.revalidate();
			validate();
		}
		if (pnlReviewList.length<4) {
			JPanel[] pnlBlank = new JPanel[3];
			for(int i=0;i<3-pnlReviewList.length;i++) {
				pnlBlank[i] = new JPanel();
				pnlBlank[i].setPreferredSize(new Dimension(500,181));
				pnlCenterMain.add(pnlBlank[i]);
				pnlCenterMain.revalidate();
				validate();
			}
		}
		if(pnlReviewList.length < 3){
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }else {
        	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
		int count = pnlReviewList.length/ 9 + 4;	//스크롤바 속도
		scroll.getVerticalScrollBar().setUnitIncrement(count);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		addListeners();
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
