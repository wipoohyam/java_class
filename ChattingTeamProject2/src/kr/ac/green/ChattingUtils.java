package kr.ac.green;
import java.awt.Color;
import java.awt.Component;
import java.io.Closeable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChattingUtils {
	
	
	public static void setBgWhite(Component...comps) {
		for(Component comp : comps) {
			if(comp instanceof JPanel) {
				JPanel pnl = (JPanel) comp;
				pnl.setOpaque(true);
				pnl.setBackground(Color.WHITE);
			}else if (comp instanceof JLabel) {
				JLabel lbl = (JLabel) comp;
				lbl.setOpaque(true);
				lbl.setBackground(Color.WHITE);
			}else if (comp instanceof JButton) {
				JButton btn = (JButton) comp;
				btn.setOpaque(true);
				btn.setBackground(Color.WHITE);
			}else if(comp instanceof JTextField) {
				JTextField tf = (JTextField) comp;
				tf.setOpaque(true);
				tf.setBackground(Color.WHITE);
			}
		}
	}
	public static void closeAll(Closeable...c) {
		for(Closeable temp : c) {
			try {
				temp.close();
			} catch(Exception e) {}
		}
	}
	public static void showDialog(Component c, String text, String title) {
		JOptionPane.showMessageDialog(c, text, title, JOptionPane.INFORMATION_MESSAGE);
	}
	public static void setUnvisible(JFrame c) {
		c.dispose();
	}
	
}
