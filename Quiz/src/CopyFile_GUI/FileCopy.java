package CopyFile_GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class FileCopy extends JFrame implements ActionListener{
	private File fTemp;
	private String dest;
	private JFileChooser chooser;
	private JTextField tfOriginFilePath;
	private JButton btnSelectOriginFile;
	private JTextField tfCopyFilePath;
	private JButton btnSelectCopyFile;
	private JTextField tfBufSize;
	private JButton btnDoCopy;
	private JTextArea taResultWindow;
	private String result;
	private SimpleDateFormat sdf;
	private Date date;
	private long time ;
	public FileCopy() {
		init();
		setDisplay();
		addListeners();
		showFrame();
		
	}
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		fTemp = null;
		chooser = new JFileChooser(".");
		tfOriginFilePath = new JTextField(15);
		tfOriginFilePath.setEditable(false);
		btnSelectOriginFile = new JButton("����");
		tfCopyFilePath = new JTextField(15);
		tfCopyFilePath.setEditable(false);
		btnSelectCopyFile = new JButton("����");
		tfBufSize = new JTextField(21);
		tfBufSize.setHorizontalAlignment(SwingConstants.CENTER);
		btnDoCopy = new JButton("����");
		taResultWindow = new JTextArea("", 8, 21);
		sdf = new SimpleDateFormat("yyyy.MM.dd(a hh:mm:ss)");
	}
	private void setDisplay() {
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		JPanel pnlFiles = new JPanel(new GridLayout(0,1));
		
		JPanel pnlOpen = new JPanel(new FlowLayout(FlowLayout.CENTER));
		LineBorder lBorder = new LineBorder(Color.BLACK, 1);
		pnlOpen.setBorder(new TitledBorder(lBorder, "��������"));
		pnlOpen.add(tfOriginFilePath,BorderLayout.CENTER);
		pnlOpen.add(btnSelectOriginFile);
		
		JPanel pnlSave = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlSave.setBorder(new TitledBorder(lBorder, "���纻����"));
		pnlSave.add(tfCopyFilePath,BorderLayout.CENTER);
		pnlSave.add(btnSelectCopyFile);
		
		JPanel pnlBuf = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlBuf.setBorder(new TitledBorder(lBorder, "����ũ��"));
		pnlBuf.add(tfBufSize);
		
		JPanel pnlDo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlDo.add(btnDoCopy);
		
		pnlFiles.add(pnlOpen);
		pnlFiles.add(pnlSave);
		pnlFiles.add(pnlBuf);
		
		JPanel pnlResult = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlResult.setBorder(new TitledBorder(lBorder, "������"));
		taResultWindow.setEditable(false);
		JScrollPane pnResult = new JScrollPane(taResultWindow);
		pnlResult.add(pnResult);
		
		add(pnlFiles, BorderLayout.NORTH);
		add(pnlDo, BorderLayout.CENTER);
		add(pnlResult, BorderLayout.SOUTH);
	}
	private void addListeners() {
		btnSelectOriginFile.addActionListener(this);
		btnSelectCopyFile.addActionListener(this);
		btnDoCopy.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if(btn == btnSelectOriginFile) {
			int cOpener = chooser.showOpenDialog(null);
			if(cOpener == JFileChooser.APPROVE_OPTION) {
				try {
					fTemp = chooser.getSelectedFile();
					tfOriginFilePath.setText(fTemp.getCanonicalPath());
				}catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(btn == btnSelectCopyFile) {
			int cSaver = chooser.showSaveDialog(null);
			if(cSaver == JFileChooser.APPROVE_OPTION) {
				try {
					dest = chooser.getSelectedFile().getCanonicalPath();
					tfCopyFilePath.setText(dest);
				}catch(Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		if(btn == btnDoCopy) {
			String src = tfOriginFilePath.getText();
			date = new Date();
			fileCopy(src, dest);
			setResult(sdf.format(date), src, dest, fTemp.length(), time);
		}
	}
	public void fileCopy(String src, String copy) {
		FileInputStream fis = null;
		FileOutputStream fos = null;
		time = System.currentTimeMillis();
		try {
			fis = new FileInputStream(src);
			fos = new FileOutputStream(copy);
			int bufSize = Integer.parseInt(tfBufSize.getText());
			byte[] buf = new byte[bufSize];
			int count = -1;
			while( (count = fis.read(buf)) != -1) {
				fos.write(buf, 0, count);
			}
			time = System.currentTimeMillis() - time;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (Exception e2) {}
			try {
				fos.close();
			} catch (Exception e2) {}
		}
	}
	public void setResult(String date, String oFile, String cFile, long size, long time) {
		result = "- �۾����� -\n";
		result += "- �۾��Ͻ� : " + date + "\n";
		result += "- �������� : " + oFile + "\n";
		result += "- ���纻���� : " + cFile + "\n";
		result += "- ����ũ�� : " + size + "\n";
		result += "- �ҿ�ð� : " + time;
		taResultWindow.setText(result);
	}
	private void showFrame() {
		setTitle("���Ϻ���");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new FileCopy();
	}
}

