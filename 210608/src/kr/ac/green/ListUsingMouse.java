package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

//ListUsingMouse
public class ListUsingMouse extends JFrame{
	private JList<String> list;
	public ListUsingMouse() {
		list = new JList<String>(new String[] {"a", "b", "c"});
		list.setPrototypeCellValue("XXXXXXXXXXXXXXXXXXXX");
		list.setVisibleRowCount(10);
		JScrollPane scroll = new JScrollPane(list);

		add(scroll, BorderLayout.CENTER);
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				//Rectangle : 위치 + 크기 
				Rectangle r = list.getCellBounds(
						list.getLastVisibleIndex(),
						list.getLastVisibleIndex()
				); 
				int listY = r.y + r.height;
				int mouseY = me.getY();
				if(mouseY > listY) {
					System.out.println("out");
				}else {
					System.out.println("in");
				}
			}
		});
		
		setTitle("MouseTest");
		pack();setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new ListUsingMouse();
	}
}
