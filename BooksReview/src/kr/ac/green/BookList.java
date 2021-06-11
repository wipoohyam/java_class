package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class BookList extends JFrame {
	private JTextField tfSearch;	//�˻�â
	private JButton btnSearch;		//�˻� ��ư
	
	private PnlBook[] pnlBook;		//å 
	
	private JButton btnBook;		//å �߰� ��ư
	private JButton btnReview;		//�ı� ��ƺ��� ��ư
	private JButton btnExit;		//���� ��ư
	
	public BookList() {
		init();
		setDisplay();
		showFrame();
	}
	
	public void init() {
		tfSearch = new JTextField(15);
		btnSearch = new JButton("�˻�");
		
		pnlBook = new PnlBook[9];
		for(int i=0; i<pnlBook.length; i++) {
			pnlBook[i] = new PnlBook();
		}
		
		btnBook = new JButton("å �߰�");
		btnReview = new JButton("�ı� ��ƺ���");
		btnExit = new JButton("����");
	}
	
	public void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlNorth.setBorder(new EmptyBorder(15,0,0,0));
		pnlNorth.add(tfSearch);
		pnlNorth.add(btnSearch);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(0,100,0,100));
		JPanel pnlCenterMain = new JPanel(new GridLayout(0, 1));
		
		/*
		 * �Ʒ����� ���� ���� �� �ְ� ���� ��
		 * ���Ŀ� �� �޾ƿ��� ���� ���� ����
		 */
		if(pnlBook.length < 7){
			JPanel pnlEx1 = new JPanel();
			pnlEx1.setBorder(new EmptyBorder(0,0,0,0));
			JPanel pnlEx2 = new JPanel();
			pnlEx2.setBorder(new EmptyBorder(0,0,0,0));
			JPanel pnlEx3 = new JPanel();
			pnlEx3.setBorder(new EmptyBorder(0,0,0,0));
			JPanel pnlEx4 = new JPanel();
			pnlEx4.setBorder(new EmptyBorder(0,0,0,0));
			JPanel pnlEx5 = new JPanel();
			pnlEx5.setBorder(new EmptyBorder(0,0,0,0));
			
			pnlCenterMain.add(pnlEx1);
			pnlCenterMain.add(pnlEx2);
			pnlCenterMain.add(pnlEx3);
			pnlCenterMain.add(pnlEx4);
			pnlCenterMain.add(pnlEx5);
		}
		
		for(PnlBook book : pnlBook) {
			pnlCenterMain.add(book);
		}
		
		JScrollPane scroll = new JScrollPane(pnlCenterMain);
        
        if(pnlBook.length < 7){
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        };

		int count = pnlBook.length/ 9 + 7;	//��ũ�ѹ� �ӵ�
		scroll.getVerticalScrollBar().setUnitIncrement(count);
        
        pnlCenter.add(scroll);
		
				
		JPanel pnlSouth = new JPanel(new BorderLayout());
		JPanel pnlSWest = new JPanel(new FlowLayout());
		pnlSWest.setBorder(new EmptyBorder(10,15,10,0));
		pnlSWest.add(btnBook);
		pnlSWest.add(btnReview);
		JPanel pnlSCenter = new JPanel();
		JPanel pnlSEast = new JPanel();
		pnlSEast.setBorder(new EmptyBorder(10,0,10,15));
		pnlSEast.add(btnExit);
		pnlSouth.add(pnlSWest, BorderLayout.WEST);
		pnlSouth.add(pnlSCenter, BorderLayout.CENTER);
		pnlSouth.add(pnlSEast, BorderLayout.EAST);
		
		
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlMain);
	}
	
	public void showFrame() {
		setTitle("Book List");
		setSize(600,480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BookList();
	}
	
}
