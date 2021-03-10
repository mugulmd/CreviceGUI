package crevice;

import javax.swing.JPanel;

import java.awt.CardLayout;

/**
 *
 * @author Loïc Vital
 *
 */

public class CreviceModelingPane extends JPanel {

	private CreviceWindow window;
	private CardLayout layout;

	public CreviceModelingPane(CreviceWindow _window) {
		super();
		window = _window;
		layout = new CardLayout();
		setLayout(layout);
		add(window.getApp().getBackgroundCtrl().getModelingPane(), ModelingMode.BACKGROUND.getDisplayName());
		add(window.getApp().getSurfaceCtrl().getModelingPane(), ModelingMode.SURFACE.getDisplayName());
		add(window.getApp().getCameraCtrl().getModelingPane(), ModelingMode.CAMERA.getDisplayName());
		add(window.getApp().getLinesCtrl().getModelingPane(), ModelingMode.LINES.getDisplayName());
		add(window.getApp().getNetworkCtrl().getModelingPane(), ModelingMode.NETWORK.getDisplayName());
		add(window.getApp().getSkeletonCtrl().getModelingPane(), ModelingMode.SKELETON.getDisplayName());
		update();
	}

	public void update() {
		layout.show(this, window.getApp().getModelingMode().getDisplayName());
	}

}
