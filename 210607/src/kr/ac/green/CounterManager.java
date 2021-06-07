package kr.ac.green;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

/*
	�ݵ�� �ϸ� �ȵǴ°� = UI�� ����
	��� Ȯ���� ���� ������ ����
	�׷�? --> �����ؾ��ϴ� "��"�� ���� ������ ��ü�� ����� save/load �ؼ� Counter�� �����ؾ��Ѵ�.
	
	says : �����ؾ��ϴ� ���� ���� �����Ͽ� �����Ѵ�.(��� �����ϰ� ��� �������� ���� �ߴ´�)
*/
public class CounterManager extends JFrame implements ActionListener{
	private Counter counter;
	
	private JButton btnNew;
	private JButton btnSave;
	private JButton btnLoad;
	
	public CounterManager() {
		init();
		setDisplay();
		addListeners();
		showFrame();
		
	}
	private void init() {
		btnNew = new JButton("New");
		btnSave = new JButton("Save");
		btnLoad = new JButton("Load");
	}
	private void setDisplay() {
		setLayout(new GridLayout(1,0));
		add(btnNew);
		add(btnSave);
		add(btnLoad);
	}
	private void addListeners() {
		btnNew.addActionListener(this);
		btnSave.addActionListener(this);
		btnLoad.addActionListener(this);
	}
	private void showFrame() {
		setTitle("Counter Manager");
		setSize(400, 100);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src == btnNew && counter == null) {
			counter = new Counter();
		} else if(src == btnSave && counter != null) {
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;
			try {
				fos = new FileOutputStream("counter.dat");
				oos = new ObjectOutputStream(fos);
				
				oos.writeObject(counter);
				oos.flush();
				oos.reset();
				counter.dispose();
				counter = null;
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				MyUtils.closeAll(oos, fos);
			}
		} else if(src == btnLoad && counter ==null){
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream("counter.dat");
				ois = new ObjectInputStream(fis);
				
				counter = (Counter) ois.readObject();
				counter.setVisible(true);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				MyUtils.closeAll(ois, fis);
			}
		}
		
	}
	public static void main(String[] args) {
		new CounterManager();
	}
}
