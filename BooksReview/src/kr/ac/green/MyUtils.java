package kr.ac.green;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

abstract class MyUtils {
	public static Image setImgSize(String path, Dimension d) {
		double dWidth = d.getWidth();
		double dHeight = d.getHeight();
		double iWidth = 1;
		double iHeight = 1;
		ImageIcon icon = new ImageIcon(path);
		Image img = icon.getImage();
		try {
			BufferedImage bImg = ImageIO.read(new File(path));
			iWidth = (double) bImg.getWidth();
			iHeight = (double) bImg.getHeight();
		} catch(Exception e) {
			e.printStackTrace();
		}
		//가로세로 비율, 1보다 크면 가로로 길다.
		double dRate = dWidth / dHeight;
		double iRate = iWidth / iHeight;
		if(dRate > iRate) {
			//Dimension이 Image보다 가로로 더 길때->Width = iWidth, Height = dHeight
			Image newImg = img.getScaledInstance((int) iWidth, (int) dHeight, 0);
			return newImg;
		}else if(dRate == iRate){
			Image newImg = img.getScaledInstance((int) dWidth, (int) dHeight, 0);
			return newImg;
		}else {
			Image newImg = img.getScaledInstance((int) dWidth, (int) iHeight, 0);
			return newImg;
		}
	}
}
