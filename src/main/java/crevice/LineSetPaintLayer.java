package crevice;

import java.util.Iterator;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.RenderingHints;

/**
 *
 * @author Loïc Vital
 *
 */

public class LineSetPaintLayer extends JPanel {

	private LineSetCtrl ctrl;

	public LineSetPaintLayer(LineSetCtrl _ctrl) {
		super();
		ctrl = _ctrl;
		setOpaque(false);
		setSize(ctrl.getApp().getCanvasDim());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHints(rh);

		LineSet lineSet = ctrl.getLines();
		Iterator<LineSegment> it = lineSet.lineIterator();

		Camera cam = ctrl.getApp().getCameraCtrl().getCamera();

		g2d.setColor(Color.GREEN);
		g2d.setStroke(new BasicStroke(3.f));

		while(it.hasNext()) {
			LineSegment line = it.next();
			g2d.draw(line.shape(cam));
		}
	}

}
