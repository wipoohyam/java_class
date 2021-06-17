package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyCalendar extends JPanel{
	
	private int year;
	private int month;
	
	public DayComponent[] days;
	
	private String[] strDay = {"일", "월", "화", "수", "목", "금", "토"};
	
	private int lineCount = 5;
	private int firstDay;
	private int endOfMonth;
	
	public MyCalendar(int year, int month) {
		this.year = year;
		this.month = month -1;
		init();
		setDisplay();
		addListeners();
	}
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
//		reset();
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month -1;
//		reset();
	}
	public DayComponent[] getDays() {
		return days;
	}
	public void reset() {
		removeAll();
		init();
		setDisplay();
		updateUI();
//		System.out.println(days[0]);
	}
	private void init() {
		Calendar cal = Calendar.getInstance();
		cal.set(year,  month, 1);
		
		//해당 달의 시작요일 : Sun->1 ~~ Sat->7
		firstDay = cal.get(Calendar.DAY_OF_WEEK);
		endOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		days = new DayComponent[endOfMonth];
		lineCount = getLine();
	}
	public String getToday(int num) {
		return strDay[num - 1];
	}
	
	/*
	 * 	달력의 라인수 결정
	 * 		시작		일수		라인 
	 * 		금		31		6
	 * 		토		30		6
	 * 		토 		31		6
	*/
	private int getLine() {
		int line = 5;
		
		if(firstDay == Calendar.FRIDAY) {
			if(endOfMonth == 31) {
				line = 6;
			}
		}else if(firstDay == Calendar.SATURDAY) {
			if(endOfMonth >= 30) {
				line = 6;
			}
		}
		return line;
	}
	private void setDisplay() {
		setLayout(new BorderLayout());
		JPanel pnlCal = new JPanel(new GridLayout(lineCount, 7));
		
		int total = lineCount*7;
		
		//시작 빈칸
		for(int i=1;i<firstDay; i++,total--) {
			pnlCal.add(new JLabel());
		}
		//날짜 채우기
		for(int i=1; i<=endOfMonth; i++, total--){
			days[i-1] = new DayComponent(year, month, i);
			pnlCal.add(days[i-1]);
		}
		
		//끝라인 빈칸
		for(int i = 0; i<total;i++) {
			pnlCal.add(new JLabel());
		}
		add(pnlCal, BorderLayout.CENTER);
	}
	private void addListeners() {
		
	}
	
	
}
