package kr.ac.green;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;

import javax.accessibility.Accessible;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.AbstractBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;

public class PnlRoundedComboBox extends JPanel{
	private static final Color BACKGROUND = Color.WHITE;
	private static final Color FOREGROUND = new Color(230, 230, 230);
	private static final Color SELECTIONFOREGROUND = Color.BLACK;
	String[] items;
	String selected;
	public PnlRoundedComboBox(String...items) {
		setLayout(new BorderLayout());
		this.items = items;
		add(makeUI(), BorderLayout.CENTER);
	}
	private JComponent makeUI() {
		UIManager.put("ComboBox.foreground", new Color(0xCCCCCC));
		UIManager.put("ComboBox.background", BACKGROUND);
		UIManager.put("ComboBox.selectionForeground", SELECTIONFOREGROUND);
		UIManager.put("ComboBox.selectionBackground", BACKGROUND);

		UIManager.put("ComboBox.buttonDarkShadow", BACKGROUND);
		UIManager.put("ComboBox.buttonBackground", FOREGROUND);
		UIManager.put("ComboBox.buttonHighlight", FOREGROUND);
		UIManager.put("ComboBox.buttonShadow", FOREGROUND);

		UIManager.put("ComboBox.border", new RoundedCornerBorder());
		JComboBox<String> combo1 = new JComboBox<>(makeModel());
		combo1.setUI(new BasicComboBoxUI());
		Object o = combo1.getAccessibleContext().getAccessibleChild(0);
		if (o instanceof JComponent) {
			JComponent c = (JComponent) o;
			c.setBorder(new RoundedCornerBorder());
			c.setForeground(FOREGROUND);
			c.setBackground(BACKGROUND);
		}
		combo1.addPopupMenuListener(new HeavyWeightContainerListener());

		UIManager.put("ComboBox.border", new RoundedCornerBorder1());

		JPanel p = new JPanel();
		p.add(combo1);
		p.setOpaque(true);
		p.setBackground(Color.WHITE);
		return p;
	}

	private DefaultComboBoxModel<String> makeModel() {
		DefaultComboBoxModel<String> m = new DefaultComboBoxModel<>();
		for(String item : items) {
			m.addElement(item);
		}
		return m;
	}

//	public static void main(String[]
//			args) {
//		EventQueue.invokeLater(() -> {
//			JFrame f = new JFrame();
//			f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//			f.getContentPane().
//			f.setSize(320, 240);
//			f.setLocationRelativeTo(null);
//			f.setVisible(true);
//		});
//	}
}

class HeavyWeightContainerListener implements PopupMenuListener {
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JComboBox combo = (JComboBox) e.getSource();
				Accessible a = combo.getUI().getAccessibleChild(combo, 0);
				if (a instanceof BasicComboPopup) {
					BasicComboPopup pop = (BasicComboPopup) a;
					Container top = pop.getTopLevelAncestor();
					if (top instanceof JWindow) {
						// http://ateraimemo.com/Swing/DropShadowPopup.html
						((JWindow) top).setBackground(new Color(0x0, true));
					}
				}
			}
		});
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	}

	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
	}
}

class RoundedCornerBorder extends AbstractBorder {
	protected static final int ARC = 36;

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int r = ARC;
		int w = width - 1;
		int h = height - 1;

		Area round = new Area(new RoundRectangle2D.Float(x, y, w, h, r, r));
		if (c instanceof JPopupMenu) {
			g2.setPaint(c.getBackground());
			g2.fill(round);
		} else {
			Container parent = c.getParent();
			if (Objects.nonNull(parent)) {
				g2.setPaint(parent.getBackground());
				Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
				corner.subtract(round);
				g2.fill(corner);
			}
		}
		g2.setPaint(new Color(230,230,230));
		g2.setStroke(new BasicStroke(2.0f));
		g2.draw(round);
		g2.dispose();
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

class RoundedCornerBorder1 extends RoundedCornerBorder {
	// http://ateraimemo.com/Swing/RoundedComboBox.html
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int r = ARC;
		int w = width - 1;
		int h = height - 1;

		Area round = new Area(new RoundRectangle2D.Float(x, y, w, h, r, r));
		Rectangle b = round.getBounds();
		b.setBounds(b.x, b.y + r, b.width, b.height - r);
		round.add(new Area(b));

		Container parent = c.getParent();
		if (Objects.nonNull(parent)) {
			g2.setPaint(parent.getBackground());
			Area corner = new Area(new Rectangle2D.Float(x, y, width, height));
			corner.subtract(round);
			g2.fill(corner);
		}

		g2.setPaint(new Color(230,230,230));
		g2.setStroke(new BasicStroke(2.0f));
		g2.draw(round);
		g2.dispose();
	}
}

