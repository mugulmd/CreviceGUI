package crevice;

import javax.swing.JPanel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Loïc Vital
 *
 */

public class CameraCtrl extends AbstractCtrl implements InteractiveElement {

	private Camera cam;
	private CameraModelingPane modelPane;
	private CameraLayer layer;

	private class SliderZoomHandle implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent e) {
			cam.setZoom(((double)modelPane.getSliderZoom().getValue())/100.0);
			modelPane.getFieldZoom().setText(String.valueOf(cam.getZoom()));
			app.cameraChanged();
		}
	}

	private class MouseHandle extends MouseInputAdapter {

		private Vec2D camPos;
		private int startX, startY, endX, endY;

		@Override
		public void mousePressed(MouseEvent e) {
			camPos = new Vec2D(cam.getOrigin());
			startX = e.getX(); startY = e.getY();
			endX = e.getX(); endY = e.getY();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			endX = e.getX(); endY = e.getY();
			cam.setOrigin(camPos.getX() + (endX-startX), camPos.getY() + (endY-startY));
			app.cameraChanged();
		}
	}

	public CameraCtrl(CreviceApp _app, Camera _cam) {
		super(_app);
		cam = _cam;

		modelPane = new CameraModelingPane(this);
		modelPane.getSliderZoom().addChangeListener(new SliderZoomHandle());

		layer = new CameraLayer(this);
		MouseHandle mouseHandle = new MouseHandle();
		layer.addMouseListener(mouseHandle);
		layer.addMouseMotionListener(mouseHandle);
	}

	public Camera getCamera() {
		return cam;
	}

	@Override
	public JPanel getModelingPane() {
		return modelPane;
	}

	@Override
	public JPanel getInteractiveLayer() {
		return layer;
	}

}
