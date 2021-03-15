package crevice;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Box;

/**
 *
 * @author Loïc Vital
 *
 */

public class CameraModelingPane extends JPanel {

	private CameraCtrl ctrl;

	private JSlider sliderZoom;
	private JTextField fieldZoom;

	public CameraModelingPane(CameraCtrl _ctrl) {
		super();
		ctrl = _ctrl;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JLabel("Camera"));
		add(Box.createVerticalStrut(50));

		buildZoomPane();

		add(Box.createVerticalStrut((int)ctrl.getApp().getWindowDim().getHeight()));
	}

	private void buildZoomPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Zoom"));

		double z = ctrl.getCamera().getZoom();

		sliderZoom = new JSlider(10, 200, (int)(100*z));
		panel.add(sliderZoom);

		fieldZoom = new JTextField(String.valueOf(z), 3);
		fieldZoom.setEditable(false);
		panel.add(fieldZoom);

		add(panel);
	}

	public JSlider getSliderZoom() {
		return sliderZoom;
	}

	public JTextField getFieldZoom() {
		return fieldZoom;
	}

}
