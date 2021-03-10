package crevice;

import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

/**
 *
 * @author Loïc Vital
 *
 */

public class SkeletonCtrl extends AbstractCtrl implements PaintedElement, InteractiveElement {

	private Skeleton skeleton;
	private SkeletonModelingPane modelPane;
	private SkeletonPaintLayer paintLayer;
	private SkeletonInteractiveLayer interactiveLayer;

	boolean isDrawing;
	LineSegment tmpLine;

	private class MouseHandle extends MouseInputAdapter {

		private Vec2D vStart, vEnd;

		@Override
		public void mousePressed(MouseEvent e) {
			app.stageChanged(skeleton);
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
			Vec2D a = cam.applyInvTo(vStart);
			Vec2D b = cam.applyInvTo(vEnd);
			AffineElement elt = new AffineElement(a, Vec2D.sub(b, a));
			Network net = app.getProject().getNetwork();
			skeleton.connect(net, elt);
			interactiveLayer.repaint();
			paintLayer.repaint();
		}
	}

	private class ChangeRootEdgeAction extends AbstractAction {

		public ChangeRootEdgeAction() {
			super("Change root edge");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(skeleton.isConnected()) {
				app.stageChanged(skeleton);
				skeleton.changeRootEdge();
				paintLayer.repaint();
			}
		}
	}

	private class ExploreAction extends AbstractAction {

		public ExploreAction() {
			super("Explore network");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(skeleton.isConnected()) {
				app.stageChanged(skeleton);
				Network net = app.getProject().getNetwork();
				skeleton.explore(net);
				paintLayer.repaint();
			}
		}
	}

	private class SliderDepthHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			if(skeleton.isComplete()) {
				app.stageChanged(skeleton);
				skeleton.setMaxDepth(modelPane.getSliderDepth().getValue());
				modelPane.getFieldDepth().setText(String.valueOf(skeleton.getMaxDepth()));
				paintLayer.repaint();
			}
		}
	}

	public SkeletonCtrl(CreviceApp _app, Skeleton _skeleton) {
		super(_app);
		skeleton = _skeleton;

		modelPane = new SkeletonModelingPane(this);
		modelPane.getSliderDepth().addChangeListener(new SliderDepthHandle());

		paintLayer = new SkeletonPaintLayer(this);

		interactiveLayer = new SkeletonInteractiveLayer(this);
		MouseHandle mouseHandle = new MouseHandle();
		interactiveLayer.addMouseListener(mouseHandle);
		interactiveLayer.addMouseMotionListener(mouseHandle);

		isDrawing = false;
	}

	public Skeleton getSkeleton() {
		return skeleton;
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

	public Action createChangeRootEdgeAction() {
		return new ChangeRootEdgeAction();
	}

	public Action createExploreAction() {
		return new ExploreAction();
	}

}
