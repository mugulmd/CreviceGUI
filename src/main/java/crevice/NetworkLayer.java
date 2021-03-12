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

public class NetworkLayer extends JPanel {

	private NetworkCtrl ctrl;

	public NetworkLayer(NetworkCtrl _ctrl) {
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

		Network net = ctrl.getNetwork();
		Iterator<Edge> it = net.edgeIterator();

		Camera cam = ctrl.getApp().getCameraCtrl().getCamera();

		g2d.setColor(Color.BLUE);
		g2d.setStroke(new BasicStroke(3.f));

		while(it.hasNext()) {
			Edge e = it.next();
			if(net.filter(e)) {
				g2d.draw(e.getSegment().applyCam(cam).shape());
			}
		}
	}

}
