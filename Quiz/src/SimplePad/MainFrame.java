package SimplePad;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.Document;

public class MainFrame extends JFrame implements ActionListener {
	private JMenuBar mBar;
	private JMenu mFile;
	private JMenuItem miNew;
	private JMenuItem miOpen;
	private JMenuItem miSave;
	private JMenuItem miSaveAs;
	private JMenuItem miExit;

	private JTextArea taEditor;
	private Content content;
	private String path;

	private String title;

	private boolean changed;
	private boolean saved;
	private JFileChooser chooser;

	public MainFrame() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}

	private void init() {
		// UI룩 초기화
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 메뉴바 초기화
		mBar = new JMenuBar();
		mFile = new JMenu("File");
		miNew = new JMenuItem("New");
		miOpen = new JMenuItem("Open");
		miSave = new JMenuItem("Save");
		miSaveAs = new JMenuItem("Save as");
		miExit = new JMenuItem("Exit");

		// 텍스트 입력창 초기화
		taEditor = new JTextArea(50, 50);

		// 창 타이틀 초기화
		content = new Content();
		title = "SimplePad Ver1.0 : ";

		changed = false;
		saved = false;
		// 파일추져 초기화
		chooser = new JFileChooser(".");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(File f) {
				if (f.isFile()) {
					String fileName = f.getName();
					int idx = fileName.lastIndexOf(".");
					String ext = fileName.substring(idx);
					return ext.equalsIgnoreCase(".txt");
				} else {
					return true;
				}
			}

			@Override
			public String getDescription() {
				return "텍스트파일";
			}
		});
		
	}

	private void setDisplay() {
		// 패널배치
		setJMenuBar(mBar);
		mBar.add(mFile);
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(miSaveAs);
		mFile.add(new JSeparator());
		mFile.add(miExit);

		// 단축키 추가
		miNew.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		miOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		miSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		miSaveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl alt S"));
		miExit.setAccelerator(KeyStroke.getKeyStroke("alt F4"));

		// 입력창 배치
		taEditor.setLineWrap(true);
		add(new JScrollPane(taEditor, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED), BorderLayout.CENTER);
	}

	private boolean isNewFile() {
		if (path == null) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 새파일 이벤트 발생시
	 * 
	 * 	1. changed == false
	 * 		new MainFrame();
	 * 	2. changed == true
	 * 		1. 작성중인 파일이 저장된파일일 경우
	 * 			1.저장여부 yes 저장 - 기존 파일dispose - new MainFrame();
	 * 			2. 저장여부 no 기존 파일 dispose - new MainFrame(); 
	 * 			3. 저장여부 cancel closeDialog
	 * 		2. 작성중인 파일이 새파일일 경우
	 * 			1. 저장여부 yes 새이름으로 저장 - 기존 파일dispose - new MainFrame();
	 * 			2. 저장여부 no 기존 파일 dispose - new MainFrame();
	 * 			3. 저장여부 cancel closeDialog
	 */
	private void newFile() {
		//상황2
		if(changed) {
			//상황 2
			int returnValue = JOptionPane.showConfirmDialog(
				this,
				"작성중인 파일을 저장하시겠습니까?",
				"파일 저장",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if(!isNewFile()) {
				//상황 2-1
				if(returnValue == JOptionPane.YES_OPTION) {
					//상황 2-1-1
					saveFile();
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//상황 2-1-2
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.CANCEL_OPTION){
					//상황 2-1-3
				}
			}else {
				//상황 2-2
				if(returnValue == JOptionPane.YES_OPTION) {
					//상황 2-2-1
					saveAsFile();
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//상황 2-2-2
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.CANCEL_OPTION){
					//상황 2-2-3
				}
			}
		}else {
			dispose();
			new MainFrame();
		}
	}

	/*
	 * 열기 이벤트 발생시
	 * 
	 * 	1. changed == false
	 * 		showOpenDialog 
	 * 	2. changed == true 
	 * 		1. 작성중인 파일이 저장된파일일 경우
	 * 			1. 저장여부 yes 저장 - 기존 파일dispose - showOpenDialog
	 * 			2. 저장여부 no 기존 파일 dispose - showOpenDialog
	 * 			3. 저장여부 cancel closeDialog
	 * 		2. 작성중인 파일이 새파일일 경우
	 * 			1. 저장여부 yes 새이름으로 저장 - 기존 파일dispose - showOpenDialog
	 * 			2. 저장여부 no 기존 파일 dispose - showOpenDialog
	 * 			3. 저장여부 cancel closeDialog
	 */
	private void openFile() {
		chooser.setDialogTitle("Open");
		chooser.setApproveButtonText("Open");
		if(!changed) {
			//상황 1
			load();
		}else {
			//상황 2
			int returnValue = JOptionPane.showConfirmDialog(
				this,
				"작성중인 파일을 저장하시겠습니까?",
				"파일 저장",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if(!isNewFile()) {
				//상황 2-1
				if(returnValue == JOptionPane.YES_OPTION) {
					//상황 2-1-1
					saveFile();
					dispose();
					load();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//상황 2-1-2
					dispose();
					load();
				}else if(returnValue == JOptionPane.CANCEL_OPTION){
					//상황 2-1-3
				}
			}else if(isNewFile()){
				//상황 2-2
				if(returnValue ==JOptionPane.YES_OPTION) {
					//상황 2-2-1
					saveAsFile();
					dispose();
					load();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//상황 2-2-2
					dispose();
					load();
				}else if(returnValue == JOptionPane.CANCEL_OPTION) {
					//상황 2-2-3
				}
			}
		}
	}
	private void load() {
		int result = chooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			File selectedFile = chooser.getSelectedFile();
			path = selectedFile.getAbsolutePath();
			FileInputStream fis = null;
			ObjectInputStream ois= null;
			
			try {
				fis = new FileInputStream(path);
				ois = new ObjectInputStream(fis);
				
				content = (Content) ois.readObject();
				taEditor.setText(content.contentText);
				changed = false;
				setTitle(title + content.contentTitle);
			} catch(FileNotFoundException e){
				e.printStackTrace();
			} catch(IOException e){
				e.printStackTrace();
			} catch(ClassNotFoundException e){
				e.printStackTrace();
			}finally {
				try {
					ois.close();
				}catch(Exception e) {}
				try {
					fis.close();
				}catch(Exception e) {}
			}
		}else if(result == JFileChooser.CANCEL_OPTION) {}
		
	}

	/*
	 * 저장 이벤트 발생시
	 * 
	 * 	1. 작성중인 파일이 저장된파일일 경우
	 * 		기존파일 덮어쓰기
	 * 	2. 작성중인 파일이 새파일일 경우
	 * 		새이름으로 저장
	 */
	private void saveFile() {
		if(!isNewFile()) {
			save();
		}else if(isNewFile()){
			saveAsFile();
		}
	}

	/*
	 * 다른이름으로 저장 발생시
	 * 
	 * - 새이름으로 저장
	 */
	private void saveAsFile() {
		chooser.setDialogTitle("Save as");
		chooser.setApproveButtonText("Save");
		String fileName;
		//추져 팝업 
		int result = chooser.showSaveDialog(this);
		//저장버튼 
		if (result == JFileChooser.APPROVE_OPTION) {
			//저장 경로+파일명 
			path = chooser.getSelectedFile().getPath();
			//fileName = 파일명만 
			fileName = path.substring(path.lastIndexOf("/")+1);
			//fileName에 "."이 포함되어 있을 경우 
			if(fileName.contains(".")) {
				//포함된 "."이 ".txt"이 아닌 경우 
				if(!fileName.contains(".txt")) {
					JOptionPane.showMessageDialog(
						this,
						"잘못된 파일명입니다.",
						"Information",
						JOptionPane.INFORMATION_MESSAGE
					);
					saved = false;
					//오류메시지 출력 후 saveAsFile재귀호출 
					saveAsFile();
				}
				//포함된 "."이 ".txt"인 경우 path를 그대로 쓴다.
				//파일명 저장 
				content.contentTitle = fileName.substring(0, fileName.lastIndexOf("."));
			}else {
				//파일명이 확장자가 없는 경우 
				content.contentTitle = fileName;
				path += ".txt";
			}
			File ftoSave = new File(path);
			if(ftoSave.exists()) {
				int returnValue = JOptionPane.showConfirmDialog(
					this,
					"이미 존재하는 파일명입니다. 덮어쓰겠습니까?",
					"파일 저장",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE
				);
				if(returnValue == JOptionPane.YES_OPTION) {
					save();
				}else {
					saved = false;
				}
			}else {
				save();
			}
		}else {
			saved = false;
		}
	}
	//저장되어있는 path와작성내용으로 저장하는 메써드 
	private void save() {
//		System.out.println(path);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		content.contentText = taEditor.getText();
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(content);
			oos.flush();
			oos.reset();
		} catch (IOException ex) {
			ex.printStackTrace();
		}finally {
			try {
				oos.close();
			}catch(Exception e) {}
			try {
				fos.close();
			}catch(Exception e) {}
		}
		setTitle(title + content.contentTitle);
		changed = false;
		saved = true;
	}
	/*
	 * 창종료 발생시
	 * 
	 * 	1. changed = false 창종료
	 * 	2. changed = true
	 * 		1. 새 파일인 경우 새이름으로 저장
	 * 		2. 기존 파일을 작성중이던 경우 
	 * 			1. 저장여부 yes = 저장 후 종료
	 * 			2. 저장여부 no = 그냥 종료
	 * 			3. 저장여부 cancel = 돌아가기 
	 */
	private void exitFile() {
		if (changed) {
			int returnValue = JOptionPane.showConfirmDialog(
					this,
					"작성중인 파일을 저장하시겠습니까?",
					"파일 저장",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE
				);
			if (isNewFile()) {
				//상황 2-1
				if(returnValue == JOptionPane.YES_OPTION) {
					saveAsFile();
					if(saved) {
						System.exit(0);
					}
				}else if (returnValue == JOptionPane.NO_OPTION) {
					System.exit(0);
				}else if (returnValue == JOptionPane.CANCEL_OPTION) {
					
				}
			} else {
				//상황 2-1
				if(returnValue == JOptionPane.YES_OPTION) {
					save();
					if(saved) {
						System.exit(0);
					}
				}else if (returnValue == JOptionPane.NO_OPTION) {
					System.exit(0);
				}else if (returnValue == JOptionPane.CANCEL_OPTION) {
					
				}
			}
		} else {
			System.exit(0);
		}
	}

	// 내용 변화 발생시
	private void hasChanged() {
		changed = true;
		setTitle(title + content.contentTitle + " *");
	}

	
	// 이벤트 정의
	@Override
	public void actionPerformed(ActionEvent ae) {
		Object obj = ae.getSource();
		if (obj == miNew) {
			newFile();
		}
		if (obj == miOpen) {
			openFile();
		}
		if (obj == miSave) {
			saveFile();
		}
		if (obj == miSaveAs) {
			saveAsFile();
		}
		if (obj == miExit) {
			exitFile();
		}
	}

	// 버튼들에 이벤트 리스너 추가
	private void addListeners() {
		miNew.addActionListener(this);
		miOpen.addActionListener(this);
		miSave.addActionListener(this);
		miSaveAs.addActionListener(this);
		miExit.addActionListener(this);

		Document doc = taEditor.getDocument();
		doc.addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				hasChanged();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				hasChanged();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				exitFile();
			}
		});
	}

	// 창 기본설정
	private void showFrame() {
		setTitle(title + content.contentTitle);
		pack();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
