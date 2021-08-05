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

public class PnlMyMsg extends JPanel{
	public PnlMyMsg(Message message) {
		String msg = message.getMsg();
		boolean whisper = false;
		try {
			if(message.getMsgListener().length()>0 || message.getMsgListener() != null) {
				whisper = true;
			}
		}catch(Exception e) {}
		//display
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(50,50));
		setMaximumSize(new Dimension(350,50));
		setAlignmentY(JPanel.TOP_ALIGNMENT);
		setBorder(new EmptyBorder(0,0,5,0));
		JPanel pnlMyMessage = new JPanel(new BorderLayout());
		
		JPanel pnlListener = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel lblListener = new JLabel();
		pnlListener.add(lblListener);
		if(whisper) {
			lblListener.setText(message.getMsgListener()+"¿¡°Ô ±Ó¼Ó¸»");
		}
		JPanel pnlMessage = new JPanel(new BorderLayout());
		
		JPanel pnlBubble = new JPanel(new BorderLayout());
		JPanel pnlBubbleTail = new JPanel(new BorderLayout());
		JPanel pnlBubbleBody = new JPanel(new BorderLayout());
		
		JLabel lblTailImg = new JLabel();
		
		if(whisper) {
			lblTailImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/myWhisperTail.png")
					.getScaledInstance(7, 9, Image.SCALE_SMOOTH)));
		}else {
			lblTailImg.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().getImage("img/myTail.png")
					.getScaledInstance(7, 9, Image.SCALE_SMOOTH)));
		}
		
		pnlBubbleTail.setBorder(new EmptyBorder(0,0,7,0));
		pnlBubbleTail.add(lblTailImg, BorderLayout.SOUTH);
		RoundedPanel pnlRound;
		if(whisper) {
			pnlRound = new RoundedPanel(new Color(0x00DB3F), 20);
		}else {
			pnlRound = new RoundedPanel(new Color(0x107aff), 20);
		}
		
		JPanel pnlMsgWrap = new JPanel(new BorderLayout());
		
		JTextArea taMsg = null;
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
		}else {
			taMsg.setBackground(new Color(0x107aff));
		}
		taMsg.setForeground(Color.WHITE);
		pnlMsgWrap.add(taMsg, BorderLayout.CENTER);
		
		pnlRound.add(pnlMsgWrap, BorderLayout.CENTER);
		pnlRound.setBorder(new EmptyBorder(5,7,5,7));
		pnlBubbleBody.add(pnlRound, BorderLayout.CENTER);
		pnlBubble.add(pnlBubbleTail, BorderLayout.EAST);
		pnlBubble.add(pnlBubbleBody, BorderLayout.CENTER);
		pnlBubble.add(pnlListener, BorderLayout.NORTH);
		pnlMessage.add(pnlBubble, BorderLayout.CENTER);
		
		pnlMyMessage.add(pnlMessage, BorderLayout.CENTER);
		add(pnlMyMessage, BorderLayout.EAST);
		
		ChattingUtils.setBgWhite(this, pnlListener,pnlMessage, pnlBubble, pnlBubbleTail, pnlBubbleBody);
	}
}
