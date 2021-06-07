package kr.ac.green;


import java.io.Closeable;

public class MyUtils {
	public static void closeAll(Closeable... list){
		for(Closeable temp : list){
			try{
				temp.close();
 			} catch(Exception e){}
			
		}
	}
}
