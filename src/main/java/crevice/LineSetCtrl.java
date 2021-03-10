package crevice;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Loïc Vital
 *
 */

public class LineSetCtrl extends AbstractCtrl implements PaintedElement, InteractiveElement {

	private LineSet lines;
	private LineSetModelingPane modelPane;
	private LineSetPaintLayer paintLayer;
	private LineSetInteractiveLayer interactiveLayer;

	boolean isDrawing;
	LineSegment tmpLine;

	private class MouseHandle extends MouseInputAdapter {

		private Vec2D vStart, vEnd;

		@Override
		public void mousePressed(MouseEvent e) {
			app.stageChanged(lines);
			isDrawing = true;
			vStart = new Vec2D(e.getX(), e.getY());
			vEnd = new Vec2D(e.getX(), e.getY());
			tmpLine = new LineSegment(vStart, vEnd);
			interactiveLayer.repaint();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			vEnd.setX(e.getX());
			vEnd.setY(e.getY());
			interactiveLayer.repaint();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			isDrawing = false;
			Camera cam = app.getCameraCtrl().getCamera();
			lines.addLine(new LineSegment(cam.applyInvTo(vStart), cam.applyInvTo(vEnd)));
			interactiveLayer.repaint();
			paintLayer.repaint();
		}
	}

	public LineSetCtrl(CreviceApp _app, LineSet _lines) {
		super(_app);
		lines = _lines;

		modelPane = new LineSetModelingPane(this);

		paintLayer = new LineSetPaintLayer(this);

		interactiveLayer = new LineSetInteractiveLayer(this);
		MouseHandle mouseHandle = new MouseHandle();
		interactiveLayer.addMouseListener(mouseHandle);
		interactiveLayer.addMouseMotionListener(mouseHandle);

		isDrawing = false;
	}

	public LineSet getLines() {
		return lines;
	}

	@Override
	public JPanel getModelingPane() {
		return modelPane;
	}

	// TODO

	@Override
	public JPanel getPaintLayer() {
		return paintLayer;
	}

	@Override
	public JPanel getInteractiveLayer() {
		return interactiveLayer;
	}

}
