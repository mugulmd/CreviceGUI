package crevice;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 *
 * @author Loïc Vital
 *
 */

public class NetworkModelingPane extends JPanel {

	private NetworkCtrl ctrl;

	private JSlider sliderOffset;
	private JTextField fieldOffset;
	private JButton buttonBuild;
	private JSlider sliderLength;
	private JTextField fieldLength;
	private JSlider sliderAffineWeight;
	private JTextField fieldAffineWeight;
	private JSlider sliderSupportAngle;
	private JTextField fieldSupportAngle;

	public NetworkModelingPane(NetworkCtrl _ctrl) {
		super();
		ctrl = _ctrl;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JLabel("Affine connexion network"));

		buildOffsetPane();

		buttonBuild = new JButton(_ctrl.createBuildAction());
		add(buttonBuild);

		buildLengthPane();
		buildAffineWeightPane();
		buildSupportAnglePane();
	}

	private void buildOffsetPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Sampling offset"));

		double offset = ctrl.getNetwork().getSamplingOffset();

		sliderOffset = new JSlider(0, 90, (int)(200*offset));
		panel.add(sliderOffset);

		fieldOffset = new JTextField(String.valueOf(offset), 5);
		fieldOffset.setEditable(false);
		panel.add(fieldOffset);

		add(panel);
	}

	private void buildLengthPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Max length"));

		double l = ctrl.getNetwork().getMaxLength();

		sliderLength = new JSlider(0, 1500, (int)l);
		panel.add(sliderLength);

		fieldLength = new JTextField(String.valueOf(l), 5);
		fieldLength.setEditable(false);
		panel.add(fieldLength);

		add(panel);
	}

	private void buildAffineWeightPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Max affine weight"));

		double w = ctrl.getNetwork().getMaxAffineWeight();

		sliderAffineWeight = new JSlider(0, 200, (int)w);
		panel.add(sliderAffineWeight);

		fieldAffineWeight = new JTextField(String.valueOf(w), 5);
		fieldAffineWeight.setEditable(false);
		panel.add(fieldAffineWeight);

		add(panel);
	}

	private void buildSupportAnglePane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Min support angle"));

		double a = ctrl.getNetwork().getMinSupportAngle();

		sliderSupportAngle = new JSlider(0, 100, (int)(100*a/(Math.PI)));
		panel.add(sliderSupportAngle);

		fieldSupportAngle = new JTextField(String.format("%.2f", a), 5);
		fieldSupportAngle.setEditable(false);
		panel.add(fieldSupportAngle);

		add(panel);
	}

	public JSlider getSliderOffset() {
		return sliderOffset;
	}

	public JTextField getFieldOffset() {
		return fieldOffset;
	}

	public JSlider getSliderLength() {
		return sliderLength;
	}

	public JTextField getFieldLength() {
		return fieldLength;
	}

	public JSlider getSliderAffineWeight() {
		return sliderAffineWeight;
	}

	public JTextField getFieldAffineWeight() {
		return fieldAffineWeight;
	}

	public JSlider getSliderSupportAngle() {
		return sliderSupportAngle;
	}

	public JTextField getFieldSupportAngle() {
		return fieldSupportAngle;
	}

}
