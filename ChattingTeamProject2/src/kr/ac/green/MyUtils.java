package kr.ac.green;


import java.awt.Color;
import java.io.Closeable;

public class MyUtils {
	public static final Color GRAY = new Color(240, 240, 240);
	public static void closeAll(Closeable...c) {
		for(Closeable temp : c) {
			try {
				temp.close();
			} catch(Exception e) {}
		}
	}
}
