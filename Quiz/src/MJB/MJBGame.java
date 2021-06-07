package MJB;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Random;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class MJBGame extends JFrame{
	private JRadioButton[] rbtns;
	private String[] mjb = {"묵", "찌", "빠"};
	private JButton btnStart;
	private JTextArea taGame;
	public final static int MOOK = -1;
	public final static int JJI = 0;
	public final static int BBA = 1;
	private int user;
	private int computer;
	
	public MJBGame() {
		init();
		setDisplay();
		addListeners();
		showFrame();
	}
	
	private void init() {
		rbtns = new JRadioButton[mjb.length];
		ButtonGroup bg = new ButtonGroup();
		for (int i = 0; i < rbtns.length; i++) {
			rbtns[i] = new JRadioButton(mjb[i]);
			bg.add(rbtns[i]);
		}
		btnStart = new JButton("시작");
		taGame = new JTextArea(5,20);
		
		
		rbtns[0].setActionCommand("-1");	//묵 
		rbtns[1].setActionCommand("0");		//찌
		rbtns[2].setActionCommand("1");		//빠
	}
	private void setDisplay() {
		JPanel pnlRbtns = new JPanel(new FlowLayout(FlowLayout.CENTER));
		for(int i = 0; i<rbtns.length;i++) {
			pnlRbtns.add(rbtns[i]);
		}
		
		JPanel pnlCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlCenter.add(btnStart);
		
		add(pnlRbtns, BorderLayout.NORTH);
		add(pnlCenter, BorderLayout.CENTER);
		add(taGame, BorderLayout.SOUTH);
	}
	
	private void addListeners() {
		ItemListener listener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				JRadioButton o = (JRadioButton) e.getSource();
				user = Integer.parseInt(o.getActionCommand());
			}
		};
		
		for(JRadioButton rbtn : rbtns) {
			rbtn.addItemListener(listener);
		}
		ActionListener aListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton o = (JButton) e.getSource();
				if (o==btnStart) {
					taGame.setText("");
					getResult();
				}
			}
		};
		btnStart.addActionListener(aListener);
	}
	private void computerResult() {
		taGame.append("컴퓨터 : ");
		Random r = new Random();
		computer = r.nextInt(3) - 1;
		if(computer == MOOK) {
			taGame.append("묵");
		}else if (computer == JJI) {
			taGame.append("찌");
		}else if (computer == BBA){
			taGame.append("빠");
		}
		taGame.append("\n");
	}
	private void getResult() {
		computerResult();
		taGame.append("당신 : ");
		if(user == MOOK) {
			taGame.append("묵");
		}else if(user == JJI) {
			taGame.append("찌");
		}else if(user == BBA) {
			taGame.append("빠");
		}
		taGame.append("\n");
		if(user == computer) {
			taGame.append("비겼습니다.\n");
		}else if (user == MOOK) {
			switch(computer) {
				case JJI:
					taGame.append("당신이 이겼습니다.\n");
					break;
				case BBA:
					taGame.append("당신이 졌습니다.\n");
			}
		}else if(user == JJI) {
			switch(computer) {
				case MOOK:
					taGame.append("당신이 졌습니다.\n");
					break;
				case BBA:
					taGame.append("당신이이겼습니다.\n");
					break;
			}
		}else if(user == BBA) {
			switch(computer) {
				case MOOK:
					taGame.append("당신이 이겼습니다.");
					break;
				case JJI:
					taGame.append("당신이 졌습니다.");
					break;
			}
		}
	}
	private void showFrame() {
		setTitle("MJBGame");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new MJBGame();
	}
}
