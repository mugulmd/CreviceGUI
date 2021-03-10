package crevice;

import java.util.Iterator;
import java.util.Queue;
import java.util.LinkedList;

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

public class SkeletonPaintLayer extends JPanel {

	private SkeletonCtrl ctrl;

	public SkeletonPaintLayer(SkeletonCtrl _ctrl) {
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

		Camera cam = ctrl.getApp().getCameraCtrl().getCamera();

		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(3.f));

		Skeleton skeleton = ctrl.getSkeleton();
		if(skeleton.isConnected()) {
			if(skeleton.isComplete()) {
				Queue<Edge> queue = new LinkedList<Edge>();
				queue.add(skeleton.getRootEdge());
				while(!queue.isEmpty()) {
					Edge e = queue.poll();
					if(!skeleton.filter(e)) continue;
					g2d.draw(e.getSegment().shape(cam));
					Iterator<Edge> childrenIt = skeleton.childrenIterator(e);
					while(childrenIt.hasNext()) {
						Edge c = childrenIt.next();
						queue.add(c);
					}
				}
			} else {
				Iterator<ParabolaEdge> edgeIt = skeleton.getRootNode().edgeIterator();
				while(edgeIt.hasNext()) {
					Edge e = edgeIt.next();
					g2d.draw(e.getSegment().shape(cam));
				}

				g2d.setColor(Color.RED);
				g2d.draw(skeleton.getRootEdge().getSegment().shape(cam));
			}
		}
	}

}
