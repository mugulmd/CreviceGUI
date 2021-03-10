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

public class SkeletonModelingPane extends JPanel {

	private SkeletonCtrl ctrl;

	private JButton buttonChangeRootEdge;
	private JButton buttonExplore;
	private JSlider sliderDepth;
	private JTextField fieldDepth;

	public SkeletonModelingPane(SkeletonCtrl _ctrl) {
		super();
		ctrl = _ctrl;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		add(new JLabel("Skeleton"));

		buttonChangeRootEdge = new JButton(ctrl.createChangeRootEdgeAction());
		add(buttonChangeRootEdge);

		buttonExplore = new JButton(ctrl.createExploreAction());
		add(buttonExplore);

		buildDepthPane();
	}

	private void buildDepthPane() {
		JPanel panel = new JPanel();

		panel.add(new JLabel("Depth"));

		int d = 100;

		sliderDepth = new JSlider(0, 100, d);
		panel.add(sliderDepth);

		fieldDepth = new JTextField(String.valueOf(d), 3);
		fieldDepth.setEditable(false);
		panel.add(fieldDepth);

		add(panel);
	}

	public JSlider getSliderDepth() {
		return sliderDepth;
	}

	public JTextField getFieldDepth() {
		return fieldDepth;
	}

}
