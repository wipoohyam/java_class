package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListStep4 extends JFrame implements ActionListener {
	private JList<String> list;
	private DefaultListModel<String> model;
	private JButton btnAdd;
	private JButton btnDel;
	
	public ListStep4() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		list.setPrototypeCellValue("abcdefghijklmnopqrstu");
		list.setVisibleRowCount(10);
		
		btnAdd = new JButton("Add");
		btnDel = new JButton("Del");
	}
	
	private void setDisplay() {
		JPanel pnlCenter = new JPanel();
		JScrollPane scroll = new JScrollPane(list);
		pnlCenter.add(scroll);
		
		JPanel pnlSouth = new JPanel(new GridLayout(1,0));
		pnlSouth.add(btnAdd);
		pnlSouth.add(btnDel);
		
		add(pnlCenter, BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
	}
	private void addListeners() {
		btnAdd.addActionListener(this);
		btnDel.addActionListener(this);;
	}
	private void showFrame() {
		setTitle("ListEx");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnAdd) {
			String data = JOptionPane.showInputDialog(this, "Input your Item");
			model.addElement(data);
		}else {
			int idx = list.getSelectedIndex();
			if(idx >= 0) {
				model.remove(idx);
			}
		}
	}
	public static void main(String[] args) {
		new ListStep4();
	}
}
