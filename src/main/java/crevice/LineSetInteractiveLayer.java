package crevice;

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

public class LineSetInteractiveLayer extends JPanel {

	private LineSetCtrl ctrl;

	public LineSetInteractiveLayer(LineSetCtrl _ctrl) {
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

		if(ctrl.isDrawing) {
			g2d.setColor(Color.RED);
			g2d.setStroke(new BasicStroke(3.f));
			g2d.draw(ctrl.tmpLine.shape());
		}
	}

}
