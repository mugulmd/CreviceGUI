package crevice;

import javax.swing.JPanel;

/**
 *
 * @author Loïc Vital
 *
 */

public class CameraLayer extends JPanel {

	public CameraLayer(CameraCtrl _ctrl) {
		super();
		setSize(_ctrl.getApp().getCanvasDim());
		setOpaque(false);
	}

}
