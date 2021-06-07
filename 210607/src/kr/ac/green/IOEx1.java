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
		파일 1개당 객체 1개만 쓴다.(배열을 쓸 경우 복수의 객체를 쓸 수 있다)
		-> 데이터가 뒤죽박죽이 될 가능성이 높기때
	*/
	public static void save(Object obj, String path) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = new FileOutputStream(path);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(obj);
			oos.flush();
			//반드시 writeObject후 호출할 것 = 남은 오브젝트 정보를 지워야한다.
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
		//NotSerializableException 발생 -> 직렬화되지않은 객체(Engine)이 있기 때문 -> Engine implements Serializable 해줘야함 
//		Car car = new Car("model", 1000, 100.8, "company", "this is a car", new Engine(2000));
//		Car[] arr = {car, new Car("model2", 1000, 100.8, "company2", "this is a car2", new Engine(1000))};
//		save(arr, "test.dat");
		//*.dat파일을 살리려면 당시의 Class파일이 필요하다!!
		System.out.println(Arrays.toString((Car[])load("test.dat")));
	}
}
