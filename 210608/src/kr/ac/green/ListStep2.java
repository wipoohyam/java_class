package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ListStep2 extends JFrame{
	private JList<String> list;
	private JLabel lbl;
	
	public ListStep2() {
		list = new JList<String>(new String[] {"apple", "banana", "kiwi", "grape"});
		lbl = new JLabel("start");
		
		list.setPrototypeCellValue("XXXXXXXXXXXXXXX");
		list.setVisibleRowCount(3);
		
		list.setCellRenderer(new MyListCellRenderer());
		
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(lbl, BorderLayout.SOUTH);
		
		/*
			1. 단일선택
				ListSelectionModel.SINGLE_SELECTION
			2. 연속된 복수선택 
				ListSelectionModel.SINGLE_INTERVAL_SELECTION
			3. 연속, 비연속 복수선택(default)
				ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
		*/
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				lbl.setText("selected item : " + list.getSelectedValue());
			}
		});
		setTitle("JLst step2");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	//listCellRenderer 를 조절할수 있게해주는 추상클래스 DefaultListCellRenderer
	private class MyListCellRenderer extends DefaultListCellRenderer{
		@Override
		//핵심
		//리스트셀렌더러 : 리스트안에 들어가있는 아이템 하나하나를 어떻게 표현할지 정하는
		//list:리스트, value: 값, idx
		public Component getListCellRendererComponent(JList list, Object value, int idx, boolean isSelected, boolean cellHasfocus) {
//			JLabel lblItem = new JLabel(value.toString());
//			
//			if(isSelected) {
//				lblItem.setText(lblItem.getText()+ "<--Selected");
//			}
			JButton lblItem = new JButton(value.toString());
			lblItem.setOpaque(true);
			if(isSelected) {
				lblItem.setBackground(Color.YELLOW);
				lblItem.setText(lblItem.getText());
			}
			if(cellHasfocus) {
				lblItem.setBackground(Color.GREEN);
				lblItem.setText(lblItem.getText() + " <-- Selected");
			}
			return lblItem;
		}
	}

	public static void main(String[] args) {
		new ListStep2();
	}
}
