package kr.ac.green;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

public class RoundedButton extends JButton {
	public RoundedButton(Color releasedColor, Color pressedColor) {
		setBackground(releasedColor);
		setForeground(Color.WHITE);
		setOpaque(false);
		setLayout(new BorderLayout());
		super.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(pressedColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(releasedColor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}
	public RoundedButton(String label, Color releasedColor, Color pressedColor) {
		setBackground(releasedColor);
		setForeground(Color.WHITE);
		setOpaque(false);
		setLayout(new BorderLayout());
		JLabel lbl = new JLabel(label);

		lbl.setForeground(Color.WHITE);
		lbl.setBorder(new EmptyBorder(0, 0, 2, 0));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl, BorderLayout.CENTER);

		super.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(pressedColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(releasedColor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	public RoundedButton(String label, int currentUser, int maxUser, Color releasedColor, Color pressedColor) {
		setBackground(releasedColor);
		setForeground(Color.WHITE);
		setOpaque(false);
		setLayout(new BorderLayout());
		JLabel lbl = new JLabel(label, JLabel.LEFT);
		JLabel lbl2 = new JLabel("(" + currentUser + " / " + maxUser + " )", JLabel.RIGHT);
		lbl2.setForeground(Color.BLUE);

		lbl.setForeground(Color.BLACK);
		lbl.setBorder(new EmptyBorder(0, 0, 2, 0));
		lbl.setHorizontalAlignment(JLabel.CENTER);
		add(lbl, BorderLayout.WEST);
		add(lbl2, BorderLayout.EAST);

		super.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				setBackground(pressedColor);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				setBackground(releasedColor);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (!isOpaque() && getBorder() instanceof RoundedButtonDecorate) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setPaint(getBackground());
			g2.fill(((RoundedButtonDecorate) getBorder()).getBorderShape(0, 0, getWidth() - 1, getHeight() - 1));
			g2.dispose();
		}
		super.paintComponent(g);
	}

	@Override
	public void updateUI() {
		setBorder(new RoundedButtonDecorate());

		super.updateUI();
	}
}

class RoundedButtonDecorate extends AbstractBorder {
	private static final Color ALPHA_ZERO = new Color(0x0, true);

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Shape border = getBorderShape(x + 1, y + 1, width - 3, height - 3);

		g2.setPaint(ALPHA_ZERO);
		// Area corner = new Area(border.getBounds2D());
		Area corner = new Area(new Rectangle2D.Double(x, y, width, height));
		corner.subtract(new Area(border));
		g2.fill(corner);

		g2.setPaint(new Color(230, 230, 230));
		g2.setStroke(new BasicStroke(0f));
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
