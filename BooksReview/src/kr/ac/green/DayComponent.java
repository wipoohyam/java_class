package kr.ac.green;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class DayComponent extends JPanel{
	private int year;
	private int month;
	private int dayOfWeek;
	
	private int day;
	private DayKind kind;
	
	private JLabel lbl;
	private TitledBorder tBorder;
	public DayComponent(int year, int month, int day) {
		this.year = year;
		this.month = month+1;
		this.day = day;
		
		Calendar c = Calendar.getInstance();
		c.set(year, month, day);
		dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		
		switch(dayOfWeek) {
			case Calendar.SUNDAY:
				kind = DayKind.SUN;
				break;
			case Calendar.SATURDAY:
				kind = DayKind.SAT;
				break;
			default:
				kind = DayKind.NORMAL;
				break;
		}
		init();
		setDisplay();
		addListeners();
	}
	public int getYear() {
		return year;
	}
	public int getMonth() {
		return month;
	}
	public int getDay() {
		return day;
	}
	private void init() {
		setBackground(Color.WHITE);
		
		tBorder = new TitledBorder(new LineBorder(Color.WHITE, 0), String.valueOf(day));
		lbl = new JLabel("", JLabel.CENTER);
		
		lbl.setBorder(null);
		setBorder(tBorder);
	}
	
	private void setDisplay() {
		setLayout(new BorderLayout());
		add(lbl, BorderLayout.CENTER);
	}
	
	private void addListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent me) {
				setBackground(new Color(0xFAF4C0));
			}
			@Override
			public void mouseExited(MouseEvent me) {
				setBackground(Color.WHITE);
			}
		});
	}
	public Color getDayFontColor() {
		Color color;
		
		switch(kind) {
			case SAT:
				color = Color.BLUE;
				break;
			case SUN:
			case HOLY:
				color = Color.RED;
				break;
			case EVENT:
				color = Color.ORANGE;
				break;
			default:
				color = Color.BLACK;
		}
		return color;
	}
	public void setEventText(String text) {
		lbl.setText(text);
	}
	public String getEventText() {
		return lbl.getText();
	}
	public void setDayFont(Font font) {
		tBorder.setTitleFont(font);
	}
	public Font getDayFont() {
		return tBorder.getTitleFont();
	}
	public void setKind(DayKind kind) {
		this.kind = kind;
	}
	public DayKind getKind() {
		return kind;
	}
}
