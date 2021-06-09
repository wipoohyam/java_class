package kr.ac.green;

import java.awt.Dimension;
import java.awt.Point;
import java.io.Serializable;

public class CounterState implements Serializable{
	String num;
	private Point location;
	private Dimension size;
	public CounterState() {
		
	}
	public CounterState(String num, Point location, Dimension size) {
		super();
		this.num = num;
		this.location = location;
		this.size = size;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public Dimension getSize() {
		return size;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "CounterState [num=" + num + ", location=" + location + ", size=" + size + "]";
	}
}

