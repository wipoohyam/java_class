package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class DatePicker extends JDialog implements ActionListener{
	private JLabel lblYear;
	private JLabel lblMonth;
	
	private JButton btnPreYear;
	private JButton btnNextYear;
	
	private JButton btnPreMonth;
	private JButton btnNextMonth;
	
	private MyCalendar calendar;
	private BookSaver owner;
	public DatePicker(BookSaver owner,Calendar cal, boolean modal) {
		this.owner = owner;
		Dimension dMoveBtns = new Dimension(50,28);
		btnPreYear = new JButton("《 작년");
		MyUtils.setMyButton(btnPreYear, 0);
		btnPreYear.setPreferredSize(dMoveBtns);
		btnNextYear = new JButton("내년 》");
		MyUtils.setMyButton(btnNextYear, 0);
		btnNextYear.setPreferredSize(dMoveBtns);
		
		btnPreMonth = new JButton("〈	 전월");
		MyUtils.setMyButton(btnPreMonth, 0);
		btnPreMonth.setPreferredSize(dMoveBtns);
		btnNextMonth = new JButton("내월 〉");
		MyUtils.setMyButton(btnNextMonth, 0);
		btnNextMonth.setPreferredSize(dMoveBtns);
		
		
		btnPreYear.addActionListener(this);
		btnNextYear.addActionListener(this);
		btnPreMonth.addActionListener(this);
		btnNextMonth.addActionListener(this);
		
		Font font = new Font(Font.DIALOG, Font.BOLD, 30);
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH)+1;
		lblYear = new JLabel(String.valueOf(year));
		lblYear.setBorder(new EmptyBorder(0,30,0,0));
		lblYear.setFont(font);
		lblMonth = new JLabel(String.valueOf(month));
		lblMonth.setBorder(new EmptyBorder(0,0,0,30));
		lblMonth.setFont(font);
		
		calendar = new MyCalendar(year, month);
		JPanel pnlNorth = new JPanel();
		pnlNorth.add(btnPreYear);
		pnlNorth.add(btnPreMonth);
		pnlNorth.add(lblYear);
		pnlNorth.add(new JLabel("."));
		pnlNorth.add(lblMonth);
		pnlNorth.add(btnNextMonth);
		pnlNorth.add(btnNextYear);
		
		add(pnlNorth, BorderLayout.NORTH);
		add(calendar, BorderLayout.CENTER);
		
		addListeners();
		
		setTitle("날짜 선택");
		setSize(400, 300);
		setLocation(owner.p);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);
	}
	private void addListeners() {
		for(int i = 0; i<calendar.getDays().length;i++) {
			calendar.getDays()[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent me) {
					Object o = me.getSource();
					if(o instanceof DayComponent || o != null) {
						DayComponent day = (DayComponent) o;
						Calendar selectedDate = Calendar.getInstance();
						try {
							selectedDate.setTime(owner.sdf.parse(day.getYear()+"."+day.getMonth()+"."+day.getDay()));
						} catch(Exception e) {
							e.printStackTrace();
						}
						if (owner.pick==owner.DATEFROM) {
							owner.setTfDateFrom(selectedDate);
						}else {
							owner.setTfDateTo(selectedDate);
						}
						dispose();
					}
				}
			});
		}
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowDeactivated(WindowEvent we) {
				dispose();
			}
		});
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		int year = Integer.parseInt(lblYear.getText());
		int month = Integer.parseInt(lblMonth.getText());
		
		if(src == btnPreMonth) {
			month--;
			if(month < 1) {
				month = 12;
				year--;
			}
		}else if(src == btnPreYear) {
			if(year>2) {
				year--;
			}
		}else if(src == btnNextMonth) {
			month++;
			if(month>12) {
				month = 1;
				year ++;
			}
		}else {
			year++;
		}
		
		calendar.setYear(year);;
		calendar.setMonth(month);
		calendar.reset();
		addListeners();
		lblYear.setText(String.valueOf(year));
		lblMonth.setText(String.valueOf(month));
	}
}
