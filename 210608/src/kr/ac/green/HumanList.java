package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

public class HumanList extends JFrame{
	private JList<Human> list;

	public HumanList() {
		list= new JList<Human>(new Human[] {
				new Human("박우석", 50, Human.FEMALE),
				new Human("박규일", 47, Human.FEMALE),
				new Human("구민희", 33, Human.MALE),
				new Human("이다영", 35, Human.MALE)
		}
		);
		list.setPrototypeCellValue(new Human("XXXXXXXXXXX", 1000000, Human.FEMALE));
		list.setVisibleRowCount(3);
		
		ListCellRenderer renderer = new HumanListRenderer();
		list.setCellRenderer(renderer);
		
		add(new JScrollPane(list), BorderLayout.CENTER);
		setTitle("Human List");
		pack();
		setLocation(100,0);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	

	private class HumanListRenderer extends DefaultListCellRenderer {
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus) {
			
			JPanel pnl = new JPanel();
			Human h = (Human) value;
			String iconName = "female.png";
			if(h.getGender() == Human.MALE) {
				iconName = "male.png";
			}
			JLabel lbl = new JLabel(new ImageIcon(iconName));
			pnl.add(lbl);
			lbl = new JLabel(h.getName() + "(" + h.getAge() + ")");
			pnl.add(lbl);
			
			if(isSelected) {
				pnl.setBackground(Color.YELLOW);
			}
			if(cellHasFocus) {
				pnl.setBorder(new LineBorder(Color.RED, 1));
			}
			return pnl;
		}
	}
	public static void main(String[] args) {
		new HumanList();
	}
}
