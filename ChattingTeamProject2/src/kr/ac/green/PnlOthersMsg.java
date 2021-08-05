package kr.ac.green;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class PnlOthersMsg extends JPanel{
	private JLabel lblIconImg;
	private JLabel lblTalker;
	private JLabel lblTailImg;
	private JTextArea taMsg;
	private RoundedPanel pnlRound;
	
	public PnlOthersMsg(Message message) {
		int iIcon = message.getIcon();
		String talker = message.getMsgTalker();
		String msg = message.getMsg();
		boolean whisper = false;
		try {
			if(message.getMsgListener().length()>0 || message.getMsgListener() != null) {
				whisper = true;
			}
		} catch(Exception e) {}
		
		//display
		setLayout(new BorderLayout());
		setBorder(new EmptyBorder(0,0,5,0));
		setMinimumSize(new Dimension(50,80));
		setMaximumSize(new Dimension(350,500));
		
		
		JPanel pnlOthersMessage = new JPanel(new BorderLayout());
		
		JPanel pnlIcon = new JPanel(new BorderLayout());
		lblIconImg = new JLabel();
		setIcon(iIcon);
		lblIconImg.setPreferredSize(new Dimension(30,30));
		pnlIcon.add(lblIconImg, BorderLayout.NORTH);
		
		
		JPanel pnlMessage = new JPanel(new BorderLayout());
		lblTalker = new JLabel();
		if(whisper) {
			lblTalker.setText(talker + "(庇加富)");
		}else {
			lblTalker.setText(talker);
		}
		
		lblTalker.setBorder(new EmptyBorder(0,5,3,0));
		pnlMessage.add(lblTalker, BorderLayout.NORTH);
		
		JPanel pnlBubble = new JPanel(new BorderLayout());
		JPanel pnlBubbleTail = new JPanel(new BorderLayout());
		JPanel pnlBubbleBody = new JPanel(new BorderLayout());
		
		lblTailImg = new JLabel();
		if(whisper) {
			lblTailImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/otherWhisperTail.png")
					.getScaledInstance(7, 9, Image.SCALE_SMOOTH)));
		}else {
			lblTailImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/otherTail.png")
					.getScaledInstance(7, 9, Image.SCALE_SMOOTH)));
		}
		
		pnlBubbleTail.setBorder(new EmptyBorder(7,0,0,0));
		pnlBubbleTail.add(lblTailImg, BorderLayout.NORTH);
		if(whisper) {
			pnlRound = new RoundedPanel(new Color(0x0add0a), 20);
		}else {
			pnlRound = new RoundedPanel(new Color(0xE6E6E6), 20);
		}
		
		
		JPanel pnlMsgWrap = new JPanel(new BorderLayout());
		
		taMsg = null;
		if(msg.length()<17) {
			taMsg = new JTextArea();
		}else {
			taMsg = new JTextArea(1,16);
		}
		taMsg.setText(msg);
		taMsg.setWrapStyleWord(true);
		taMsg.setLineWrap(true);
		taMsg.setEditable(false);
		taMsg.setOpaque(true);
		if(whisper) {
			taMsg.setBackground(new Color(0x00DB3F));
			taMsg.setForeground(Color.WHITE);
		}else {
			taMsg.setBackground(new Color(0xE6E6E6));
		}
		
		pnlMsgWrap.add(taMsg, BorderLayout.CENTER);
		
		pnlRound.add(pnlMsgWrap, BorderLayout.CENTER);
		pnlRound.setBorder(new EmptyBorder(5,7,5,7));
		pnlBubbleBody.add(pnlRound, BorderLayout.CENTER);
		pnlBubble.add(pnlBubbleTail, BorderLayout.WEST);
		pnlBubble.add(pnlBubbleBody, BorderLayout.CENTER);
		
		pnlMessage.add(pnlBubble, BorderLayout.CENTER);
		
		
		pnlOthersMessage.add(pnlIcon, BorderLayout.WEST);
		pnlOthersMessage.add(pnlMessage, BorderLayout.CENTER);
		add(pnlOthersMessage, BorderLayout.WEST);
		
		ChattingUtils.setBgWhite(this,pnlIcon, pnlMessage, pnlBubble, pnlBubbleTail, pnlBubbleBody);
	}
	private void setIcon(int iIcon) {
		ImageIcon imgIcon = null;
		if(iIcon == 1) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0101.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 2) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0202.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 3) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0303.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 4) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0404.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 5) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0505.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 6) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0606.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 7) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0707.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 8) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0808.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 9) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/0909.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 10) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/1010.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}else if(iIcon == 11) {
			imgIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/1111.png")
					.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		}
		lblIconImg.setIcon(imgIcon);
	}
	public void setOthersMsg(Message message) {
		/*
		private JLabel lblIconImg;
	private JLabel lblTalker;
	private JLabel lblTailImg;
	private JTextArea taMsg;
		*/
		boolean whisper = false;
		if(message.getMsgListener().length()>0) {
			whisper = true;
		}
		setIcon(message.getIcon());
		if(whisper) {
			lblTalker.setText(message.getMsgTalker() + "(庇加富)");
			lblTailImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/otherWhisperTail.png")
					.getScaledInstance(7, 9, Image.SCALE_SMOOTH)));
		}else {
			lblTalker.setText(message.getMsgTalker());
			lblTailImg.setIcon(null);
		}
		
	}
}
