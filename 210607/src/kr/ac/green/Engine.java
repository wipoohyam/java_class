package kr.ac.green;

import java.io.Serializable;

public class Engine implements Serializable{
	private int cc;

	public int getCc() {
		return cc;
	}

	public void setCc(int cc) {
		this.cc = cc;
	}

	@Override
	public String toString() {
		return "Engine [cc=" + cc + "]";
	}

	public Engine() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Engine(int cc) {
		super();
		this.cc = cc;
		// TODO Auto-generated constructor stub
	}
	
}
