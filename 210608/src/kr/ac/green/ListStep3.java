package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class ListStep3 extends JFrame{
	private JList<String> list;
	private JLabel lbl;
	private JTextField tfNewItem;
	//리스트내부에서 변동이 있는 데이터를 쓸때는 model을 써야함 
	private DefaultListModel<String> model;
	
	private JPopupMenu pMenu;
	private JMenuItem miRemove;
	
	public ListStep3() {
		//모양 = 렌더러 / 데이터 = 모델 
		pMenu = new JPopupMenu();
		miRemove = new JMenuItem("Remove");
		pMenu.add(miRemove);
		
		model = new DefaultListModel<String>();
		list = new JList<String>(model);
		lbl = new JLabel("추가항목");
		tfNewItem = new JTextField(5);
		
		list.setPrototypeCellValue("XXXXXXXXXXX");
		list.setVisibleRowCount(3);
		
		JPanel pnlSouth = new JPanel();
		pnlSouth.add(lbl);
		pnlSouth.add(tfNewItem);
		
		add(new JScrollPane(list), BorderLayout.CENTER);
		add(pnlSouth, BorderLayout.SOUTH);
		
		tfNewItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//enter키를 눌렀을 때 
				//모델에 요소를 추가하고 
				model.addElement(tfNewItem.getText());
				//텍스트필드를 비우고 
				tfNewItem.setText("");
				//리스트에서 모델의 마지막 인덱스를 보여지도록 보장함 
				list.ensureIndexIsVisible(model.getSize() - 1);
			}
		});
		
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me) {
				showPopup(me);
			}
			@Override
			public void mouseReleased(MouseEvent me) {
				showPopup(me);
			}
			
			private void showPopup(MouseEvent me) {
				if(me.isPopupTrigger()) {
					int x = me.getX();
					int y = me.getY();
					int idx = list.locationToIndex(new Point(x,y));
					if(idx >= 0 && list.getSelectedIndex() >= 0) {
						pMenu.show(list, x, y);
					}
				}
			}
		});
		
		miRemove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.remove(list.getSelectedIndex());
			}
		});
		setTitle("JList step3");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}

	public static void main(String[] args) {
		new ListStep3();
	}
}
