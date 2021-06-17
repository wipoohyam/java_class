package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


public class BookList extends JFrame implements ActionListener, ItemListener{
	private JComboBox cbSearchBy;
	private String[] searchBy = {"제목", "저자", "읽은책", "읽고있는책"};
	private JTextField tfSearch;	//검색창
	private JButton btnSearch;		//검색 버튼
	private JButton btnInitList;		//초기화 버튼
	
	//9가지색상 차례대로 적용
	private Integer[] colors = {0xecf8f8,0xeee4e1,0xe7d8c9,0xe6beae,0xb2967d};
	
	private JPanel pnlCenter;
	private JPanel pnlCenterMain;
	private JScrollPane scroll;
	private Vector<Book> books;
	private PnlBook[] pnlBook;		//책 
	
	private JButton btnBook;		//책 추가 버튼
	private JButton btnReview;		//후기 모아보기 버튼
	private JButton btnExit;		//종료 버튼
	
	public BookList() {
		init();
		setDisplay();
		addListeners();
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
			saveBooks(books);
		} catch(IOException e) {
			saveBooks(books);
		} catch(ClassNotFoundException e) {
			saveBooks(books);
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
			fos = new FileOutputStream("books.dat", false);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(books);
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
	public Vector<Book> getBooks(){
		return books;
	}
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		cbSearchBy = new JComboBox<String>(searchBy);
		cbSearchBy.addItemListener(this);
		((JLabel)cbSearchBy.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

		tfSearch = new JTextField(15);
		tfSearch.setText("검색어를 입력해주세요.");
		tfSearch.setForeground(Color.GRAY);
		tfSearch.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (tfSearch.getText().equals("검색어를 입력해주세요.")) {
		        	tfSearch.setText("");
		        	tfSearch.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (tfSearch.getText().isEmpty()) {
		        	tfSearch.setForeground(Color.GRAY);
		        	tfSearch.setText("검색어를 입력해주세요.");
		        }
		    }
	    });
		btnSearch = new JButton("검색");
		btnInitList = new JButton("초기화");
		BookList.this.getRootPane().setDefaultButton(btnSearch);
		books = loadBooks();
//		pnlBook = new PnlBook[books.size()];
		btnBook = new JButton("새책 추가");
		btnReview = new JButton("후기 모아보기");
		btnExit = new JButton("종료");
	}
	private void setDisplay() {
		JPanel pnlMain = new JPanel(new BorderLayout());
		
		JPanel pnlNorth = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlNorth.setBorder(new EmptyBorder(15,0,5,0));
		tfSearch.setBorder(new EmptyBorder(5,3,5,3));
		pnlNorth.add(cbSearchBy);
		pnlNorth.add(tfSearch);
		MyUtils.setMyButton(btnSearch, MyUtils.DEFAULTBTN);
		btnSearch.setPreferredSize(new Dimension(60,26));
		pnlNorth.add(btnSearch);
		MyUtils.setMyButton(btnInitList, MyUtils.DEFAULTBTN);
		btnInitList.setPreferredSize(new Dimension(60,26));
		pnlNorth.add(btnInitList);
		
		pnlCenter = new JPanel(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(0,100,0,100));

		pnlCenterMain = new JPanel(new GridLayout(0, 1));
		scroll = new JScrollPane(pnlCenterMain);
		setBooksShow();

        pnlCenter.add(scroll);
		JPanel pnlSouth = new JPanel(new BorderLayout());
		pnlSouth.setBorder(new EmptyBorder(10,15,10,15));
		JPanel pnlSWest = new JPanel(new FlowLayout());
		MyUtils.setMyButton(btnBook, MyUtils.DEFAULTBTN);
		MyUtils.setMyButton(btnReview, MyUtils.DEFAULTBTN);
		btnReview.setPreferredSize(new Dimension(110,26));
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
	public void setBooksShow() {
		books = loadBooks();
        pnlCenterMain.removeAll();
		pnlBook = new PnlBook[books.size()];
		for(int i=0; i<books.size(); i++) {
			pnlBook[i] = new PnlBook(books.get(i));
		}
		addPnlBookListener(pnlBook);
		setPnlBooksDisplay(pnlBook);

	}
	private void addListeners() {
		btnBook.addActionListener(this);
		btnReview.addActionListener(this);
		btnExit.addActionListener(this);
		btnSearch.addActionListener(this);
		btnInitList.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				int result = JOptionPane.showConfirmDialog(BookList.this, "종료하시겠습니까?", "프로그램 종료",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
			@Override
			public void windowActivated(WindowEvent we) {
				toFront();
				btnSearch.requestFocus();
			}
		});
		
	}
	private void addPnlBookListener(PnlBook[] pnlBook) {
		for(int i=0;i<pnlBook.length;i++) {
			pnlBook[i].getBtnReview().addActionListener(this);
			pnlBook[i].addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					PnlBook pb = (PnlBook) me.getSource();
					if(me.getButton()== MouseEvent.BUTTON1) {
						new ReviewViewer(BookList.this, pb.getBook(), true);
					}
				}
			});
		}
		
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		JComboBox cb;
		String selected ="";
        pnlCenterMain.removeAll();
		if (e.getSource() instanceof JComboBox && e.getSource() != null) {
			cb = (JComboBox)e.getSource();
			selected = cb.getSelectedItem().toString();
		}
		//검색결과 book들을 담을 Vector생성
		Vector<Book> foundBooks = new Vector<>();
		if(selected.equals("읽고있는책")) {
			for(int i=0;i<books.size();i++) {
				if(!books.get(i).getRead()) {
					foundBooks.add(books.get(i));
				}
			}
		}else if(selected.equals("읽은책")) {
			for(int i=0;i<books.size();i++) {
				if(books.get(i).getRead()) {
					foundBooks.add(books.get(i));
				}
			}
		}else if(selected.equals("제목") || selected.equals("저자")) {
			foundBooks = loadBooks();
		}
		pnlBook = new PnlBook[foundBooks.size()];
		if(foundBooks.size()>0) {
			for (int i=0;i<foundBooks.size();i++) {
				pnlBook[i] = new PnlBook(foundBooks.get(i));
			}
		}
		setPnlBooksDisplay(pnlBook);
		addPnlBookListener(pnlBook);
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		for(int i=0;i<pnlBook.length; i++) {
			if(o == pnlBook[i].getBtnReview()) {
				Review rv = new Review(BookList.this, pnlBook[i].getBook(), true);
				rv.toFront();
			}
		}
		if(o == btnBook) {
			BookSaver sv = new BookSaver(BookList.this, true);
			sv.toFront();
		}
		if(o == btnReview) {
			ReviewList rl = new ReviewList(BookList.this, true);
			rl.toFront();
		}
		if(o == btnExit) {
			int result = JOptionPane.showConfirmDialog(BookList.this, "종료하시겠습니까?", "프로그램 종료",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				saveBooks(books);
				System.exit(0);
			}
		}
		if(o== btnInitList) {
			tfSearch.setText("");
			//검색창에 포커스 줬다 빼기(placeholder 나올수 있게)
			tfSearch.requestFocus();
			cbSearchBy.setSelectedIndex(0);
			cbSearchBy.requestFocus();
			setBooksShow();
		}
		if(o == btnSearch) {
	        pnlCenterMain.removeAll();
			books = loadBooks();
			//검색어 받기 
			String word = tfSearch.getText();
			//스페이스를 제외한 텍스트들만 키워드에 담는다.
			String keyword[] = word.split(" ");
			System.out.println(Arrays.toString(keyword));
			//콤보박스 선택값 받기(검색분류)
			String cbSelected = cbSearchBy.getSelectedItem().toString();
			//검색결과 book들을 담을 Vector생성
			Vector<Book> foundBooks = new Vector<>();
			if(cbSelected.equals("읽고있는책") || cbSelected.equals("읽은책")) {
				//읽은책,읽고있는책을 선택하고 검색어가 있는경우
				if(!word.equals("검색어를 입력해주세요.")) {
					//읽은책,읽고있는 책 중 검색어와 일치하는 도서를 담는다.
					if(cbSelected.equals("읽고있는책")) {
						for(int i=0;i<books.size();i++) {
							if(!books.get(i).getRead() && (searchTitleByKeyword(keyword, books.get(i)) || searchAuthorByKeyword(keyword, books.get(i)))) {
								foundBooks.add(books.get(i));
							}
						}
					}else if(cbSelected.equals("읽은책")) {
						for(int i=0;i<books.size();i++) {
							if(books.get(i).getRead() && (searchTitleByKeyword(keyword, books.get(i)) || searchAuthorByKeyword(keyword, books.get(i)))) {
								foundBooks.add(books.get(i));
							}
						}
					}
				}else {
					//검색어가 없는경우 읽은책or읽고있는책을 모두 담는다.
					if(cbSelected.equals("읽고있는책")) {
						for(int i=0;i<books.size();i++) {
							if(!books.get(i).getRead()) {
								foundBooks.add(books.get(i));
							}
						}
					}else if(cbSelected.equals("읽은책")) {
						for(int i=0;i<books.size();i++) {
							if(books.get(i).getRead()) {
								foundBooks.add(books.get(i));
							}
						}
					}
				}
			}else if(cbSelected.equals("제목")) {
				for(int i=0;i<books.size();i++) {
					if(searchTitleByKeyword(keyword, books.get(i))) {
						foundBooks.add(books.get(i));
					}
				}
			}else if(cbSelected.equals("저자")) {
				for(int i=0;i<books.size();i++) {
					if(searchAuthorByKeyword(keyword, books.get(i))) {
						foundBooks.add(books.get(i));
					}
				}
			}
			if(foundBooks.size()>0) {
				//검색결과가 1개 이상인 경우 
				//검색결과 PnlBook들을 담을 JPanel 생성
				pnlBook = new PnlBook[foundBooks.size()];
				for (int i=0;i<foundBooks.size();i++) {
					pnlBook[i] = new PnlBook(foundBooks.get(i));
				}
			}else {
				//검색결과가 없는 경우 
				if(word.equals("검색어를 입력해주세요.") || keyword.length == 0) {
					//검색어를 넣지 않아서 검색결과가 없는경우 
					tfSearch.setText("");
					tfSearch.requestFocus();
					cbSearchBy.requestFocus();
					JOptionPane.showMessageDialog(this, "검색어를 입력해주세요.");

				}else {
					//검색어를 넣었지만 조건에 맞는 결과가 없는경우 
					JOptionPane.showMessageDialog(this, "\'"+word+"\' 검색 결과를 찾을 수 없습니다.");
				}
				pnlBook = new PnlBook[0];
			}

			addPnlBookListener(pnlBook);
			setPnlBooksDisplay(pnlBook);
		}
	}
	private boolean searchTitleByKeyword(String[] keyword, Book book) {
		for(int j=0;j<keyword.length;j++) {
			if(book.getTitle().contains(keyword[j])) {
				return true;
			}
		}
		return false;
	}
	private boolean searchAuthorByKeyword(String[] keyword, Book book) {
		for(int j=0;j<keyword.length;j++) {
			if(book.getAuthor().contains(keyword[j])) {
				return true;
			}
		}
		return false;
	}
	private void setPnlBooksDisplay(PnlBook[] pnlBook) {
		for(int i=0;i<pnlBook.length; i++) {
			pnlBook[i].setBackground(new Color(colors[i%5]));
			pnlCenterMain.add(pnlBook[i]);
		}
		
		//books가 6개 이하일때 GridLayout으로 넓어지지않게 blank인 패널들을 추가해준다.
		if (pnlBook.length<7) {
			JPanel[] pnlBlank = new JPanel[6];
			for(int i=0;i<6-pnlBook.length;i++) {
				pnlBlank[i] = new JPanel();
				pnlBlank[i].setPreferredSize(new Dimension(220,50));
				pnlCenterMain.add(pnlBlank[i]);
			}
		}
		if(pnlBook.length < 6){
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }else {
        	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
		int count = pnlBook.length/ 9 + 7;	//스크롤바 속도
		scroll.getVerticalScrollBar().setUnitIncrement(count);
		
		pnlCenterMain.revalidate();
		validate();
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
