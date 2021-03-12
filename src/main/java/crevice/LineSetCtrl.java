package crevice;

import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

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

	private class MouseAddHandle extends MouseInputAdapter {

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
			lines.addLine(tmpLine.applyCamInv(cam));
			interactiveLayer.repaint();
			paintLayer.repaint();
		}
	}

	private class MouseRemoveHandle extends MouseInputAdapter {

		private double epsilon = 10.;

		@Override
		public void mouseClicked(MouseEvent e) {
			app.stageChanged(lines);
			Vec2D vMouse = new Vec2D(e.getX(), e.getY());
			Camera cam = app.getCameraCtrl().getCamera();
			Iterator<LineSegment> it = lines.lineIterator();
			while(it.hasNext()) {
				LineSegment seg = it.next();
				Vec2D vProject = seg.applyCam(cam).project(vMouse);
				if(Vec2D.dist(vMouse, vProject) < epsilon) {
					lines.removeLine(seg);
					break;
				}  
			}
			paintLayer.repaint();
		}
	}

	private MouseInputAdapter mouseAddHandle, mouseRemoveHandle;

	private class SwitchAddAction extends AbstractAction {

		public SwitchAddAction() {
			super("Add lines");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			interactiveLayer.removeMouseListener(mouseRemoveHandle);
			interactiveLayer.addMouseListener(mouseAddHandle);
			interactiveLayer.addMouseMotionListener(mouseAddHandle);
		}
	}

	private class SwitchRemoveAction extends AbstractAction {

		public SwitchRemoveAction() {
			super("Remove lines");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			interactiveLayer.removeMouseListener(mouseAddHandle);
			interactiveLayer.removeMouseMotionListener(mouseAddHandle);
			interactiveLayer.addMouseListener(mouseRemoveHandle);
		}
	}

	public LineSetCtrl(CreviceApp _app, LineSet _lines) {
		super(_app);
		lines = _lines;

		modelPane = new LineSetModelingPane(this);

		paintLayer = new LineSetPaintLayer(this);

		interactiveLayer = new LineSetInteractiveLayer(this);

		mouseAddHandle = new MouseAddHandle();
		mouseRemoveHandle = new MouseRemoveHandle();

		interactiveLayer.addMouseListener(mouseAddHandle);
		interactiveLayer.addMouseMotionListener(mouseAddHandle);

		isDrawing = false;
	}

	public LineSet getLines() {
		return lines;
	}

	@Override
	public JPanel getModelingPane() {
		return modelPane;
	}

	@Override
	public JPanel getPaintLayer() {
		return paintLayer;
	}

	@Override
	public JPanel getInteractiveLayer() {
		return interactiveLayer;
	}

	public Action createSwitchAddAction() {
		return new SwitchAddAction();
	}

	public Action createSwitchRemoveAction() {
		return new SwitchRemoveAction();
	}

}
