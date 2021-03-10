package crevice;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceCanvas extends JLayeredPane {

	private CreviceWindow window;
	private JPanel glassPanel;

	public CreviceCanvas(CreviceWindow _window) {
		super();

		window = _window;
		setSize(window.getApp().getCanvasDim());

		add(window.getApp().getBackgroundCtrl().getPaintLayer(), new Integer(0));
		add(window.getApp().getSurfaceCtrl().getPaintLayer(), new Integer(1));
		add(window.getApp().getLinesCtrl().getPaintLayer(), new Integer(2));
		add(window.getApp().getNetworkCtrl().getPaintLayer(), new Integer(3));
		add(window.getApp().getSkeletonCtrl().getPaintLayer(), new Integer(4));

		add(window.getApp().getCameraCtrl().getInteractiveLayer(), new Integer(10));
		add(window.getApp().getLinesCtrl().getInteractiveLayer(), new Integer(10));
		add(window.getApp().getSkeletonCtrl().getInteractiveLayer(), new Integer(10));

		glassPanel = new JPanel();
		glassPanel.setSize(getWidth(), getHeight());
		glassPanel.setOpaque(false);
		glassPanel.addMouseListener(new MouseInputAdapter() {});
		add(glassPanel, new Integer(10));
		moveToFront(glassPanel);
	}

	public void update() {
		switch(window.getApp().getModelingMode()) {
			case CAMERA: {
				moveToFront(window.getApp().getCameraCtrl().getInteractiveLayer());
				break;
			}
			case LINES: {
				moveToFront(window.getApp().getLinesCtrl().getInteractiveLayer());
				break;
			}
			case SKELETON: {
				moveToFront(window.getApp().getSkeletonCtrl().getInteractiveLayer());
				break;
			}
			default: {
				moveToFront(glassPanel);
				break;
			}
		}
	}

}
