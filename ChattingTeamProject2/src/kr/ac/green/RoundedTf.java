package kr.ac.green;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class RoundedTf extends JTextField {
	@Override
	protected void paintComponent(Graphics g) {
		if (!isOpaque() && getBorder() instanceof RoundedTfDecorate) {
			Graphics2D g2 = (Graphics2D) g.create();
//			g2.setPaint(Color.WHITE);
			g2.fill(((RoundedTfDecorate) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
			g2.dispose();
		}
		super.paintComponent(g);
	}

	@Override
	public void updateUI() {
		super.updateUI();
		setOpaque(true);
		Border empty = new EmptyBorder(0, 5, 0, 5);
		CompoundBorder border = new CompoundBorder(new RoundedTfDecorate(), empty);
		setBorder(border);
	}
}

class RoundedTfDecorate extends AbstractBorder {
	private static final Color ALPHA_ZERO = new Color(0x0, true);

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape border = getBorderShape(x+1, y+1, width - 3, height - 3);

		g2.setPaint(ALPHA_ZERO);
		// Area corner = new Area(border.getBounds2D());
		Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
		corner.subtract(new Area(border));
		g2.fill(corner);

		g2.setPaint(new Color(230,230,230));
		g2.setStroke(new BasicStroke(2.0f));
		g2.draw(border);
		g2.dispose();
	}

	public Shape getBorderShape(int x, int y, int w, int h) {
		int r = h; // h / 2;
		return new RoundRectangle2D.Double(x, y, w, h, r, r);
	}

	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(4, 8, 4, 8);
	}

	@Override
	public Insets getBorderInsets(Component c, Insets insets) {
		insets.set(4, 8, 4, 8);
		return insets;
	}
}
