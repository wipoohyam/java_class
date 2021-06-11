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
		//���μ��� ����, 1���� ũ�� ���η� ���.
		double dRate = dWidth / dHeight;
		double iRate = iWidth / iHeight;
		if(dRate > iRate) {
			//Dimension�� Image���� ���η� �� �涧->Width = iWidth, Height = dHeight
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
