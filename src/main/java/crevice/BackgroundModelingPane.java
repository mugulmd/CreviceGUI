package crevice;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Box;

/**
 *
 * @author Loïc Vital
 *
 */

public class BackgroundModelingPane extends JPanel {

	private BackgroundCtrl ctrl;

	private JSlider sliderBrightness;
	private JTextField fieldBrightness;

	public BackgroundModelingPane(BackgroundCtrl _ctrl) {
		super();
		ctrl = _ctrl;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JLabel("Background"));
		add(Box.createVerticalStrut(50));

		buildBrightnessPane();

		add(Box.createVerticalStrut((int)ctrl.getApp().getWindowDim().getHeight()));
	}

	private void buildBrightnessPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Brightness"));
		
		int b = ctrl.getBackground().getBrightness();

		sliderBrightness = new JSlider(0, 255, b);
		panel.add(sliderBrightness);

		fieldBrightness = new JTextField(String.valueOf(b), 3);
		fieldBrightness.setEditable(false);
		panel.add(fieldBrightness);

		add(panel);
	}

	public JSlider getSliderBrightness() {
		return sliderBrightness;
	}

	public JTextField getFieldBrightness() {
		return fieldBrightness;
	}

}
