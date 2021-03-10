package crevice;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;

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
	}

}
