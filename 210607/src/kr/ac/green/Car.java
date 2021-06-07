package kr.ac.green;

import java.io.Serializable;

/*
	Serializable, Externalizable 구현해야 직렬화가 가능하다.
*/
public class Car implements Serializable{
	//UID값을 직접 줄 경우 컴파일해도 UID가 바뀌지 않기 때문에 InvalidClassException을 피할 수 있
	//섬세하게 컨트롤해야함. 값이 바뀌어서 문제가 생겨야하는데(예외발생) 모르는 상황이 발생할 수 있다.=돌아가기는 하는데 의미없이 돌아가는 상

	private static final long serialVersionUID = 1;
	//transient : 직렬화에서 배제
	private transient String model;
	private int price;
	private double weight;
	private String company;
	private String desc;
	private Engine engine;
	
	private int abcd;
	
	public Engine getEngine() {
		return engine;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	public Car() {
		
	}
	public Car(String model, int price, double weight, String company, String desc) {
		super();
		this.model = model;
		this.price = price;
		this.weight = weight;
		this.company = company;
		this.desc = desc;
	}
	
	public Car(String model, int price, double weight, String company, String desc, Engine engine) {
		super();
		this.model = model;
		this.price = price;
		this.weight = weight;
		this.company = company;
		this.desc = desc;
		this.engine = engine;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return "Car [price=" + price + ", weight=" + weight + ", company=" + company + ", desc=" + desc + ", engine="
				+ engine + "]";
	}
	
	
	
}
