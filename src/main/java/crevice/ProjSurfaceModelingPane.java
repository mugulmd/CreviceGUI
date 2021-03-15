package crevice;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.Box;

/**
 *
 * @author Loïc Vital
 *
 */

public class ProjSurfaceModelingPane extends JPanel {

	public ProjSurfaceModelingPane(ProjSurfaceCtrl _ctrl) {
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		add(new JLabel("Projection Surface"));
		
		add(Box.createVerticalStrut((int)_ctrl.getApp().getWindowDim().getHeight()));
	}

}
