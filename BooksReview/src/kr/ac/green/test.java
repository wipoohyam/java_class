package kr.ac.green;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

//public class test extends JFrame implements ActionListener {
//
//    JPanel panel;
//
//    public test() {
//        super("Add component on JFrame at runtime");
//        setLayout(new BorderLayout());
//        this.panel = new JPanel();
//        this.panel.setLayout(new FlowLayout());
//        add(panel, BorderLayout.CENTER);
//        JButton button = new JButton("CLICK HERE");
//        add(button, BorderLayout.SOUTH);
//        button.addActionListener(this);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(500, 500);
//        setVisible(true);
//    }
//
//    public void actionPerformed(ActionEvent evt) {
//        this.panel.add(new JButton("Button"));
//        this.panel.revalidate();
//        validate();
//    }
//
//    public static void main(String[] args) {
//        test acojfar = new test();
//    }
//}
public class test {
	public boolean returnTest() {
		int i = 5;
		boolean flag = true;
		if(i > 6 || i>7) {
			flag = false;
			return flag;
		}else if(i <6) {
			flag = false;
			return true;
		}
		return flag;
	}
	public static void main(String[] args) {
		test t = new test();
		System.out.println("start");
		System.out.println(1.5%1);
		
		String[] arr = new String[5];
		arr[0] = "asdf";
		arr = new String[3];
		System.out.println("length"+arr.length);
		System.out.println(Arrays.toString(arr));
	}
}