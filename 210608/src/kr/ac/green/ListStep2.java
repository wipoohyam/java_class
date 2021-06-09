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
			1. ���ϼ���
				ListSelectionModel.SINGLE_SELECTION
			2. ���ӵ� �������� 
				ListSelectionModel.SINGLE_INTERVAL_SELECTION
			3. ����, �񿬼� ��������(default)
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
	//listCellRenderer �� �����Ҽ� �ְ����ִ� �߻�Ŭ���� DefaultListCellRenderer
	private class MyListCellRenderer extends DefaultListCellRenderer{
		@Override
		//�ٽ�
		//����Ʈ�������� : ����Ʈ�ȿ� ���ִ� ������ �ϳ��ϳ��� ��� ǥ������ ���ϴ�
		//list:����Ʈ, value: ��, idx
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
