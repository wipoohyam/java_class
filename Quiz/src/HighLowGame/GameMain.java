package HighLowGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameMain extends JFrame{
	private JTextField tfUser;
	private JTextArea taGameInfo;
	private JScrollPane scroll;
	private JLabel lblRest;
	
	Random r;
	private String nl = "\n\n";
	private int answer;
	private int tempAnswer;
	private int prevAnswer = -1;
	private String status;
	private String strStatus = "Status :: ";
	private String strRest = "현재 남은 기회 > ";
	private int chance = 6;
	
	public GameMain() {
		init();
		setDisplay();
		gameSetting();
		printResult();
		addListeners();
		showFrame();
	}
	private void init() {
		tfUser = new JTextField(5);
		taGameInfo = new JTextArea(30, 40);
		taGameInfo.setEditable(false);
		scroll = new JScrollPane(taGameInfo);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		taGameInfo.add(scroll);
		lblRest = new JLabel();
	}
	private void setDisplay() {
		add(tfUser, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(lblRest, BorderLayout.SOUTH);
		lblRest.setBorder(new EmptyBorder(5,5,5,0));
	}
	private String gameRule() {
		String printRule = "";
		String bp = "♠♠♠♠♠♠ ";
		for(int i = 0; i<47; i++) {
			printRule += "+";
		}
		printRule += nl;
		printRule += "\t\tHighLow Game";
		printRule += nl;
		for(int i = 0; i<47; i++) {
			printRule += "+";
		}
		printRule += nl;
		printRule += bp + "하이로우 게임에 오신것을 환영합니다!!!" + nl;
		printRule += bp + "6번의 기회 안에 숨겨진 숫자륾 맞추면 당신이 승리합니다." + nl;
		printRule += bp + "숨겨진 숫자는 1에서 100 사이의 숫자입니다." + nl;
		printRule += bp + "건투를 빕니다!!! 게임을 시작합니다." + nl;
		return printRule;
	}
	private void gameSetting() {
		r = new Random();
		answer = r.nextInt(100) + 1; 
		taGameInfo.setText(gameRule());
	}
	private void chkAnswer() {
		int newUAnswer = 0;
		System.out.println(answer);
		try {
			newUAnswer = Integer.parseInt(tfUser.getText());
			if (newUAnswer < 0 || newUAnswer>100) {
				JOptionPane.showMessageDialog(this, "1~100 내의 숫자를 입력해주세요.", "Information", JOptionPane.WARNING_MESSAGE);
			}else {
				String printConsole = "♠ 결과 :";
				if (answer == newUAnswer) {
					printConsole += "축하합니다. 당신의 승리입니다!!!" + nl;
					printConsole += "♠ 숨겨진 숫자는 " + answer + "이었습니다." + nl;
					lblRest.setText("축하합니다. 당신의 승리입니다.");
				}else {
					printConsole += "틀렸습니다." + nl;
					printConsole += "♠ hint>> " + answerRange(newUAnswer) + nl;
					printResult();
				}
				taGameInfo.append(printConsole);
			}
		} catch(NumberFormatException nfe){
			JOptionPane.showMessageDialog(this, "숫자를 입력해주세요.", "Information", JOptionPane.WARNING_MESSAGE);
		}
		tfUser.setText("");
	}
	private String answerRange(int newAnswer) {
		String range = "숨겨진 숫자는 ";
		// uAnswer, tempAnswer
		if (prevAnswer == -1) {
			if(newAnswer > answer) {
				prevAnswer = 0;
				tempAnswer = 0;
			}else {
				prevAnswer = 100;
				tempAnswer = 100;
			}
		}
		if ((answer > prevAnswer && answer < newAnswer)||(answer < prevAnswer && answer > newAnswer)) {
			range += Math.min(prevAnswer, newAnswer) + "보다 크고 " + Math.max(prevAnswer, newAnswer) + "보다 작습니다.";
			tempAnswer = prevAnswer;
		}else if((answer > tempAnswer && answer < newAnswer)||(answer < tempAnswer && answer > newAnswer)) {
			range += Math.min(tempAnswer, newAnswer) + "보다 크고 " + Math.max(tempAnswer, newAnswer) + "보다 작습니다.";
		}
		prevAnswer = newAnswer;
		taGameInfo.setCaretPosition(taGameInfo.getDocument().getLength());
		return range;
	}
	private void printResult() {
		status = strStatus + strRest + chance--;
		if(chance >0) {
			lblRest.setText(status);
		}else {
			lblRest.setText(strStatus + "당신이 졌습니다!!!");
			tfUser.setEditable(false);
		}
	}
	private void addListeners() {
		Action action = new AbstractAction()
		{
		    @Override
		    public void actionPerformed(ActionEvent ae)
		    {
				chkAnswer();
				
		    }
		};
		tfUser.addActionListener(action);
		
	}
	private void showFrame() {
		setTitle("this is HighLow Game!!");
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args) {
		new GameMain();
	}
}
