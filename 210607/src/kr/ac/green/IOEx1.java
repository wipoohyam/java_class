package kr.ac.green;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class IOEx1 {
	/*
		���� 1���� ��ü 1���� ����.(�迭�� �� ��� ������ ��ü�� �� �� �ִ�)
		-> �����Ͱ� ���׹����� �� ���ɼ��� ���⶧
	*/
	public static void save(Object obj, String path) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(obj);
			oos.flush();
			//�ݵ�� writeObject�� ȣ���� �� = ���� ������Ʈ ������ �������Ѵ�.
			oos.reset();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			MyUtils.closeAll(oos, fos);
		}
	}
	
	public static Object load(String path) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Object obj = null;
		try {
			fis = new FileInputStream(path);
			ois = new ObjectInputStream(fis);
			
			obj = ois.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		}finally {
			MyUtils.closeAll(ois, fis);
		}
		return obj;
	}
	public static void main(String[] args) {
		//NotSerializableException �߻� -> ����ȭ�������� ��ü(Engine)�� �ֱ� ���� -> Engine implements Serializable ������� 
//		Car car = new Car("model", 1000, 100.8, "company", "this is a car", new Engine(2000));
//		Car[] arr = {car, new Car("model2", 1000, 100.8, "company2", "this is a car2", new Engine(1000))};
//		save(arr, "test.dat");
		//*.dat������ �츮���� ����� Class������ �ʿ��ϴ�!!
		System.out.println(Arrays.toString((Car[])load("test.dat")));
	}
}
