package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class BookList extends JFrame implements ActionListener{
	private JTextField tfSearch;	//검색창
	private JButton btnSearch;		//검색 버튼
	
	//9가지색상 차례대로 적용
	private Integer[] colors = {0xecf8f8,0xeee4e1,0xe7d8c9,0xe6beae,0xb2967d};
	
	private JPanel pnlCenterMain;
	private Vector<Book> books;
	private PnlBook[] pnlBook;		//책 
	
	private JButton btnBook;		//책 추가 버튼
	private JButton btnReview;		//후기 모아보기 버튼
	private JButton btnExit;		//종료 버튼
	
	public BookList() {
		init();
		setDisplay();
		addListeners();
		setBooksShow();
		showFrame();
	}
	public static Vector<Book> loadBooks(){
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Vector<Book> books = new Vector<>();
		try {
			fis = new FileInputStream("books.dat");
			ois = new ObjectInputStream(fis);
			
			books = (Vector<Book>) ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				ois.close();
			} catch (Exception e) {}
			try {
				fis.close();
			} catch(Exception e) {}
		}
		return books;
	}
	public static void saveBooks(Vector<Book> books) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream("books.dat");
			oos = new ObjectOutputStream(fos);
			oos.writeObject((Object)books);
			oos.flush();
			oos.reset();
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oos.close();
			} catch(Exception e) {}
			try {
				fos.close();
			} catch(Exception e) {}
		}
	}
	public void addBook(Book book) {
		books.add(book);
	}
	public void editBook(Book book) {
		books.remove(book);
		books.add(book);
	}
	public void delBook(Book book) {
		books.remove(book);
	}
	public Vector<Book> getBooks(){
		return books;
	}
	private void init() {
		tfSearch = new JTextField(15);
		btnSearch = new JButton("검색");
		books = loadBooks();
		pnlBook = new PnlBook[books.size()];
		
		
		btnBook = new JButton("책 추가");
		btnReview = new JButton("후기 모아보기");
		btnExit = new JButton("종료");
	}
	public void setBooksShow() {
		pnlBook = new PnlBook[books.size()];
		for(int i=0; i<books.size(); i++) {
			pnlBook[i] = new PnlBook(books.get(i));
		}
		pnlCenterMain.removeAll();
		for(int i=0;i<pnlBook.length; i++) {
			pnlBook[i].setBackground(new Color(colors[i%5]));
			pnlCenterMain.add(pnlBook[i]);
		}
	}
	private void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlNorth.setBorder(new EmptyBorder(15,0,5,0));
		tfSearch.setBorder(new EmptyBorder(5,3,5,3));
		pnlNorth.add(tfSearch);
		MyUtils.setMyButton(btnSearch, MyUtils.DEFAULTBTN);
		btnSearch.setPreferredSize(new Dimension(70,26));
		pnlNorth.add(btnSearch);
		
		JPanel pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(0,100,0,100));
		pnlCenterMain = new JPanel(new GridLayout(0, 1));
		
//		/*
//		 * 아래에서 위로 쌓일 수 있게 여백 줌
//		 * 추후에 값 받아오면 갯수 조절 가능
//		 */
//		if(pnlBook.length < 7){
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
//			
//			pnlCenterMain.add(pnlEx1);
//			pnlCenterMain.add(pnlEx2);
//			pnlCenterMain.add(pnlEx3);
//			pnlCenterMain.add(pnlEx4);
//			pnlCenterMain.add(pnlEx5);
//		}
		
		JScrollPane scroll = new JScrollPane(pnlCenterMain);
        
        if(pnlBook.length < 7){
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        };

		int count = pnlBook.length/ 9 + 7;	//스크롤바 속도
		scroll.getVerticalScrollBar().setUnitIncrement(count);
        
        pnlCenter.add(scroll);
		
				
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBorder(new EmptyBorder(10,15,10,15));
		JPanel pnlSWest = new JPanel(new FlowLayout());
		MyUtils.setMyButton(btnBook, MyUtils.DEFAULTBTN);
		MyUtils.setMyButton(btnReview, MyUtils.DEFAULTBTN);
		btnReview.setPreferredSize(new Dimension(90,26));
		pnlSWest.add(btnBook);
		pnlSWest.add(btnReview);
		JPanel pnlSCenter = new JPanel();
		JPanel pnlSEast = new JPanel();
		MyUtils.setMyButton(btnExit, MyUtils.DEFAULTBTN);
		pnlSEast.add(btnExit);
		pnlSouth.add(pnlSWest, BorderLayout.WEST);
		pnlSouth.add(pnlSCenter, BorderLayout.CENTER);
		pnlSouth.add(pnlSEast, BorderLayout.EAST);
		
		
		
		pnlMain.add(pnlNorth, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		pnlMain.add(pnlSouth, BorderLayout.SOUTH);
		
		add(pnlMain);
	}
	private void addListeners() {
		btnBook.addActionListener(this);
		btnReview.addActionListener(this);
		btnExit.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(BookList.this, "종료하시겠습니까?", "프로그램 종료",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		if(o == btnBook) {
			dispose();
			new BookSaver();
		}
		if(o == btnReview) {
			dispose();
			new ReviewList();
		}
		if(o == btnExit) {
			int result = JOptionPane.showConfirmDialog(BookList.this, "종료하시겠습니까?", "프로그램 종료",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				saveBooks(books);
				System.exit(0);
			}
		}
	}
	private void showFrame() {
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
