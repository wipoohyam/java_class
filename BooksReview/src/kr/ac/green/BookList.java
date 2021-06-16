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
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class BookList extends JFrame implements ActionListener{
	private JComboBox cbSearchBy;
	private JTextField tfSearch;	//�˻�â
	private JButton btnSearch;		//�˻� ��ư
	private JButton btnInitList;		//�ʱ�ȭ ��ư
	
	//9�������� ���ʴ�� ����
	private Integer[] colors = {0xecf8f8,0xeee4e1,0xe7d8c9,0xe6beae,0xb2967d};
	
	private JPanel pnlCenter;
	private JPanel pnlCenterMain;
	private JScrollPane scroll;
	private Vector<Book> books;
	private PnlBook[] pnlBook;		//å 
	
	private JButton btnBook;		//å �߰� ��ư
	private JButton btnReview;		//�ı� ��ƺ��� ��ư
	private JButton btnExit;		//���� ��ư
	
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
		tfSearch = new JTextField(15);
		tfSearch.setText("�˻�� �Է����ּ���.");
		tfSearch.setForeground(Color.GRAY);
		tfSearch.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (tfSearch.getText().equals("�˻�� �Է����ּ���.")) {
		        	tfSearch.setText("");
		        	tfSearch.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (tfSearch.getText().isEmpty()) {
		        	tfSearch.setForeground(Color.GRAY);
		        	tfSearch.setText("�˻�� �Է����ּ���.");
		        }
		    }
		    });
		btnSearch = new JButton("�˻�");
		btnInitList = new JButton("�ʱ�ȭ");
		btnSearch.requestFocus();
		books = loadBooks();
//		pnlBook = new PnlBook[books.size()];
		btnBook = new JButton("å �߰�");
		btnReview = new JButton("�ı� ��ƺ���");
		btnExit = new JButton("����");
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
		MyUtils.setMyButton(btnInitList, MyUtils.DEFAULTBTN);
		btnInitList.setPreferredSize(new Dimension(70,26));
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
				int result = JOptionPane.showConfirmDialog(BookList.this, "�����Ͻðڽ��ϱ�?", "���α׷� ����",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if (result == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
			@Override
			public void windowActivated(WindowEvent we) {
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
						System.out.println(pb.getBook().getRate());
						
						new ReviewViewer(BookList.this, pb.getBook(), true);
						BookList.this.setEnabled(false);
					}
				}
			});
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object o = ae.getSource();
		for(int i=0;i<pnlBook.length; i++) {
			if(o == pnlBook[i].getBtnReview()) {
				new Review(BookList.this, pnlBook[i].getBook(), true);
				this.setEnabled(false);
			}
			if(o == pnlBook[i]) {
				System.out.println(""+i);
			}
		}
		if(o == btnBook) {
			new BookSaver(BookList.this, true);
			this.setEnabled(false);
		}
		if(o == btnReview) {
			new ReviewList(BookList.this, true);
			this.setEnabled(false);
		}
		if(o == btnExit) {
			int result = JOptionPane.showConfirmDialog(BookList.this, "�����Ͻðڽ��ϱ�?", "���α׷� ����",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				saveBooks(books);
				System.exit(0);
			}
		}
		if(o== btnInitList) {
			setBooksShow();
		}
		if(o == btnSearch) {
	        pnlCenterMain.removeAll();
			books = loadBooks();
			//�˻��� �ޱ� 
			String word = tfSearch.getText();
			//�˻���� book���� ���� Vector����
			Vector<Book> foundBooks = new Vector<>();
			for(int i=0;i<books.size();i++) {
				//���� �߿� �˻�� ������ å�� ã�Ƽ� found�� ����� �Բ� ���ڸ� �ִ´� 
				if(books.get(i).getTitle().contains(word)) {
					foundBooks.add(books.get(i));
				}
			}
			if(foundBooks.size()>0) {
				//�˻���� PnlBook���� ���� JPanel ����
				pnlBook = new PnlBook[foundBooks.size()];
				
				System.out.println(foundBooks);
				for (int i=0;i<foundBooks.size();i++) {
					pnlBook[i] = new PnlBook(foundBooks.get(i));
				}
				setPnlBooksDisplay(pnlBook);
			}else {
				if(word.equals("�˻�� �Է����ּ���.")) {
					JOptionPane.showMessageDialog(this, "�˻�� �Է����ּ���.");
					setBooksShow();
				}else {
					pnlBook = new PnlBook[0];
					setPnlBooksDisplay(pnlBook);
					JOptionPane.showMessageDialog(this, "\'"+word+"\' �˻� ����� ã�� �� �����ϴ�.");
				}
			}
		}
	}
	private void setPnlBooksDisplay(JPanel[] pnlBook) {
		for(int i=0;i<pnlBook.length; i++) {
			pnlBook[i].setBackground(new Color(colors[i%5]));
			pnlCenterMain.add(pnlBook[i]);
			pnlCenterMain.revalidate();
			validate();
		}
		
		//books�� 6�� �����϶� GridLayout���� �о������ʰ� blank�� �гε��� �߰����ش�.
		if (pnlBook.length<7) {
			JPanel[] pnlBlank = new JPanel[6];
			for(int i=0;i<6-pnlBook.length;i++) {
				pnlBlank[i] = new JPanel();
				pnlBlank[i].setPreferredSize(new Dimension(220,50));
				pnlCenterMain.add(pnlBlank[i]);
				pnlCenterMain.revalidate();
				validate();
			}
		}
		if(pnlBook.length < 6){
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        }else {
        	scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        }
		int count = pnlBook.length/ 9 + 7;	//��ũ�ѹ� �ӵ�
		scroll.getVerticalScrollBar().setUnitIncrement(count);
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
