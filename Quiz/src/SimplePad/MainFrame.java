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
		// UI�� �ʱ�ȭ
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		// �޴��� �ʱ�ȭ
		mBar = new JMenuBar();
		mFile = new JMenu("File");
		miNew = new JMenuItem("New");
		miOpen = new JMenuItem("Open");
		miSave = new JMenuItem("Save");
		miSaveAs = new JMenuItem("Save as");
		miExit = new JMenuItem("Exit");

		// �ؽ�Ʈ �Է�â �ʱ�ȭ
		taEditor = new JTextArea(50, 50);

		// â Ÿ��Ʋ �ʱ�ȭ
		content = new Content();
		title = "SimplePad Ver1.0 : ";

		changed = false;
		saved = false;
		// �������� �ʱ�ȭ
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
				return "�ؽ�Ʈ����";
			}
		});
		
	}

	private void setDisplay() {
		// �гι�ġ
		setJMenuBar(mBar);
		mBar.add(mFile);
		mFile.add(miNew);
		mFile.add(miOpen);
		mFile.add(miSave);
		mFile.add(miSaveAs);
		mFile.add(new JSeparator());
		mFile.add(miExit);

		// ����Ű �߰�
		miNew.setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		miOpen.setAccelerator(KeyStroke.getKeyStroke("ctrl O"));
		miSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
		miSaveAs.setAccelerator(KeyStroke.getKeyStroke("ctrl alt S"));
		miExit.setAccelerator(KeyStroke.getKeyStroke("alt F4"));

		// �Է�â ��ġ
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
	 * ������ �̺�Ʈ �߻���
	 * 
	 * 	1. changed == false
	 * 		new MainFrame();
	 * 	2. changed == true
	 * 		1. �ۼ����� ������ ����������� ���
	 * 			1.���忩�� yes ���� - ���� ����dispose - new MainFrame();
	 * 			2. ���忩�� no ���� ���� dispose - new MainFrame(); 
	 * 			3. ���忩�� cancel closeDialog
	 * 		2. �ۼ����� ������ �������� ���
	 * 			1. ���忩�� yes ���̸����� ���� - ���� ����dispose - new MainFrame();
	 * 			2. ���忩�� no ���� ���� dispose - new MainFrame();
	 * 			3. ���忩�� cancel closeDialog
	 */
	private void newFile() {
		//��Ȳ2
		if(changed) {
			//��Ȳ 2
			int returnValue = JOptionPane.showConfirmDialog(
				this,
				"�ۼ����� ������ �����Ͻðڽ��ϱ�?",
				"���� ����",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if(!isNewFile()) {
				//��Ȳ 2-1
				if(returnValue == JOptionPane.YES_OPTION) {
					//��Ȳ 2-1-1
					saveFile();
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//��Ȳ 2-1-2
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.CANCEL_OPTION){
					//��Ȳ 2-1-3
				}
			}else {
				//��Ȳ 2-2
				if(returnValue == JOptionPane.YES_OPTION) {
					//��Ȳ 2-2-1
					saveAsFile();
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//��Ȳ 2-2-2
					dispose();
					new MainFrame();
				}else if(returnValue == JOptionPane.CANCEL_OPTION){
					//��Ȳ 2-2-3
				}
			}
		}else {
			dispose();
			new MainFrame();
		}
	}

	/*
	 * ���� �̺�Ʈ �߻���
	 * 
	 * 	1. changed == false
	 * 		showOpenDialog 
	 * 	2. changed == true 
	 * 		1. �ۼ����� ������ ����������� ���
	 * 			1. ���忩�� yes ���� - ���� ����dispose - showOpenDialog
	 * 			2. ���忩�� no ���� ���� dispose - showOpenDialog
	 * 			3. ���忩�� cancel closeDialog
	 * 		2. �ۼ����� ������ �������� ���
	 * 			1. ���忩�� yes ���̸����� ���� - ���� ����dispose - showOpenDialog
	 * 			2. ���忩�� no ���� ���� dispose - showOpenDialog
	 * 			3. ���忩�� cancel closeDialog
	 */
	private void openFile() {
		chooser.setDialogTitle("Open");
		chooser.setApproveButtonText("Open");
		if(!changed) {
			//��Ȳ 1
			load();
		}else {
			//��Ȳ 2
			int returnValue = JOptionPane.showConfirmDialog(
				this,
				"�ۼ����� ������ �����Ͻðڽ��ϱ�?",
				"���� ����",
				JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE
			);
			if(!isNewFile()) {
				//��Ȳ 2-1
				if(returnValue == JOptionPane.YES_OPTION) {
					//��Ȳ 2-1-1
					saveFile();
					dispose();
					load();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//��Ȳ 2-1-2
					dispose();
					load();
				}else if(returnValue == JOptionPane.CANCEL_OPTION){
					//��Ȳ 2-1-3
				}
			}else if(isNewFile()){
				//��Ȳ 2-2
				if(returnValue ==JOptionPane.YES_OPTION) {
					//��Ȳ 2-2-1
					saveAsFile();
					dispose();
					load();
				}else if(returnValue == JOptionPane.NO_OPTION) {
					//��Ȳ 2-2-2
					dispose();
					load();
				}else if(returnValue == JOptionPane.CANCEL_OPTION) {
					//��Ȳ 2-2-3
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
	 * ���� �̺�Ʈ �߻���
	 * 
	 * 	1. �ۼ����� ������ ����������� ���
	 * 		�������� �����
	 * 	2. �ۼ����� ������ �������� ���
	 * 		���̸����� ����
	 */
	private void saveFile() {
		if(!isNewFile()) {
			save();
		}else if(isNewFile()){
			saveAsFile();
		}
	}

	/*
	 * �ٸ��̸����� ���� �߻���
	 * 
	 * - ���̸����� ����
	 */
	private void saveAsFile() {
		chooser.setDialogTitle("Save as");
		chooser.setApproveButtonText("Save");
		String fileName;
		//���� �˾� 
		int result = chooser.showSaveDialog(this);
		//�����ư 
		if (result == JFileChooser.APPROVE_OPTION) {
			//���� ���+���ϸ� 
			path = chooser.getSelectedFile().getPath();
			//fileName = ���ϸ� 
			fileName = path.substring(path.lastIndexOf("/")+1);
			//fileName�� "."�� ���ԵǾ� ���� ��� 
			if(fileName.contains(".")) {
				//���Ե� "."�� ".txt"�� �ƴ� ��� 
				if(!fileName.contains(".txt")) {
					JOptionPane.showMessageDialog(
						this,
						"�߸��� ���ϸ��Դϴ�.",
						"Information",
						JOptionPane.INFORMATION_MESSAGE
					);
					saved = false;
					//�����޽��� ��� �� saveAsFile���ȣ�� 
					saveAsFile();
				}
				//���Ե� "."�� ".txt"�� ��� path�� �״�� ����.
				//���ϸ� ���� 
				content.contentTitle = fileName.substring(0, fileName.lastIndexOf("."));
			}else {
				//���ϸ��� Ȯ���ڰ� ���� ��� 
				content.contentTitle = fileName;
				path += ".txt";
			}
			File ftoSave = new File(path);
			if(ftoSave.exists()) {
				int returnValue = JOptionPane.showConfirmDialog(
					this,
					"�̹� �����ϴ� ���ϸ��Դϴ�. ����ڽ��ϱ�?",
					"���� ����",
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
	//����Ǿ��ִ� path���ۼ��������� �����ϴ� �޽�� 
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
	 * â���� �߻���
	 * 
	 * 	1. changed = false â����
	 * 	2. changed = true
	 * 		1. �� ������ ��� ���̸����� ����
	 * 		2. ���� ������ �ۼ����̴� ��� 
	 * 			1. ���忩�� yes = ���� �� ����
	 * 			2. ���忩�� no = �׳� ����
	 * 			3. ���忩�� cancel = ���ư��� 
	 */
	private void exitFile() {
		if (changed) {
			int returnValue = JOptionPane.showConfirmDialog(
					this,
					"�ۼ����� ������ �����Ͻðڽ��ϱ�?",
					"���� ����",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE
				);
			if (isNewFile()) {
				//��Ȳ 2-1
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
				//��Ȳ 2-1
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

	// ���� ��ȭ �߻���
	private void hasChanged() {
		changed = true;
		setTitle(title + content.contentTitle + " *");
	}

	
	// �̺�Ʈ ����
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

	// ��ư�鿡 �̺�Ʈ ������ �߰�
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

	// â �⺻����
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
