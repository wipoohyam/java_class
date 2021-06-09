package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class JoinForm extends JFrame{
	private JLabel lblId;
	private JLabel lblPw;
	private JLabel lblName;
	private JLabel lblGender;
	private JLabel lblBday;
	
	private JTextField tfId;
	private JPasswordField pfPw;
	private JTextField tfName;
	
	private JRadioButton rbMale;
	private JRadioButton rbFemale;
	
	private Calendar cal;
	private JComboBox<Integer> cbYear;
	private Integer[] cbListYr;
	private JLabel lblYr;
	private JComboBox<Integer> cbMonth;
	private Integer[] cbListMon;
	private JLabel lblMon;
	private JComboBox<Integer> cbDay;
	private Integer[] cbListDay;
	private JLabel lblDay;
	
	private JCheckBox chbHobby1;
	private JCheckBox chbHobby2;
	private JCheckBox chbHobby3;
	private JCheckBox chbHobby4;
	private JCheckBox chbHobby5;
	private JCheckBox chbHobby6;
	
	private JButton bConfirm;
	private JButton bCancel;
	
	public JoinForm() {
		init();
		setDisplay();
		showFrame();
		
		
	}
	private void init() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		lblId = new JLabel("아이디");
		lblPw = new JLabel("비밀번호");
		lblName = new JLabel("이름");
		lblGender = new JLabel("성별");
		lblBday = new JLabel("생년월일");
		
		tfId = new JTextField(15);
		pfPw = new JPasswordField(15);
		tfName = new JTextField(15);
		
		rbMale = new JRadioButton("남자");
		rbFemale = new JRadioButton("여자");
		ButtonGroup bGroup = new ButtonGroup();
		bGroup.add(rbMale);
		bGroup.add(rbFemale);
		
		cbListYr = new Integer[2021 - 1970];
		for(int i = 0; i<cbListYr.length;i++) {
			cbListYr[i] = 1970 + i;
		}
		cbYear = new JComboBox<Integer>(cbListYr);
		lblYr = new JLabel("년");
		cbListYr = new Integer[2021 - 1970];
		for(int i = 0; i<cbListYr.length;i++) {
			cbListYr[i] = 1970 + i;
		}
		lblMon = new JLabel("월");
		cbListMon = new Integer[12];
		for(int i = 0; i<12;i++) {
			cbListMon[i] = i+1;
		}
		cbMonth = new JComboBox<Integer>(cbListMon);
		cbListDay = new Integer[31];
		for(int i = 0; i<31; i++) {
			cbListDay[i] = i+1;
		}
		cbDay = new JComboBox<Integer>(cbListDay);
		lblDay = new JLabel("일");
		
		chbHobby1 = new JCheckBox("강도");
		chbHobby2 = new JCheckBox("절도");
		chbHobby3 = new JCheckBox("폭력");
		chbHobby4 = new JCheckBox("사기");
		chbHobby5 = new JCheckBox("협박");
		chbHobby6 = new JCheckBox("공갈");
		
		bConfirm = new JButton("확인");
		bCancel = new JButton("취소");
	}
	private void setDisplay() {
		JPanel pnlFieldsGrid = new JPanel(new BorderLayout());
		JPanel pnlId = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlId.add(tfId);
		JPanel pnlPw = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlPw.add(pfPw);
		JPanel pnlName = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlName.add(tfName);
		
		JPanel pnlRbs = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlRbs.add(rbMale);
		pnlRbs.add(rbFemale);
		
		JPanel pnlLbls = new JPanel(new GridLayout(5,1));
		pnlLbls.add(lblId);
		pnlLbls.add(lblPw);
		pnlLbls.add(lblName);
		pnlLbls.add(lblGender);
		pnlLbls.add(lblBday);
		JPanel pnlFields = new JPanel(new GridLayout(5,1));
		
		JPanel pnlCbs = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlCbs.add(cbYear);
		pnlCbs.add(lblYr);
		pnlCbs.add(cbMonth);
		pnlCbs.add(lblMon);
		pnlCbs.add(cbDay);
		pnlCbs.add(lblDay);

		pnlFields.add(pnlId);
		pnlFields.add(pnlPw);
		pnlFields.add(pnlName);
		pnlFields.add(pnlRbs);
		pnlFields.add(pnlCbs);
		
		pnlFieldsGrid.add(pnlLbls, BorderLayout.WEST);
		pnlFieldsGrid.add(pnlFields, BorderLayout.CENTER);
		
		JPanel pnlChbs = new JPanel(new GridLayout(2,3));
		pnlChbs.add(chbHobby1);
		pnlChbs.add(chbHobby2);
		pnlChbs.add(chbHobby3);
		pnlChbs.add(chbHobby4);
		pnlChbs.add(chbHobby5);
		pnlChbs.add(chbHobby6);
		pnlChbs.setBorder(new TitledBorder(new LineBorder(Color.BLACK, 1), "취미"));
		
		JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pnlBtn.add(bConfirm);
		pnlBtn.add(bCancel);
		
		
		add(pnlFieldsGrid, BorderLayout.NORTH);
		add(pnlChbs, BorderLayout.CENTER);
		add(pnlBtn, BorderLayout.SOUTH);
	}
	private void showFrame() {
		setTitle("Join Form");
		pack();
//		setSize(200,300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new JoinForm();
	}
}
