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
	private String[] searchBy = {"����", "����", "����å", "�а��ִ�å"};
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
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		cbSearchBy = new JComboBox<String>(searchBy);
		cbSearchBy.addItemListener(this);
		((JLabel)cbSearchBy.getRenderer()).setHorizontalAlignment(JLabel.CENTER);

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
		BookList.this.getRootPane().setDefaultButton(btnSearch);
		books = loadBooks();
//		pnlBook = new PnlBook[books.size()];
		btnBook = new JButton("��å �߰�");
		btnReview = new JButton("�ı� ��ƺ���");
		btnExit = new JButton("����");
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
				int result = JOptionPane.showConfirmDialog(BookList.this, "�����Ͻðڽ��ϱ�?", "���α׷� ����",
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
		//�˻���� book���� ���� Vector����
		Vector<Book> foundBooks = new Vector<>();
		if(selected.equals("�а��ִ�å")) {
			for(int i=0;i<books.size();i++) {
				if(!books.get(i).getRead()) {
					foundBooks.add(books.get(i));
				}
			}
		}else if(selected.equals("����å")) {
			for(int i=0;i<books.size();i++) {
				if(books.get(i).getRead()) {
					foundBooks.add(books.get(i));
				}
			}
		}else if(selected.equals("����") || selected.equals("����")) {
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
			int result = JOptionPane.showConfirmDialog(BookList.this, "�����Ͻðڽ��ϱ�?", "���α׷� ����",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				saveBooks(books);
				System.exit(0);
			}
		}
		if(o== btnInitList) {
			tfSearch.setText("");
			//�˻�â�� ��Ŀ�� ��� ����(placeholder ���ü� �ְ�)
			tfSearch.requestFocus();
			cbSearchBy.setSelectedIndex(0);
			cbSearchBy.requestFocus();
			setBooksShow();
		}
		if(o == btnSearch) {
	        pnlCenterMain.removeAll();
			books = loadBooks();
			//�˻��� �ޱ� 
			String word = tfSearch.getText();
			//�����̽��� ������ �ؽ�Ʈ�鸸 Ű���忡 ��´�.
			String keyword[] = word.split(" ");
			System.out.println(Arrays.toString(keyword));
			//�޺��ڽ� ���ð� �ޱ�(�˻��з�)
			String cbSelected = cbSearchBy.getSelectedItem().toString();
			//�˻���� book���� ���� Vector����
			Vector<Book> foundBooks = new Vector<>();
			if(cbSelected.equals("�а��ִ�å") || cbSelected.equals("����å")) {
				//����å,�а��ִ�å�� �����ϰ� �˻�� �ִ°��
				if(!word.equals("�˻�� �Է����ּ���.")) {
					//����å,�а��ִ� å �� �˻���� ��ġ�ϴ� ������ ��´�.
					if(cbSelected.equals("�а��ִ�å")) {
						for(int i=0;i<books.size();i++) {
							if(!books.get(i).getRead() && (searchTitleByKeyword(keyword, books.get(i)) || searchAuthorByKeyword(keyword, books.get(i)))) {
								foundBooks.add(books.get(i));
							}
						}
					}else if(cbSelected.equals("����å")) {
						for(int i=0;i<books.size();i++) {
							if(books.get(i).getRead() && (searchTitleByKeyword(keyword, books.get(i)) || searchAuthorByKeyword(keyword, books.get(i)))) {
								foundBooks.add(books.get(i));
							}
						}
					}
				}else {
					//�˻�� ���°�� ����åor�а��ִ�å�� ��� ��´�.
					if(cbSelected.equals("�а��ִ�å")) {
						for(int i=0;i<books.size();i++) {
							if(!books.get(i).getRead()) {
								foundBooks.add(books.get(i));
							}
						}
					}else if(cbSelected.equals("����å")) {
						for(int i=0;i<books.size();i++) {
							if(books.get(i).getRead()) {
								foundBooks.add(books.get(i));
							}
						}
					}
				}
			}else if(cbSelected.equals("����")) {
				for(int i=0;i<books.size();i++) {
					if(searchTitleByKeyword(keyword, books.get(i))) {
						foundBooks.add(books.get(i));
					}
				}
			}else if(cbSelected.equals("����")) {
				for(int i=0;i<books.size();i++) {
					if(searchAuthorByKeyword(keyword, books.get(i))) {
						foundBooks.add(books.get(i));
					}
				}
			}
			if(foundBooks.size()>0) {
				//�˻������ 1�� �̻��� ��� 
				//�˻���� PnlBook���� ���� JPanel ����
				pnlBook = new PnlBook[foundBooks.size()];
				for (int i=0;i<foundBooks.size();i++) {
					pnlBook[i] = new PnlBook(foundBooks.get(i));
				}
			}else {
				//�˻������ ���� ��� 
				if(word.equals("�˻�� �Է����ּ���.") || keyword.length == 0) {
					//�˻�� ���� �ʾƼ� �˻������ ���°�� 
					tfSearch.setText("");
					tfSearch.requestFocus();
					cbSearchBy.requestFocus();
					JOptionPane.showMessageDialog(this, "�˻�� �Է����ּ���.");

				}else {
					//�˻�� �־����� ���ǿ� �´� ����� ���°�� 
					JOptionPane.showMessageDialog(this, "\'"+word+"\' �˻� ����� ã�� �� �����ϴ�.");
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
		
		//books�� 6�� �����϶� GridLayout���� �о������ʰ� blank�� �гε��� �߰����ش�.
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
		int count = pnlBook.length/ 9 + 7;	//��ũ�ѹ� �ӵ�
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
