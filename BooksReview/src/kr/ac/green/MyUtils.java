package kr.ac.green;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

abstract class MyUtils {

	public static final int EMPTYSTAR = 0;
	public static final int HALFSTAR = 1;
	public static final int FILLEDSTAR = 2;
	
	public static void setImgSize(Component c, File imgFile, Dimension d) {
		double dWidth = d.getWidth();
		double dHeight = d.getHeight();
		double iWidth = 1;
		double iHeight = 1;
		ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
		Image img = icon.getImage();
		try {
			BufferedImage bImg = ImageIO.read(imgFile);
			iWidth = (double) bImg.getWidth();
			iHeight = (double) bImg.getHeight();
		} catch(Exception e) {
			e.printStackTrace();
		}
		//가로세로 비율, 1보다 크면 가로로 길다.
		double dRate = dWidth / dHeight;
		double iRate = iWidth / iHeight;
		Image newImg = img;
		if(dRate > iRate) {
			//Dimension이 Image보다 가로로 더 길때->Width = iWidth*비율적용, Height = dHeight
			newImg = img.getScaledInstance((int) (dHeight*iRate), (int) dHeight, 0);
		}else if(dRate < iRate){
			//Dimension이 Image보다 세로로 더 길때->Width = dWidth, Height = iHeight*비율적용
			newImg = img.getScaledInstance((int) dWidth, (int) (dWidth/iRate), 0);
		}else if(dRate == iRate){
			newImg = img.getScaledInstance((int) dWidth, (int) dHeight, 0);
		}
		if(c instanceof JLabel) {
			JLabel lbl = (JLabel) c;
			lbl.setIcon(new ImageIcon(newImg));
		}else if(c instanceof JButton) {
			JButton b = (JButton) c;
			b.setIcon(new ImageIcon(newImg));
		}
	}

	public static void setStarIcon(Component[] bStars, double rate, Dimension dStars) {
		boolean isHalf = false;
		File fFilled = new File("img/star_filled.png");
		File fHalf = new File("img/star_half.png");
		File fEmpty = new File("img/star_empty.png");
		int idx = (int) rate;
		double half = rate - idx;
		if(half==0.5) {
			isHalf = true;
		}
		if(idx<=-1) {
			for(int i = 0; i<5; i++) {
				setImgSize(bStars[i], fEmpty, dStars);
			}
		}else {
			for(int i = 0; i<idx; i++) {
				setImgSize(bStars[i], fFilled, dStars);
			}
			for(int j=4;j>idx;j--) {
				setImgSize(bStars[j], fEmpty, dStars);
			}
			if(isHalf) {
				setImgSize(bStars[idx], fHalf, dStars);
			}
		}
	}
	public static void setDefaultFont(Component[] c) {
		//기본폰트 
		Font defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 13);
		for(Component comp : c) {
			comp.setFont(defaultFont);
		}
	}
	public static void setDefaultFont(Component c) {
		//기본폰트 
		Font defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
		c.setFont(defaultFont);
	}
}
