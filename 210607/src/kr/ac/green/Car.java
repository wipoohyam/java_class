package kr.ac.green;

import java.io.Serializable;

/*
	Serializable, Externalizable �����ؾ� ����ȭ�� �����ϴ�.
*/
public class Car implements Serializable{
	//UID���� ���� �� ��� �������ص� UID�� �ٲ��� �ʱ� ������ InvalidClassException�� ���� �� ��
	//�����ϰ� ��Ʈ���ؾ���. ���� �ٲ� ������ ���ܾ��ϴµ�(���ܹ߻�) �𸣴� ��Ȳ�� �߻��� �� �ִ�.=���ư���� �ϴµ� �ǹ̾��� ���ư��� ��

	private static final long serialVersionUID = 1;
	//transient : ����ȭ���� ����
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
