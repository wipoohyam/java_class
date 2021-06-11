package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

public class PnlReviewList extends JPanel {
	private JLabel lblPhoto;		//å �̹���
	private JLabel lblBookTitle;	//å ����
	private JTextArea lblReview;		//�ı�
	private JLabel lblReadDay;		//���� �Ⱓ
	private JLabel lblReviewPoint;	//����
	
	private JButton btnModify;	//����
	private JButton btnDelete;	//����
	
	public PnlReviewList() {
		init();
		setDisplay();
	}
	
	public void init() {
		//����ڰ� ���� �ִ��� ������� �����صδ� �� ���� �� ���Ƽ� ������ ��Ƶ׾��!
		lblPhoto = new JLabel();
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("�Ƹ��.png");
		img = img.getScaledInstance(85, 100, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(img);
		lblPhoto.setIcon(icon);
		
		lblBookTitle = new JLabel();
		lblBookTitle.setText("å ����");
		lblReadDay = new JLabel();
		lblReadDay.setText("2021.06.11 ~ 2021.07.11");
		lblReview = new JTextArea(3, 30);
		//���� �� �κ�
		lblReviewPoint = new JLabel();
		
		btnModify = new JButton("����");
		btnDelete = new JButton("����");
	}
	
	public void setDisplay() {
		setPreferredSize(new Dimension(300,40));
		JPanel pnlMain = new JPanel(new BorderLayout());
		pnlMain.setBorder(new LineBorder(Color.GRAY, 2));
		
		JPanel pnlWest = new JPanel();
		pnlWest.add(lblPhoto);
		
		JPanel pnlEast = new JPanel(new BorderLayout());
		
		JPanel pnlENorth = new JPanel(new BorderLayout());
		JPanel pnlENCenter = new JPanel();
		JPanel pnlENEast = new JPanel(new GridLayout(0,1));
		//����
		pnlENEast.add(lblReviewPoint);
		pnlENEast.add(lblReadDay);
		
		pnlENorth.add(lblBookTitle, BorderLayout.WEST);
		pnlENorth.add(pnlENCenter, BorderLayout.CENTER);
		pnlENorth.add(pnlENEast, BorderLayout.EAST);
		
		JPanel pnlECenter = new JPanel();
		pnlECenter.add(lblReview);
		
		JPanel pnlESouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnlESouth.add(btnModify);
		pnlESouth.add(btnDelete);
		
		pnlEast.add(pnlENorth, BorderLayout.NORTH);
		pnlEast.add(pnlECenter, BorderLayout.CENTER);
		pnlEast.add(pnlESouth, BorderLayout.SOUTH);
		
		pnlMain.add(pnlWest, BorderLayout.WEST);
		pnlMain.add(pnlEast, BorderLayout.EAST);
		
		add(pnlMain);
	}
}
